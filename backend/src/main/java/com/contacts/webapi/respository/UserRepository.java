package com.contacts.webapi.respository;

import com.contacts.webapi.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Query(value="SELECT u.* FROM users u WHERE u.username=?1", nativeQuery = true)
    List<UserEntity> findByUsername(String username);

    @Query(value="SELECT CASE WHEN EXISTS(u.* FROM users u WHERE u.username=1?) THEN 'TRUE' ELSE 'FALSE' END", nativeQuery = true)
    boolean existsByUsername(String username);
}
