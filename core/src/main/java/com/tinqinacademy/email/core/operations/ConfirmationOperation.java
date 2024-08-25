package com.tinqinacademy.email.core.operations;

import com.tinqinacademy.email.api.errors.ErrorMapper;
import com.tinqinacademy.email.api.errors.ErrorOutput;
import com.tinqinacademy.email.api.operations.confirmation.Confirmation;
import com.tinqinacademy.email.api.operations.confirmation.ConfirmationInput;
import com.tinqinacademy.email.api.operations.confirmation.ConfirmationOutput;
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
public class ConfirmationOperation extends BaseOperation implements Confirmation {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${sender.email}")
    private String sender;

    public ConfirmationOperation(Validator validator,
                                 ErrorMapper errorMapper,
                                 JavaMailSender mailSender,
                                 TemplateEngine templateEngine) {
        super(validator, errorMapper);
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public Either<ErrorOutput, ConfirmationOutput> process(ConfirmationInput input) {
        return Try.of(() -> {
                    log.info("Start process method in ConfirmationOperation. Input: {}", input);

                    validate(input);

                    Context context = new Context(Locale.ENGLISH, Map.of("username", input.getUsername(),
                            "confirmationCode", input.getConfirmationCode())
                    );

                    MimeMessage mimeMessage = mailSender.createMimeMessage();

                    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

                    setupMimeMessageHelper(input, mimeMessageHelper, context);

                    mailSender.send(mimeMessage);

                    ConfirmationOutput result = ConfirmationOutput.builder().build();

                    log.info("End process method in ConfirmationOperation. Result: {}", result);

                    return result;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        validateCase(throwable, HttpStatus.BAD_REQUEST)
                ));
    }

    private void setupMimeMessageHelper(ConfirmationInput input, MimeMessageHelper mimeMessageHelper, Context context) throws MessagingException {
        mimeMessageHelper.setTo(input.getRecipient());
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setSubject("Registration Confirmation");
        mimeMessageHelper.setText(templateEngine.process("confirmation", context), true);
    }
}
