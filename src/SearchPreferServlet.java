

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SearchPreferServlet extends HttpServlet{
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserBean db = new UserBean();
        db.setUsername(req.getParameter("name"));
        System.out.println("name: "+db.getUsername());
        ArrayList<String> prefer = db.searchPrefer(db.getUsername());
        System.out.println(prefer);
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        for(int i=0;i<prefer.size();i++){
            out.write(prefer.get(i));
            out.write(",");
        }
        out.close();
    }
}
