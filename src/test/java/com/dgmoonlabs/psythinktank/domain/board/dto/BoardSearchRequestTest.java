package com.dgmoonlabs.psythinktank.domain.board.dto;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class BoardSearchSearchRequestTest {
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
            "게시물 제목"
    })
    void valid_values(String searchText) {
        BoardSearchRequest request = new BoardSearchRequest(searchText);
        Set<ConstraintViolation<BoardSearchRequest>> violations = validator.validate(request);
        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @NullAndEmptySource
    void invalid_values(String searchText) {
        BoardSearchRequest request = new BoardSearchRequest(searchText);
        Set<ConstraintViolation<BoardSearchRequest>> violations = validator.validate(request);
        assertThat(violations).isNotEmpty();
        violations.forEach(violation -> log.info("violation = {}", violation));
    }
}