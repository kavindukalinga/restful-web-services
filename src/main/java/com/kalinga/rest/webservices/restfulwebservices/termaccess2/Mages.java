package com.kalinga.rest.webservices.restfulwebservices.termaccess;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PostLoad;

@Entity
public class Mages {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @Transient
    private String decryptedName;

    public void setName(String name) throws Exception {
        this.decryptedName = name;
        this.name = EncryptionService.encryptData(name, "kavindukalingayu@gmail.com");
    }

    public String getName() throws Exception {
        return EncryptionService.decryptData(this.name, "kk.txt");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Mages() {
    }

    public Mages(String name) throws Exception {
        setName(name);
    }

    public Mages(Integer id, String name) throws Exception {
        this.id = id;
        setName(name);
    }

    @PrePersist
    public void encryptName() throws Exception {
        if (decryptedName != null) {
            this.name = EncryptionService.encryptData(decryptedName, "kavindukalingayu@gmail.com");
        }
    }

    @PostLoad
    public void decryptName() throws Exception {
        this.decryptedName = EncryptionService.decryptData(this.name, "kk.txt");
    }

    @Override
    public String toString() {
        return "Mages{" +
                "id=" + id +
                ", name='" + decryptedName + '\'' +
                '}';
    }
}
