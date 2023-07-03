package com.googlefirestore.home;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.FieldValue;

/**
 * This controller class handles the mapping for various endpoints related to the home page and user actions.
 */
@Controller
public class HomeController {

    /**
     * Maps the request for the home page and adds the email parameter to the model.
     *
     * @param email the email of the user
     * @param model the model object to pass data to the view
     * @return the view name for the home page
     */
    @GetMapping("/homes")
    public String homePage(@RequestParam("email") String email, Model model) {
        model.addAttribute("email", email);
        return "HomePage";
    }

    /**
     * Maps the request for retrieving available students and adds the usersList and email parameters to the model.
     *
     * @param email the email of the user
     * @param model the model object to pass data to the view
     * @return the view name for the home page
     * @throws FileNotFoundException if the required Google credentials file is not found
     * @throws IOException           if an I/O exception occurs during file handling
     * @throws InterruptedException if the current thread is interrupted while waiting
     * @throws ExecutionException   if the asynchronous execution of the Firestore query fails
     */
    @GetMapping("/available")
    public String availableStudents(@RequestParam("email") String email, Model model)
            throws FileNotFoundException, IOException, InterruptedException, ExecutionException {
        model.addAttribute("usersList", CheckAvailability.onlineCheck(email));
        model.addAttribute("email", email);
        return "HomePage";
    }


    /**
     * Maps the request for logging out a user and updates the Firestore document for the user's state.
     *
     * @param email the email of the user
     * @return a response string indicating the successful logout
     * @throws FileNotFoundException if the required Google credentials file is not found
     * @throws IOException           if an I/O exception occurs during file handling
     * @throws InterruptedException if the current thread is interrupted while waiting
     * @throws ExecutionException   if the asynchronous execution of the Firestore update fails
     */
    @GetMapping("/logout")
    @ResponseBody
    public String logoutSession(@RequestParam("email") String email) throws FileNotFoundException, IOException, InterruptedException, ExecutionException {

        GoogleCredentialsClass googleCredentialsClass = new GoogleCredentialsClass();

        DocumentReference stateDocument = googleCredentialsClass.fireStoreInstance().collection("State").document(email);
                stateDocument.update("Logged_Out", true).get();
                stateDocument.update("Logged_In", "offline");
                stateDocument.update("Logged_Out_Timestamp", FieldValue.serverTimestamp()).get();
        return "<h1>Thank you for attending Serverless Data Processing Course class</h1>";
    }
}