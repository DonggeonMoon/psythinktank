package com.mdg.PSYThinktank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PsyThinktankApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(PsyThinktankApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PsyThinktankApplication.class);
    }
}
