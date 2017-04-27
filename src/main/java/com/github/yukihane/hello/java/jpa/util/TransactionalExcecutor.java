package com.github.yukihane.hello.java.jpa.util;

import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 * 1つのEntityManagerの中で一連のトランザクションを実行します.
 */
public class TransactionalExcecutor {

    private final EntityManagerFactory emf;

    /**
     * 使用するエンティティマネジャファクトリを指定します.
     *
     * @param emf
     *            使用するエンティティマネジャファクトリ
     */
    public TransactionalExcecutor(final EntityManagerFactory emf) {
        this.emf = Objects.requireNonNull(emf);
    }

    /**
     * トランザクションを実行します. 失敗した場合はロールバックします.
     *
     * @param logic
     *            実行するロジック.
     * @return ロジック戻り値.
     * @param <V>
     *            ロジック戻り値の型
     */
    public <V> V exec(final Logic<V> logic) {

        final EntityManager em = emf.createEntityManager();
        try {
            return doTransaction(em, logic);
        } finally {
            em.close();
        }

    }

    private <V> V doTransaction(final EntityManager em, final Logic<V> logic) {
        final EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            final V ret = logic.execute(em);
            tx.commit();
            return ret;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
