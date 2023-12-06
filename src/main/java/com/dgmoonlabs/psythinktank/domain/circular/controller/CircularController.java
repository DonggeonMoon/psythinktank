package com.dgmoonlabs.psythinktank.domain.circular.controller;

import com.dgmoonlabs.psythinktank.domain.circular.dto.CircularRequest;
import com.dgmoonlabs.psythinktank.domain.circular.service.CircularService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.CIRCULARS_KEY;
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
