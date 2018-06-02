package org.BodyMapOfEmotion.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
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
 
@WebServlet(urlPatterns = { "/emotionRating" })
public class EmotionRatingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static JSONObject requestJson, finalJsonObj, msgBodyJsonObj;
    private static String userName = null;
    private static int rating;
    private boolean hasError = false;
    private int errNum = 0;
    private String errMsg = "";
 
    public EmotionRatingServlet() {
        super();
    }
 
    // Show Register page.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        // Forward to /WEB-INF/views/emotionRatingView.jsp
        // (Users can not access directly into JSP pages placed in WEB-INF)
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/emotionRatingView.jsp");
 
        dispatcher.forward(request, response);
 
    }
 
    
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
        		rating = requestJson.getInt("rating");
        		
            if (userName == null || userName.length() == 0) {
                hasError = true;
                errMsg = "Required username. [CodeIdx:501]";
                errNum = 5;
            } else if (rating < 0){
            		hasError = true;
            		errMsg = "Invalid input (rating). [CodeIdx:502]";
            		errNum = 5;
            }else {
            	
                Connection conn = MyUtils.getStoredConnection(request);
            		//Insert new emotion rating record into the DB.
            		hasError = (DBUtils.insertEmotionRating(conn, userName, rating))?false:true;
 
                if (hasError) {
                    errMsg = "Cannot insert this record. [CodeIdx:503]";
                    errNum = 5;
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
            // If no error, return message to user about successful insertion
            else {
            	
            		msgBodyJsonObj.put("USER_NAME", userName);
            		msgBodyJsonObj.put("MESSAGE", "Record is successfully inserted");
            		finalJsonObj.put("ERRNUM", errNum);
            		finalJsonObj.put("ERRMSG", errMsg);
            		finalJsonObj.put("MSGBODY", msgBodyJsonObj);
                		
    				response.setContentType("application/json");
    				response.getWriter().write(finalJsonObj.toString());
    				
            }
        				
    		}catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                errMsg = "SQLException. [CodeIdx:504]";
                errNum = 5;
        } catch (JSONException e) {
			e.printStackTrace();
		}
    	
 
 

        
        
    }
 
}