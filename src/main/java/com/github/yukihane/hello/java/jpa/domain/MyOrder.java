package com.github.yukihane.hello.java.jpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import org.hibernate.envers.Audited;

@Audited
@Entity
public class MyOrder {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    private String name;

    @ManyToOne
    private Product product;

    public MyOrder() {
    }

    public MyOrder(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product
     *            the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

}
