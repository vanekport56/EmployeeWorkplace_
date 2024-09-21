package com.example.employeeworkplace.Serviсes;

import com.example.employeeworkplace.Models.Primary.SalaryOffset;
import com.example.employeeworkplace.Repositories.Primary.SalaryOffsetRepository;
import com.example.employeeworkplace.Services.DocumentServices.DocumentNumberGeneratorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class TestGenerateSalaryOffsets {

    @Mock
    private SalaryOffsetRepository salaryOffsetRepository;

    @Mock
    private DocumentNumberGeneratorService documentNumberGeneratorService;

    @InjectMocks
    private DocumentGeneratorService documentGeneratorService;

    @Test
    void testGenerateSalaryOffsets() {
        MockitoAnnotations.openMocks(this);


        when(documentNumberGeneratorService.generateDocumentNumber("SO")).thenReturn("SO-0001");

        documentGeneratorService.generateSalaryOffsets();


        verify(salaryOffsetRepository, times(15)).save(any(SalaryOffset.class));
    }
}
