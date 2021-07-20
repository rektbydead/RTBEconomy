package rtb.economy.plugin.database;

import org.bukkit.plugin.Plugin;

import java.sql.*;

public class SQLite extends AbstractDatabase {

    public SQLite(Plugin instance) {
        super("database.db", "localhost", "root", "", "SQLite", 3066, instance);
    }

    @Override
    public boolean open() {
        try {
            Class.forName("org.sqlite.JDBC");
            if (connection == null) {
                connection = DriverManager
                        .getConnection("jdbc:sqlite:" + instance.getDataFolder().getPath() + "/" + database);

                connection.setAutoCommit(true);
            }

            if (statement == null) {
                statement = connection.createStatement();

            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return this.connection();
    }
}
