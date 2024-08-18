package com.tinqinacademy.email.api.operations.confirmation;

import com.tinqinacademy.email.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ConfirmationInput implements OperationInput {
    private String recipient;
    private String confirmationCode;
    private String username;
}
