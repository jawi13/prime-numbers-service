package com.project.primenumbersservice.request.controller;

import com.project.primenumbersservice.exception.FailedRequestException;
import com.project.primenumbersservice.request.service.PrimesService;
import com.project.primenumbersservice.response.Primes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

@RestController
public class PrimesController {

    private final PrimesService primesService;

    public PrimesController(PrimesService primesService) {
        this.primesService = primesService;
    }

    @GetMapping(value = "/primes/{initial}", produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public WebAsyncTask<ResponseEntity<String>> getResponse(@PathVariable("initial") Integer initial, @RequestHeader("Accept") String acceptHeader,
                                              @RequestParam(value = "alternateAlgo", required = false, defaultValue = "false") boolean isAlternateAlgo) throws FailedRequestException {
        try {
        return new WebAsyncTask<>(() -> {
            System.out.println("Task execution start...");
            Primes primes = primesService.assignPrimesValues(initial, isAlternateAlgo);
            primesService.cacheResultIfNotInCache(primes.initial, primes.primes);
            System.out.println("Task execution finished.");
            return acceptHeader.contentEquals(MediaType.APPLICATION_XML_VALUE) ?
                    ResponseEntity.ok(primes.asXmlString()) :
                    ResponseEntity.ok(primes.asJsonString());
        });
        } catch (FailedRequestException ex) {
            throw new FailedRequestException("Could not retrieve response from the application.", ex);
        }
    }
}