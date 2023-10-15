package com.servlet.register;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/show")
public class Display extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Display() {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String RollNo = request.getParameter("RollNo");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String databaseUrl = "jdbc:mysql://localhost:3306/miniproject";
            String username = "root";
            String password = "tiger";
            Connection connection = DriverManager.getConnection(databaseUrl, username, password);

            PreparedStatement ps = connection.prepareStatement("select * from payments where RollNo = ?");
            ps.setString(1, RollNo);

            out.println("<html><body>");

            ResultSet rs = ps.executeQuery();
            
            //Add Bootstrap
            out.println("<!-- Latest compiled and minified CSS -->\r\n"
            		+ "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css\">\r\n"
            		+ "\r\n"
            		+ "<!-- jQuery library -->\r\n"
            		+ "<script src=\"https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.slim.min.js\"></script>\r\n"
            		+ "\r\n"
            		+ "<!-- Popper JS -->\r\n"
            		+ "<script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js\"></script>\r\n"
            		+ "\r\n"
            		+ "<!-- Latest compiled JavaScript -->\r\n"
            		+ "<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js\"></script>");
            
            
            //Print data
            while (rs.next()) {
            	out.println("<br>");
            	out.println("<br>");
            	out.println("<br>");
            	out.println("<br>");
            	out.println("<div style='width:500px;margin:auto;margin-top:60px;'>");
            	out.println("<h2 style='text-align:center'>Student Details</h2>");
                out.println("<table class='table table-hover' style='border:2px solid black;'>");
                out.println("<tr>");
                out.println("<td>Student ID:</td>");
                out.println("<td>"+ rs.getString(1) +"</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>Subjects:</td>");
                out.println("<td>"+ rs.getString(2) +"</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>Query:</td>");
                out.println("<td>"+ rs.getString(3) +"</td>");
                out.println("</tr>");
                out.println("<tr>");
                out.println("<td>UTR ID:</td>");
                out.println("<td>"+ rs.getString(4) +"</td>");
                out.println("</tr>");
            }
            rs.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<html><body>");
            out.println("<h2>Error:</h2>");
            out.println("<p>An error occurred while retrieving student records.</p>");
            out.println("</body></html>");
        }
    }
}
