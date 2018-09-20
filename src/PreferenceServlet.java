

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class PreferenceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserBean db = new UserBean();
        String change = req.getParameter("change");
        ArrayList<String> prefer = new ArrayList<>();
        db.setUsername(req.getParameter("username"));
        if(change.equals("yes")){
            db.deleteprefer(db.getUsername());
        }
        int i=0;
        while (req.getParameter("product"+Integer.toString(i))!=null){
            String pre= req.getParameter("product"+Integer.toString(i));
            prefer.add(pre);
            i++;
        }
        db.setPrefer(prefer);
        String userid = db.searchid(db.getUsername());
//        System.out.println("prefer: "+prefer);
//        System.out.println("uuid: "+ uuid);
        PrintWriter out = resp.getWriter();
        if(db.savePrefer(prefer,userid)&&userid!=null){
            out.write("success");
            //System.out.println("save prefer success");
        }else {
            out.write("fail");
            //System.out.println("save prefer fail");
        }
        out.close();

    }
}
