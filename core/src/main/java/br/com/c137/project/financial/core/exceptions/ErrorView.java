package br.com.c137.project.financial.core.exceptions;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorView {
    private LocalDateTime timeStamp;
    private int status;
    private String message;
    private String error;
    private String path;
    public ErrorView(int status, String message, String error, String path) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.path = path;
        this.timeStamp = LocalDateTime.now();
    }
}
