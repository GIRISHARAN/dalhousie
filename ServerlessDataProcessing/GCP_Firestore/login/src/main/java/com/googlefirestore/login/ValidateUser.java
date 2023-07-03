package com.googlefirestore.login;

/*
Code is based on the reference :
 [1]	“Create a Firestore database by using a server client library,” Google Cloud. [Online].
        Available: https://cloud.google.com/firestore/docs/create-database-server-client-library. [Accessed: 03-Jul-2023].

 [2]	“Getting data,” Google Cloud. [Online].
        Available: https://cloud.google.com/firestore/docs/query-data/get-data. [Accessed: 03-Jul-2023].
*/

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This class provides methods to validate user login credentials using Firestore database.
 */
public class ValidateUser {

    /**
     * Validates the login credentials of a user.
     *
     * @param loginDetails the login details of the user
     * @return true if the login credentials are valid, false otherwise
     * @throws IOException          if an I/O exception occurs during file handling
     * @throws InterruptedException if the current thread is interrupted while waiting
     * @throws ExecutionException   if the asynchronous execution of the Firestore query fails
     */
    public static boolean loginUserValidation(LoginDetails loginDetails)
            throws IOException, InterruptedException, ExecutionException {

        GoogleCredentialsClass googleCredentialsClass = new GoogleCredentialsClass();

        // asynchronously retrieve all users
        ApiFuture<QuerySnapshot> query = googleCredentialsClass.fireStoreInstance().collection("Reg").get();
        // ...
        // query.get() blocks on response
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            // Print the document ID and password
            System.out.println(document.getId() + "---" + document.getString("Password"));

            // Check if the document ID matches the user's email and the password matches the user's password
            if (document.getId().equalsIgnoreCase(loginDetails.getEmail())
                    && document.getString("Password").equals(loginDetails.getPassword())) {
                // Retrieve the document reference for the user in the "State" collection
                DocumentReference stateDocument = googleCredentialsClass.fireStoreInstance().collection("State").document(document.getId());

                // Update the "Logged_In" field to "online" and block the execution until the update is complete
                stateDocument.update("Logged_In", "online").get();

                // Update the "Logged_In_Timestamp" field with the server timestamp and block the execution until the update is complete
                stateDocument.update("Logged_In_Timestamp", FieldValue.serverTimestamp()).get();

                // Return true to indicate that the login credentials are valid
                return true;
            }
        }

        // Return false if no matching document is found or the password does not match
        return false;
    }
}