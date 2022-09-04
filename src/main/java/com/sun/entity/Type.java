package com.sun.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "types")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Type implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;

//    @OneToMany(mappedBy = "type")
//    private Set<TypeBook> typeBooks = new HashSet<>();

//    @ManyToMany(mappedBy = "typeBooks")
//    private List<Book> books = new ArrayList<>();
}
