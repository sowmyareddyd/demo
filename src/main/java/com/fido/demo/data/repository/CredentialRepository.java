package com.fido.demo.data.repository;

import com.fido.demo.data.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CredentialRepository extends JpaRepository<CredentialEntity, Integer> {
    
}
