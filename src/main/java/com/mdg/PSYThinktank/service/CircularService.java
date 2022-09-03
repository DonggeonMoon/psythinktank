package com.mdg.PSYThinktank.service;

import com.mdg.PSYThinktank.model.Circular;
import com.mdg.PSYThinktank.repository.CircularRepository;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    @SuppressWarnings("SpellCheckingInspection")
    public void insertOneCircular(Circular circular, HttpServletRequest request, MultipartFile file) {
        String realPath = request.getServletContext().getRealPath("/uploadfile");
        String filePath = realPath + "/" + circular.getFileName();
        try {
            file.transferTo(new File(filePath));
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
        String realPath = request.getServletContext().getRealPath("/uploadfile");
        Circular circular = selectOneCircular(circularId);
        String filePath = realPath + "/" + circular.getFileName();
        try {
            if (new File(filePath).delete()) {
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
            Path filePath = Paths.get(realPath + "/" + circular.getFileName());
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
