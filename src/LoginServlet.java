

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;


public class LoginServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserBean db = new UserBean();
        db.setUsername(req.getParameter("name"));
        db.setPassword(req.getParameter("password"));
        System.out.println(db.getUsername());
        System.out.println(db.getPassword());
        PrintWriter out = resp.getWriter();
        if(db.valid(db.getUsername(),db.getPassword())){
            out.print("success");
        }else {
            out.print("fail");
        }
        out.close();
    }
}

