package com.example.bookshop.service;

import com.example.bookshop.ds.CartItem;
import com.example.bookshop.entity.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private  final  CartBean cartBean;


    public  int cartSize(){
        return cartBean.getCartItems().size();
    }
    public void addToCart(Book book){
        this.cartBean.addcartItem(
                CartItem.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .price(book.getPrice())
                        .build()
        );
    }

    public Set<CartItem> listCartItem(){
        return cartBean.getCartItems();

    }
    public  void deletebyid(int id){
        cartBean.getCartItems().removeIf(
                c->c.getId()==id
        );
    }
    public void clearCart(){
        cartBean.getCartItems().clear();
    }
    public  void setValueRenderer(boolean value){
cartBean.setCartItems(
        cartBean.getCartItems()
                .stream().map(c->{
                    c.setRenderer(value);
                    return c;
                }).collect(Collectors.toSet())
);
    }
/*    public  void addToCartItemQuantity(List<Integer> list){
       int i=0;
       for (CartItem cartItem:cartBean.getCartItems()){
           cartItem.getQuantitylist().add(list.get(i));
           i++;
       }
    }*/
}
