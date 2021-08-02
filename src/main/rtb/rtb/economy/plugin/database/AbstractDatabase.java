package rtb.economy.plugin.database;

import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDatabase implements IDatabase {

    protected final String database;
    protected final String hostname;
    protected final String username;
    protected final String password;
    protected final String type;
    protected final int port;

    protected final Plugin instance;

    protected Connection connection;

    protected AbstractDatabase(String database, String hostname, String username, String password, String type, int port, Plugin instance) {
        this.database = database;
        this.hostname = hostname;
        this.username = username;
        this.password = password;
        this.type = type;
        this.port = port;
        this.instance = instance;
    }

    public boolean connection() {
        return connection != null;
    }

    @Override
    public boolean close() {
        if (connection()) {
            try {
                if (connection != null) {
                    connection.close();
                }

                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connection();
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }
}
