package com.dgmoonlabs.psythinktank.domain.sitemap.controller;

import com.dgmoonlabs.psythinktank.domain.sitemap.service.SitemapService;
import com.dgmoonlabs.psythinktank.global.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SitemapRestController {
    private final SitemapService sitemapService;
    @Value("${sitemap.location}")
    private String sitemapLocation;

    @PostMapping("/sitemap")
    public ResponseEntity<Void> createSitemap(HttpServletRequest request) {
        sitemapService.createSitemap(
                RequestUtils.getBaseUrl(request)
        );
        return ResponseEntity.created(
                URI.create("/sitemap.xml")
        ).build();
    }

    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Resource> getSitemap() {
        try {
            return ResponseEntity.ok()
                    .body(
                            new UrlResource(
                                    Paths.get(sitemapLocation, "sitemap.xml").toUri()
                            )
                    );
        } catch (MalformedURLException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
