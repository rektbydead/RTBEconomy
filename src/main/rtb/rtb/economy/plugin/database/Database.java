package rtb.economy.plugin.database;

import org.bukkit.plugin.Plugin;
import rtb.economy.plugin.configuration.Config;
import rtb.economy.plugin.configuration.consts.ConstVariables;
import rtb.economy.plugin.economy.Account;
import rtb.economy.plugin.util.UUIDHelper;

import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Database {
    private static final String TABLE_NAME = "ExemploPlugin";

    private AbstractDatabase abstractDatabase;

    public Database(Plugin instance) {
        this.database(instance);
        this.abstractDatabase.open();
        this.createDBTable();
    }

    public void closeDatabaseConnection() {
        if (abstractDatabase != null && abstractDatabase.connection()) {
            abstractDatabase.close();
        }
    }

    public void updateAccount(final Account account) {
        try (
                PreparedStatement preparedStatement = abstractDatabase.getConnection().prepareStatement("update " + TABLE_NAME + " set numberString = ?, toggle = ?, formatted = ? where uuid = ?")
        ) {
            preparedStatement.setString(1, account.getMoneyPlainText());
            preparedStatement.setBoolean(2, account.getToggle());
            preparedStatement.setBoolean(3, account.getFormatted());
            preparedStatement.setBytes(4, UUIDHelper.convertUniqueId(account.getPlayerUUID()).readAllBytes());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertAccount(final Account account) {
        try (
                PreparedStatement preparedStatement = abstractDatabase.getConnection().prepareStatement("insert into " + TABLE_NAME + " values(?, ?, ?, ?)")
        ) {
            preparedStatement.setBytes(1, UUIDHelper.convertUniqueId(account.getPlayerUUID()).readAllBytes());
            preparedStatement.setString(2, account.getMoneyPlainText());
            preparedStatement.setBoolean(3, account.getToggle());
            preparedStatement.setBoolean(4, account.getFormatted());
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement getLoadAccountsStatement() throws SQLException {
        return abstractDatabase.getConnection().prepareStatement("select * from " + TABLE_NAME);
    }

    private void database(Plugin instance) {
        boolean useMySQL = Config.getInstance().getConfigBoolean(ConstVariables.USE_MYSQL);

        if (useMySQL) {
            String hostname = Config.getInstance().getConfigString(ConstVariables.MYSQL_HOSTNAME);
            String databaseName = Config.getInstance().getConfigString(ConstVariables.MYSQL_DATABASE);
            String username = Config.getInstance().getConfigString(ConstVariables.MYSQL_USERNAME);
            String password = Config.getInstance().getConfigString(ConstVariables.MYSQL_PASSWORD);
            int port = Config.getInstance().getConfigInt(ConstVariables.MYSQL_PORT);
            abstractDatabase = new MySQL(databaseName, hostname, username, password, port, instance);
        }
        else {
            abstractDatabase = new SQLite(instance);
        }
    }

    private void createDBTable() {
        try (
                PreparedStatement preparedStatement = abstractDatabase.getConnection().prepareStatement("create table if not exists " + TABLE_NAME + " (uuid BINARY(16) PRIMARY KEY, numberString varchar(128), toggle BOOLEAN, formatted BOOLEAN);")
        ) {
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
