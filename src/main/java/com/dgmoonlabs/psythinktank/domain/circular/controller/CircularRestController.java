package com.dgmoonlabs.psythinktank.domain.circular.controller;

import com.dgmoonlabs.psythinktank.domain.circular.service.CircularService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.CIRCULAR_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/circulars")
public class CircularRestController {
    private final CircularService circularService;

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getCircular(@PathVariable long id, Model model) {
        model.addAttribute(CIRCULAR_KEY.getText(), circularService.getCircular(id));
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_PDF_VALUE
                ).body(circularService.downloadCircular(id));
    }
}
