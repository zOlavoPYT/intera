package com.palma.intera.mavenproject1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    private final FirestoreService firestoreService;

    @Autowired
    public HelloController(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;
    }

    @GetMapping("/hello")
    public String hello() {
        try {
            String userId = "lGClYL6nrv28j2sMZbFp";
            String nome = firestoreService.getUserName(userId);
            logger.info("Mensagem personalizada gerada para o usuário: {}", nome);
            return String.format("Olá, %s!", nome);
        } catch (Exception e) {
            logger.error("Erro ao processar requisição", e);
            return "Olá, Visitante!";
        }
    }
}