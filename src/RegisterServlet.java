

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

public class RegisterServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserBean db = new UserBean();
        String User_id= UUID.randomUUID().toString().replaceAll("-", "");
        db.setUserid(User_id);
        db.setUsername(req.getParameter("name"));
        db.setPassword(req.getParameter("password"));
        db.setEmail(req.getParameter("email"));
        db.setGender(req.getParameter("gender"));
        //System.out.println(db.getUsername());
        PrintWriter out = resp.getWriter();
        //存入数据库
        if(db.CreateUser(db.getUserid(), db.getUsername(), db.getPassword(), db.getEmail(), db.getGender())){
            out.write("register success");
        }else {
            out.write("register fail");
        }
        out.close();
    }
}
