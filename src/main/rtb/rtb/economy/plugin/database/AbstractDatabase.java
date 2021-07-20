package rtb.economy.plugin.database;

import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDatabase implements IDatabase {

    protected final String database;
    protected final String hostname;
    protected final String username;
    protected final String password;
    protected final String type;
    protected final int port;

    protected final Plugin instance;

    protected Connection connection;
    protected Statement statement;

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
                if (statement != null) {
                    statement.close();
                }

                if (connection != null) {
                    connection.close();
                }

                statement = null;
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return connection();
    }

    @Override
    public ResultSet query(String query) {
        try {
            return statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean execute(String string) {
        try {
            statement.execute(string);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public Statement getStatement() {
        return statement;
    }

    @Override
    public String getHostname() {
        return hostname;
    }

    @Override
    public String getDatabase() {
        return database;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getPort() {
        return port;
    }
}
