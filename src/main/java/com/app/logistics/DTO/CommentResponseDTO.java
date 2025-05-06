package com.app.logistics.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentResponseDTO {
	
	private Long id;
    private String message;
    private LocalDateTime timestamp;
    private String username;

}
