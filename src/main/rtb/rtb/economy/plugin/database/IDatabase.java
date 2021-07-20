package rtb.economy.plugin.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface IDatabase {

    boolean open();

    boolean close();

    boolean connection();

    ResultSet query(String query);

    boolean execute(String string);

    Connection getConnection();

    Statement getStatement();

    String getHostname();

    String getDatabase();

    String getUsername();

    String getPassword();

    String getType();

    int getPort();
}
