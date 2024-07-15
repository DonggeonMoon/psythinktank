package com.dgmoonlabs.psythinktank.domain.circular.service;

import com.dgmoonlabs.psythinktank.domain.circular.dto.CircularResponse;
import com.dgmoonlabs.psythinktank.domain.circular.model.Circular;
import com.dgmoonlabs.psythinktank.domain.circular.repository.CircularRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CircularServiceTest {
    private static final long CIRCULAR_ID_1 = 1L;
    private static final long CIRCULAR_ID_2 = 2L;
    private static final Circular CIRCULAR_1 = Circular.builder()
            .id(CIRCULAR_ID_1)
            .title("제목1")
            .fileName("파일명1")
            .createdAt(LocalDateTime.now())
            .build();
    private static final Circular CIRCULAR_2 = Circular.builder()
            .id(CIRCULAR_ID_2)
            .title("제목2")
            .fileName("파일명2")
            .createdAt(LocalDateTime.now())
            .build();
    private static final List<Circular> CIRCULARS = List.of(CIRCULAR_1, CIRCULAR_2);
    private static final List<CircularResponse> CIRCULAR_RESPONSES = List.of(
            CircularResponse.from(CIRCULAR_1),
            CircularResponse.from(CIRCULAR_2)
    );
    private static final Page<Circular> CIRCULAR_PAGE = new PageImpl<>(CIRCULARS);
    private static final Page<CircularResponse> CIRCULAR_RESPONSE_PAGE = new PageImpl<>(CIRCULAR_RESPONSES);
    private static final PageRequest PAGE_REQUEST = PageRequest.of(1, 10);
    @Mock
    private CircularRepository circularRepository;
    @InjectMocks
    private CircularService circularService;

    @Test
    void selectCirculars() {
        when(circularRepository.findAll(any(Pageable.class)))
                .thenReturn(CIRCULAR_PAGE);

        assertThat(circularService.selectCirculars(PAGE_REQUEST))
                .isEqualTo(CIRCULAR_RESPONSE_PAGE);
    }

    @Test
    void selectCircular() {
        when(circularRepository.findById(CIRCULAR_ID_1))
                .thenReturn(Optional.of(CIRCULAR_1));

        assertThat(circularService.selectCircular(CIRCULAR_ID_1))
                .isEqualTo(CircularResponse.from(CIRCULAR_1));
    }
}