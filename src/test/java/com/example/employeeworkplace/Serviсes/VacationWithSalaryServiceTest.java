package com.example.employeeworkplace.Serviсes;

import com.example.employeeworkplace.Models.Primary.VacationWithSalary;
import com.example.employeeworkplace.Repositories.Primary.VacationWithSalaryRepository;
import com.example.employeeworkplace.Services.DocumentServices.DocumentNumberGeneratorService;
import com.example.employeeworkplace.Services.DocumentServices.VacationWithSalaryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class VacationWithSalaryServiceTest {

    @Mock
    private VacationWithSalaryRepository vacationWithSalaryRepository;

    @Mock
    private DocumentNumberGeneratorService documentNumberGeneratorService;

    @Mock
    private VacationWithSalaryService vacationWithSalaryService;

    @InjectMocks
    private DocumentGeneratorService documentGeneratorService;

    @Test
    void testGenerateVacationWithSalaries() {
        MockitoAnnotations.openMocks(this);


        when(documentNumberGeneratorService.generateDocumentNumber("VOS")).thenReturn("VOS-0001");

        documentGeneratorService.generateVacationWithSalaries();


        verify(vacationWithSalaryService, times(10)).save(any(VacationWithSalary.class));
    }
}
