package com.github.yukihane.hello.java.jpa.util;

import javax.persistence.EntityManager;

/**
 * {@link TransactionalExcecutor} で実行するロジック.
 *
 * @param <V>
 *            実行結果の型
 */
@FunctionalInterface
public interface Logic<V> {

    /**
     * トランザクション内でロジックを実行します.
     *
     * @param em
     *            エンティティマネージャ. persist等はこれを用います.
     * @return ロジック実行結果.
     * @throws Exception
     *             例外を送出した場合, ロールバックされます.
     */
    V execute(EntityManager em) throws Exception;

}
