package com.medical.registry.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FicheMapperTest {

    private FicheMapper ficheMapper;

    @BeforeEach
    public void setUp() {
        ficheMapper = new FicheMapperImpl();
    }
}
