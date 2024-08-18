package com.tinqinacademy.email.api.operations.recovery;

import com.tinqinacademy.email.api.base.OperationInput;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RecoveryInput implements OperationInput {
    private String email;
    private String username;
    private String newPassword;
}
