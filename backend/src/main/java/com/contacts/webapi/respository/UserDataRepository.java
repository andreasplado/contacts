package com.contacts.webapi.respository;

import com.contacts.webapi.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserDataRepository extends JpaRepository<UserEntity, Integer> {
    @Query(value="SELECT CASE WHEN COUNT(u)> 0 then true else false end FROM users u WHERE u.username=?1", nativeQuery = true)
    boolean existsByUsername(@Param("username") String username);

    @Query(value="SELECT u.* FROM users u WHERE u.username=?1", nativeQuery = true)
    UserEntity findByUsername(@Param("username") String username);

    @Query(value="SELECT u.* FROM users u WHERE u.id=?1", nativeQuery = true)
    UserEntity findSingleById(@Param("id") Integer id);

    @Query(value="SELECT CASE WHEN COUNT(u)> 0 then true else false end FROM users u WHERE u.account_email=?1 AND u.contact=?2", nativeQuery = true)
    boolean emailAndPhoneNumberMatches(@Param("account_email") String accountEmail, @Param("contact") String contact);

    @Query(value="SELECT u.id FROM users u WHERE u.username=?1", nativeQuery = true)
    int findIdByUsername(@Param("username") String username);

    @Query(value="SELECT u.* FROM users u WHERE u.id=?1", nativeQuery = true)
    UserEntity findUserById(@Param("id") Integer userId);

    @Query(value="SELECT u.firebase_token FROM users u WHERE u.id=?1", nativeQuery = true)
    String getUserFirebaseToken(@Param("id") Integer userId);

    @Modifying
    @Transactional
    @Query(value="UPDATE users SET firebase_token=?1 WHERE id=?2", nativeQuery = true)
    void updateUserFirebaseToken(@Param("firebasetoken") String firebaseToken, @Param("id") Integer userId);

    @Modifying
    @Transactional
    @Query(value="UPDATE users SET role=?1 WHERE id=?2", nativeQuery = true)
    void updateUserRole(@Param("role") String role, @Param("id") Integer id);


    @Modifying
    @Transactional
    @Query(value="UPDATE settings SET role=?1 WHERE id=?2", nativeQuery = true)
    void setMemberRole(@Param("role") String role, @Param("id") Integer id);

    @Query(value="SELECT u.role FROM users u WHERE u.id=?1", nativeQuery = true)
    String getMemberRole(@Param("id") Integer id);

    @Query(value="SELECT u.password FROM users u WHERE u.account_email=?1", nativeQuery = true)
    String getUserPassword(@Param("id") String username);

    @Query(value="SELECT CASE WHEN COUNT(u)> 0 then true else false end FROM users u WHERE u.account_email=?1 AND u.password=?2", nativeQuery = true)
    boolean userAuthenticated(@Param("username") String username, @Param("password") String password);

}