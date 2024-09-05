package com.dgmoonlabs.psythinktank.domain.sitemap.service;

import com.dgmoonlabs.psythinktank.global.exception.SitemapCreationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Service
@Slf4j
public class SitemapService {
    private static final Set<String> visitedUrls = new HashSet<>();
    private static final Queue<String> urlQueue = new LinkedList<>();

    @Value("${sitemap.location}")
    private String sitemapLocation;

    public void createSitemap(final String baseUrl) {
        try {
            urlQueue.add(baseUrl);
            Document sitemap = createDocument();
            while (!urlQueue.isEmpty()) {
                String currentUrl = urlQueue.poll();
                traverse(currentUrl, baseUrl, sitemap);
            }
            saveSitemap(sitemap);
        } catch (SitemapCreationFailedException | ParserConfigurationException | TransformerException e) {
            throw new SitemapCreationFailedException(e);
        }
    }

    private static void traverse(final String currentUrl, final String baseUrl, final Document sitemap) {
        try {
            if (!visitedUrls.contains(currentUrl)) {
                org.jsoup.nodes.Document document = Jsoup.connect(currentUrl).get();
                visitedUrls.add(currentUrl);

                Element urlSet = (Element) sitemap.getElementsByTagName("urlset").item(0);

                Element urlElement = sitemap.createElement("url");
                urlSet.appendChild(urlElement);

                Element loc = sitemap.createElement("loc");
                loc.appendChild(sitemap.createTextNode(currentUrl));
                urlElement.appendChild(loc);

                Element lastModificationDate = sitemap.createElement("lastmod");
                lastModificationDate.appendChild(sitemap.createTextNode(
                        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
                ));
                urlElement.appendChild(lastModificationDate);

                Elements links = document.select("a[href]");
                for (org.jsoup.nodes.Element link : links) {
                    String absHref = link.absUrl("href");
                    if (absHref.startsWith(baseUrl) && !visitedUrls.contains(absHref)) {
                        urlQueue.add(absHref);
                    }
                }
            }
        } catch (IOException e) {
            log.debug(e.getMessage(), e);
        }
    }

    private Document createDocument() throws ParserConfigurationException {
        Document document = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
        Element urlSet = document.createElement("urlset");
        urlSet.setAttribute("xmlns", "https://www.sitemaps.org/schemas/sitemap/0.9");
        document.appendChild(urlSet);
        return document;
    }

    private void saveSitemap(final Document sitemap) throws TransformerException {
        TransformerFactory
                .newDefaultInstance()
                .newTransformer()
                .transform(
                        new DOMSource(sitemap),
                        new StreamResult(new File(sitemapLocation + "sitemap.xml"))
                );
    }
}
