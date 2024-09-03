package com.dgmoonlabs.psythinktank.domain.newsletter.service;

import com.dgmoonlabs.psythinktank.domain.newsletter.dto.NewsletterRequest;
import com.dgmoonlabs.psythinktank.domain.newsletter.dto.NewsletterResponse;
import com.dgmoonlabs.psythinktank.domain.newsletter.model.Newsletter;
import com.dgmoonlabs.psythinktank.domain.newsletter.repository.NewsletterRepository;
import com.dgmoonlabs.psythinktank.global.constant.CriteriaField;
import com.dgmoonlabs.psythinktank.global.constant.Pagination;
import com.dgmoonlabs.psythinktank.global.exception.FileDeletionException;
import com.dgmoonlabs.psythinktank.global.exception.FileDownloadException;
import com.dgmoonlabs.psythinktank.global.exception.FileSaveFailedException;
import com.dgmoonlabs.psythinktank.global.exception.NewsletterNotExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsletterService {
    private final NewsletterRepository newsletterRepository;
    private final MultipartProperties multipartProperties;

    @Transactional
    public void createNewsletter(NewsletterRequest newsletterRequest) {
        Newsletter newsletter = newsletterRequest.toEntity();
        MultipartFile multipartFile = newsletterRequest.file();
        try {
            if (!multipartFile.isEmpty()) {
                String originalFilename = multipartFile.getOriginalFilename();
                if (originalFilename == null) {
                    originalFilename = "";
                }
                int dotIndex = originalFilename.lastIndexOf(".");
                String extension = originalFilename.substring(++dotIndex);
                String fileName = UUID.randomUUID() + "." + extension;
                newsletter.setFileName(fileName);
            }
            File file = new File(newsletter.getFileName());
            multipartFile.transferTo(file);
            newsletterRepository.save(newsletter);
        } catch (IOException e) {
            throw new FileSaveFailedException(e);
        }
    }

    @Transactional(readOnly = true)
    public Page<NewsletterResponse> getNewsletters(Pageable pageable) {
        return newsletterRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        Pagination.SIZE.getValue(),
                        Sort.by(CriteriaField.ID.getName())
                                .ascending()
                )
        ).map(NewsletterResponse::from);
    }

    @Transactional(readOnly = true)
    public NewsletterResponse getNewsletter(long id) {
        return NewsletterResponse.from(
                newsletterRepository.findById(id)
                        .orElseThrow(NewsletterNotExistException::new)
        );
    }

    @Transactional(readOnly = true)
    public Resource downloadNewsletter(long id) {
        Newsletter newsletter = newsletterRepository.findById(id)
                .orElseThrow(NewsletterNotExistException::new);
        try {
            Path filePath = Paths.get(multipartProperties.getLocation(), newsletter.getFileName());
            log.info("uri: {}", filePath.toUri());
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new FileDownloadException(e);
        }
    }

    @Transactional
    public void deleteNewsletter(long id) {
        Newsletter newsletter = newsletterRepository.findById(id)
                .orElseThrow(NewsletterNotExistException::new);
        try {
            Path path = Paths.get(multipartProperties.getLocation(), newsletter.getFileName());
            Files.delete(path);
            newsletterRepository.deleteById(id);
        } catch (IOException e) {
            throw new FileDeletionException(e);
        }
    }
}
