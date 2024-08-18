package com.tinqinacademy.email.api.exceptions;

import com.tinqinacademy.email.api.errors.Error;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class InvalidInputException extends RuntimeException {
    private final List<Error> errors;
}
