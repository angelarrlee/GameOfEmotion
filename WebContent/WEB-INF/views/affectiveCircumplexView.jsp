<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Affective Circumplex</title>
   </head>
   <body>
 
      <h3>Affective Circumplex Page</h3>
      <p style="color: red;">${errMsg}</p>
 
 
      <form method="POST" action="${pageContext.request.contextPath}/affectiveCircumplex">
         <table border="0">
            <tr>
               <td>User Name</td>
               <td><input type="text" name="userName" value= "${user.userName}" /> </td>
            </tr>
            <tr>
               <td>Position X</td>
               <td><input type="text" name="posX" /> </td>
            </tr>
            <tr>
               <td>Position Y</td>
               <td><input type="text" name="posY" /> </td>
            </tr>
            <tr>
               <td>Radius</td>
               <td><input type="text" name="radius" /> </td>
            </tr>
            <tr>
               <td colspan ="2">
                  <input type="submit" value= "Insert" />
                  <a href="${pageContext.request.contextPath}/">Cancel</a>
               </td>
            </tr>
         </table>
      </form>
 
   </body>
</html>