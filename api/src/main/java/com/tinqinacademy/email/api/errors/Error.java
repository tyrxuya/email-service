package com.tinqinacademy.email.api.errors;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Error {
    private String message;
    private String field;
}
