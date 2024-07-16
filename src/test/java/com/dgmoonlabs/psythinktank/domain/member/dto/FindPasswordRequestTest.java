package com.dgmoonlabs.psythinktank.domain.member.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class FindPasswordRequestTest {
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
            "email@example.com,test11"
    })
    void valid_values(String memberEmail, String memberId) {
        FindPasswordRequest request = new FindPasswordRequest(memberEmail, memberId);
        Set<ConstraintViolation<FindPasswordRequest>> violations = validator.validate(request);
        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            ",",
            "'',",
            ",''",
            "'',''",
            "email@example.com,",
            "email@example.com,''",
            ",test11",
            "'',test11"
    })
    void invalid_values(String memberEmail, String memberId) {
        FindPasswordRequest request = new FindPasswordRequest(memberEmail, memberId);
        Set<ConstraintViolation<FindPasswordRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
        violations.forEach(violation -> log.info("violation = {}", violation));
    }
}