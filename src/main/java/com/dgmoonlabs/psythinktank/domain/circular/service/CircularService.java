package com.dgmoonlabs.psythinktank.domain.circular.service;

import com.dgmoonlabs.psythinktank.domain.circular.dto.CircularRequest;
import com.dgmoonlabs.psythinktank.domain.circular.dto.CircularResponse;
import com.dgmoonlabs.psythinktank.domain.circular.model.Circular;
import com.dgmoonlabs.psythinktank.domain.circular.repository.CircularRepository;
import com.dgmoonlabs.psythinktank.global.constant.CriteriaField;
import com.dgmoonlabs.psythinktank.global.constant.Pagination;
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
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CircularService {
    private final CircularRepository circularRepository;
    private final MultipartProperties multipartProperties;

    @Transactional
    public Page<Circular> selectCirculars(Pageable pageable) {
        return circularRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        Pagination.SIZE.getValue(),
                        Sort.by(CriteriaField.ID.getName())
                                .ascending()
                )
        );
    }

    @Transactional
    public CircularResponse selectCircular(long id) {
        return CircularResponse.from(
                circularRepository.findById(id)
                        .orElseThrow(IllegalStateException::new)
        );
    }

    @Transactional
    public Resource downloadCircular(long id) {
        Resource resource = null;
        try {
            Circular circular = circularRepository.findById(id)
                    .orElseThrow(IllegalStateException::new);
            Path filePath = Paths.get(multipartProperties.getLocation(), circular.getFileName());
            resource = new UrlResource(filePath.toUri());
            log.info("uri: " + filePath.toUri());
        } catch (MalformedURLException e) {
            log.info(e.getMessage());
        }
        return resource;
    }

    @Transactional
    public void addCircular(CircularRequest circularRequest) {
        Circular circular = circularRequest.toEntity();
        MultipartFile multipartFile = circularRequest.file();
        try {
            if (!multipartFile.isEmpty()) {
                String originalFilename = multipartFile.getOriginalFilename();
                assert originalFilename != null;
                int dotIndex = originalFilename.lastIndexOf(".");
                String extension = originalFilename.substring(++dotIndex);
                String fileName = UUID.randomUUID() + "." + extension;
                circular.setFileName(fileName);
            }
            File file = new File(circular.getFileName());
            multipartFile.transferTo(file);
            circularRepository.save(circular);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    @Transactional
    public void deleteCircular(long id) {
        Circular circular = circularRepository.findById(id)
                .orElseThrow(IllegalStateException::new);
        try {
            Path path = Paths.get(multipartProperties.getLocation(), circular.getFileName());
            Files.delete(path);
            circularRepository.deleteById(id);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
