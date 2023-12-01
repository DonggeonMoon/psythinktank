package com.dgmoonlabs.psythinktank.domain.circular.controller;

import com.dgmoonlabs.psythinktank.domain.circular.model.Circular;
import com.dgmoonlabs.psythinktank.domain.circular.service.CircularService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.CIRCULARS_KEY;
import static com.dgmoonlabs.psythinktank.global.constant.KeyName.CIRCULAR_KEY;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.CIRCULAR_LIST;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.INSERT_CIRCULAR;

@Controller
@RequiredArgsConstructor
public class CircularController {
    private final CircularService circularService;

    @GetMapping("/circularList")
    public String getCirculars(Pageable pageable, Model model) {
        Page<Circular> circulars = circularService.selectAllCircular(pageable.getPageNumber());
        model.addAttribute(CIRCULARS_KEY.getText(), circulars);

        return CIRCULAR_LIST.getText();
    }

    @GetMapping("/circular")
    @ResponseBody
    public ResponseEntity<Resource> getCircular(@RequestParam(defaultValue = "1") int id, Model model) {
        Resource file = circularService.downloadCircular(id);
        ResponseEntity<Resource> response = ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE).body(file);
        model.addAttribute(CIRCULAR_KEY.getText(), circularService.selectOneCircular(id));
        return response;
    }

    @GetMapping("/insertCircular")
    public String getAddCircularForm() {
        return INSERT_CIRCULAR.getText();
    }

    @PostMapping("/circular")
    public String insertCircular(Circular circular, @RequestParam("file") MultipartFile file) throws IllegalStateException {
        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            int dotIndex = originalFilename.lastIndexOf(".");
            String extension = originalFilename.substring(++dotIndex);
            String fileName = UUID.randomUUID() + "." + extension;
            circular.setFileName(fileName);
            circularService.insertOneCircular(circular, file);
        }
        return CIRCULAR_LIST.redirect();
    }

    @DeleteMapping("/circular")
    public String deleteCircular(int id) {
        circularService.deleteOneCircular(id);
        return CIRCULAR_LIST.redirect();
    }
}
