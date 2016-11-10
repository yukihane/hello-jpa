package com.github.yukihane.hello.java.jpa.jpa;

import com.github.yukihane.hello.java.jpa.domain.Product;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaTest {

    public static void main(String[] args) {

        new JpaTest().exec();
    }

    private void exec() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("mypu");

        try {

            EntityManager em = factory.createEntityManager();
            try {
                EntityTransaction tx = em.getTransaction();
                tx.begin();
                insertProducts(em);
                tx.commit();
            } finally {
                em.close();
            }

        } finally {
            factory.close();
        }
    }

    private void insertProducts(EntityManager em) {
        Product p1 = new Product("test1");
        Product p2 = new Product("test2");
        em.persist(p1);
        em.persist(p2);
    }

}
