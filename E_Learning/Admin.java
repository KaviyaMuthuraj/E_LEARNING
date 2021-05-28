package E_Learning;

import java.sql.*;
import java.util.*;

public class Admin {
    private Scanner sc=new Scanner(System.in);
    Creation_Con_Sta cs=new Creation_Con_Sta();
    private Connection con=null;
    private Statement st=null;
    ResultSet rs = null;
    protected void start(){
        try {
            con = cs.createCon(con);
            st = cs.createStat(st, con);
            boolean state = true;
            while (state) {
                System.out.println("\n    ____ ADMIN FORM ____");
                System.out.println("\nPress '1' for Add New User ");
                System.out.println("\nPress '2' for Update the User Account ");
                System.out.println("\nPress '3' for Delete the User Account ");
                System.out.println("\nPress '4' for View All the User Account ");
                System.out.println("\nPress '5' for Add a Role to New User ");
                System.out.println("\nPress '6' for Exit ");
                System.out.print("\nEnter your Option : ");
                int op = sc.nextInt();
                switch (op) {
                    case 1:
                        insertQuery(con, st);
                        break;
                    case 2:
                        updateQuery(con, st);
                        break;
                    case 3:
                        deleteQuery(con, st, rs);
                        break;
                    case 4:
                        selectQuery(con, st, rs);
                        break;
                    case 5:
                        addRole(con, st, rs);
                        break;
                    case 6:
                        state = false;
                        System.out.println("\nThe Process is Successfully Finished ....!");
                        break;
                    default:
                        System.out.println("\nPlease Provide a Valid Option.....!");
                }
            }
        } catch (Exception e) {
            System.out.println("\nAn Error Occurred ..! While start the Admin from.");
        }
        finally {
            try {
                if (con != null)
                    con.close();
                if (st != null)
                    st.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException throwables) {
                System.out.println("\nAn Error Occurred ..! While close the Connection object,Statement Object and ResultSet Object.");
            }
        }
    }
    private void insertQuery(Connection con, Statement s){
        try{
            System.out.print("\nEnter the User ID : ");
            int userid=sc.nextInt();
            System.out.print("\nEnter the User Name : ");
            String name=sc.next();
            System.out.print("\nEnter the Password for user Account : ");
            String password=sc.next();
            System.out.print("\nEnter the User Phone Number : ");
            String pNumber=sc.next();
            s=con.prepareStatement("insert into Users values(?,?,?,?)");
            ((PreparedStatement) s).setInt(1,userid);
            ((PreparedStatement) s).setObject(2,name);
            ((PreparedStatement) s).setObject(3,password);
            ((PreparedStatement) s).setObject(4,pNumber);
            ((PreparedStatement) s).executeUpdate();
            System.out.println("\nUser added Successfully...!");


        } catch (SQLException throwables) {
            System.out.println("\nAn Error Occurred.! While creating a new user account..!");
        }
    }
    private void updateQuery(Connection con,Statement s){
        try {
            System.out.print("\nEnter the user id to update the Records : ");
            int userid=sc.nextInt();
            System.out.print("\nEnter the User Name to Update : ");
            String name=sc.next();
            System.out.print("\nEnter the User password to Update : ");
            String password=sc.next();
            System.out.print("\nEnter the user Mobile Number to Update : ");
            String pNumber=sc.next();
            s=con.prepareStatement("update Users set User_name='"+name+"',Password='"+password+"',Phone_Number='"+pNumber+"' where User_id="+userid);
            ((PreparedStatement) s).executeUpdate();
            System.out.println("\nUser account Updated Successfully...!");
        } catch (SQLException throwables) {
            System.out.println("\nAn Error Occurred.! While Updating a user account..!");
        }
    }
    private void deleteQuery(Connection con,Statement s,ResultSet rs){
        try {
            int id = 0;
            String role=null;
            System.out.print("\nEnter the user id to delete the Records : ");
            int userid=sc.nextInt();
            rs=s.executeQuery("select id from Users_Role where User_id="+userid);
            while(rs.next()){
                id=rs.getInt(1);
            }
            rs=s.executeQuery("select r.Role_name from Users u Join Users_Role ur Join Role r where u.User_id=ur.User_id and r.id=ur.Role_id and u.User_id="+userid);
            while(rs.next()){
                role=rs.getString(1);
            }
            if(id!=0 && role!=null) {
                if (role.contains("Staff") || role.contains("Student")) {
                    s = con.prepareStatement("delete from Users_Role where id=" + id);
                    ((PreparedStatement) s).executeUpdate();
                }
                else{
                    System.out.println("\nPlease check the User Account...!");
                }
            }
            s=con.prepareStatement("delete from Users where User_id="+userid);
            ((PreparedStatement) s).executeUpdate();
            System.out.println("\nUser account was deleted Successfully...!");

        } catch (SQLException throwables) {
            System.out.println("\nAn Error Occurred.! While deleting a user account..!");
        }
    }
    private void selectQuery(Connection con,Statement s,ResultSet rs){
        try {
            rs = s.executeQuery("select u.User_id,u.User_name,u.Phone_Number,r.Role_name from Users u Join Users_Role ur Join Role r where u.User_id=ur.User_id and r.id=ur.Role_id");
            while(rs.next()){
                System.out.println("\nUser Id : "+rs.getObject(1)+"     User Name : "+rs.getObject(2)+"     Phone Number : "+rs.getObject(3)+"     Role Name : "+rs.getObject(4));
            }
        } catch (SQLException throwables) {
            System.out.println("\nAn Error Occurred.! While selecting the user account..!");
        }

    }
    private void addRole(Connection con,Statement s,ResultSet rs){
        try {
            System.out.println("\n    _____ New Users Are ____");
            rs=s.executeQuery("select u.User_id,u.User_name from Users u where u.User_id not in (select User_id from Users_Role)");
            while(rs.next()){
                System.out.println("\nUser Id : "+rs.getObject(1)+"     User Name : "+rs.getObject(2));
            }
            System.out.print("\nEnter the Id : ");
            int id=sc.nextInt();
            System.out.print("\nEnter the User Id : ");
            int userid=sc.nextInt();
            System.out.print("\nEnter the Role Id : ");
            int roleid=sc.nextInt();
            s=con.prepareStatement("insert into Users_Role values("+id+","+userid+","+roleid+")");
            ((PreparedStatement) s).executeUpdate();
            System.out.println("\nRole added Successfully...!");
        } catch (Exception e) {
            System.out.println("\nAn Error Occurred.! While add the Role to new user..!");
        }
    }
}
