package com.tinqinacademy.email.core.operations;

import com.tinqinacademy.email.api.errors.ErrorMapper;
import com.tinqinacademy.email.api.errors.ErrorOutput;
import com.tinqinacademy.email.api.operations.recovery.Recovery;
import com.tinqinacademy.email.api.operations.recovery.RecoveryInput;
import com.tinqinacademy.email.api.operations.recovery.RecoveryOutput;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;
import java.util.Map;

import static io.vavr.API.Match;

@Service
@Slf4j
public class RecoveryOperation extends BaseOperation implements Recovery {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${sender.email}")
    private String sender;

    public RecoveryOperation(Validator validator,
                             ErrorMapper errorMapper,
                             JavaMailSender mailSender,
                             TemplateEngine templateEngine) {
        super(validator, errorMapper);
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public Either<ErrorOutput, RecoveryOutput> process(RecoveryInput input) {
        return Try.of(() -> {
            log.info("Start process method in RecoveryOperation. Input: {}", input);

            Context context = new Context(Locale.ENGLISH, Map.of("username", input.getUsername(),
                    "password", input.getNewPassword())
            );

            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

            setupMimeMessageHelper(input, mimeMessageHelper, context);

            mailSender.send(mimeMessage);

            RecoveryOutput result = RecoveryOutput.builder().build();

            log.info("End process method in RecoveryOperation. Result: {}", result);

            return result;
        })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        defaultCase(throwable, HttpStatus.I_AM_A_TEAPOT),
                        validateCase(throwable, HttpStatus.BAD_REQUEST)
                ));
    }

    private void setupMimeMessageHelper(RecoveryInput input, MimeMessageHelper mimeMessageHelper, Context context) throws MessagingException {
        mimeMessageHelper.setTo(input.getEmail());
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setSubject("Requested password change");
        mimeMessageHelper.setText(templateEngine.process("recovery", context), true);
    }
}
