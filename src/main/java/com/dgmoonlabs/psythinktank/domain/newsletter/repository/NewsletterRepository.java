package com.dgmoonlabs.psythinktank.domain.newsletter.repository;

import com.dgmoonlabs.psythinktank.domain.newsletter.model.Newsletter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterRepository extends JpaRepository<Newsletter, Long> {
}
