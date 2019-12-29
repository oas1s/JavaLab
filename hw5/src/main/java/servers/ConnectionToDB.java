package servers;

import java.sql.*;
import java.util.Map;
import java.util.Properties;

public class ConnectionToDB {
    java.sql.Connection connection;

    public java.sql.Connection getInstance() throws SQLException, ClassNotFoundException {
        if (connection == null) {
//            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
//            PropertyParser propertyParser = new PropertyParser("db.properties");
//            Map<String,String> map = propertyParser.parse();
            String url = "jdbc:mysql://127.0.0.1:3306/?user=root&useLegacyDatetimeCode=false&amp&serverTimezone=UTC";
//            Properties props = new Properties();
//            props.setProperty("user","root");
//            props.setProperty("password","azat1504");
            try {
                this.connection = DriverManager.getConnection(url,"root", "azat1504");
            }
            catch(Exception e) {
                System.out.println(e);
            }
        }
        return connection;
    }
}