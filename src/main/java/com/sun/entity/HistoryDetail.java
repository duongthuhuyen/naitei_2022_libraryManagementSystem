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
    private History history;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book bookId;

    public HistoryDetail(History history, Book bookId) {
        this.history = history;
        this.bookId = bookId;
    }
}
