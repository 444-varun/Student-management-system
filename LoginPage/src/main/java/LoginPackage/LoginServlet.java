package LoginPackage;

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

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String url = "jdbc:mysql://localhost:3306/school";
	private static final String username = "root";
	private static final String password = "Varun@444";

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession();
			session.setAttribute("AdminN","txtName");
			session.setAttribute("AdminP","txtPass");
			
			PrintWriter pw = response.getWriter();
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, username, password);
			String n = request.getParameter("txtName");
			String p = request.getParameter("txtPass");
			PreparedStatement pstate = con
					.prepareStatement("select name from AdminLogin where name = ? and pass = ?");
			pstate.setString(1, n);
			pstate.setString(2, p);
			ResultSet a = pstate.executeQuery();
			if (a.next()) {
				RequestDispatcher rd = request.getRequestDispatcher("Credentials");
				rd.forward(request, response);
			} else {
				pw.println("<!DOCTYPE html>");
				pw.println("<html lang='en'>");
				pw.println("<head>");
				pw.println("<meta charset='UTF-8'>");
				pw.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
				pw.println("<title>Login Failed</title>");
				pw.println("<style>");
				pw.println(
						"body { font-family: Arial, sans-serif; background-color: #f8d7da; text-align: center; margin-top: 50px; }");
				pw.println(
						".container { background: white; padding: 30px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); display: inline-block; }");
				pw.println("h2 { color: #721c24; }");
				pw.println(
						"a { display: inline-block; padding: 10px 20px; margin-top: 10px; color: white; text-decoration: none; border-radius: 5px; }");
				pw.println(".retry-btn { background-color: #007bff; }");
				pw.println(".retry-btn:hover { background-color: #0056b3; }");
				pw.println(".signup-btn { background-color: #28a745; }");
				pw.println(".signup-btn:hover { background-color: #218838; }");
				pw.println("</style>");
				pw.println("</head>");
				pw.println("<body>");
				pw.println("<div class='container'>");
				pw.println("<h2>❌ Login Failed! ❌</h2>");
				pw.println("<p>Please check your username and password.</p>");
				pw.println("<a href='Login.html' class='retry-btn'>🔄 Try Again</a><br>");
				pw.println("<a href='NewLogin.html' class='signup-btn'>🆕 Sign Up</a>");
				pw.println("</div>");
				pw.println("</body>");
				pw.println("</html>");

			}
			con.close();
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException ae) {
			System.out.println(ae.getMessage());
		}

	}

}
