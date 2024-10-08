package com.dgmoonlabs.psythinktank.domain.newsletter.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Slf4j
class NewsletterRequestTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }

    @ParameterizedTest
    @CsvSource({
            "제목"
    })
    void valid_values(String title) {
        NewsletterRequest request = new NewsletterRequest(null, title, Mockito.mock(MultipartFile.class));
        Set<ConstraintViolation<NewsletterRequest>> violations = validator.validate(request);
        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void invalid_values(String id) {
        NewsletterRequest request = new NewsletterRequest(null, id, Mockito.mock(MultipartFile.class));
        Set<ConstraintViolation<NewsletterRequest>> violations = validator.validate(request);
        assertThat(violations).isNotEmpty();
        violations.forEach(violation -> log.info("violation = {}", violation));
    }
}