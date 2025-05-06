package com.app.logistics.repositories;

import org.springframework.stereotype.Repository;

import com.app.logistics.entities.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
	@Query("select t from Token t inner join t.user u "
			+ "where u.idUser = :id and (t.expired = false or t.revoked = false)")
	List<Token> findAllValidTokenByUser(Long id);

	Optional<Token> findByToken(String token);
	Token findTokenByToken(String token);
}
