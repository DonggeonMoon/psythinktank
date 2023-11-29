package com.mdg.PSYThinktank.circular.service;

import com.mdg.PSYThinktank.circular.model.Circular;
import com.mdg.PSYThinktank.circular.repository.CircularRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    @SuppressWarnings("SpellCheckingInspection")
    public void insertOneCircular(Circular circular, HttpServletRequest request, MultipartFile file) {
//        String realPath = request.getServletContext().getRealPath("/uploadfile");
//        String filePath = realPath + "/" + circular.getFileName();

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
        return circularRepository.findAll(PageRequest.of(page, 10, Sort.by("circularId").ascending()));
    }

    @Transactional
    public Circular selectOneCircular(int circularId) {
        return circularRepository.findById(circularId).orElse(new Circular());
    }

    @Transactional
    @SuppressWarnings("SpellCheckingInspection")
    public void deleteOneCircular(int circularId, HttpServletRequest request) {
//        String realPath = request.getServletContext().getRealPath("/uploadfile");
//        String filePath = realPath + "/" + circular.getFileName();
        Circular circular = selectOneCircular(circularId);
        try {
            if (new File(multipartProperties.getLocation() + circular.getFileName()).delete()) {
                circularRepository.deleteById(circularId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Transactional
    public Resource downloadCircular(int circularId, HttpServletRequest request) {
        try {
            String realPath = request.getServletContext().getRealPath("/uploadfile");
            Circular circular = selectOneCircular(circularId);
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
