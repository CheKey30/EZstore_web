


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AdmiDeleteServlet2 extends HttpServlet{
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductBean db = new ProductBean();
        ArrayList<String> delete = new ArrayList<>();
        PrintWriter out = resp.getWriter();
        int i=0;
        while (i<Integer.parseInt(req.getParameter("num"))){
            String product= req.getParameter(Integer.toString(i));
            delete.add(product);
            i++;
        }
        System.out.println(delete);
        for(int j=0; j<delete.size();j++){
            if(db.deleteProduct(delete.get(j)))
                continue;
            else{
                out.write("fail");
                break;
            }
        }
        out.write("successs");
        out.close();
    }

}
