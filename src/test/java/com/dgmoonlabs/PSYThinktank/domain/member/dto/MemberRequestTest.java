package com.dgmoonlabs.PSYThinktank.domain.member.dto;

import com.dgmoonlabs.psythinktank.domain.member.constant.UserLevel;
import com.dgmoonlabs.psythinktank.domain.member.dto.MemberRequest;
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
class MemberRequestTest {
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
            "test1, abcde%1234, email@example.com"
    })
    void valid_values(String id, String password, String email) {
        MemberRequest request = new MemberRequest(id, password, email, UserLevel.USER.getLevel());
        Set<ConstraintViolation<MemberRequest>> violations = validator.validate(request);
        assertThat(violations).isEmpty();
    }

    @ParameterizedTest
    @CsvSource({
            ",,",
            "test11,,",
            "test11,abcde1234!!,",
            "test, abcde1234, email@example.com",
            "test11@, abcde1234!!, email@example.com",
            "test11, abcde1234!!, emailexamplecomdsaf"
    })
    void invalid_values(String id, String password, String email) {
        MemberRequest request = new MemberRequest(id, password, email, UserLevel.USER.getLevel());
        Set<ConstraintViolation<MemberRequest>> violations = validator.validate(request);
        assertThat(violations).isNotEmpty();
        violations.forEach(violation -> log.info("violation = {}", violation));
    }
}