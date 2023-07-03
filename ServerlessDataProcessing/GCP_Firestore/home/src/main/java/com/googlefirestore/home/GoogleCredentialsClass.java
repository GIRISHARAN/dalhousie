package com.googlefirestore.home;

/*
Code is based on the reference :
 [1]	“Create a Firestore database by using a server client library,” Google Cloud. [Online].
        Available: https://cloud.google.com/firestore/docs/create-database-server-client-library. [Accessed: 03-Jul-2023].
*/

// import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.api.client.util.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

public class GoogleCredentialsClass {
    
    @Value("${myProjectId}")
    private String myProjectId;

    public Firestore fireStoreInstance() throws FileNotFoundException, IOException {

        // GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
        //         new FileInputStream("C:\\Users\\AVuser\\Desktop\\firestore\\src\\avid-shape-390123-a4c2e12795ea.json"));

        // Initialize an instance of Firestore:
        FirestoreOptions firestoreOptions = FirestoreOptions
                .getDefaultInstance()
                .toBuilder()
                .setProjectId(myProjectId)
                // .setCredentials(googleCredentials)
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build();

        Firestore firestoreDatabase = firestoreOptions.getService();

        return firestoreDatabase;
    }
}
