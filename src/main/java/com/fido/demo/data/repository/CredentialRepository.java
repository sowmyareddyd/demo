package com.fido.demo.data.repository;

import com.fido.demo.data.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<CredentialEntity, Integer> {
    CredentialEntity findById(int id);
    CredentialEntity findByUserId(int userId);
    CredentialEntity findByRpAndUserId(int rpId, int userId);
}
