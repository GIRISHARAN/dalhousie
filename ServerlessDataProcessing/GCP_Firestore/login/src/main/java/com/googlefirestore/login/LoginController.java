package com.googlefirestore.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller class for handling login-related requests.
 */
@Controller
public class LoginController {

    /**
     * Handles the GET request for the login page.
     *
     * @param model the Spring MVC model object
     * @return the view name for the login page
     */
    @GetMapping("/login")
    public String loginPage(Model model) {
        LoginDetails loginDetails = new LoginDetails();
        model.addAttribute("loginUser", loginDetails);
        return "LoginPage";
    }

    /**
     * Handles the POST request for validating user login credentials.
     *
     * @param loginDetails the LoginDetails object containing user login information
     * @param model        the Spring MVC model object
     * @return to home page name for the corresponding page after login validation along with email id of login user
     * @throws Exception if an exception occurs during the login validation process
     */
    @PostMapping("/validateUser")
    private String saveNewUser(@ModelAttribute("loginUser") LoginDetails loginDetails, Model model) throws Exception {
        if (ValidateUser.loginUserValidation(loginDetails)) {
            return "redirect:https://home-image-5rpozvvtfa-uc.a.run.app/homes?email=" + loginDetails.getEmail();
        } else {
            return "redirect:/";
        }
    }
}