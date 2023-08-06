package com.contacts.webapi.respository;

import com.contacts.webapi.dao.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface ContactPagingRepository extends PagingAndSortingRepository<ContactEntity, Long> {
}