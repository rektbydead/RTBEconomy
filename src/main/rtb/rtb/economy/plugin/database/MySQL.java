package rtb.economy.plugin.database;

import org.bukkit.plugin.Plugin;

import java.sql.*;

public class MySQL extends AbstractDatabase {

    public MySQL(String database, String hostname, String username, String password, int port, Plugin instance) {
        super(database, hostname, username, password, "MySQL", port, instance);
    }

    @Override
    public boolean open() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + database,
                        username, password);

                connection.setAutoCommit(true);
            }

            if (statement == null) {
                statement = connection.createStatement();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return connection();
    }
}
