package newUpdateInfo;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class UpdateInfo
 */
@WebServlet("/UpdateInfo")
public class UpdateInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
    	session.getAttribute("AdminN");
    	session.getAttribute("AdminP");
    	if(session.getAttribute("AdminN")==null && session.getAttribute("AdminP")==null ) {
    		RequestDispatcher rd = request.getRequestDispatcher("Login.html");
    		rd.forward(request,response);
    	}
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html lang='en'>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
		out.println("<title>Profile Updated</title>");
		out.println("<style>");
		out.println(
				"body { font-family: Arial, sans-serif; background-color: #d4edda; text-align: center; margin-top: 50px; }");
		out.println(
				".container { background: white; padding: 30px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1); display: inline-block; }");
		out.println("h2 { color: #155724; }");
		out.println(
				"a { display: inline-block; padding: 10px 20px; margin-top: 10px; color: white; text-decoration: none; border-radius: 5px; background-color: #007bff; }");
		out.println("a:hover { background-color: #0056b3; }");
		out.println("</style>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class='container'>");
		out.println("<h2>✅ Profile Updated Successfully! ✅</h2>");
		out.println("<p>Your profile changes have been saved.</p>");
		out.println("<form action='Credentials' method='post' style='display: inline;'>");
		out.println("<button type='submit' style='background-color: #007BFF; color: white; border: none; ");
		out.println("padding: 10px 20px; font-size: 16px; cursor: pointer; border-radius: 5px;'>");
		out.println("🔄 Go to Student Records</button>");
		out.println("</form>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	
	}
}
