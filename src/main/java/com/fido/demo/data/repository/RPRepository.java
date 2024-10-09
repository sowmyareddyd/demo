package com.fido.demo.data.repository;


import com.fido.demo.data.entity.RelyingPartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RPRepository extends JpaRepository<RelyingPartyEntity, Long> {
    // Additional query methods can be defined here
    RelyingPartyEntity findById(int id);
    RelyingPartyEntity findByRpId(int rpId);
}
