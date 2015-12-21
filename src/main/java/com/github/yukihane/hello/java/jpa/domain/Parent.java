package com.github.yukihane.hello.java.jpa.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "parent_table")
@Entity
public class Parent implements Serializable {
    private static final long serialVersionUID = 8058371942878604902L;

    @EmbeddedId
    private ParentKey parentKey = new ParentKey();

    @Column(name = "parent_name")
    private String parentName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumns({ @JoinColumn(name = "join_id", referencedColumnName = "join_id"),
            @JoinColumn(name = "child_id", referencedColumnName = "child_id") })
    private List<Child> childList;

    @Column(name = "child_id")
    private String childId;

    /** getter,setter省略 **/

    @Embeddable
    public static class ParentKey implements Serializable {
        private static final long serialVersionUID = -795173904285910114L;
        @Column(name = "parent_id")
        String parentId;
        @Column(name = "join_id")
        String joinId;
    }

    /** equals,hashCode省略 **/
}
