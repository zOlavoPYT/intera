package com.palma.intera.mavenproject1.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService {

    private static final Logger logger = LoggerFactory.getLogger(FirestoreService.class);

    private final Firestore firestore;

    @Autowired
    public FirestoreService(Firestore firestore) {
        this.firestore = firestore;
    }

    public String getUserName(String userId) {
        try {
            DocumentReference docRef = firestore.collection("users").document(userId);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                return document.getString("nome");
            } else {
                logger.warn("Usuário com ID {} não encontrado", userId);
                return "Visitante";
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Erro ao buscar usuário no Firestore", e);
            Thread.currentThread().interrupt();
            return "Visitante";
        }
    }
}