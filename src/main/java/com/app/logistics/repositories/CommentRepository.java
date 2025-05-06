package com.app.logistics.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.logistics.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

	//List<Comment> findByUserIdUser(Long userId);
}
