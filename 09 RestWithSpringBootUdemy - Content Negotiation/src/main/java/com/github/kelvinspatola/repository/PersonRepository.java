package com.github.kelvinspatola.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.kelvinspatola.data.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
