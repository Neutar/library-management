package com.hexad.librarymanagement.common.controller;

import com.hexad.librarymanagement.common.service.TestDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/initialize")
@RequiredArgsConstructor
public class TestDataController {

    private final TestDataService testDataService;

    @PostMapping
    public void initializeData() throws IOException {
        testDataService.initializeData();
    }
}
