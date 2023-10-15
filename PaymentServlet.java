package com.servlet.register;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // JDBC database connection information (move these to a configuration file in production)
    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/miniproject";
    private static final String dbUser = "root";
    private static final String dbPassword = "tiger";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form values
        String rollNumber = request.getParameter("rno");
        String subjects = request.getParameter("subjects");
        String query = request.getParameter("query");
        String utrId = request.getParameter("utr");

        try {
            // Load the MySQL JDBC driver (you can use a context listener for this)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the database connection
            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
                // Define the SQL query
                String sql = "INSERT INTO PAYMENTS (RollNo, Subjects, Query, UTRID) VALUES (?, ?, ?, ?)";

                // Create a PreparedStatement
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    // Set the parameters for the query
                    preparedStatement.setString(1, rollNumber);
                    preparedStatement.setString(2, subjects);
                    preparedStatement.setString(3, query);
                    preparedStatement.setString(4, utrId);

                    // Execute the query to insert data
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        // Data inserted successfully
                        response.sendRedirect("Rocket.html");
                    } else {
                        // Handle the case where no rows were affected
                        response.sendRedirect("error.html");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
