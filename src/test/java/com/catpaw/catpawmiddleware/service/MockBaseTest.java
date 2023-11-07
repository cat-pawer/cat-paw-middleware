package com.catpaw.catpawmiddleware.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public abstract class MockBaseTest {

    private AutoCloseable closeable;

    @BeforeEach
    void beforeEach() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void afterEach() throws Exception {
        closeable.close();
    }
}
