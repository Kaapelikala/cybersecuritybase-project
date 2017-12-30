package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.SQLMiddleware;
import sec.project.repository.SignupRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
public class SetupController {


    @RequestMapping(value = "/setup", method = RequestMethod.GET)
    public String Setup() throws SQLException {
        Statement st;
        try {
            st= SQLMiddleware.getConnection().createStatement();
            st.executeUpdate("drop table users");
            st.close();
        } catch (Exception e) {

        }

        st = SQLMiddleware.getConnection().createStatement();
        st.executeUpdate("Create table users (username unique not null, password not null)");
        st.close();

        try {
            st = SQLMiddleware.getConnection().createStatement();
            st.executeUpdate("drop table passwords");
            st.close();
        } catch (Exception e) {
        }
        st = SQLMiddleware.getConnection().createStatement();
        st.executeUpdate("Create table passwords (owner not null, username not null, password,comments)");
        st.close();


        try {
            st = SQLMiddleware.getConnection().createStatement();
            st.executeUpdate("drop table sessions");
            st.close();
        } catch (Exception e) {
        }
        st = SQLMiddleware.getConnection().createStatement();
        st.executeUpdate("Create table sessions (username not null unique, session not null)");
        st.close();




        st = SQLMiddleware.getConnection().createStatement();
        st.executeUpdate("insert into users values('admin','admin')");
        st.close();

        st = SQLMiddleware.getConnection().createStatement();
        st.executeUpdate("insert into passwords values('admin','admin','password','comment')");
        st.close();


        st = SQLMiddleware.getConnection().createStatement();
        st.executeUpdate("insert into sessions values('admin','adminpassword')");
        st.close();


        return "done";

    }

}
