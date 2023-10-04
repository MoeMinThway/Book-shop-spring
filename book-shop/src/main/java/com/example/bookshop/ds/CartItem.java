package com.example.bookshop.ds;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;


@Builder
@Data

public class CartItem {
    private int id;
    private  String title;
    private String author;
    private double price;
    private int quantity=1;

    private boolean renderer;
    private double sum;

  //  private  List<Integer> quantitylist=new LinkedList<>();
}
