package com.contacts.webapi.controller;

import com.contacts.webapi.dao.entity.ContactEntity;
import com.contacts.webapi.model.FileEncryptionResponse;
import com.contacts.webapi.model.ResponseModel;
import com.contacts.webapi.service.ContactService;
import com.contacts.webapi.service.FilesStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/contact")
public class ContactsController {

    private static String KEY_CONTACTS = "contacts";

    @Autowired
    private ContactService contactService;

    @Autowired
    FilesStorageServiceImpl storageService;

    @GetMapping
    @RequestMapping(value = "/few", method = RequestMethod.GET)
    public ResponseEntity<List<ContactEntity>> getAllContacts(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy)
    {
        List<ContactEntity> contacts = contactService.getAllContacts(pageNo, pageSize, sortBy);

        return new ResponseEntity<>(contacts, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        List<ContactEntity> contacts = contactService.findAll();

        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<?> getByPublicValues(@RequestParam(defaultValue = "name") String name,
                                       @RequestParam(defaultValue = "phone") String phone) {
        List<ContactEntity> contacts = contactService.findByVaues(name, phone);

        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable("id") Integer id) {

        Optional<ContactEntity> contact = contactService.findById(id);
        return ResponseEntity.ok(contact);

    }

    @PostMapping("/unhash-secret")
    public ResponseEntity<FileEncryptionResponse> uploadFile(@RequestParam("file") MultipartFile file,
                                                             @RequestParam("hashed-secret") String hashedSecret) {
        String message = "";
        try {
            storageService.save(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            String filename = file.getName();
            Scanner myReader = new Scanner((Readable) file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
            FileEncryptionResponse  fileEncryptionResponse = new FileEncryptionResponse();
            fileEncryptionResponse.setToken(filename);
            return ResponseEntity.status(HttpStatus.OK).body(fileEncryptionResponse);
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileEncryptionResponse());
        }
    }

    @RequestMapping(path = "/hash-secret", method = RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> download(@RequestParam("secret") String secret,
                                                      @RequestParam("name") String name) throws IOException {
        File file = null;

        String fileContent = Arrays.toString(Base64.getEncoder().encode(secret.getBytes()));
        try {
            file = new File("salajane_voti.secret");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cryptedWord.txt");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        Path path = Paths.get(file.getAbsolutePath());
        Files.writeString(path, fileContent);
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody ContactEntity contactEntity) {
        contactService.save(contactEntity);
        return ResponseEntity.ok(contactEntity);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateContact(@RequestBody ContactEntity contactEntity) {
        contactService.update(contactEntity);
        return ResponseEntity.ok(contactEntity);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {

            Optional<ContactEntity> contactEntity = contactService.findById(id);
            contactService.delete(id);

            return ResponseEntity.ok(contactEntity);
    }


    @RequestMapping(value = "deleteall", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteaLL() {
        contactService.deleteAll();
        ResponseModel responseModel = new ResponseModel();
        responseModel.setMessage("Sa oled kõik kontaktid edukalt kustutanud!");

        return ResponseEntity.ok(responseModel);
    }
}