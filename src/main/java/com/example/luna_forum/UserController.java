package com.example.luna_forum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserRepository rep;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private boolean validateUser(User user) {
        String regexUsername = "[a-zA-ZæøåÆØÅ. \\-]{2,20}"; // Username must consist of 2-20 letters
        String regexPassword = "(?=.*[a-zA-ZæøåÆØÅ])(?=.*\\d)[a-zA-ZæøåÆØÅ\\d]{8,}"; // Password must consist of atleast 8 characters, contain 1 letter and 1 number
        boolean usernameOk = user.getUsername().matches(regexUsername);
        boolean passwordOk = user.getPassword().matches(regexPassword);
        if (usernameOk && passwordOk) {
            return true;
        }
        logger.error("Validating error");
        return false;
    }

    @PostMapping("/saveUser")
    public void saveUser(User user, HttpServletResponse response) throws IOException {
        if (!validateUser(user)) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error in validation - try again later");
        } else {
            if (!rep.saveUser(user)) {
                response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error in DB - try again later");
            }
        }
    }

    @Autowired
    private HttpSession session;

    @GetMapping("/login")
    public boolean login(User user) {
        if (rep.checkUsernameAndPassword(user)) {
            session.setAttribute("Innlogget", user);
            return true;
        }
        return false;
    }

    @GetMapping("/logout")
    public void logout() {
        session.removeAttribute("Innlogget");
    }


    @PostMapping("/krypterPassord")
    public void encryptPassword() {
        rep.encryptAllPasswords();
    }
}
