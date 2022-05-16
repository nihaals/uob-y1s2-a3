import java.sql.*;

public class Database {
    private Connection con;

    //This method executes a query and returns the number of albums for the artist with name artistName
    public int getTitles(String artistName) {
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) FROM album WHERE artistid IN (SELECT artistid FROM artist WHERE name = ?);");
            stmt.setString(1, artistName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // This method establishes a DB connection & returns a boolean status
    public boolean establishDBConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(Credentials.URL, Credentials.USERNAME, Credentials.PASSWORD);
            return con.isValid(5);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
