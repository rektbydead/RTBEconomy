package rtb.economy.plugin.database;

import java.sql.Connection;

public interface IDatabase {

    boolean open();

    boolean close();

    boolean connection();

    Connection getConnection();
}
