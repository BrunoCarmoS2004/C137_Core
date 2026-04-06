package br.com.c137.project.financial.core.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorView handleInternalServerException(HttpServletRequest req, Exception e) {
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ErrorView(
                httpStatus.value(),
                e.getMessage(),
                httpStatus.name(),
                req.getServletPath());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorView handleBadRequestException(HttpServletRequest req, MethodArgumentNotValidException e) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ErrorView(
                httpStatus.value(),
                e.getMessage(),
                httpStatus.name(),
                req.getServletPath()
        );
    }

    @ExceptionHandler(value = ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorView handleValidationException(HttpServletRequest req, ValidationException e) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ErrorView(
                httpStatus.value(),
                e.getMessage(),
                httpStatus.name(),
                req.getServletPath()
        );
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorView handleNotFoundException(HttpServletRequest req, NotFoundException e) {
        final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return new ErrorView(
                httpStatus.value(),
                e.getMessage(),
                httpStatus.name(),
                req.getServletPath()
        );
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorView handleUnauthorizedException(HttpServletRequest req, UnauthorizedException e) {
        final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        return new ErrorView(
                httpStatus.value(),
                e.getMessage(),
                httpStatus.name(),
                req.getServletPath()
        );
    }

    @ExceptionHandler(value = TenantSchemaNotReadyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorView handleTenantSchemaNotReadyException(HttpServletRequest req, TenantSchemaNotReadyException e) {
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ErrorView(
                httpStatus.value(),
                e.getMessage(),
                httpStatus.name(),
                req.getServletPath()
        );
    }
}
