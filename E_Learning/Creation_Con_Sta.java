package E_Learning;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Creation_Con_Sta {
    protected Connection createCon(Connection con){
        try {
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/E_Learning","root","root");
            System.out.println("Connection created....");
        } catch (SQLException throwables) {
            System.out.println("An Error Occured.! While creating the Connection....");
        }
        return con;
    }
    protected Statement createStat(Statement s, Connection con){
        try {
            s=con.createStatement();
            System.out.println("Statement created....");
        } catch (SQLException throwables) {
            System.out.println("An Error Occured.! While creating the Statement....");
        }
        return s;
    }
}
