package com.example.springsecurityinaction.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextCallable;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HelloController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v2/hello")
    public String helloV2() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return "Hello! " + authentication.getName();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v3/hello")
    public String helloV3(Authentication authentication) {
        return "Hello! " + authentication.getName();
    }

    @Async
    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("/async/hello")
    public void helloAsync() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        log.info("Hello! " + authentication.getName());
    }

    @GetMapping("/call/hello")
    @ResponseStatus(HttpStatus.OK)
    public String helloCallable()
        throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
//            new DelegatingSecurityContextRunnable(delegate);
            var contextTask = new DelegatingSecurityContextCallable<>(callableTask());

            return executorService.submit(contextTask).get(10, TimeUnit.SECONDS);
        } finally {
            executorService.shutdown();
        }
    }
    @GetMapping("/v2/call/hello")
    @ResponseStatus(HttpStatus.OK)
    public String helloCallableV2() throws Exception {
//        new DelegatingSecurityContextExecutor(delegate);
//        new DelegatingSecurityContextScheduledExecutorService(delegate);
        ExecutorService executorService = new DelegatingSecurityContextExecutorService(
            Executors.newCachedThreadPool()
        );

        try {
            return executorService.submit(callableTask()).get(10, TimeUnit.SECONDS);
        } finally {
            executorService.shutdown();
        }
    }

    @GetMapping("/v3/call/hello")
    @Async
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<String> helloCallableV3() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return CompletableFuture.completedFuture(authentication.getName());
    }

    private static Callable<String> callableTask() {
        return () -> {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            return authentication.getName();
        };
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/health")
    public String health() {
        return "I am alive!";
    }

}
