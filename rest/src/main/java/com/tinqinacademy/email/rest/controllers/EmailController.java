package com.tinqinacademy.email.rest.controllers;

import com.tinqinacademy.email.api.EmailRestApiPaths;
import com.tinqinacademy.email.api.errors.ErrorOutput;
import com.tinqinacademy.email.api.operations.confirmation.Confirmation;
import com.tinqinacademy.email.api.operations.confirmation.ConfirmationInput;
import com.tinqinacademy.email.api.operations.confirmation.ConfirmationOutput;
import com.tinqinacademy.email.api.operations.recovery.Recovery;
import com.tinqinacademy.email.api.operations.recovery.RecoveryInput;
import com.tinqinacademy.email.api.operations.recovery.RecoveryOutput;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Email REST APIs")
@RequiredArgsConstructor
public class EmailController extends BaseController {
    private final Confirmation confirmation;
    private final Recovery recovery;

    @PostMapping(EmailRestApiPaths.SEND_CONFIRMATION)
    public ResponseEntity<?> sendConfirmation(@RequestBody ConfirmationInput input) {
        Either<ErrorOutput, ConfirmationOutput> result = confirmation.process(input);

        return getOutput(result, HttpStatus.OK);
    }

    @PostMapping(EmailRestApiPaths.SEND_RECOVERY)
    public ResponseEntity<?> sendRecovery(@RequestBody RecoveryInput input) {
        Either<ErrorOutput, RecoveryOutput> result = recovery.process(input);

        return getOutput(result, HttpStatus.OK);
    }
}
