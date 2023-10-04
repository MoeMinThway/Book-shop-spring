package com.example.bookshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private double price;
    private String author;
    private  String  imageUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishedDate;

    @Enumerated(EnumType.STRING)
    private Category category;
    @Transient
    private List<Integer> quantitylist=new LinkedList<>();


    public Book(String title, double price, String author, String imageUrl, LocalDate publishedDate, Category category) {
        this.title = title;
        this.price = price;
        this.author = author;
        this.imageUrl = imageUrl;
        this.publishedDate = publishedDate;
        this.category = category;
    }
}
