package com.github.kelvinspatola.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.kelvinspatola.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> { // <Book, Long> = o tipo do objeto e o tipo do id desse objeto

}
