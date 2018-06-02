package org.BodyMapOfEmotion.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import org.BodyMapOfEmotion.servlet.UserAccount;
 
public class DBUtils {
 
	private static ArrayList<Integer> numbers = new ArrayList<Integer>(Arrays.asList(0, 1));
	
    public static UserAccount findUser(Connection conn, //
            String userName, String password) throws SQLException {
 
        String sql = "Select a.USER_NAME, a.PASSWORD, a.MODEL_NUM from User_Account a " //
                + " where a.USER_NAME = ? and a.PASSWORD= ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);
        pstm.setString(2, password);
        ResultSet rs = pstm.executeQuery();
 
        if (rs.next()) {
            UserAccount user = new UserAccount();
            user.setUserName(userName);
            user.setPassword(password);
            user.setModelNum(rs.getInt("MODEL_NUM"));
            return user;
        }
        return null;
    }
    
    public static UserAccount findUser(Connection conn, String userName) throws SQLException {
 
        String sql = "Select a.USER_NAME, a.PASSWORD, a.MODEL_NUM from User_Account a "//
                + " where a.USER_NAME = ? ";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);
 
        ResultSet rs = pstm.executeQuery();
 
        if (rs.next()) {
            String password = rs.getString("Password");
            UserAccount user = new UserAccount();
            user.setUserName(userName);
            user.setPassword(password);
            return user;
        }
        return null;
    }

    public static Boolean registerNewUser(Connection conn,  //
    			String userName, String password) throws SQLException {
    		
    		String sql = "Insert into User_Account (`USER_NAME`, `PASSWORD`, `MODEL_NUM`)" + 
    				"values (?, ?, ?);";
    		
    		PreparedStatement pstm = conn.prepareStatement(sql);
    		pstm.setString(1, userName);
    		pstm.setString(2, password);
    		
    		//randomly assign a model for the user
    		Collections.shuffle(numbers);
    		pstm.setInt(3, numbers.get(0));
    		
    		int result = pstm.executeUpdate();
    		if(result > 0) {
    			return true;
    		}
    		
    		return false;
    } 
    
    public static Boolean insertEmotionRating(Connection conn,  //
			String userName, int rating) throws SQLException {
		
		String sql = "Insert into EMOTION_RATING (`USER_NAME`, `RATING`, `CREATEDTIME`)" + 
				"values (?, ?, now());";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setInt(2, rating);
		
		int result = pstm.executeUpdate();
		if(result > 0) {
			return true;
		}
		
		return false;
} 
    
    
    public static Boolean insertAffCircModelRecord (Connection conn,
    			String userName, float posX, float posY, int radius) throws SQLException {
    	
    		String sql = "Insert into AFFECTIVECIRCUMPLEX_MODEL (`USER_NAME`, `POSITION_X`, `POSITION_Y`, `RADIUS`, `CREATEDTIME`)" +
    				"values (?, ?, ?, ?, now())";
    		
    		PreparedStatement pstm = conn.prepareStatement(sql);
    		pstm.setString(1, userName);
    		pstm.setFloat(2, posX);
    		pstm.setFloat(3, posY);
    		pstm.setInt(4, radius);
    		
    		int result = pstm.executeUpdate();
    		if(result > 0) {
    			return true;
    		}
    	
    		return false;
    	
    }
    
    public static Boolean insertBodyMapModelRecord (Connection conn,
			String userName, String iconType, float posX, float posY, Timestamp nowTime) throws SQLException {
	
		String sql = "Insert into BODYMAP_MODEL (`USER_NAME`, `ICON_TYPE`, `POSITION_X`, `POSITION_Y`, `CREATEDTIME`)" +
				"values (?, ?, ?, ?, ?)";
		
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, userName);
		pstm.setString(2, iconType);
		pstm.setFloat(3, posX);
		pstm.setFloat(4, posY);
		pstm.setTimestamp(5, nowTime);
		
		int result = pstm.executeUpdate();
		if(result > 0) {
			return true;
		}
	
		return false;
	
}
 
    
 
}