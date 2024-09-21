package com.example.employeeworkplace.Serviсes;

import com.example.employeeworkplace.Models.Primary.Certificate;
import com.example.employeeworkplace.Repositories.Primary.CertificateRepository;
import com.example.employeeworkplace.Services.DocumentServices.DocumentNumberGeneratorService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class CertificateServiceTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private DocumentNumberGeneratorService documentNumberGeneratorService;

    @InjectMocks
    private DocumentGeneratorService documentGeneratorService;

    @Test
    void testGenerateCertificates() {
        MockitoAnnotations.openMocks(this);


        when(documentNumberGeneratorService.generateDocumentNumber("CERT")).thenReturn("CERT-0001");

        documentGeneratorService.generateCertificates();


        verify(certificateRepository, times(10)).save(any(Certificate.class));
    }
}
