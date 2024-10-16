package com.fido.demo.data.repository;

import org.springframework.stereotype.Repository;
import com.fido.demo.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
    UserEntity findById(int id);
}
