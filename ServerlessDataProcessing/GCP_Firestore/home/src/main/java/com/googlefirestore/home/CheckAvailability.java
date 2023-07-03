package com.googlefirestore.home;

/*
Code is based on the reference :
 [1]	“Create a Firestore database by using a server client library,” Google Cloud. [Online].
        Available: https://cloud.google.com/firestore/docs/create-database-server-client-library. [Accessed: 03-Jul-2023].

 [2]	“Getting data,” Google Cloud. [Online].
        Available: https://cloud.google.com/firestore/docs/query-data/get-data. [Accessed: 03-Jul-2023].
*/


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

/**
 * This class provides a method to check the availability of online users in a Firestore database.
 */
public class CheckAvailability {

    /**
     * Checks the availability of online users in the Firestore database.
     *
     * @param email the email of the user for whom availability needs to be checked
     * @return a list of available users (excluding the user with the provided email)
     * @throws FileNotFoundException if the required Google credentials file is not found
     * @throws IOException           if an I/O exception occurs during file handling
     * @throws InterruptedException if the current thread is interrupted while waiting
     * @throws ExecutionException   if the asynchronous execution of the Firestore query fails
     */
    public static List<String> onlineCheck(String email)
            throws FileNotFoundException, IOException, InterruptedException, ExecutionException {

        List<String> availableUsersList = new ArrayList<String>();

        GoogleCredentialsClass googleCredentialsClass = new GoogleCredentialsClass();

        // Asynchronously retrieve all users from Firestore collection "State"
        ApiFuture<QuerySnapshot> query = googleCredentialsClass.fireStoreInstance().collection("State").get();

        /*// Synchronously retrieve all users from Firestore collection "State" and this blocks other code from execution until we get data
        ApiFuture<QuerySnapshot> query = googleCredentialsClass.fireStoreInstance().collection("State").get().get();*/

        // Get the response from the asynchronous query execution
        QuerySnapshot querySnapshot = query.get();

        // Get the list of documents retrieved from the query response
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        // Iterate through each document in the documents list
        for (QueryDocumentSnapshot document : documents) {

            // Print the document ID and the value of the "Logged_In" field
            System.out.println(document.getId() + "---" + document.getString("Logged_In"));

            // Check if the user is online and not the provided user email
            if (document.getString("Logged_In").equalsIgnoreCase("online")
                    && (!document.getId().equalsIgnoreCase(email))) {

                // Add the user ID to the availableUsersList
                System.out.println("Added User");
                availableUsersList.add(document.getId());
            }
        }

        // Return the list of available users
        return availableUsersList;
    }
}