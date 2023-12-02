package com.dgmoonlabs.psythinktank.domain.circular.controller;

import com.dgmoonlabs.psythinktank.domain.circular.dto.CircularRequest;
import com.dgmoonlabs.psythinktank.domain.circular.service.CircularService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute(CIRCULARS_KEY.getText(), circularService.selectCirculars(pageable));
        return CIRCULAR_LIST.getText();
    }

    @GetMapping("/circular")
    @ResponseBody
    public ResponseEntity<Resource> getCircular(@RequestBody long id, Model model) {
        model.addAttribute(CIRCULAR_KEY.getText(), circularService.selectCircular(id));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .body(circularService.downloadCircular(id));
    }

    @GetMapping("/insertCircular")
    public String getAddCircularForm() {
        return INSERT_CIRCULAR.getText();
    }

    @PostMapping("/circular")
    public String insertCircular(CircularRequest circularRequest) {
        circularService.addCircular(circularRequest);
        return CIRCULAR_LIST.redirect();
    }

    @DeleteMapping("/circular")
    public String deleteCircular(long id) {
        circularService.deleteCircular(id);
        return CIRCULAR_LIST.redirect();
    }
}
