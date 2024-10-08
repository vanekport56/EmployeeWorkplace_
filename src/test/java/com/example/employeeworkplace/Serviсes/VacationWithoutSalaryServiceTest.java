package com.example.employeeworkplace.Serviсes;

import com.example.employeeworkplace.Models.Primary.VacationWithoutSalary;
import com.example.employeeworkplace.Services.DocumentServices.DocumentNumberGeneratorService;
import com.example.employeeworkplace.Services.DocumentServices.VacationWithoutSalaryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class VacationWithoutSalaryServiceTest {

    @Mock
    private DocumentNumberGeneratorService documentNumberGeneratorService;

    @Mock
    private VacationWithoutSalaryService vacationWithoutSalaryService;

    @InjectMocks
    private DocumentGeneratorService documentGeneratorService;

    @Test
    void testGenerateVacationWithoutSalaries() {
        MockitoAnnotations.openMocks(this);


        when(documentNumberGeneratorService.generateDocumentNumber("VWS")).thenReturn("VWS-0001");

        documentGeneratorService.generateVacationWithoutSalaries();


        verify(vacationWithoutSalaryService, times(10)).save(any(VacationWithoutSalary.class));
    }
}
