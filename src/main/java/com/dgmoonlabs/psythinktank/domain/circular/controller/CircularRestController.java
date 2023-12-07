package com.dgmoonlabs.psythinktank.domain.circular.controller;

import com.dgmoonlabs.psythinktank.domain.circular.service.CircularService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.CIRCULAR_KEY;

@RestController
@RequiredArgsConstructor
public class CircularRestController {
    private final CircularService circularService;

    @GetMapping("/circular")
    public ResponseEntity<Resource> getCircular(@RequestParam long id, Model model) {
        model.addAttribute(CIRCULAR_KEY.getText(), circularService.selectCircular(id));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .body(circularService.downloadCircular(id));
    }
}
