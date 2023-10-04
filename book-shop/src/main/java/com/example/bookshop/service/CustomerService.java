package com.example.bookshop.service;

import com.example.bookshop.dao.CustomerDao;
import com.example.bookshop.ds.CartItem;
import com.example.bookshop.entity.BookItem;
import com.example.bookshop.entity.Customer;
import com.example.bookshop.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;
    private final CartService cartService;

    @Transactional
    public void saveCustomer(Customer customer) {
        Optional<Customer> customerOptional = customerDao.findByName(customer.getName());
        if (!customerOptional.isPresent()) {

            customer.setPassword(passwordEncoder.encode(customer.getPassword())); // pw def ma kyite lo
            customer.setRole(Role.ROLE_USER); //Role ma htae yin null
            addBookItems(customer);

            customerDao.save(customer);
        } else {
            Customer customer1 = customerOptional.get();  //pr yin old customer // khaw kyi lo lr yin
            customer1.setId(customer1.getId());  //id pr lr yin spring data jba save so savaAndFlash id nae so update pyit twr
            customer1.setPassword(customer1.getPassword());

            //old bookitem
            var customerBookItem = customer1.getBookItems();//old book su

            //all book  list  mo same ll ya ,set so same ma ya
            var bookItems = cartService.listCartItem()
                    .stream()
                    .map(c -> toBookItemOld(c)).collect(Collectors.toList());//cartitem ko bookitem chg p bookitems set twat


       //                         new book (loop  pat p item htaw hte)
            for (BookItem item : bookItems) {
                item.setCustomer(customer1);// bl thu book ll ma thi mhr soe lo
                customerBookItem.add(item);//old book item htae new item htae // bi-direction htae ways nae thu thu pl
            }
            customer1.setBookItems(customerBookItem);//customer1 book item

            customerDao.save(customer1);

        }
    }

    //c = cartitem
    private void addBookItems(Customer customer) {   //customer br twe wel ll thi chin  lo
        cartService.listCartItem()
                .stream()
                .forEach(c -> {
                            customer.addBook(toBookItem(c));
                        }
                );
    }

    private BookItem toBookItem(CartItem cartItem) {
        return new BookItem(

                cartItem.getTitle(),
                cartItem.getAuthor(),
                cartItem.getPrice(),
                cartItem.getQuantity(),
                cartItem.getPrice() * cartItem.getQuantity()
        );
    }
   private BookItem toBookItemOld(CartItem cartItem) {
        return new BookItem(
                cartItem.getId(),
                cartItem.getTitle(),
                cartItem.getAuthor(),
                cartItem.getPrice(),
                cartItem.getQuantity(),
                cartItem.getPrice() * cartItem.getQuantity()
        );
    }
}
