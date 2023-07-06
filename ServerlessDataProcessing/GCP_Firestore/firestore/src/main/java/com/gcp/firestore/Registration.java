/*
Code is based on the reference :
 [1]	“Create a Firestore database by using a server client library,” Google Cloud. [Online].
        Available: https://cloud.google.com/firestore/docs/create-database-server-client-library. [Accessed: 03-Jul-2023].

 [2]	“Getting data,” Google Cloud. [Online].
        Available: https://cloud.google.com/firestore/docs/query-data/get-data. [Accessed: 03-Jul-2023].
*/

package com.gcp.firestore;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.common.base.Preconditions;
import com.google.cloud.firestore.WriteResult;

public class Registration {

    /**
     * Registers a user by storing their information in the Firestore database.
     *
     * @param user The User object representing the user to be registered.
     * @throws InterruptedException If the execution is interrupted while performing asynchronous operations.
     * @throws ExecutionException   If an error occurs during the execution of asynchronous operations.
     * @throws IOException          If an I/O error occurs while initializing Firestore options.
     * @throws Exception            If any other exception occurs during the registration process.
     */

    public static void registerUser(User user) {

        String myProjectId = "avid-shape-390123";
        System.out.println("Project_Id" + myProjectId);

        try {

            /**
             * Initializes the Firestore options for connecting to the Firestore database.
             * The options include the project ID and credentials used for authentication.
             *
             * @param myProjectId The project ID for the Firestore database.
             * @throws IOException If an I/O error occurs while initializing Firestore options.
             * @throws RuntimeException If an exception occurs during the creation of Firestore options.
             */

            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\AVuser\\Desktop\\GiriSharanReddy\\dalhousie\\ServerlessDataProcessing\\GCP_Firestore\\firestore\\src\\avid-shape-390123-a4c2e12795ea.json"));

            FirestoreOptions firestoreOptions = FirestoreOptions
                    .getDefaultInstance()
                    .toBuilder()
                    .setProjectId(myProjectId)
                    // .setCredentials(GoogleCredentials.getApplicationDefault())
                    .setCredentials(googleCredentials)
                    .build();

            /**
             * Retrieves the Firestore instance from the FirestoreOptions.
             * The Firestore instance is used to interact with the Firestore database.
             *
             * @return The Firestore instance.
             */
            Firestore firestoreDatabase = firestoreOptions.getService();


            /**
             * Initializes the document reference for the "Reg" collection in the Firestore database.
             * The document reference is used to access a specific document in the "Reg" collection based on the user's email.
             *
             * @param user The User object representing the user to be registered.
             * @return The document reference for the user's document in the "Reg" collection.
             * @throws NullPointerException If the user email is null.
             */
            DocumentReference regDocumentReference = firestoreDatabase.collection("Reg")
                    .document(Preconditions.checkNotNull(user.getEmail()));


            /**
             * Initializes the document reference for the "State" collection in the Firestore database.
             * The document reference is used to access a specific document in the "State" collection based on the user's email.
             *
             * @param user The User object representing the user to be registered.
             * @return The document reference for the user's document in the "State" collection.
             * @throws NullPointerException If the user email is null.
             */
            DocumentReference stateDocumentReference = firestoreDatabase.collection("State")
                    .document(Preconditions.checkNotNull(user.getEmail()));


            // Add document data with id "Reg" using a hashmap
            Map<String, Object> regDocumentData = new HashMap<>();
            regDocumentData.put("Full Name", user.getName());
            regDocumentData.put("Password", user.getPassword());
            regDocumentData.put("Location", user.getLocation());

            System.out.println("Coming Here to update");

            // asynchronously write data to 'Reg' collection
            ApiFuture<WriteResult> regDocumentResult = regDocumentReference.set(regDocumentData);

            // Add document data with id "State" using a hashmap
            Map<String, Object> stateDocumentData = new HashMap<>();
            stateDocumentData.put("Registration Timestamp", FieldValue.serverTimestamp());
            stateDocumentData.put("Logged_In", "offline");
            stateDocumentData.put("Logged_In_Timestamp", null);
            stateDocumentData.put("Logged_Out", false);
            stateDocumentData.put("Logged_Out_Timestamp", null);

            // asynchronously write data to 'State' collection
            ApiFuture<WriteResult> stateDocumentResult = stateDocumentReference.set(stateDocumentData);

            // result.get() blocks on response
            System.out.println("Successfully Registered on : " + regDocumentResult.get().getUpdateTime());
            firestoreDatabase.close();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}