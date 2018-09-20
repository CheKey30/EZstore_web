

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdmiPreferenceServlet extends HttpServlet{
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductBean db = new ProductBean();
        ArrayList<String> mpop = db.getMostPop();
        ArrayList<String> lpop = db.getLeastPop();
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        out.write("<h3>5 most popular products: </h3>");
        out.write("<ol>");
        for(int i=0; i<mpop.size();i++) {

            out.write("<li>"+db.change(mpop.get(i))+"</li>");

        }
        out.write("</ol>");
        out.write("<br>");
        out.write("<h3>5 least popular products: </h3>");
        out.write("<ol>");
        for(int i=0; i<lpop.size();i++) {

            out.write("<li>"+db.change(lpop.get(i))+"</li>");
        }
        out.write("</ol>");
        out.close();
    }
}
