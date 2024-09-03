package com.dgmoonlabs.psythinktank.domain.newsletter.service;

import com.dgmoonlabs.psythinktank.domain.newsletter.dto.NewsletterResponse;
import com.dgmoonlabs.psythinktank.domain.newsletter.model.Newsletter;
import com.dgmoonlabs.psythinktank.domain.newsletter.repository.NewsletterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsletterServiceTest {
    private static final long NEWSLETTER_ID_1 = 1L;
    private static final long NEWSLETTER_ID_2 = 2L;
    private static final Newsletter NEWSLETTER_1 = Newsletter.builder()
            .id(NEWSLETTER_ID_1)
            .title("제목1")
            .fileName("파일명1")
            .build();
    private static final Newsletter NEWSLETTER_2 = Newsletter.builder()
            .id(NEWSLETTER_ID_2)
            .title("제목2")
            .fileName("파일명2")
            .build();
    private static final List<Newsletter> NEWSLETTERS = List.of(NEWSLETTER_1, NEWSLETTER_2);
    private static final List<NewsletterResponse> NEWSLETTER_RESPONSES = List.of(
            NewsletterResponse.from(NEWSLETTER_1),
            NewsletterResponse.from(NEWSLETTER_2)
    );
    private static final Page<Newsletter> NEWSLETTER_PAGE = new PageImpl<>(NEWSLETTERS);
    private static final Page<NewsletterResponse> NEWSLETTER_RESPONSE_PAGE = new PageImpl<>(NEWSLETTER_RESPONSES);
    private static final PageRequest PAGE_REQUEST = PageRequest.of(1, 10);
    @Mock
    private NewsletterRepository newsletterRepository;
    @InjectMocks
    private NewsletterService newsletterService;

    @Test
    void getNewsletters() {
        when(newsletterRepository.findAll(any(Pageable.class)))
                .thenReturn(NEWSLETTER_PAGE);

        assertThat(newsletterService.getNewsletters(PAGE_REQUEST))
                .isEqualTo(NEWSLETTER_RESPONSE_PAGE);
    }

    @Test
    void getNewsletter() {
        when(newsletterRepository.findById(NEWSLETTER_ID_1))
                .thenReturn(Optional.of(NEWSLETTER_1));

        assertThat(newsletterService.getNewsletter(NEWSLETTER_ID_1))
                .isEqualTo(NewsletterResponse.from(NEWSLETTER_1));
    }
}