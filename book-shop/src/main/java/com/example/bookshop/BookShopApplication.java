package com.example.bookshop;

import com.example.bookshop.dao.BookDao;
import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class BookShopApplication {
    private final BookDao bookdao;

    public static void main(String[] args) {
        SpringApplication.run(BookShopApplication.class, args);
    }

    //    public Book(String title, double price, String author, String imageUrl, LocalDate publishedDate, Category category) {
    @Bean @Transactional @Profile("test")
    public ApplicationRunner runner(){
        return r->{
            List.of(
                    new Book("England",35.5,"Macaulay","https://source.unsplash.com/355x200/?flowers", LocalDate.of(1950,7,21), Category.HISTORICAL),
                    new Book("World History",40.5,"H.G Wells","https://source.unsplash.com/355x200/?fruit", LocalDate.of(1956,9,13), Category.HISTORICAL),
                    new Book("Return of The Native",60,"Thomas Hardy","https://source.unsplash.com/355x200/?sunrise", LocalDate.of(1950,7,21), Category.HISTORICAL),
                    new Book("Victor Frankenstein",55.9,"Mary Shel","https://source.unsplash.com/355x200/?water", LocalDate.of(1900,3,8), Category.HISTORICAL),


            new Book("A pale of View hill  ",50,"Kazu Ishiguro","https://source.unsplash.com/355x200/?flowers", LocalDate.of(1950,7,21), Category.ROMANCE),
                    new Book("History",40.5," Wells","https://source.unsplash.com/355x200/?fruit", LocalDate.of(1956,9,13), Category.ROMANCE),
                    new Book(" Native",60," Hardy","https://source.unsplash.com/355x200/?sunrise", LocalDate.of(1950,7,21), Category.ROMANCE),
                    new Book("Victor ",55.9,"Mary","https://source.unsplash.com/355x200/?water", LocalDate.of(1900,3,8), Category.NOVEL)

            ).forEach(bookdao::save);
        };
    }
}
