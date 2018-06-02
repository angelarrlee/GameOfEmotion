package org.BodyMapOfEmotion.servlet;

public class UserAccount {
	 
	   private String userName;
	   private String password;
	   private int modelNum;		//0: Affective Circumplex model		1: Body Map Model
	    
	 
	   public UserAccount() {
	        
	   }
	    
	   public String getUserName() {
	       return userName;
	   }
	 
	   public void setUserName(String userName) {
	       this.userName = userName;
	   }
	 
	   public String getPassword() {
	       return password;
	   }
	 
	   public void setPassword(String password) {
	       this.password = password;
	   }
	   
	   public int getModelNum() {
		   return modelNum;
	   }
	   
	   public void setModelNum(int modelNum) {
		   this.modelNum = modelNum;
	   }
	 
	}