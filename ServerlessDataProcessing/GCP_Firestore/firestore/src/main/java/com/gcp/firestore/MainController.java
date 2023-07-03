package com.gcp.firestore;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller class for handling HTTP requests related to user registration.
 * Includes methods for rendering the registration page and saving a new user.
 */
@Controller
public class MainController {

    /**
     * Renders the registration page.
     *
     * @param model The Model object to be used for adding attributes.
     * @return The name of the registration page view.
     */
    @GetMapping("/")
    public String registerPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registrationPage";
    }

    /**
     * Saves a new user based on the submitted form data.
     * Invokes the Registration class to register the user in the Firestore database.
     *
     * @param user The User object representing the user to be registered.
     * @return The redirect URL after the user is successfully registered else will get redirected to same page
     */
    @PostMapping("/register")
    private String saveNewUser(@ModelAttribute("user") User user) {
        try {
            Registration.registerUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            // Redirect to the same page if there is any error
            return "redirect:/";
        }

        // Redirect to the login page
        return "redirect:https://login-image-5rpozvvtfa-uc.a.run.app/login";
    }
}
