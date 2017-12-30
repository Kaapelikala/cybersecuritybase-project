package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sec.project.SQLMiddleware;
import sec.project.domain.PasswordEntry;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;

import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InsertController {

    @Autowired
    private SignupRepository signupRepository;

    private List<PasswordEntry> passwords;

    public InsertController() {
        this.passwords = new ArrayList<PasswordEntry>();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    @ResponseBody
    public String showPasswords(
            @CookieValue(value = "session", defaultValue = "NOTLOGGEDIN") String session,
            HttpServletResponse response) {
        int amount = 0;
        Statement st;
        ResultSet rs;
        try {
            st = SQLMiddleware.getConnection().createStatement();
            rs = st.executeQuery(
                    "select username from sessions where session = '" + session + "'");

            List<String> kissa = new ArrayList<String>();
            while (rs.next()) {
                kissa.add(rs.getString("username"));
                amount =1;
                break;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (session == null || session.equals("NOTLOGGEDIN") || amount != 1) {
            return "<html><head><title>NOT LOGGED IN</title></head><body><a href=\"login\">Login</a></body></html>";

        }


        String resp = "<!DOCTYPE html>" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\"\">" +
                "    <head>" +
                "        <title>Passwords!" +
                "        </title>" +
                "    </head>" +
                "    <body>" +
                "        <h1>Passwords!</h1>" +
                "        <table>" +
                "            <thead>" +
                "            <tr>" +
                "                <th>Owner</th>" +
                "                <th>Username</th>" +
                "                <th>Password</th>" +
                "                <th>Comment</th>" +
                "            </tr>" +
                "            </thead>" +
                "            <tbody>";
        try {
            st = SQLMiddleware.getConnection().createStatement();
            rs = st.executeQuery(
                    "select * from passwords where owner = (select username from sessions where session = '" + session + "')");
            while (rs.next()) {
                resp = resp + "<tr>";
                resp = resp + "<td>" + rs.getString("owner") + "</td>";
                resp = resp + "<td>" + rs.getString("username") + "</td>";
                resp = resp + "<td>" + rs.getString("password") + "</td>";
                resp = resp + "<td>" + rs.getString("comments") + "</td>";
                resp = resp + "</tr>";
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp = resp + "</tbody>" +
                "</table>" +
                "<form action=\"insert\" method=\"POST\">" +
                "<p><label for=\"username\">Username</label>: <input type=\"text\" name=\"username\" id=\"username\"/></p>" +
                "<p><label for=\"password\">Password</label>: <input type=\"passwod\" name=\"password\" id=\"password\"/></p>" +
                "<p><label for=\"comments\">Comment</label>: <input type=\"text\" name=\"comments\" id=\"comments\"/></p>" +
                "<p><input type=\"submit\" value=\"Submit\" /></p>" +
                "</form>" +
                "<b>LOGOUT BELOW!</b>" +
                "<form action=\"logout\" method=\"POST\">" +
                "<p><input type=\"submit\" value=\"LOGOUT!\" /></p>" +
                "</form>" +
                "<p></p>" +
                "</body>" +
                "</html>";

        return resp;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String addPassword(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String comments,
            @CookieValue(value = "session", defaultValue = "NOTLOGGEDIN") String session,
            HttpServletResponse response
    ) {

        Statement st;
        String owner = "";
        try {
            st = SQLMiddleware.getConnection().createStatement();
            ResultSet rs = st.executeQuery("select username from sessions where session = '" + session + "'");
            while (rs.next()) {
                owner = rs.getString("username");
                st.close();
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            st = SQLMiddleware.getConnection().createStatement();
            st.executeUpdate("insert into passwords values ('" + owner + "','" + username + "','" + password + "','" + comments + "')");
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return "redirect:/insert";
    }

}
