package com.palma.intera.mavenproject1;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.io.IOException;

@SpringBootApplication
public class Mavenproject1 {

    private static final Logger logger = LoggerFactory.getLogger(Mavenproject1.class);

    public static void main(String[] args) {
        SpringApplication.run(Mavenproject1.class, args);
    }

    @PostConstruct
    public void initializeFirebase() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                InputStream serviceAccount = new ClassPathResource(
                        "config/teste-intera-firebase-adminsdk-fbsvc-71fc429596.json"
                ).getInputStream();

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options);
                logger.info("Firebase inicializado com sucesso!");
            }
        } catch (IOException e) {
            logger.error("Erro ao inicializar o Firebase: ", e);
            throw new RuntimeException("Falha na inicialização do Firebase", e);
        }
    }

    @Bean
    public Firestore firestore() {
        if (FirebaseApp.getApps().isEmpty()) {
            throw new IllegalStateException("FirebaseApp não foi inicializado");
        }
        return FirestoreClient.getFirestore();
    }
}