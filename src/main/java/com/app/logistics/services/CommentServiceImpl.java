package com.app.logistics.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.logistics.DTO.CommentRequestDTO;
import com.app.logistics.DTO.CommentResponseDTO;
import com.app.logistics.entities.Comment;
import com.app.logistics.entities.User;
import com.app.logistics.repositories.CommentRepository;
import com.app.logistics.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	
	private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public CommentResponseDTO addComment(CommentRequestDTO dto) {
        /*User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));*/

        Comment comment = new Comment();
        comment.setMessage(dto.getMessage());
        comment.setTimestamp(LocalDateTime.now());
        //comment.setUser(user);

        Comment saved = commentRepository.save(comment);

        return mapToResponseDTO(saved);
    }

    @Override
    public List<CommentResponseDTO> getAllComments() {
        return commentRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private CommentResponseDTO mapToResponseDTO(Comment comment) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(comment.getIdComment());
        dto.setMessage(comment.getMessage());
        dto.setTimestamp(comment.getTimestamp());
        //dto.setUsername(comment.getUser().getUsername());
        return dto;
    }

}
