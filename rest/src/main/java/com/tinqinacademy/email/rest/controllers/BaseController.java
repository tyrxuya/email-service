package com.tinqinacademy.email.rest.controllers;

import com.tinqinacademy.email.api.base.OperationOutput;
import com.tinqinacademy.email.api.errors.ErrorOutput;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {
    public ResponseEntity<?> getOutput(Either<ErrorOutput, ? extends OperationOutput> result, HttpStatus status) {
        return result.fold(
                errorOutput -> new ResponseEntity<>(errorOutput, errorOutput.getStatus()),
                output -> new ResponseEntity<>(output, status)
        );
    }
}
