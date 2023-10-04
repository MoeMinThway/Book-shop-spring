package com.example.bookshop.controller;

import com.example.bookshop.ds.CartItem;
import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Customer;
import com.example.bookshop.service.BookService;
import com.example.bookshop.service.CartService;
import com.example.bookshop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final CartService cartService;

    private final BookService bookService;
    private final CustomerService customerService;

    @GetMapping("/books")
    public String book(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "book";
    }

    @GetMapping("/books/detail")
    public String Book(@RequestParam("id") int id, Model model) {
        model.addAttribute("book", bookService.findBookById(id));
        return "bookdetails";
    }

    @GetMapping("/books/add")
    public String addtoCar(@RequestParam("id") int id) {
        cartService.addToCart(bookService.findBookById(id));
        return "redirect:/book/books/detail?id=" + id;
    }

    @ModelAttribute("cardSize")
    private int a() {
        return cartService.cartSize();
    }

    boolean action = false;

    @GetMapping("/delete")
    public String removeCartItem(@RequestParam("id") int id) {
        cartService.deletebyid(id);
        return "redirect:/book/books/view/cart";
    }

    @GetMapping("/remove/all")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/book/books/view/cart";
    }




    @GetMapping("/books/view/cart")
    public String viewCart(Model model) {
        action = false;
        model.addAttribute("action", action);
        model.addAttribute("book", new Book());  // dii ka book ka checkout mhr bind ,or null win
        //  cartService.setValueRenderer(false);
        model.addAttribute("cartItems", cartService.listCartItem());
        return "viewCart";
    }

    @PostMapping("/checkout")
    public String checkOut(Book book, Model model) {

        int i = 0;
        for (CartItem cartItem : cartService.listCartItem()) {
            cartItem.setQuantity(book.getQuantitylist().get(i));
            i++;
        }
        System.out.println(book.getQuantitylist() + "========================");
        System.out.println(cartService.listCartItem() + "============");
        //    model.addAttribute("action", true);
        //     cartService.setValueRenderer(true);
//        cartService.clearCart();
        // return "redirect:/book/info";
        return "redirect:/book/register";
    }

    @GetMapping("/info")
    public String showInfo(Model model) {
        model.addAttribute("cartItem", cartService.listCartItem());
        return "info";
    }

    @GetMapping("/register")
    public String registerCustomer(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/save-customer")
    public String saveCustomer(Customer customer, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        customerService.saveCustomer(customer);
        cartService.clearCart();
        return "redirect:/login";
    }
    @ModelAttribute("totalPrice")
    public double totalPrice() {
        return cartService.listCartItem()
                .stream()
                .map(
                        c ->
                        {
                            c.setSum(c.getPrice() * c.getQuantity());
                            return c.getSum();
                        }
                )


                .mapToDouble(i -> i)
                .sum();
    }
}
