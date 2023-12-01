package com.dgmoonlabs.psythinktank.domain.circular.controller;

import com.dgmoonlabs.psythinktank.domain.circular.model.Circular;
import com.dgmoonlabs.psythinktank.domain.circular.service.CircularService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CircularController {
    private final CircularService circularService;

    @GetMapping("/circularList")
    public String getCirculars(Pageable pageable, Model model) {
        Page<Circular> circulars = circularService.selectAllCircular(pageable.getPageNumber());
        model.addAttribute("circulars", circulars);

        return "circularList";
    }

    @GetMapping("/circular")
    @ResponseBody
    public ResponseEntity<Resource> getCircular(@RequestParam(defaultValue = "1") int id, Model model) {
        Resource file = circularService.downloadCircular(id);
        ResponseEntity<Resource> response = ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/pdf").body(file);
        model.addAttribute("circular", circularService.selectOneCircular(id));
        return response;
    }

    @GetMapping("/insertCircular")
    public String getAddCircularForm() {
        return "insertCircular";
    }

    @PostMapping("/circular")
    public String insertCircular(Circular circular, @RequestParam("file") MultipartFile file) throws IllegalStateException {
        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String fileName = UUID.randomUUID() + "." + extension;
            circular.setFileName(fileName);
            circularService.insertOneCircular(circular, file);
        }
        return "redirect:/circularList";
    }

    @DeleteMapping("/circular")
    public String deleteCircular(int id) {
        circularService.deleteOneCircular(id);
        return "redirect:/circularList";
    }
}
