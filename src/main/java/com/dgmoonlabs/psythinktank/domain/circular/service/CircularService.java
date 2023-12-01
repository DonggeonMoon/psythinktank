package com.dgmoonlabs.psythinktank.domain.circular.service;

import com.dgmoonlabs.psythinktank.domain.circular.model.Circular;
import com.dgmoonlabs.psythinktank.domain.circular.repository.CircularRepository;
import com.dgmoonlabs.psythinktank.global.constant.CriteriaField;
import com.dgmoonlabs.psythinktank.global.constant.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CircularService {
    private final CircularRepository circularRepository;
    private final MultipartProperties multipartProperties;

    @Transactional
    public Page<Circular> selectCirculars(int page) {
        return circularRepository.findAll(
                PageRequest.of(page,
                        Pagination.SIZE.getValue(),
                        Sort.by(CriteriaField.ID.getName())
                                .ascending()
                )
        );
    }

    @Transactional
    public Circular selectCircular(long circularId) {
        return circularRepository.findById(circularId)
                .orElseThrow(IllegalStateException::new);
    }

    @Transactional
    public Resource downloadCircular(long id) {
        try {
            Circular circular = selectCircular(id);
            Path filePath = Paths.get(multipartProperties.getLocation() + "/" + circular.getFileName());
            Resource resource = new UrlResource(filePath.toUri());
            System.out.println("uri: " + filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public void addCircular(Circular circular, MultipartFile multipartFile) {
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
            e.printStackTrace();
        }
    }

    @Transactional
    public void deleteCircular(long id) {
        Circular circular = selectCircular(id);
        try {
            if (new File(multipartProperties.getLocation() + circular.getFileName()).delete()) {
                circularRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
