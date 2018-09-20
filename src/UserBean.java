

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import netscape.javascript.JSObject;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UserBean implements Serializable{
    private String userid;
    private String username;
    private String password;
    private String email;
    private String gender;
    private ArrayList<String> prefer;



    public void setPrefer(ArrayList<String> prefer) { this.prefer=prefer; }

    public String getUserid() {

        return userid;

    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {

        return username;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public boolean valid(String username, String password) {
        System.out.println("check password....");
        Boolean valid=true;
        String SQL;
        SQL = "select * from user_info where User_name = ? and User_password = ?";
        Connection connection;
        PreparedStatement pstmt;
        try {
            connection = DBDao.getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1,username);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                valid=true;
                System.out.println("success");
            } else {
                valid=false;
                System.out.println("fail");
            }
            connection.close();
            pstmt.close();
            DBDao.closeConnection(connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
                return valid;
    }

    public boolean CreateUser(String userid, String username, String  password, String email, String gender){
        System.out.println("Create user...");
        Boolean valid;
        String SQL;
        SQL = "INSERT INTO user_info VALUES(?,?,?,?,?)";
        Connection connection;
        PreparedStatement pstmt;
        try{
            connection= DBDao.getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, userid);
            pstmt.setString(2,password);
            pstmt.setString(3,username);
            pstmt.setString(4,email);
            pstmt.setString(5,gender);
            pstmt.executeUpdate();
            connection.close();
            pstmt.close();
            DBDao.closeConnection(connection);
            valid = true;
        } catch (SQLException e) {
            e.printStackTrace();
            valid = false;
        }
        return  valid;
    }
    public boolean savePrefer(ArrayList<String> list, String userid){
        System.out.println("Save preference...");
        boolean success;
        String SQL;
        SQL = "INSERT INTO preference VALUES(?,?)";
        Connection connection;
        PreparedStatement pstmt;
        try{

            for(int i=0;i<list.size();i++) {
                connection = DBDao.getConnection();
                pstmt = connection.prepareStatement(SQL);
                pstmt.setString(1, userid);
                pstmt.setString(2, list.get(i));
                pstmt.executeUpdate();
                connection.close();
                pstmt.close();
                DBDao.closeConnection(connection);

            }
            success = true;
        }catch (Exception e){
            e.printStackTrace();
            success =false;
        }

        return success;
    }
    public ArrayList<String> getPrefer(String userid){
        System.out.println("Search prefer...");
        String SQL;
        SQL = "SELECT * FROM preference WHERE User_User_ID = ?";
        Connection connection;
        PreparedStatement pstmt;
        try {
            connection = DBDao.getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, userid);
            ResultSet resultSet = pstmt.executeQuery();
            ArrayList<String> preference = new ArrayList<>();
            while(resultSet.next()){
                preference.add(resultSet.getString(2));
            }
            return preference;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    public String searchid(String username){
        System.out.println("Search id...");
        System.out.println(username);
        String SQL;
        SQL = "SELECT * FROM user_info WHERE User_name = ?";
        Connection connection;
        PreparedStatement pstmt;
        try {
            connection = DBDao.getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            if(!resultSet.next()){
                return "error";
            }else {
                String id = resultSet.getString(1);
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean checkname(String username){
        System.out.println("check username...");
        String SQL;
        SQL = "SELECT * FROM user_info WHERE User_name = ?";
        Connection connection;
        PreparedStatement pstmt;
        try{
            connection = DBDao.getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1,username);
            ResultSet resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                return false;
            }else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList<String> searchPrefer(String name) {
        System.out.println("Search preference...");
        ArrayList<String> prefer = new ArrayList<>();
        String SQL;
        SQL = "SELECT subclass_name FROM preference, user_info, sub_class WHERE user_info.User_name=? AND preference.User_User_ID=user_info.User_ID AND sub_class.sub_class_ID=preference.sub_class_ID";
        Connection connection;
        PreparedStatement pstmt;
        JSONObject result = new JSONObject();
        try {
            connection = DBDao.getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, name);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                prefer.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return prefer;
    }
    public boolean deleteprefer(String username){
        Boolean valid;
        System.out.println("delete prefer...");
        String SQL;
        SQL = "delete from preference where preference.User_User_ID=(select user_info.User_ID from user_info where user_info.User_name=?)";
        Connection connection;
        PreparedStatement pstmt;
        try {
            connection = DBDao.getConnection();
            pstmt = connection.prepareStatement(SQL);
            pstmt.setString(1, username);
            pstmt.executeUpdate();
            connection.close();
            pstmt.close();
            DBDao.closeConnection(connection);
            valid = true;
        } catch (SQLException e) {
            e.printStackTrace();
            valid =false;
        }
        return valid;
    }
}
