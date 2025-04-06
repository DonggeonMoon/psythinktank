package com.dgmoonlabs.psythinktank.global.limiter;

import org.springframework.stereotype.Component;

import java.util.concurrent.Semaphore;

@Component
public class DownloadLimiter {
    private final Semaphore semaphore = new Semaphore(3);

    public boolean tryAcquire() {
        return semaphore.tryAcquire();
    }

    public void release() {
        semaphore.release();
    }
}
