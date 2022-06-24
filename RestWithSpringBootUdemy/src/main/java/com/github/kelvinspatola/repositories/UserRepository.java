package com.github.kelvinspatola.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.kelvinspatola.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { // <Person, Long> = o tipo do objeto e o tipo do id desse objeto
	
	@Query("SELECT u FROM User u WHERE u.userName = :userName") // JPQL query
	public User findByUsername(@Param("userName") String userName);
}
