package RegisterPage;

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
import java.sql.Statement;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String url = "jdbc:mysql://localhost:3306/school";
	private static final String username = "root";
	private static final String password = "Varun@444";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession();
        	session.getAttribute("AdminN");
        	session.getAttribute("AdminP");
        	if(session.getAttribute("AdminN")==null && session.getAttribute("AdminP")==null ) {
        		RequestDispatcher rd = request.getRequestDispatcher("Login.html");
        		rd.forward(request,response);
        	}
			PrintWriter pw1 = response.getWriter();
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, username, password);
			String n1 = request.getParameter("userName");
			String e1 = request.getParameter("UserEmail");
			String phone1 = request.getParameter("UserPh");
			String pass1 = request.getParameter("Password");
			String insertQuery = "Insert into information(name ,email, phone , pass ) values(?,?,?,?)";
			PreparedStatement pstate1 = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
			pstate1.setString(1, n1);
			pstate1.setString(2, e1);
			pstate1.setString(3, phone1);
			pstate1.setString(4, pass1);
			int regis = pstate1.executeUpdate();
			int genID = 0;
			if (regis > 0) {
				ResultSet result = pstate1.getGeneratedKeys();
				if (result.next()) {
					genID = result.getInt(1);

				}
				pw1.println("<html>");
				pw1.println("<head>");
				pw1.println("<title>Registration Success</title>");
				pw1.println("<style>");
				pw1.println(".container { text-align: center; margin-top: 50px; font-family: Arial, sans-serif; }");
				pw1.println("h1 { color: green; }");
				pw1.println(
						"a { display: inline-block; padding: 10px 20px; background-color: blue; color: white; text-decoration: none; border-radius: 5px; }");
				pw1.println("a:hover { background-color: darkblue; }");
				pw1.println("</style>");
				pw1.println("</head>");
				pw1.println("<body>");
				pw1.println("<div class='container'>");
				pw1.println("<h1>Registered Successfully!</h1>");
				pw1.println("<h3>Registration ID is  " + genID + "</h3>");
				pw1.println("<h3>Save This ID for Future Reference </h3>");
				pw1.println("</div>");
				pw1.println("</body>");
				pw1.println("</html>");
			} else {
				pw1.println("<!DOCTYPE html>");
				pw1.println("<html lang='en'>");
				pw1.println("<head>");
				pw1.println("<meta charset='UTF-8'>");
				pw1.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
				pw1.println("<title>Registration Failed</title>");
				pw1.println("<style>");
				pw1.println(
						"body { font-family: Arial, sans-serif; background-color: #f8d7da; text-align: center; margin-top: 50px; }");
				pw1.println(
						".container { background: white; padding: 30px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); display: inline-block; }");
				pw1.println("h2 { color: #721c24; }");
				pw1.println(
						"a { display: inline-block; padding: 10px 20px; margin-top: 10px; color: white; text-decoration: none; border-radius: 5px; }");
				pw1.println(".signup-btn { background-color: #28a745; }");
				pw1.println(".signup-btn:hover { background-color: #218838; }");
				pw1.println("</style>");
				pw1.println("</head>");
				pw1.println("<body>");
				pw1.println("<div class='container'>");
				pw1.println("<h2>❌ Registration Failed! ❌</h2>");
				pw1.println("<p>Please try again.</p>");
				pw1.println("<a href='NewLogin.html' class='signup-btn'>🆕 Try Signing Up Again</a>");
				pw1.println("</div>");
				pw1.println("</body>");
				pw1.println("</html>");

			}
			conn.close();
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException ae) {
			System.out.println(ae.getMessage());
		}
	}
}
