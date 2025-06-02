package updatePackage;

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

@WebServlet("/updateServlets")
public class updateServlets extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String url = "jdbc:mysql://localhost:3306/school";
	private static final String username = "root";
	private static final String password = "Varun@444";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		try {
			// Connect to DB
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, username, password);
			

			String UserID = request.getParameter("userID");
			 String action = request.getParameter("action");

			// Get updated values from form
			String newUserName = request.getParameter("userName");
			String newEmail = request.getParameter("userEmail");
			String newPhone = request.getParameter("userPh");
			String newPass = request.getParameter("Password");
			
			HttpSession session = request.getSession();
        	session.getAttribute("AdminN");
        	session.getAttribute("AdminP");
        	if(session.getAttribute("AdminN")==null && session.getAttribute("AdminP")==null ) {
        		RequestDispatcher rd = request.getRequestDispatcher("Login.html");
        		rd.forward(request,response);
        	}

			PreparedStatement pstate3 = conn.prepareStatement("select name,email,phone,pass from information where id = ?");
			pstate3.setString(1,UserID);
			ResultSet rs = pstate3.executeQuery();
			while(rs.next()) {
				if(newUserName==null || newUserName.trim().isEmpty()) {
					newUserName = rs.getString("name");
				}
				if(newEmail==null || newEmail.trim().isEmpty()) {
					newEmail = rs.getString("email");
				}
				if(newPhone==null || newPhone.trim().isEmpty()) {
					newPhone = rs.getString("phone");
				}
				if(newPass==null || newPass.trim().isEmpty()) {
					newPass = rs.getString("pass");
				}
			}
			
			// Prepare UPDATE statement
			if("update-Name".equals(action)) {
				PreparedStatement pstateName = conn.prepareStatement("UPDATE information SET name=? WHERE id=?");
				pstateName.setString(1, newUserName);
				pstateName.setString(2, UserID);
				pstateName.executeUpdate();
				RequestDispatcher rd = request.getRequestDispatcher("UpdateInfo");
				rd.forward(request, response);
				pstateName.close();
			}else if("update-Email".equals(action)) {
				PreparedStatement pstateEmail = conn.prepareStatement("UPDATE information SET email=? WHERE id=?");
				pstateEmail.setString(1, newEmail);
				pstateEmail.setString(2, UserID);
				pstateEmail.executeUpdate();
				RequestDispatcher rd = request.getRequestDispatcher("UpdateInfo");
				rd.forward(request, response);
				pstateEmail.close();
		     }else if("update-Phone".equals(action)) {
			PreparedStatement pstatePhone = conn.prepareStatement("UPDATE information SET phone= ?WHERE id=?");		
			pstatePhone.setString(1, newPhone);
			pstatePhone.setString(2, UserID);
			pstatePhone.executeUpdate();
			RequestDispatcher rd = request.getRequestDispatcher("UpdateInfo");
			rd.forward(request, response);
			pstatePhone.close();
		}else if("update-Pass".equals(action)) {
			PreparedStatement pstatePass = conn.prepareStatement("UPDATE information SET pass=? WHERE id=?");
			pstatePass.setString(1, newPass);
			pstatePass.setString(2, UserID);
			pstatePass.executeUpdate();
			RequestDispatcher rd = request.getRequestDispatcher("UpdateInfo");
			rd.forward(request, response);
			pstatePass.close();
		}else if("update-All".equals(action)) {
			PreparedStatement pstateAll = conn.prepareStatement("UPDATE information SET name=?, email=?, phone=?, pass=? WHERE id=?");
			pstateAll.setString(1, newUserName);
			pstateAll.setString(2, newEmail);
			pstateAll.setString(3, newPhone);
			pstateAll.setString(4, newPass);
			pstateAll.setString(5, UserID);
			pstateAll.executeUpdate();
			RequestDispatcher rd = request.getRequestDispatcher("UpdateInfo");
			rd.forward(request, response);
			pstateAll.close();
		}else {
			    out.println("<!DOCTYPE html>");
				out.println("<html lang='en'>");
				out.println("<head>");
				out.println("<meta charset='UTF-8'>");
				out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
				out.println("<title>Update Failed</title>");
				out.println("<style>");
				out.println(
						"body { font-family: Arial, sans-serif; background-color: #f8d7da; text-align: center; margin-top: 50px; }");
				out.println(
						".container { background: white; padding: 30px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); display: inline-block; }");
				out.println("h2 { color: #721c24; }");
				out.println("p { color: #721c24; }");
				out.println(
						"a { display: inline-block; padding: 10px 20px; margin-top: 10px; color: white; text-decoration: none; border-radius: 5px; background-color: #dc3545; }");
				out.println("a:hover { background-color: #c82333; }");
				out.println("</style>");
				out.println("</head>");
				out.println("<body>");
				out.println("<div class='container'>");
				out.println("<h2>❌ Error: Update Failed! ❌</h2>");
				out.println("<p>Something went wrong while updating your profile. Please try again.</p>");
				out.println("<a href='update.html'>🔄 Try Again</a>");
				out.println("</div>");
				out.println("</body>");
				out.println("</html>");
			}
			conn.close();

		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
