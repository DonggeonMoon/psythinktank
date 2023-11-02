package com.mdg.PSYThinktank.circular.controller;

import com.mdg.PSYThinktank.circular.model.Circular;
import com.mdg.PSYThinktank.circular.service.CircularService;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class CircularController {
    private final CircularService circularService;

    @GetMapping("/circularList")
    public String circularList(Pageable pageable, Model model) {
        Page<Circular> circulars = circularService.selectAllCircular(pageable.getPageNumber());

        int startNumber = (pageable.getPageNumber() / pageable.getPageSize()) * pageable.getPageSize() + 1;
        int endNumber =
                (pageable.getPageNumber() / pageable.getPageSize()) * pageable.getPageSize() + pageable.getPageSize();

        List<Pageable> pages = IntStream.rangeClosed(startNumber, endNumber)
                .mapToObj(i -> circulars.getPageable().withPage(i))
                .collect(Collectors.toList());

        model.addAttribute("circulars", circulars);
        model.addAttribute("currentBlock", circulars.getNumber() / circulars.getSize());
        model.addAttribute("pages", pages);
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
