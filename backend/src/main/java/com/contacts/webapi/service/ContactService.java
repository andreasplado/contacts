package com.contacts.webapi.service;

import com.contacts.webapi.dao.entity.ContactEntity;
import com.contacts.webapi.respository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService implements IContactService {

    @Autowired
    private ContactRepository repository;

    @Override
    public List<ContactEntity> getAllContacts(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<ContactEntity> pagedResult = repository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public ContactEntity save(ContactEntity contactEntity) {
        return repository.save(contactEntity);
    }
    @Override
    public Optional<ContactEntity> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<ContactEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public ContactEntity update(ContactEntity contactEntity) {
        System.out.println(contactEntity.getName());
        if(repository.existsById(contactEntity.getId())){
            repository.save(contactEntity);
        }
        return contactEntity;
    }

    @Override
    public List<ContactEntity> findByVaues(String name, String phone) {
        return repository.findByName(name);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

}