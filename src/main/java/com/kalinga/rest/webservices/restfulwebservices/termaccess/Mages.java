package com.kalinga.rest.webservices.restfulwebservices.termaccess;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;

@Entity
public class Mages {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name="name", columnDefinition = "bytea")
    @ColumnTransformer(
            read = "pgp_pub_decrypt(name,dearmor(pg_read_file('keys/private.key')),'kalinga')",
            write = "pgp_pub_encrypt(?,dearmor(pg_read_file('keys/public.key')))"
   )
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mages() {
    }

    public Mages(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Mages{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
