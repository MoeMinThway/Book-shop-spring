package com.example.bookshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter

@NoArgsConstructor
public class BookItem { // thu si lr save po

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // ho bet ka id ko yuu thone
    private  String title;
    private String author;
    private double price;
    private int quantity=1;


    private double sum;
    @ManyToOne
    private Customer customer;

 /*      cartItem.getId(),
               cartItem.getTitle(),
               cartItem.getAuthor(),
               cartItem.getPrice(),
               cartItem.getQuantity(),
               cartItem.getSum()*/

    public BookItem(int id,String title, String author, double price, int quantity, double sum) {
        this.id=id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.sum = sum;
    }
    public BookItem(String title, String author, double price, int quantity, double sum) {

        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.sum = sum;
    }
}
