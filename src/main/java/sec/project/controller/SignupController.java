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
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class SignupController {

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loadForm() {
        return "login";

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, @RequestParam String name, @RequestParam String password,
                        @CookieValue(value = "session", defaultValue = "NOTLOGGEDIN") String session, HttpServletResponse response) {

        try {
            Statement st = SQLMiddleware.getConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from users where password = '" + password + "' and username = '" + name + "' ");
            //st.close();
            while (rs.next()) {
                if (rs.getString("username") != null && !rs.getString("username").equals("")) {
                    String un = rs.getString("username");
                    String pw = rs.getString("password");
                    String up = un+pw;
                    String digest = org.apache.commons.codec.digest.DigestUtils.md5Hex(up);
                    st = SQLMiddleware.getConnection().createStatement();
                    st.executeUpdate("delete from sessions where username = '"+name+"'");
                    st.executeUpdate("insert into sessions values ('"+name+"','"+digest+"')");
                    st.close();
                    Cookie cookie = new Cookie("session",digest);
                    response.addCookie(cookie);
                    st.close();
                    return "redirect:/insert";
                }


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(Model model, @RequestParam String name, @RequestParam String password,
                           HttpServletResponse response) {
        try {
            Statement st = SQLMiddleware.getConnection().createStatement();
            ResultSet rs = st.executeQuery("select * from users where username = '" + name + "' and password = '" + password + "'");

            while (rs.next()) {
                st.close();
                return "login";

            }
            st = SQLMiddleware.getConnection().createStatement();
            st.executeUpdate("insert into users values ('" + name + "','" + password + "' )");
            st.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "login";
    }

}
