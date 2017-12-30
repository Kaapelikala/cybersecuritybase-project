package sec.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sec.project.SQLMiddleware;
import sec.project.domain.Signup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CheckController {


    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    public String check() throws SQLException {
        String s = "<html><head><title>Check!</title></head><body>";
        Statement st;
        ResultSet rs ;
        st = SQLMiddleware.getConnection().createStatement();
        rs = st.executeQuery("select * from users");

        s=s+"<br><b>Users</b><br><br>";
        while (rs.next()) {

            s = s + (rs.getString("username")) + ", ";
            s = s + (rs.getString("password"));
            s = s + "<br>";
        }
        st.close();

        st = SQLMiddleware.getConnection().createStatement();
        rs = st.executeQuery("select * from sessions");
        s=s+"<br><b>Sessions</b><br><br>";
        while (rs.next()) {

            s = s + (rs.getString("username")) + ", ";
            s = s + (rs.getString("session"));
            s = s + "<br>";
        }
        st.close();


        st = SQLMiddleware.getConnection().createStatement();
        rs = st.executeQuery("select * from passwords");
        s=s+"<br><b>Passwords</b><br><br>";
        while (rs.next()) {

            s = s + (rs.getString("owner")) + ", ";
            s = s + (rs.getString("username")) + ", ";
            s = s + (rs.getString("password")) + ", ";
            s = s + (rs.getString("comments"));
            s = s + "<br>";
        }
        st.close();



        s = s + "</body></html>";
        return s;
    }

}
