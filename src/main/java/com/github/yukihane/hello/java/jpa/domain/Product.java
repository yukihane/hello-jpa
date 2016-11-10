package com.github.yukihane.hello.java.jpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "product", schema = "myschema")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    private String name;

    public Product() {
    }

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
