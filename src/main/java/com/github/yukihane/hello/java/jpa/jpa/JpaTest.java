package com.github.yukihane.hello.java.jpa.jpa;

import com.github.yukihane.hello.java.jpa.domain.MyOrder;
import com.github.yukihane.hello.java.jpa.domain.Product;
import com.github.yukihane.hello.java.jpa.domain.Product_;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class JpaTest {

    @FunctionalInterface
    public interface Executable {
        void exec(EntityManager em);
    }

    public static void main(String[] args) {

        new JpaTest().exec();
    }

    private void exec() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("h2");

        try {

            EntityManager em = factory.createEntityManager();
            try {
                EntityTransaction tx = em.getTransaction();
                tx.begin();
                insertProducts(em);
                insertOrders(em);
                tx.commit();

                EntityTransaction tx2 = em.getTransaction();
                tx2.begin();
                updateProduct(em);
                tx2.commit();
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

    private void insertOrders(EntityManager em) {

        Product p1 = findProduct(em, "test1");

        MyOrder o1 = new MyOrder("order1");
        o1.setProduct(p1);
        em.persist(o1);

        MyOrder o2 = new MyOrder("order2");
        o2.setProduct(p1);
        em.persist(o2);
    }

    /**
     * @param em
     * @return
     */
    private Product findProduct(EntityManager em, String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery <Product> query = cb.createQuery(Product.class);
        Root <Product> r = query.from(Product.class);
        query.select(r);
        query.where(cb.equal(r.get(Product_.name), name));
        Product p1 = em.createQuery(query).getSingleResult();
        return p1;
    }

    private void updateProduct(EntityManager em) {
        Product p1 = findProduct(em, "test1");
        p1.setName("test11");
        em.persist(p1);
    }
}
