package com.example.employeeworkplace.Controller.Mvc.Documents;

import com.example.employeeworkplace.Models.Primary.*;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Services.DocumentServices.DocumentNumberGeneratorService;
import com.example.employeeworkplace.Services.UserServices.UserService;
import com.example.employeeworkplace.Services.SalaryAndCertificateService.CertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Контроллер для обработки запросов на страницу заказа документа.
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderADocumentController {


    private final UserService userService;
    private final CertificateService certificateService;
    private final DocumentNumberGeneratorService documentNumberGeneratorService;

    @GetMapping("/orderadocument/")
    public String orderADocument(Authentication authentication,
                                 @RequestParam(required = false) String name,
                                 Model model) {
        log.debug("Получение информации о пользователе и сертификатах для страницы заказа документа");


        log.info("Получено значение параметра name: {}", name);


        model.addAttribute("name", name != null ? name : "");


        User currentUser = userService.getUserByUsername(authentication.getName());
        model.addAttribute("userRoles", currentUser.getRole());
        model.addAttribute("selectedName", name != null ? name : "");


        Map<String, List<String>> certificateNamesMap = certificateService.getCertificateNames();
        List<String> certificateNames = certificateNamesMap.values().stream()
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());

        model.addAttribute("certificateNames", certificateNames);


        List<OrderedDocuments> certificates = certificateService.getAllFilterCertificates(
                name != null && !name.isEmpty() ? name : null, currentUser);

        model.addAttribute("certificates", certificates);

        return "ContentTemplates/OrderADocument";
    }

    @GetMapping("/orderadocument/create")
    public String showCreateDocumentForm(@RequestParam(required = false) String type, Model model) {
        Map<String, List<String>> certificateNamesMap = certificateService.getCertificateNames();
        model.addAttribute("types", new ArrayList<>(certificateNamesMap.keySet()));



        List<String> names = certificateNamesMap.values().stream()
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());








        model.addAttribute("names", names);

        model.addAttribute("selectedType", type != null ? type : "Выберите тип:");
        model.addAttribute("selectedName", names.isEmpty() ? "Выберите название:" : names.get(0));

        return "ContentTemplates/OrderCreate";
    }




    @PostMapping("/orderadocument/create")
    public String createDocument(@RequestParam String type,
                                 @RequestParam String name,
                                 Authentication authentication) {

        User currentUser = userService.getUserByUsername(authentication.getName());


        OrderedDocuments newCertificate;
        if ("2-НДФЛ".equals(type)) {
            newCertificate = new TaxCertificate();
        } else if ("Справка".equals(type)) {
            newCertificate = new Certificate();
        } else {
            throw new IllegalArgumentException("Не известный тип сертификата: " + type);
        }

        newCertificate.setNameOfTheCertificate(name);
        newCertificate.setTypeOfTheCertificate(type);

        String sertNum = documentNumberGeneratorService.generateDocumentNumber(
                "SERT");
        newCertificate.setCertificateNumber(sertNum);

        newCertificate.setUserId(currentUser.getId());
        Date sqlDate = Date.valueOf(LocalDate.now());
        newCertificate.setDateOfCreation(sqlDate);

        certificateService.saveOrderedDocument(newCertificate);

        return "redirect:/orderadocument/";
    }




}
