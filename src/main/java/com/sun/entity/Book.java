package com.sun.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "books")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Book implements Serializable {
   
	public Book(int id, String author, String name, String publisher, String description) {
		super();
		this.id = id;
		this.author = author;
		this.name = name;
		this.publisher = publisher;
		this.description = description;
	}
    

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column
    private String author;
	
	
    @Column
    private String name;
    
	
    @Column
    private String publisher;
    
	
    @Column
    public String description;
    

}
