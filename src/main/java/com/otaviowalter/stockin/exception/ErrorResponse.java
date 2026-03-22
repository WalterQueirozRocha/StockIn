package com.otaviowalter.stockin.exception;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ErrorResponse {
	
	private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}
