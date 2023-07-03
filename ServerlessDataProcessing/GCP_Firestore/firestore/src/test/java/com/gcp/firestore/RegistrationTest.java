package com.gcp.firestore;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import org.junit.jupiter.api.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest {

    private static final String TEST_PROJECT_ID = "avid-shape-390123";

    private Firestore firestoreDatabase;
    private CollectionReference regCollectionReference;
    private CollectionReference stateCollectionReference;

    @BeforeEach
    public void setUp() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(
                new FileInputStream("C:\\Users\\AVuser\\Desktop\\firestore\\src\\avid-shape-390123-a4c2e12795ea.json"));

        // Initialize Firestore options for the test project
        FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder()
                .setProjectId(TEST_PROJECT_ID)
                .setCredentials(googleCredentials)
                .build();

        // Connect to the Firestore database in your Google Cloud project
        firestoreDatabase = firestoreOptions.getService();

        // Create a test collection in the Firestore database
        regCollectionReference = firestoreDatabase.collection("Reg");
        stateCollectionReference = firestoreDatabase.collection("State");
    }

    @AfterEach
    public void tearDown() throws ExecutionException, InterruptedException {
        // Delete all documents in the test collections after each test
        // deleteCollection(regCollectionReference);
        // deleteCollection(stateCollectionReference);
    }

    @Test
    public void testRegisterUser() throws InterruptedException, ExecutionException {
        // Create a test user
        User user = new User("Testing Method", "password", "testmethod@example.com", "New York");

        // Call the method to register the user
        Registration.registerUser(user);

        // Retrieve the documents from Firestore
        DocumentSnapshot regDocumentSnapshot = getDocumentSnapshot(regCollectionReference.document(user.getEmail()));
        DocumentSnapshot stateDocumentSnapshot = getDocumentSnapshot(stateCollectionReference.document(user.getEmail()));

        // Assert the data in the "Reg" document
        Map<String, Object> regDocumentData = regDocumentSnapshot.getData();
        assertEquals(user.getName(), regDocumentData.get("Full Name"));
        assertEquals(user.getPassword(), regDocumentData.get("Password"));
        assertEquals(user.getLocation(), regDocumentData.get("Location"));

        // Assert the data in the "State" document
        Map<String, Object> stateDocumentData = stateDocumentSnapshot.getData();
        assertNotNull(stateDocumentData.get("Registration Timestamp"));
        assertEquals("offline", stateDocumentData.get("Logged_In"));
        assertNull(stateDocumentData.get("Logged_In_Timestamp"));
        assertFalse((boolean) stateDocumentData.get("Logged_Out"));
        assertNull(stateDocumentData.get("Logged_Out_Timestamp"));
    }

    private void deleteCollection(CollectionReference collectionReference) throws ExecutionException, InterruptedException {
        // Get all documents in the collection
        ApiFuture<QuerySnapshot> querySnapshotFuture = collectionReference.get();
        QuerySnapshot querySnapshot = querySnapshotFuture.get();
        for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
            // Delete each document in the collection
            ApiFuture<WriteResult> deleteResultFuture = documentSnapshot.getReference().delete();
            deleteResultFuture.get();
        }
    }

    private DocumentSnapshot getDocumentSnapshot(DocumentReference documentReference) throws InterruptedException, ExecutionException {
        ApiFuture<DocumentSnapshot> snapshotFuture = documentReference.get();
        return snapshotFuture.get();
    }
}