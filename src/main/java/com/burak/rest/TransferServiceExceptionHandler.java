package com.burak.rest;

import com.burak.exception.AccountAlreadyExistException;
import com.burak.exception.AccountNotFoundException;
import com.burak.exception.NegativeBalanceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ControllerAdvice(annotations = RestController.class)
@RequestMapping(produces = "application/vnd.error+json")
public class TransferServiceExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(TransferServiceExceptionHandler.class);

    @ExceptionHandler(NegativeBalanceException.class)
    public ResponseEntity<VndErrors> handleNegativeBalance(final NegativeBalanceException e) {
        return error(e, HttpStatus.BAD_REQUEST, e.getAccountName());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<VndErrors> handelAccountNotFound(final AccountNotFoundException e) {
        logger.error("Account Not Found With Name: {}", e.getAccountName(), e);
        return error(e, HttpStatus.NOT_FOUND, e.getAccountName());
    }

    @ExceptionHandler(AccountAlreadyExistException.class)
    public ResponseEntity<VndErrors> handelAccountAlreadyExist(final AccountAlreadyExistException e) {
        logger.error("Account Already Exist With Name: {}", e.getAccountName(), e);
        return error(e, HttpStatus.CONFLICT, e.getAccountName());
    }

    @ExceptionHandler(URISyntaxException.class)
    public ResponseEntity<VndErrors> handleURISyntaxFailure(URISyntaxException e) {
        return error(e, HttpStatus.INTERNAL_SERVER_ERROR, "Error Occurred When Creating a New Account");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<VndErrors> handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        List<VndErrors.VndError> vndErrorsList = new ArrayList<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            vndErrorsList.add(new VndErrors.VndError(error.getField(), error.getDefaultMessage()));
        }
        return new ResponseEntity<>(new VndErrors(vndErrorsList), HttpStatus.BAD_REQUEST);
    }

    private <E extends Exception> ResponseEntity<VndErrors> error(final E exception, final HttpStatus httpStatus, final String logRef) {
        final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new VndErrors(logRef, message), httpStatus);
    }
}