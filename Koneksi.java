import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {

    private static Connection mysqlconfig;

    public static Connection configDB() throws SQLException {

        try {

            String url = "jdbc:mysql://localhost:3306/db_toko";
            String user = "root";
            String pass = "";

            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            mysqlconfig = DriverManager.getConnection(url, user, pass);

            System.out.println("Koneksi Berhasil");

        } catch (SQLException e) {

            System.out.println("Koneksi Gagal : " + e.getMessage());

        }

        return mysqlconfig;
    }
}