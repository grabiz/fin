/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author NGUYEN
 */
@WebServlet("/insert-user")
public class InsertUser extends HttpServlet{
     
    protected void doInsert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String dbUrl ="jdbc:mysql://localhost:3306/trainning";
        String userName="root";
        String password="";
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(dbUrl, userName, password);
            String sqlCommand = "Insert into user (id,first_name,last_name,email) values (?,?,?,?)";
            PreparedStatement pst = connection.prepareStatement(sqlCommand);
           
            //if((Integer.parseInt(req.getParameter("id")))<=0 || CountID.count.contains((Integer.parseInt(req.getParameter("id"))))==true )
            if((Integer.parseInt(req.getParameter("id")))<=0)
            {
               req.setAttribute("message1", "New ID must be unexistent and greater than 0 !"); 
               getServletContext().getRequestDispatcher("/insertuser.jsp").forward(req, resp);
            }
            else if (((String)req.getParameter("firstname")).equals(""))
            { 
                req.setAttribute("message2", "First name must be filled!!!"); 
                getServletContext().getRequestDispatcher("/insertuser.jsp").forward(req, resp);
            }
            else if (((String) req.getParameter("lastname")).equals(""))
            {
                req.setAttribute("message3", "Last name must be filled!!!"); 
                getServletContext().getRequestDispatcher("/insertuser.jsp").forward(req, resp);
            }
            else 
            {
                
                pst.setInt(1, Integer.parseInt(req.getParameter("id")));
                pst.setString(2, req.getParameter("firstname"));
                pst.setString(3, req.getParameter("lastname"));
                pst.setString(4, req.getParameter("email"));
            
            if(pst.executeUpdate()!=0)
            {
               req.setAttribute("message", "Inserted succesfully!");
               //CountID.count =null;
               getServletContext().getRequestDispatcher("/show-user").forward(req, resp);
            }
            else
            {
              req.setAttribute("message", "Failed to insert!");
              getServletContext().getRequestDispatcher("/insertuser.jsp").forward(req, resp);
            }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doInsert(req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
         doInsert(req,resp);
    }
}
