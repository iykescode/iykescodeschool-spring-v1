package com.iykescode.demo.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/*
@Data annotation is provided by Lombok library which generates getter, setter,
equals(), hashCode(), toString() methods & Constructor at compile time.
This makes our code short and clean.=
* */
@Getter
@Setter
@Entity
@Table(name = "holidays")
public class Holiday extends BaseEntity {

    @Id
    @Column(name = "day", nullable = false)
    private String day;

    @Column(name = "reason")
    private String reason;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        FESTIVAL, FEDERAL
    }
}
