

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class ProductServlet extends HttpServlet{
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str = acceptJSON(req);
        JSONArray json = JSONArray.fromObject(str);
        ArrayList<JSONObject> product = (ArrayList<JSONObject>)JSONArray.toCollection(json, JSONObject.class);
        PrintWriter out = resp.getWriter();
        for(int i=0;i<product.size();i++){
            ProductBean db = new ProductBean();
            String Product_ID = UUID.randomUUID().toString().replaceAll("-", "");
            db.setGoodsID(Product_ID);
            db.setGoodname(product.get(i).getString("goodsname"));
            db.setPrice(Float.parseFloat(product.get(i).getString("price")));
            db.setDiscount(Integer.parseInt(product.get(i).getString("discount")));
            db.setClassID(product.get(i).getString("classID"));
            db.setQuantity(Integer.parseInt(product.get(i).getString("quantity")));
            db.setShelf(Integer.parseInt(product.get(i).getString("shelf")));
            if(db.CreatProduct(db.getGoodsID(), db.getGoodname(),db.getClassID(),db.getDiscount(),db.getPrice(),db.getQuantity(),db.getShelf())){
            continue;
        }else{
            out.write("add product fail");
            break;
        }
        }
        out.write("success");
        out.close();
    }
        public static String acceptJSON(HttpServletRequest request){
            String acceptjson = "";
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader( (ServletInputStream) request.getInputStream(), "utf-8"));
                StringBuffer sb = new StringBuffer("");
                String temp;
                while ((temp = br.readLine()) != null) {
                    sb.append(temp);
                }
                br.close();
                acceptjson = sb.toString();
                System.out.print(acceptjson);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return acceptjson;
        }
}

