package poc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class SiteInterface
 */
public class SiteInterface extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SiteInterface() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		final String DB_URL = "jdbc:mysql://localhost/SU_ARTIFACT";
		// Database credentials
		final String USER = "root";
		final String PASS = "root";

		try {
			// register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			// OPen A connection
			Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
			// Execute a statement
			Statement stmt = con.createStatement();

			String sql = "";
			String apiMode = request.getParameter("apimode");
			String catId ="";
			ResultSet rs;
			JSONArray arrJ = new JSONArray();
			JSONObject wrapperObject;
			if(apiMode.equalsIgnoreCase("category"))
			{
				sql = "select * from categoryDetails";
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					JSONObject obj = new JSONObject();
					obj.put("catId", rs.getInt("catId"));
					obj.put("catName", rs.getString("catName"));
					obj.put("catDescription", rs.getString("description"));
					arrJ.put(obj);
				}
				
				response.setContentType("application/json");

				PrintWriter out = response.getWriter();
				out.write(arrJ.toString());
				out.close();
			}
			else
			{
				catId = request.getParameter("catId");
				wrapperObject = new JSONObject();
				sql = "select * from imageDetails where catId ="+catId;
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					JSONObject obj = new JSONObject();
					obj.put("imgId", rs.getInt("imageId"));
					obj.put("imgDesc", rs.getString("imgDescription"));
					obj.put("imgTitle", rs.getString("imgTitle"));
					arrJ.put(obj);
				};
				
				wrapperObject.put("imgList", arrJ);
				wrapperObject.put("catId", catId);
				

				response.setContentType("application/json");

				PrintWriter out = response.getWriter();
				out.write(wrapperObject.toString());
				out.close();
				
			}

			

			
			

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
