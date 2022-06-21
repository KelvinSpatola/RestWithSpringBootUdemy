package com.github.kelvinspatola.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.kelvinspatola.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> { // <Person, Long> = o tipo do objeto e o tipo do id desse objeto

}
