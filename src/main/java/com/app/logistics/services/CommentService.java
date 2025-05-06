package com.app.logistics.services;

import java.util.List;

import com.app.logistics.DTO.CommentRequestDTO;
import com.app.logistics.DTO.CommentResponseDTO;

public interface CommentService {
	
	CommentResponseDTO addComment(CommentRequestDTO dto);
    List<CommentResponseDTO> getAllComments();

}
