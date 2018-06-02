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
import javax.servlet.http.HttpSession;

import org.BodyMapOfEmotion.servlet.DBUtils;
import org.BodyMapOfEmotion.servlet.MyUtils;
import org.BodyMapOfEmotion.servlet.UserAccount;
import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
 
@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static JSONObject requestJson, finalJsonObj, msgBodyJsonObj;
    private static String userName = null;
    private static String password = null;
    private static String rememberMeStr = null;
    
    private UserAccount user = null;
    private boolean hasError = false;
    private String errMsg = null;
    private int errNum = 0;
 
    public LoginServlet() {
        super();
    }
 
    // Show Login page.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        // Forward to /WEB-INF/views/loginView.jsp
        // (Users can not access directly into JSP pages placed in WEB-INF)
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
 
        dispatcher.forward(request, response);
 
    }
 
    // When the user enters userName & password, and click Submit.
    // This method will be executed.
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        finalJsonObj = new JSONObject();
        msgBodyJsonObj = new JSONObject();
		errMsg = null;
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
        	    	rememberMeStr = "Y";
        				

    	        boolean remember = "Y".equals(rememberMeStr);
    	 
    	        if (userName == null || password == null || userName.length() == 0 || password.length() == 0) {
    	            hasError = true;
    	            errMsg = "Required username and password. [CodeIdx:201]";
    	            errNum = 2;
    	        } else {
    	            Connection conn = MyUtils.getStoredConnection(request);
    	            
                // Find the user in the DB.
            		System.out.println(userName);
            		System.out.println(password);
                user = DBUtils.findUser(conn, userName, password);
                
                if (user == null) {
                    hasError = true;
                    errMsg = "User Name or password invalid. [CodeIdx:202]";
                    errNum = 2;
                }
    	        }
        	    	
    	        // If error, forward to /WEB-INF/views/login.jsp
    	        if (hasError) {
    	        		
            		msgBodyJsonObj = null;
				finalJsonObj.put("ERRMSG", errMsg);
				finalJsonObj.put("ERRNUM", errNum);
				finalJsonObj.put("MSGBODY", msgBodyJsonObj);
				
				response.setContentType("application/json");
				response.getWriter().write(finalJsonObj.toString());
    					
    	        		
    	        }
    	        // If no error
    	        // Store user information in Session
    	        else {
    	            HttpSession session = request.getSession();
    	            MyUtils.storeLoginedUser(session, user);
    	 
    	            // If user checked "Remember me".
    	            if (remember) {
    	                MyUtils.storeUserCookie(response, user);
    	            }
    	            // Else delete cookie.
    	            else {
    	                MyUtils.deleteUserCookie(response);
    	            }
    	 
    	            // Redirect to userInfo page.
    	            //response.sendRedirect(request.getContextPath() + "/userInfo");
    	            
            		msgBodyJsonObj.put("MODEL_NUM", user.getModelNum());
            		finalJsonObj.put("ERRNUM", errNum);
            		finalJsonObj.put("ERRMSG", errMsg);
            		finalJsonObj.put("MSGBODY", msgBodyJsonObj);
            		
				response.setContentType("application/json");
				response.getWriter().write(finalJsonObj.toString());
    	            
    	        }
        	    	
    		}catch (SQLException e) {
            e.printStackTrace();
            hasError = true;
            errMsg = e.getMessage();
            errNum = 2;
        }catch (JSONException e) {
			e.printStackTrace();
		} 
    	
        
    }
 
}