package com.github.yukihane.hello.java.jpa.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "child_table")
@Entity
public class Child implements Serializable {
    private static final long serialVersionUID = 1718966005562173513L;
    @EmbeddedId
    private ChildKey childKey = new ChildKey();
    @Column(name = "child_name")
    private String childName;
    @ManyToOne
    @JoinColumns({ @JoinColumn(name = "join_id", referencedColumnName = "join_id"),
            @JoinColumn(name = "parent_id", referencedColumnName = "parent_id") })
    private Parent parent;

    /** getter,setter省略 **/

    @Embeddable
    public static class ChildKey implements Serializable {
        private static final long serialVersionUID = -3848325403357861609L;
        @Column(name = "child_id")
        String childId;
        @Column(name = "join_id")
        String joinId;
    }

    /** equals,hashCode省略 **/
}