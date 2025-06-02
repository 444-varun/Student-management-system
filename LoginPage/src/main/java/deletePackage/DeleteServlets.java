package deletePackage;

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
import java.sql.SQLException;


@WebServlet("/DeleteServlets")
public class DeleteServlets extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String url = "jdbc:mysql://localhost:3306/school";
	private static final String username = "root";
	private static final String password = "Varun@444";
	
      
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		PrintWriter out = response.getWriter();
		String userID = request.getParameter("userID");
		try {
			// Connect to DB
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, username, password);

			// Prepare DELETE statement
			
			PreparedStatement pstate = conn.prepareStatement("delete from information WHERE id = ?");
			pstate.setString(1, userID);

			HttpSession session = request.getSession();
        	session.getAttribute("AdminN");
        	session.getAttribute("AdminP");
        	if(session.getAttribute("AdminN")==null && session.getAttribute("AdminP")==null ) {
        		RequestDispatcher rd = request.getRequestDispatcher("Login.html");
        		rd.forward(request,response);
        	}
			// Execute deletion
			int rowsAffected = pstate.executeUpdate();
			if (rowsAffected > 0) {
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Account Deleted</title>");
				out.println("<style>");
				out.println("body { font-family: Arial, sans-serif; text-align: center; margin-top: 100px; }");
				out.println(".container { background-color: #f8d7da; color: #721c24; padding: 20px; border-radius: 10px; display: inline-block; }");
				out.println("a { text-decoration: none; background-color: #28a745; color: white; padding: 10px 20px; border-radius: 5px; display: inline-block; margin-top: 10px; }");
				out.println("a:hover { background-color: #218838; }");
				out.println("</style>");
				out.println("</head>");
				out.println("<body>");
				out.println("<div class='container'>");
				out.println("<h2>Account Deleted Successfully!</h2>");
				out.println("<p>Your account has been removed from our system.</p>");
				out.println("<form action='Credentials' method='post' style='display: inline;'>");
				out.println("<button type='submit' style='background-color: #007bff; color: white; border: none; padding: 10px 15px; cursor: pointer; border-radius: 5px;'>");
				out.println("🔙 Back to Student Records");
				out.println("</button>");
				out.println("</form>");

				out.println("</div>");
				out.println("</body>");
				out.println("</html>");

			} else {
				out.println("<h2>Error: Account not found!</h2>");
			}

			// Close connection
			pstate.close();
			conn.close();

		} catch (ClassNotFoundException e ) {
			System.out.println(e.getMessage());
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	 }
}

