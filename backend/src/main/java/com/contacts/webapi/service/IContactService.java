package com.contacts.webapi.service;

import com.contacts.webapi.dao.entity.ContactEntity;

import java.util.List;
import java.util.Optional;

public interface IContactService {

    List<ContactEntity> getAllContacts(Integer pageNo, Integer pageSize, String sortBy);

    ContactEntity save (ContactEntity contactEntity);
    Optional<ContactEntity> findById(Integer id);
    List<ContactEntity> findAll();

    ContactEntity update(ContactEntity contactEntity);

    List<ContactEntity> findByVaues(String name, String Phone);

    void delete(Integer id);
    void deleteAll();
}
