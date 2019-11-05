package servers;

import java.sql.*;
import java.util.Map;
import java.util.Properties;

public class ConnectionMain {
    public static Connection main() throws SQLException {
        PropertyParser propertyParser = new PropertyParser("db.properties");
        Map<String,String> map = propertyParser.parse();
        String user = map.get("user");
        String password = map.get("password");
        String port = map.get("port");
        // String url = "jdbc:postgresql://localhost:5432/shop_db";
        String url = "jdbc:postgresql://localhost:" + port + "/postgres";
        Properties props = new Properties();
        props.setProperty("user",user);
        props.setProperty("password",password);
        Connection conn = DriverManager.getConnection(url, props);
        return conn;
    }
}
