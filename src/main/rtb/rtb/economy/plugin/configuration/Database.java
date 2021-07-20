package rtb.economy.plugin.configuration;

import org.bukkit.plugin.Plugin;
import rtb.economy.plugin.configuration.consts.ConstVariables;
import rtb.economy.plugin.database.AbstractDatabase;
import rtb.economy.plugin.database.MySQL;
import rtb.economy.plugin.database.SQLite;
import rtb.economy.plugin.util.UUIDHelper;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class Database {
    private static final String TABLE_NAME = "ExemploPlugin";

    private AbstractDatabase abstractDatabase;

    private PreparedStatement updateAccount;
    private PreparedStatement insertAccount;
    private PreparedStatement loadAccounts;

    private Plugin instance;
    private Config config;

    public Database(Plugin instance, Config config) {
        this.instance = instance;
        this.config = config;

        database();
        openDatabase();
        createDBTable();
        closeDatabaseConnection();

        openDatabase();
    }

    public PreparedStatement getLoadAccountsStatement() {
        return loadAccounts;
    }

    public void closeDatabaseConnection() {
        if (abstractDatabase != null && abstractDatabase.connection()) {
            abstractDatabase.close();
        }
    }

    public PreparedStatement getUpdateStatement(UUID uuid, String value, boolean toggle, boolean formatted) throws SQLException, IOException {
        updateAccount.setString(1, value);
        updateAccount.setBoolean(2, toggle);
        updateAccount.setBoolean(3, formatted);
        updateAccount.setBytes(4, UUIDHelper.convertUniqueId(uuid).readAllBytes());
        return updateAccount;
    }

    public PreparedStatement getInsertStatement(UUID uuid, String value, boolean toggle, boolean formatted) throws SQLException, IOException {
        insertAccount.setBytes(1, UUIDHelper.convertUniqueId(uuid).readAllBytes());
        insertAccount.setString(2, value);
        insertAccount.setBoolean(3, toggle);
        insertAccount.setBoolean(4, formatted);
        return insertAccount;
    }

    private void prepareStatements() {
        try {
            updateAccount = abstractDatabase.getConnection().prepareStatement("update " + TABLE_NAME + " set numberString = ?, toggle = ?, formatted = ? where uuid = ?");
            insertAccount = abstractDatabase.getConnection().prepareStatement("insert into " + TABLE_NAME + " values(?, ?, ?, ?)");
            loadAccounts = abstractDatabase.getConnection().prepareStatement("select * from " + TABLE_NAME);
        } catch (SQLException ignored) { }
    }

    private void database() {
        boolean useMySQL = config.getConfigBoolean(ConstVariables.USE_MYSQL);

        if (useMySQL) {
            String hostname = config.getConfigString(ConstVariables.MYSQL_HOSTNAME);
            String databaseName = config.getConfigString(ConstVariables.MYSQL_DATABASE);
            String username = config.getConfigString(ConstVariables.MYSQL_USERNAME);
            String password = config.getConfigString(ConstVariables.MYSQL_PASSWORD);
            int port = config.getConfigInt(ConstVariables.MYSQL_PORT);
            abstractDatabase = new MySQL(databaseName, hostname, username, password, port, instance);
        }
        else {
            abstractDatabase = new SQLite(instance);
        }
    }

    private void createDBTable() {
        abstractDatabase.execute("create table if not exists " + TABLE_NAME + " (uuid BINARY(16) PRIMARY KEY, numberString varchar(128), toggle BOOLEAN, formatted BOOLEAN);");
    }

    private void openDatabase() {
        abstractDatabase.open();
        prepareStatements();
    }
}
