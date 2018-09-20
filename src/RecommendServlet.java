

import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class RecommendServlet extends HttpServlet{
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductBean productBean = new ProductBean();
        UserBean userBean = new UserBean();
        productBean.setLocation(req.getParameter("UUID"));
        userBean.setUsername(req.getParameter("name"));
        ArrayList<String> preference = userBean.getPrefer(userBean.searchid(userBean.getUsername()));
        ArrayList<String> subclass = productBean.getSubclass(productBean.getLocation());
        preference.retainAll(subclass);
        PrintWriter out = resp.getWriter();
        if(preference.size()>0){
            JSONArray recommend = new JSONArray();
            for(int i=0;i<preference.size();i++){
                recommend.addAll(productBean.searchProducts(preference.get(i)));
            }
            System.out.println(recommend);
            resp.setContentType("text/html;charset=utf-8");
            resp.setCharacterEncoding("UTF-8");
            out.println(recommend);

        }else {
            out.write("There is nothing you like");
        }
        out.close();

    }
}
