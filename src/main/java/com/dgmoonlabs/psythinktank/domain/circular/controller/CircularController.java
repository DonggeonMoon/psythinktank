package com.dgmoonlabs.psythinktank.domain.circular.controller;

import com.dgmoonlabs.psythinktank.domain.circular.dto.CircularRequest;
import com.dgmoonlabs.psythinktank.domain.circular.service.CircularService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.dgmoonlabs.psythinktank.global.constant.KeyName.CIRCULARS_KEY;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.CIRCULAR_LIST;
import static com.dgmoonlabs.psythinktank.global.constant.ViewName.INSERT_CIRCULAR;

@Controller
@RequiredArgsConstructor
@RequestMapping("/circulars")
public class CircularController {
    private final CircularService circularService;

    @GetMapping
    public String getCirculars(Pageable pageable, Model model) {
        model.addAttribute(CIRCULARS_KEY.getText(), circularService.selectCirculars(pageable));
        return CIRCULAR_LIST.getText();
    }

    @GetMapping("/add")
    public String getAddCircularForm() {
        return INSERT_CIRCULAR.getText();
    }

    @PostMapping
    public String insertCircular(@Valid CircularRequest circularRequest) {
        circularService.addCircular(circularRequest);
        return "redirect:/circulars";
    }

    @DeleteMapping("/{id}")
    public String deleteCircular(@PathVariable long id) {
        circularService.deleteCircular(id);
        return "redirect:/circulars";
    }
}
