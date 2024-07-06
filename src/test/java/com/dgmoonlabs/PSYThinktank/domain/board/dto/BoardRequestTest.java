package com.dgmoonlabs.PSYThinktank.domain.board.dto;

import com.dgmoonlabs.psythinktank.domain.board.dto.BoardRequest;
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
class BoardRequestTest {
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
            "test11, 제목, 내용, true"
    })
    void valid_values(String memberId, String title, String content, Boolean isNotice) {
        BoardRequest request = new BoardRequest(null, memberId, title, content, isNotice);
        Set<ConstraintViolation<BoardRequest>> violations = validator.validate(request);
        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            ",,",
            "'','',''",
            "test11,,내용",
            "test11,제목,",
            "test11,제목,"
    })
    void invalid_values(String memberId, String title, String content) {
        BoardRequest request = new BoardRequest(null, memberId, title, content, false);
        Set<ConstraintViolation<BoardRequest>> violations = validator.validate(request);
        assertThat(violations).isNotEmpty();
        violations.forEach(violation -> log.info("violation = {}", violation));
    }
}