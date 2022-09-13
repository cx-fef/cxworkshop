import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.PrintWriter;
import com.google.common.html.HtmlEscapers;


public class Vulns {

	private boolean loggedIn = false;
	private Result result;
	private HttpServletResponse response;
	private HttpServletRequest req;

	// SQLi vulnerability
	public static void input (DataSource pool) {
		try {

			String email = request.getParameter ("email");
			String password = request.getParameter ("password");
			Connection connection = pool.getConnection();
			String sql = "";
			

			// vulnerable sqli
			
			sql = "select * from users where (email = '" + email + "' and password = '" + password + "')";
			Statement statement = connection.createStatement();
			result = statement.executeQuery(sql);

			// clean sqli
			/*
			sql = "select * from users where email = ? and password = ? ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			result = ps.executeQuery();
			*/
			
			if (result.next()) {
				loggedIn = true;
				doGet(result,req,response);
			} else
				out.println("No results");
		
		}
		catch(SQLException ex)	{
			out.println("Overly broad Exception " + ex.Message());
		}
	}

	// XSS vulnerability	
	protected void doGet(Result res, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    		try {
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
      
  			PrintWriter out = response.getWriter();
  			String loc = request.getParameter("location");
			loc+=res.getString("GEO_LOC");
			
			// clean xss
			/*
 			String escapedLocation = HtmlEscapers.htmlEscaper().escape(loc); 
  			out.println("<h1> Location: " + escapedLocation + "<h1>");
			*/

			//not clean xss
			out.println("<h1> Location: " + loc + "<h1>");
		}
		catch(IOException ex)	{
			out.println("Error caught by overly broad exception handler: " + ex.Message());
		}
	}
}
