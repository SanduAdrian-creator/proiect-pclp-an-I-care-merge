package test2;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


///TIPURILE: 1 = KG, 2 = BUC, 3 = ALTCEVA !!!!!!!!!!!!


public class HelloWorld {

	public static void main(String[] args) {
		Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Produs[] p1 = new Produs[100];
        int k = 0, i = 0;
        
        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");

            // Execute a query
            stmt = conn.createStatement();
            String sql = "SELECT * FROM produs";
            rs = stmt.executeQuery(sql);

            // Process the result set
            while (rs.next()) {
                // Retrieve data by column name
            	String valability = rs.getString("valabilitate");
            	String disponibility = rs.getString("disponibilitate");
                int id = rs.getInt("ID");
                String name = rs.getString("nume");
                int p = rs.getInt("pret");
                String type = rs.getString("tip");
                
                Produs p90 = new Produs(valability, disponibility, name, p, 400, 1, 1, 2000, type, id);
                p1[i] = p90;
                i++;
                k++;
                // Process the retrieved data as needed
                //System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
		/*fruct castravete = new fruct();
		castravete.setNume("ahaaaaaaaa");
		System.out.println(castravete.getNume());
		*/
		
		i = 0;
		while(i < k) {
			System.out.println(p1[i].getDisponibilitate());
			System.out.println(p1[i].getValabilitate());
			System.out.println(p1[i].getNume());
			System.out.println(p1[i].getPret());
			System.out.println(p1[i].getCalorii());
			System.out.println(p1[i].getExpira_an());
			System.out.println(p1[i].getExpira_luna());
			System.out.println(p1[i].getExpira_zi());
			System.out.println(p1[i].getType());
			System.out.println(p1[i].getId());
			System.out.println('\n');
			i++;
		}
	}

}
