package com.googlefirestore.login;

/*
Code is based on the reference :
 [1]	“Create a Firestore database by using a server client library,” Google Cloud. [Online].
        Available: https://cloud.google.com/firestore/docs/create-database-server-client-library. [Accessed: 03-Jul-2023].
*/

import com.google.api.client.util.Value;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class provides methods to obtain an instance of Firestore with the appropriate credentials.
 */
public class GoogleCredentialsClass {

    @Value("${myProjectId}")
    private String myProjectId;

    /**
     * Retrieves an instance of Firestore with the default credentials.
     *
     * @return an instance of Firestore connected to the specified project
     * @throws FileNotFoundException if the required Google credentials file is not found
     * @throws IOException           if an I/O exception occurs during file handling
     */
    public Firestore fireStoreInstance() throws FileNotFoundException, IOException {

        // Initialize an instance of Firestore:
        FirestoreOptions firestoreOptions = FirestoreOptions
                .getDefaultInstance()
                .toBuilder()
                .setProjectId(myProjectId)
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .build();

        Firestore firestoreDatabase = firestoreOptions.getService();

        return firestoreDatabase;
    }
}
