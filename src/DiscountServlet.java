

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DiscountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductBean db = new ProductBean();
        db.setDiscount(Integer.parseInt(req.getParameter("off")));
        PrintWriter out = resp.getWriter();
        JSONArray result;
        result = db.discountInfo(db.getDiscount());
        //System.out.println(result);
        resp.setContentType("text/html;charset=utf-8");
        resp.setCharacterEncoding("UTF-8");
        out.println(result);
        out.close();
    }
}
