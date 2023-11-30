package com.dgmoonlabs.psythinktank.domain.circular.service;

import com.dgmoonlabs.psythinktank.domain.circular.model.Circular;
import com.dgmoonlabs.psythinktank.domain.circular.repository.CircularRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class CircularService {
    private final CircularRepository circularRepository;
    private final MultipartProperties multipartProperties;

    @Transactional
    public void insertOneCircular(Circular circular, MultipartFile file) {
        try {
            File file1 = new File(circular.getFileName());
            file.transferTo(file1);
            circularRepository.save(circular);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public Page<Circular> selectAllCircular(int page) {
        return circularRepository.findAll(PageRequest.of(page, 10, Sort.by("id").ascending()));
    }

    @Transactional
    public Circular selectOneCircular(long circularId) {
        return circularRepository.findById(circularId).orElse(new Circular());
    }

    @Transactional
    public void deleteOneCircular(long id) {
        Circular circular = selectOneCircular(id);
        try {
            if (new File(multipartProperties.getLocation() + circular.getFileName()).delete()) {
                circularRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public Resource downloadCircular(int id) {
        try {
            Circular circular = selectOneCircular(id);
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
}
