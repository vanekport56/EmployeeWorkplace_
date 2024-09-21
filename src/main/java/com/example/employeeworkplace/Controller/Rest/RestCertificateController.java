package com.example.employeeworkplace.Controller.Rest;


import com.example.employeeworkplace.Models.ConstantsAndEnums.CertificateType;
import com.example.employeeworkplace.Models.Primary.Certificate;
import com.example.employeeworkplace.Models.Primary.OrderedDocuments;
import com.example.employeeworkplace.Models.Primary.TaxCertificate;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Services.SalaryAndCertificateService.CertificateService;
import com.example.employeeworkplace.Services.UserServices.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Контроллер для обработки запросов, связанных с документами.
 */

@RestController
@RequestMapping("/api/documents")
public class RestCertificateController {

    private final CertificateService certificateService;
    private final UserService userService;

    public RestCertificateController(CertificateService certificateService, UserService userService) {
        this.certificateService = certificateService;
        this.userService = userService;
    }

    /**
     * Получает список сертификатов.
     *
     * @return список сертификатов
     */
    @GetMapping("/filter/certificates")
    public ResponseEntity<List<Certificate>> getCertificates() {
        List<Certificate> certificates = certificateService.getAllCertificates();
        return ResponseEntity.ok(certificates);
    }

    /**
     * Получает список налоговых сертификатов.
     *
     * @return список налоговых сертификатов
     */
    @GetMapping("/filter/tax-certificates")
    public ResponseEntity<List<TaxCertificate>> getTaxCertificates() {
        List<TaxCertificate> taxCertificates = certificateService.getAllTaxCertificates();
        return ResponseEntity.ok(taxCertificates);
    }
    @GetMapping("/certificate-types")
    public ResponseEntity<List<String>> getCertificateTypes() {
        List<String> types = Arrays.stream(CertificateType.values())
                .map(CertificateType::getDisplayName)
                .collect(Collectors.toList());
        return ResponseEntity.ok(types);
    }
    @GetMapping("/certificate-names")
    public ResponseEntity<Map<String, List<String>>> getCertificateNames() {
        Map<String, List<String>> types = Arrays.stream(CertificateType.values())
                .collect(Collectors.toMap(
                        CertificateType::getDisplayName,
                        CertificateType::getPossibleNames
                ));
        return ResponseEntity.ok(types);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<OrderedDocuments>> filterCertificatesForUser(Authentication authentication,
                                                                            @RequestParam(required = false) String name)
                                                                           {
        User currentUser = userService.getUserByUsername(authentication.getName());

        List<OrderedDocuments> certificates = certificateService.getAllFilterCertificates(name, currentUser);
        return ResponseEntity.ok(certificates);
    }
}
