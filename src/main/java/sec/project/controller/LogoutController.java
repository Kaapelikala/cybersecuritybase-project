package sec.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.SQLMiddleware;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
public class LogoutController {



    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String loadForm(
            @CookieValue(value = "session", defaultValue = "NOTLOGGEDIN") String session,
            HttpServletResponse response) {

        Cookie cookie = new Cookie ("session", "NOTLOGGEDIN");
        response.addCookie(cookie);
        return "login";

    }

}
