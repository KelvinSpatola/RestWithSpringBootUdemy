package com.github.kelvinspatola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.kelvinspatola.data.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
