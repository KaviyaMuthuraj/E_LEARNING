package E_Learning;

import java.sql.*;
import java.util.Scanner;

public class Student {
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
                System.out.println("\n    ____ STUDENT FORM ____");
                System.out.println("\nPress '1' for View your account information ");
                System.out.println("\nPress '2' for Edit your account information ");
                System.out.println("\nPress '3' for Exit ");
                System.out.print("\nEnter your Option : ");
                int op = sc.nextInt();
                switch (op) {
                    case 1:
                        selectQuery(con, st, rs);
                        break;
                    case 2:
                        updateQuery(con, st);
                        break;
                    case 3:
                        state = false;
                        System.out.println("\nThe Process is Successfully Finished ....!");
                        break;
                    default:
                        System.out.println("\nPlease Provide a Valid Option.....!");
                }
            }
        } catch (Exception e) {
            System.out.println("\nAn Error Occurred ..! While start the staff from.");
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
    private void selectQuery(Connection con,Statement s,ResultSet rs){
        try {
            System.out.print("\nEnter your id : ");
            int userid=sc.nextInt();
            rs = s.executeQuery("select * from Users where User_id="+userid);
            while(rs.next()){
                System.out.println("\nUser Id : "+rs.getObject(1)+"     User Name : "+rs.getObject(2)+"     Password : "+rs.getObject(3)+"     Phone Number : "+rs.getObject(4));
            }
        } catch (SQLException throwables) {
            System.out.println("\nAn Error Occurred.! While selecting the user..!");
        }

    }
    private void updateQuery(Connection con,Statement s){
        try {
            System.out.print("\nEnter Your id : ");
            int userid=sc.nextInt();
            System.out.print("\nEnter the New Name : ");
            String name=sc.next();
            System.out.print("\nEnter the New Password : ");
            String password=sc.next();
            System.out.print("\nEnter the New Mobile Number : ");
            String pNumber=sc.next();
            s=con.prepareStatement("update Users set User_name='"+name+"',Password='"+password+"',Phone_Number='"+pNumber+"' where User_id="+userid);
            ((PreparedStatement) s).executeUpdate();
            System.out.println("\nUser account Updated Successfully...!");
        } catch (SQLException throwables) {
            System.out.println("\nAn Error Occurred.! While Updating a user account..!");
        }
    }
}
