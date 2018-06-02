package org.BodyMapOfEmotion.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.BodyMapOfEmotion.servlet.DBUtils;
import org.BodyMapOfEmotion.servlet.MyUtils;
import org.json.JSONException;
import org.json.JSONObject;
 
@WebServlet(urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static JSONObject requestJson, finalJsonObj, msgBodyJsonObj;
    private static String userName = null;
    private static String password = null;
    private boolean hasError = false;
    private int errNum = 0;
    private String errMsg = "";
 
    public RegisterServlet() {
        super();
    }
 
    // Show Register page.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        // Forward to /WEB-INF/views/registerView.jsp
        // (Users can not access directly into JSP pages placed in WEB-INF)
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/registerView.jsp");
 
        dispatcher.forward(request, response);
 
    }
 
    // When the user enters userName & password, and click Register.
    // This method will be executed.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        finalJsonObj = new JSONObject();
        msgBodyJsonObj = new JSONObject();
		errMsg = "";
		errNum = 0;
		hasError = false;
    	
    		System.out.println(request);
    		StringBuffer jb = new StringBuffer();
    		String line = null;
    		try{
    			BufferedReader reader = request.getReader();
    			while((line = reader.readLine()) != null) {
    				jb.append(line);
    			}

        		System.out.println(jb);
        		requestJson = new JSONObject(jb.toString());
        		userName = requestJson.get("user_name").toString();
        		password = requestJson.get("password").toString();
        				
        		//UserAccount user = null;
        		 
            if (userName == null || password == null || userName.length() == 0 || password.length() == 0) {
                hasError = true;
                errMsg = "Required username and password. [CodeIdx:101]";
                errNum = 1;
            } else {
                Connection conn = MyUtils.getStoredConnection(request);
                
            		if(DBUtils.findUser(conn, userName) == null) {
            			//checked there is no existing user
                		//Register new user in the DB.
                		hasError = (DBUtils.registerNewUser(conn, userName, password))?false:true;
                		if (hasError) {
                            errMsg = "Cannot register this user. [CodeIdx:102]";
                            errNum = 1;
                     }
                		
            		}else {
            			//user already exists.
            			hasError = true;
            			errMsg = "This user already exists. [CodeIdx:103]";
            			errNum = 1;
            		}
     
            }
            

            // If error, return back the error message
            if (hasError) {
            		
                	msgBodyJsonObj = null;
    				finalJsonObj.put("ERRMSG", errMsg); 
    				finalJsonObj.put("ERRNUM", errNum);
    				finalJsonObj.put("MSGBODY", msgBodyJsonObj);
    				
    				response.setContentType("application/json");
    				response.getWriter().write(finalJsonObj.toString());
    				
            }
            // If no error, return message to user about successful registration
            else {
            		
            		msgBodyJsonObj.put("USER_NAME", userName);
            		msgBodyJsonObj.put("PASSWORD", password);
            		msgBodyJsonObj.put("MESSAGE", "Success Registration");
            		finalJsonObj.put("ERRNUM", errNum);
            		finalJsonObj.put("ERRMSG", errMsg);
            		finalJsonObj.put("MSGBODY", msgBodyJsonObj);
                		
    				response.setContentType("application/json");
    				response.getWriter().write(finalJsonObj.toString());
    				
            }
        		
    		}catch (SQLException e) {
            e.printStackTrace();
            hasError = true;
            errMsg = "SQLException. [CodeIdx:104]";
            errNum = 1;
        } catch (JSONException e) {
			e.printStackTrace();
		}
    }
 
}