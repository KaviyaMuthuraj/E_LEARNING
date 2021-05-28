package E_Learning;
import java.sql.*;
import java.util.*;

public class Users {
    private static Connection con=null;
    private static Statement st=null;
    private static ResultSet rs=null;
    private static Scanner in=new Scanner(System.in);
    public static void main(String args[]){
        try {
            Creation_Con_Sta cs = new Creation_Con_Sta();
            con = cs.createCon(con);
            st = cs.createStat(st, con);
            System.out.println("Press '1' for Login \nPress '2' for Create an Account ");
            int choice = in.nextInt();
            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter your id : ");
                        int id = in.nextInt();
                        System.out.print("\nEnter your Name : ");
                        String name = in.next();
                        System.out.print("\nEnter your password : ");
                        String password = in.next();
                        String role = Authendicate(id, name, password);
                        if (role.equals("Admin")) {
                            System.out.println("\nAdmin Login Successfully...");
                            Admin a = new Admin();
                            a.start();
                        } else if (role.equals("Staff")) {
                            System.out.println("\nStaff Login Successfully...");
                            Staff s = new Staff();
                            s.start();
                        } else if (role.equals("Student")) {
                            System.out.println("\nStudent Login Successfully...");
                            Student s = new Student();
                            s.start();
                        } else {
                            System.out.println("\nAn Error Occurred.! Invalid User Account ..!");
                        }
                        break;
                    } catch (Exception ee) {
                        System.out.println("\nAn Error Occurred.! Invalid User Account ..!");
                    }

                case 2:
                    insertQuery(con, st);
                    break;
                default:
                    System.out.println("\nAn Error Occurred.! Please Enter the valid Option..!");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    private static String Authendicate(int id,String name,String password) {
        String role = null;
        try {
            rs = st.executeQuery("select r.Role_name from Role r JOIN Users u join Users_Role ur where u.User_id=ur.User_id and r.id=ur.Role_id and u.User_id="+id+" and u.User_name='"+name+"' and u.Password='"+password+"'");
            while (rs.next()){
               // System.out.println(rs.getString(1));
                role = rs.getString(1);
            }
        } catch (SQLException throwables) {
            System.out.println("\nAn Error Occurred.! While Authendicating the User..!");
        }
        return role;

    }
    private static void insertQuery(Connection con, Statement s){
        try{
            System.out.print("\nEnter the User ID : ");
            int userid=in.nextInt();
            System.out.print("\nEnter the User Name : ");
            String name=in.next();
            System.out.print("\nEnter the Password for user Account : ");
            String password=in.next();
            System.out.print("\nEnter the User Phone Number : ");
            String pNumber=in.next();
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
}
