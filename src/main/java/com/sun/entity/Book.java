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
    public Book() {
		super();
	}
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
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column
    private String author;
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
    @Column
    private String name;
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
    @Column
    private String publisher;
    public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
    @Column
    public String description;
    public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
