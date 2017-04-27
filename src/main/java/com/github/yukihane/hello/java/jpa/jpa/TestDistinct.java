package com.github.yukihane.hello.java.jpa.jpa;

import com.github.yukihane.hello.java.jpa.domain.Department;
import com.github.yukihane.hello.java.jpa.domain.Department_;
import com.github.yukihane.hello.java.jpa.domain.Employee;
import com.github.yukihane.hello.java.jpa.domain.Employee_;
import com.github.yukihane.hello.java.jpa.util.Logic;
import com.github.yukihane.hello.java.jpa.util.TransactionalExcecutor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TestDistinct {

    public static void main(final String[] args) {
        final EntityManagerFactory factory = Persistence.createEntityManagerFactory("h2");

        try {
            final TransactionalExcecutor executor = new TransactionalExcecutor(factory);

            executor.exec(new Insert());
            final List<Department> res = executor.exec(new Select());

            System.out.println(res.size());
            res.stream().map(e -> e.getId()).forEach(e -> System.out.println("ID: " + e));

        } finally {
            factory.close();
        }
    }

    public static class Insert implements Logic<Void> {
        @Override
        public Void execute(final EntityManager em) throws Exception {
            final Department dep = new Department("department");
            em.persist(dep);

            final Employee emp1 = new Employee("emp1", dep);
            final Employee emp2 = new Employee("emp2", dep);
            em.persist(emp1);
            em.persist(emp2);

            return null;
        }
    }

    public static class Select implements Logic<List<Department>> {

        @Override
        public List<Department> execute(final EntityManager em) throws Exception {

            // http://docs.oracle.com/javaee/6/tutorial/doc/gjivm.html
            // あたりを参考に

            // CriteriaBuilder インスタンスを生成します.
            final CriteriaBuilder cb = em.getCriteriaBuilder();

            // CriteriaQuery インスタンスを生成します.
            // ここで引数に指定するのは, クエリ実行結果として期待する型です.
            final CriteriaQuery<Department> cq = cb.createQuery(Department.class);

            // FROM句で指定する情報相当のものをセットします.
            // クエリルートインスタンスが生成されます.
            final Root<Department> department = cq.from(Department.class);

            final ListJoin<Department, Employee> employees = department.join(Department_.employees);
            final Predicate p1 = cb.equal(employees.get(Employee_.name), "emp1");
            final Predicate p2 = cb.equal(employees.get(Employee_.name), "emp2");
            final Predicate p3 = cb.notEqual(employees.get(Employee_.name), "emp1");

            // where で条件を設定します.
            cq.where(cb.or(p1, p2, p3));

            cq.distinct(true);

            // クライテリアクエリが完成したら, そのインスタンスからクエリを生成します.
            final TypedQuery<Department> q = em.createQuery(cq);

            return q.getResultList();
        }
    }
}
