package com.github.kelvinspatola.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.kelvinspatola.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> { // <Person, Long> = o tipo do objeto e o tipo do id desse objeto
	
	@Modifying
	@Query("UPDATE Person p SET p.enabled = false WHERE p.id = :id") // JPQL query
	void disablePerson(@Param("id") Long id);
	
	@Query("SELECT p FROM Person p WHERE p.enabled = 0")
	List<Person> findAllDisabled();
}
