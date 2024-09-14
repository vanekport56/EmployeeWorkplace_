package com.example.employeeworkplace.Controller;

import com.example.employeeworkplace.Models.Primary.*;
import com.example.employeeworkplace.Models.Secondary.User;
import com.example.employeeworkplace.Services.UserService;
import com.example.employeeworkplace.Services.CertificateService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Контроллер для обработки запросов на страницу заказа документа.
 */
@Controller
@RequiredArgsConstructor
public class OrderADocumentController {

    private static final Logger log = LoggerFactory.getLogger(OrderADocumentController.class);

    private final UserService userService;
    private final CertificateService certificateService;

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
        List<String> combinedNames = certificateNamesMap.values().stream()
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());

        List<String> certificateNames = combinedNames;
        model.addAttribute("certificateNames", certificateNames);


        List<OrderedDocuments> certificates = certificateService.getAllFilterCertificates(
                name != null && !name.isEmpty() ? name : null, currentUser);

        model.addAttribute("certificates", certificates);

        return "OrderADocument";
    }
    @GetMapping("/orderadocument/create")
    public String showCreateDocumentForm(@RequestParam(required = false) String type, Model model) {
        Map<String, List<String>> certificateNamesMap = certificateService.getCertificateNames();
        model.addAttribute("types", List.of("2-НДФЛ", "Справка"));
        List<String> names = (type != null && certificateNamesMap.containsKey(type))
                ? certificateNamesMap.get(type)
                : List.of();
        model.addAttribute("names", names);
        model.addAttribute("selectedType", type);
        return "CreateDocument";
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
        newCertificate.setUserId(currentUser.getId());
        Date sqlDate = Date.valueOf(LocalDate.now());
        newCertificate.setDateOfCreation(sqlDate);

        certificateService.saveOrderedDocument(newCertificate);

        return "redirect:/orderadocument/";
    }




}
