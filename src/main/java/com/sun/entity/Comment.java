package com.sun.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column
    private String body;
    @Column(name = "parent_id")
    private int parentId;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

}
