package com.contacts.webapi.service;

import com.contacts.webapi.dao.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<UserEntity> findAll();
    UserEntity save(UserEntity userEntity);
    UserEntity update(UserEntity userEntity);
    void delete(Integer id);
    Optional<UserEntity> findById(Integer id);
    boolean exists(Integer id);
    boolean phoneNumberAndEmailMatches(String accountEmail, String phoneNumber);
    boolean existByEmail(String googleAccountId);
    UserEntity saveUser(UserEntity userEntity);
    UserEntity findByUsername(String email);
    UserEntity findUserById(Integer id);
    String getUserFirebaseToken(Integer id);
    int findIdByUsername(String username);
    void updateUserFirebaseToken(String firebaseToken, Integer id);
    void updateUserRole(String role, Integer id);
    String memberRole(Integer id);
    boolean tokenExists();
    void setMemberRole(Integer id, String memberRole);
    boolean userAuthenticated(String username, String password);
}