package com.sun.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "HistoryDetails")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class HistoryDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "history_id")
    private Histories history;
    @Column(name = "book_id")
    private int bookId;
}
