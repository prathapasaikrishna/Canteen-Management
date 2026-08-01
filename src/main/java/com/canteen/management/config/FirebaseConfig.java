package com.canteen.management.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

import java.io.InputStream;

@Configuration
public class FirebaseConfig {
    @PostConstruct
    public void init() {

        InputStream serviceAccount =
                getClass().getClassLoader()
                        .getResourceAsStream("firebase-service-account.json");

        if (serviceAccount == null) {
            System.out.println("Firebase json not found");
            return;
        }

        try {

            FirebaseOptions options =
                    FirebaseOptions.builder()
                            .setCredentials(
                                    GoogleCredentials.fromStream(serviceAccount)
                            )
                            .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}