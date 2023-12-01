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
    public void insertOneCircular(Circular circular, MultipartFile multipartFile) {
        try {
            File file = new File(circular.getFileName());
            multipartFile.transferTo(file);
            circularRepository.save(circular);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public Page<Circular> selectAllCircular(int page) {
        return circularRepository.findAll(PageRequest.of(page, Pagination.SIZE.getValue(), Sort.by(CriteriaField.ID.getName()).ascending()));
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
