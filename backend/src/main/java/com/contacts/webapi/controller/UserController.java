package com.contacts.webapi.controller;

import com.contacts.webapi.dao.entity.UserEntity;
import com.contacts.webapi.model.Note;
import com.contacts.webapi.model.ResponseModel;
import com.contacts.webapi.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDataService userDataService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(BCryptPasswordEncoder bCryptPasswordEncoder){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<UserEntity> userEntities = userDataService.findAll();

        if (userEntities != null && userEntities.isEmpty()) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("Users not found!");
            return ResponseEntity.ok(responseModel);
        }

        return ResponseEntity.ok(userEntities);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String userId){
        Note note = new Note();
        note.setMessage("You logged out successfully");
        new InMemoryTokenStore().findTokensByClientId(userId).clear();
        return ResponseEntity.ok(note);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody UserEntity userEntity) {
        if(userDataService.findByUsername(userEntity.getUsername()) == null){
            userDataService.save(userEntity);
        }

        userEntity = userDataService.findByUsername(userEntity.getUsername());

        return ResponseEntity.ok(userEntity);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserEntity user)
    {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        if(userDataService.findByUsername(user.getUsername()) == null){
            userDataService.save(user);

            int userId = userDataService.findByUsername(user.getUsername()).getId();
        }else{
            Note note = new Note();
            note.setMessage("User with this username already exists!");
            return ResponseEntity.ok(note);
        }
        user = userDataService.findByUsername(user.getUsername());

        return ResponseEntity.ok(user);
    }

    @CrossOrigin(origins = "http://localhost:8082")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UserEntity userEntity) {

        if (!userDataService.exists(id)) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("User already exists!");
            return ResponseEntity.ok(responseModel);
        }
        userDataService.update(userEntity);

        return ResponseEntity.ok(userEntity);
    }


    @RequestMapping(value = "/update-role", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRole(@RequestParam String userRole, @RequestParam Integer id) {
        if (!userDataService.exists(id)) {
            return ResponseEntity.ok("");
        }

        userDataService.updateUserRole(userRole, id);

        return ResponseEntity.ok(userRole);
    }


    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        Optional<UserEntity> userEntity = userDataService.findById(id);

        if (!userEntity.isPresent()) {
            ResponseModel responseModel = new ResponseModel();
            responseModel.setMessage("User not found!");
            return ResponseEntity.ok(responseModel);
        }

        userDataService.delete(id);
        return ResponseEntity.ok(userEntity);
    }
    @RequestMapping(value = "/does-exists", method = RequestMethod.GET)
    public ResponseEntity<Boolean> getMyApplications(@RequestParam String email) {
        boolean userExists = userDataService.existByEmail(email);
        return ResponseEntity.ok(userExists);
    }
}