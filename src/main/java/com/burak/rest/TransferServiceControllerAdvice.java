package com.burak.rest;

import com.burak.exception.AccountNotFoundException;
import com.burak.exception.NegativeBalanceException;
import org.springframework.dao.OptimisticLockingFailureException;
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
public class TransferServiceControllerAdvice {

    @ExceptionHandler(NegativeBalanceException.class)
    public ResponseEntity<VndErrors> handleNegativeBalance(final NegativeBalanceException e) {
        return error(e, HttpStatus.BAD_REQUEST, e.getAccountName());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<VndErrors> handelAccountNotFound(final AccountNotFoundException e) {
        return error(e, HttpStatus.NOT_FOUND, e.getAccountName());
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<VndErrors> handleOptimisticLockingFailure(OptimisticLockingFailureException e) {
        return error(e, HttpStatus.CONFLICT,"Simultaneous Changes not Allowed!");
    }

    @ExceptionHandler(URISyntaxException.class)
    public ResponseEntity<VndErrors> handleURISyntaxFailure(URISyntaxException e) {
        return error(e, HttpStatus.INTERNAL_SERVER_ERROR,"Error Occurred When Creating a New Account");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<VndErrors> handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        List<VndErrors.VndError> vndErrorsList = new ArrayList<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            vndErrorsList.add(new VndErrors.VndError(error.getField(),error.getDefaultMessage()));
        }
        return new ResponseEntity<>(new VndErrors(vndErrorsList), HttpStatus.BAD_REQUEST);
    }

    private <E extends Exception> ResponseEntity<VndErrors> error(final E exception, final HttpStatus httpStatus, final String logRef) {
        final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
        return new ResponseEntity<>(new VndErrors(logRef, message), httpStatus);
    }
}