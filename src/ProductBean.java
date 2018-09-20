

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductBean implements Serializable {
    private String goodsname;
    private int discount;
    private float price;
    private String goodsID;
    private String classID;
    private String location;
    private int quantity;
    private int shelf;

    public int getShelf() {
        return shelf;
    }

    public void setShelf(int shelf) {
        this.shelf = shelf;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGoodsID() {
        return goodsID;
    }

    public void setGoodsID(String goodsID) {
        this.goodsID = goodsID;
    }

    public String getGoodname() {
        return goodsname;
    }

    public void setGoodname(String goodname) {
        this.goodsname = goodname;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean CreatProduct(String ProductID, String ProductName, String classID, int discount, float price, int quantity, int shelf) {
        System.out.println("create product...");
        Boolean valid;
        String SQL;
        SQL = "INSERT INTO product VALUES(?,?,?,?,?,?,?)";
        Connection connection;
        PreparedStatement pstmt;
        try {
            connection = DBDao.getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, ProductID);
            pstmt.setString(2, ProductName);
            pstmt.setInt(3, discount);
            pstmt.setFloat(4, price);
            pstmt.setString(5, classID);
            pstmt.setInt(6,quantity);
            pstmt.setInt(7,shelf);
            pstmt.executeUpdate();
            connection.close();
            pstmt.close();
            DBDao.closeConnection(connection);
            valid = true;
        } catch (SQLException e) {
            e.printStackTrace();
            valid = false;
        }
        return valid;
    }

    public JSONArray discountInfo(int off) {
        System.out.println("Search discount info...");
        JSONArray jsonArray = new JSONArray();
        String SQL;
        SQL = "SELECT product.*,class.class_name FROM product,class,sub_class WHERE discount=? AND class.class_ID=sub_class.class_ID AND sub_class.sub_class_ID=product.sub_class_ID";
        Connection connection;
        PreparedStatement pstmt;
        try {
            connection = DBDao.getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, String.valueOf(off));
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                JSONObject obj = new JSONObject();
                obj.put("product_ID", resultSet.getString(1));
                obj.put("product_name", resultSet.getString(2));
                obj.put("discount", Integer.parseInt(resultSet.getString(3)));
                obj.put("price", Float.parseFloat(resultSet.getString(4)));
                obj.put("category", resultSet.getString(8));
                obj.put("quantity", Integer.parseInt(resultSet.getString(6)));
                obj.put("sub_class_ID",resultSet.getString(5));
                obj.put("shelf",resultSet.getString(7));
                jsonArray.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return jsonArray;
    }
    public ArrayList<String> getSubclass(String mac){
        System.out.println("Search classID...");
        String SQL;
        SQL = "SELECT sub_class_ID FROM sub_class,class WHERE class.Mac= ? AND sub_class.class_ID=class.class_ID";
        Connection connection;
        PreparedStatement pstmt;
        try {
            connection = DBDao.getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, mac);
            ResultSet resultSet = pstmt.executeQuery();
            ArrayList<String> subclass = new ArrayList<>();
            while(resultSet.next()){
                subclass.add(resultSet.getString(1));
            }
            return subclass;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    public ArrayList<String> getMostPop(){
        System.out.println("search most popular...");
        String SQL;
        SQL = "SELECT sub_class_ID, count( * ) AS count\n" +
                "FROM preference\n" +
                "GROUP BY sub_class_ID\n" +
                "ORDER BY count DESC\n" +
                "LIMIT 5";
        Connection connection;
        PreparedStatement pstmt;
        try {
            connection =DBDao.getConnection();
            pstmt = connection.prepareStatement(SQL);
            ResultSet resultSet = pstmt.executeQuery();
            ArrayList<String> goods = new ArrayList<>();
            while (resultSet.next()){
                goods.add(resultSet.getString(1));
            }
            return goods;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<String> getLeastPop(){
        System.out.println("search most popular...");
        String SQL;
        SQL = "SELECT sub_class_ID, count( * ) AS count\n" +
                "FROM preference\n" +
                "GROUP BY sub_class_ID\n" +
                "ORDER BY count \n" +
                "LIMIT 5";
        Connection connection;
        PreparedStatement pstmt;
        try {
            connection =DBDao.getConnection();
            pstmt = connection.prepareStatement(SQL);
            ResultSet resultSet = pstmt.executeQuery();
            ArrayList<String> goods = new ArrayList<>();
            while (resultSet.next()){
                goods.add(resultSet.getString(1));
            }
            return goods;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public JSONArray searchProducts(String classid){
        System.out.println("search products...");
        String SQL;
        SQL = "SELECT product.*,class.class_name FROM product,class,sub_class WHERE product.sub_class_ID=? AND class.class_ID=sub_class.class_ID AND sub_class.sub_class_ID=product.sub_class_ID";
        Connection connection;
        PreparedStatement pstmt;
        JSONArray result = new JSONArray();
        try {
            connection = DBDao.getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1,classid);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                JSONObject obj = new JSONObject();
                obj.put("product_ID",resultSet.getString(1));
                obj.put("product_name",resultSet.getString(2));
                obj.put("discount",Integer.parseInt(resultSet.getString(3)));
                obj.put("price",Float.parseFloat(resultSet.getString(4)));
                obj.put("category",resultSet.getString(8));
                obj.put("quantity",Integer.parseInt(resultSet.getString(6)));
                obj.put("sub_class_ID",resultSet.getString(5));
                obj.put("shelf",resultSet.getString(7));
                result.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
    public static String change(String id){
        String [] Id = {"1.1","1.2","1.3","1.4","2.1","2.2","2.3","2.4","3.1","3.2","3.3","3.4","4.1","4.2","4.3","4.4","5.1","5.2","5.3","5.4","6.1","6.2","6.3","6.4"};
        String [] name = {"Coat","Trousers","Sweater","Shoes","Computer", "Smartphone", "Television", "Camera","Chips","Cookies","Drinks","Nuts","Novel","Poetry","Prose","Reference Book","Pencil","Notebook","Pen","Ruler","Skin care", "Lipstick", "Toothpaste","household"};
        Map map = new HashMap();
        for(int i=0;i<Id.length;i++){
            map.put(Id[i],name[i]);
        }
        return String.valueOf(map.get(id));
    }
    public boolean deleteProduct(String name){
        System.out.println("Delete product...");
        Boolean valid;
        String SQL;
        SQL = "delete from product where product_name = ?";
        Connection connection;
        PreparedStatement pstmt;
        try {
            connection = DBDao.getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1,name);
            pstmt.executeUpdate();
            connection.close();
            pstmt.close();
            DBDao.closeConnection(connection);
            valid = true;
        } catch (SQLException e) {
            e.printStackTrace();
            valid = false;
        }
        return valid;
    }
}
