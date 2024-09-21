package com.example.employeeworkplace.Serviсes;

import com.example.employeeworkplace.Models.Primary.TaxCertificate;
import com.example.employeeworkplace.Repositories.Primary.TaxCertificateRepository;
import com.example.employeeworkplace.Services.DocumentServices.DocumentNumberGeneratorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class TaxCertificateServiceTest {

    @Mock
    private TaxCertificateRepository taxCertificateRepository;

    @Mock
    private DocumentNumberGeneratorService documentNumberGeneratorService;

    @InjectMocks
    private DocumentGeneratorService documentGeneratorService;

    @Test
    void testGenerateTaxCertificates() {
        MockitoAnnotations.openMocks(this);


        when(documentNumberGeneratorService.generateDocumentNumber("TXCRT")).thenReturn("TXCRT-0001");

        documentGeneratorService.generateTaxCertificates();


        verify(taxCertificateRepository, times(10)).save(any(TaxCertificate.class));
    }
}
