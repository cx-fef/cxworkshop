import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.PrintWriter;
import com.google.common.html.HtmlEscapers;

/* feferman */


public class Vulns {

	private boolean loggedIn = false;
	private Result result;
	private HttpServletResponse response;
	private HttpServletRequest req;

	// SQLi vulnerability
	public static void input (DataSource pool) {
		try {

			// get the email and password from the request parameters
			String email = request.getParameter ("email");
			String password = request.getParameter ("password");
			// get a connection to the sql server
			Connection connection = pool.getConnection();
			String sql = "";
			

			// vulnerable SQLi
/*
			// this is what building a sql statement inline is like
			sql = "select * from users where (email = '" + email + "' and password = '" + password + "')";
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
*/			

			// clean sqli
			// this is the right way to use a preparedstatement, which can be used incorrectly, also. :)
			
			sql = "select * from users where email = ? and password = ? ";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, password);
			result = ps.executeQuery();
			
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

	// fake cleansing function
	protected string myCleanXSS(string taintedString)	{
		try	{
			string cleanedString;
			// do some stuff to the taintedString to clean it
			cleanedString = taintedString;
			return cleanedString;
		}
		catch(Exception ex)	{
			console.Message(ex.Message.String());
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

			loc = myCleanXSS(loc);

			//not clean xss
			out.println("<h1> Location: " + loc + "<h1>");
		}
		catch(IOException ex)	{
			out.println("Error caught by overly broad exception handler: " + ex.Message());
		}
	}
}
