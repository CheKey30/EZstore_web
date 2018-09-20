

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ChecknameServlet extends HttpServlet{
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserBean db = new UserBean();
        db.setUsername(req.getParameter("name"));
        PrintWriter out = resp.getWriter();
        if(db.checkname(db.getUsername())&db.getUsername()!=""){
            out.write("success");
        }else {
            out.write("fail");
        }
        out.close();
    }
}
