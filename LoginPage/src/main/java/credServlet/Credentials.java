package credServlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/Credentials")
public class Credentials extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String url = "jdbc:mysql://localhost:3306/school";
    private static final String username = "root";
    private static final String password = "Varun@444";  

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	PrintWriter out = response.getWriter();
        try {
            HttpSession session = request.getSession();
            session.getAttribute("AdminN");
            session.getAttribute("AdminP");
            if (session.getAttribute("AdminN") == null && session.getAttribute("AdminP") == null) {
                RequestDispatcher rd = request.getRequestDispatcher("Login.html");
                rd.forward(request, response);
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            PreparedStatement pstate = con.prepareStatement("SELECT id, name, email, phone FROM information");
            ResultSet rs = pstate.executeQuery(); 

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Student Details</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; align-items: center; justify-content: center; height: 100vh; margin: 0; }");
            out.println(".container { width: 80%; max-width: 900px; background: white; padding: 20px; border-radius: 10px; box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1); }");
            out.println("h1 { color: #333; margin-bottom: 10px; text-align: center; }");
            out.println("table { width: 100%; border-collapse: collapse; margin: 20px 0; }");
            out.println("th, td { padding: 12px; border: 1px solid #ddd; text-align: left; }");
            out.println("th { background-color: #007bff; color: white; font-size: 18px; }");
            out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
            out.println("tr:hover { background-color: #f1f1f1; }");
            out.println("button { background-color: #007bff; color: white; border: none; padding: 8px 15px; cursor: pointer; font-size: 14px; border-radius: 5px; transition: 0.3s; }");
            out.println("button.delete { background-color: #dc3545; }");
            out.println("button:hover { opacity: 0.8; }");
            out.println("</style>");
            out.println("<script>");
            out.println("function confirmDelete() { return confirm('Are you sure you want to delete this record?'); }");
            out.println("</script>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");

            // Heading
            out.println("<h1>Student Records</h1>");
            
            out.println("<form action='NewLogin.html' method='post' style='display: inline;'>");
            out.println("<button type='submit' class='new-btn'>New</button>");
            out.println("</form>");
            // Table
            out.println("<table>");
            out.println("<tr><th>Name</th><th>Email</th><th>Phone</th><th>Actions</th></tr>");

            // Display student records with Update & Delete buttons
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");

                out.println("<tr>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + email + "</td>");
                out.println("<td>" + phone + "</td>");
                out.println("<input type='hidden' name='id' value= '" +id +"' autocomplete='off'>");
                out.println("<td>");
                
                // Update Button
                out.println("<form action='Update.html' method='post' style='display: inline;'>");
                out.println("<input type='hidden' name='userID' value='" + id + "'>");
                out.println("<button type='submit' class='update-btn'>Update</button>");
                out.println("</form>");
            

                // Delete Button with confirmation alert
                out.println("<form action='DeleteServlets' method='post' style='display: inline;'>");
                out.println("<input type='hidden' name='userID' value='" + id + "'>");
                out.println("<button type='submit' class='delete-btn' onclick='return confirmDelete();'>Delete</button>");
                out.println("</form>");

                out.println("</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            // Back to Login Button
            out.println("<div style='text-align: center;'>");
            out.println("<form action='Login.html'><button type='submit'>Back to Login</button></form>");
            out.println("</div>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
