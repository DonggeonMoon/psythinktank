package com.mdg.PSYThinktank.controller;

import com.mdg.PSYThinktank.model.Circular;
import com.mdg.PSYThinktank.service.CircularService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CircularController {
    private final CircularService circularService;

    @GetMapping("/circularList")
    public String circularList(@RequestParam(defaultValue = "1") int page, Model model) {
        Page<Circular> circularPage = circularService.selectAllCircular(page - 1);
        model.addAttribute("circularList", circularPage);
        model.addAttribute("currentBlock", circularPage.getNumber() / circularPage.getSize());
        return "circularList";
    }

    @GetMapping("/circular")
    @ResponseBody
    public ResponseEntity<Resource> viewCircular(@RequestParam(defaultValue = "1") int circularId, Model model, HttpServletRequest request) {
        Resource file = circularService.downloadCircular(circularId, request);
        ResponseEntity<Resource> response = ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/pdf").body(file);
        model.addAttribute("circular", circularService.selectOneCircular(circularId));
        return response;
    }

    @GetMapping("/insertCircular")
    public String insertCircular() {
        return "insertCircular";
    }

    @PostMapping("/circular")
    public String insertCircular2(Circular circular, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            assert originalFilename != null;
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            String fileName = UUID.randomUUID() + "." + extension;
            circular.setFileName(fileName);
            circularService.insertOneCircular(circular, request, file);
        }
        return "redirect:/circularList";
    }

    @DeleteMapping("/circular")
    public String deleteCircular(int circularId, HttpServletRequest request) {
        circularService.deleteOneCircular(circularId, request);
        return "redirect:/circularList";
    }
}
