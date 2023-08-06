package com.contacts.webapi.respository;

import com.contacts.webapi.dao.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Integer> {
    @Query(value="SELECT c.* FROM contacts c WHERE c.name LIKE %?1%", nativeQuery = true)
    List<ContactEntity> findByName(String name);
}