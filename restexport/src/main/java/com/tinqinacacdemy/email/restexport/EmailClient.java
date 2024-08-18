package com.tinqinacacdemy.email.restexport;

import com.tinqinacademy.email.api.EmailFeignClientApiPaths;
import com.tinqinacademy.email.api.operations.confirmation.ConfirmationInput;
import com.tinqinacademy.email.api.operations.confirmation.ConfirmationOutput;
import com.tinqinacademy.email.api.operations.recovery.RecoveryInput;
import com.tinqinacademy.email.api.operations.recovery.RecoveryOutput;
import feign.Body;
import feign.Headers;
import feign.RequestLine;

@Headers({
        "Content-Type: application/json"
})
public interface EmailClient {
    @RequestLine(EmailFeignClientApiPaths.SEND_CONFIRMATION)
    ConfirmationOutput sendConfirmation(ConfirmationInput confirmationInput);

    @RequestLine(EmailFeignClientApiPaths.SEND_RECOVERY)
    RecoveryOutput sendRecovery(RecoveryInput recoveryInput);
}
