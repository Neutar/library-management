package com.hexad.librarymanagement.common.service;

import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

public interface TestDataService {

    void initializeData() throws IOException;
}
