package com.sun.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "histories")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Histories implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "received_date")
    private LocalDateTime receivedDate;
    @Column(name = "borrow_date")
    private LocalDateTime borrowDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    private EStatus status;
    public enum EStatus{
        REQUEST,ACCEPT,REJECTP;
    }
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
