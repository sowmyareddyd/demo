package com.fido.demo.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Entity
@Table(name = "ATTESTATION_FORMATS")
@Data // Generates getters, setters, toString, etc. (Requires Lombok)
@NoArgsConstructor
@AllArgsConstructor
public class AttestationFormatEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "formate_name")
    private String format;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Time createdAt;

    @Column(name = "updated_at")
    private Time updatedAt;
}
