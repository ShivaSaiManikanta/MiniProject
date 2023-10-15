package com.servlet.register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Login")
public class Login extends HttpServlet {

    // Query to check if the login credentials are valid
    private static final String LOGIN_QUERY = "SELECT * FROM REGISTER WHERE UName = ? AND Password = ?";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get PrintWriter
        PrintWriter pw = res.getWriter();
        // Set content type
        res.setContentType("text/html");
        // Read the form values
        String uname = req.getParameter("uname");
        String password = req.getParameter("Password");

        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Establish the connection with the database
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/miniproject", "root", "tiger");
             PreparedStatement ps = con.prepareStatement(LOGIN_QUERY);) {

            // Set the values
            ps.setString(1, uname);
            ps.setString(2, password);

            // Execute the query
            ResultSet resultSet = ps.executeQuery();

            if (uname.equals("Admin@anurag.edu.in") && password.equals("Anurag@cvsr")) {
                // Admin login successful
                String redirectURL = "AdminHome.html";
                res.sendRedirect(redirectURL);
            } if (resultSet.next()) {
                // Regular user login successful
                String redirectURL = "PPHome.html";
                res.sendRedirect(redirectURL);
            } else {
                // Login failed
                pw.println("Login failed. Please check your email and password.");
            }

        } catch (SQLException se) {
            pw.println(se.getMessage());
            se.printStackTrace();
        } catch (Exception e) {
            pw.println(e.getMessage());
            e.printStackTrace();
        }

        // Close the stream
        pw.close();
    }
}
