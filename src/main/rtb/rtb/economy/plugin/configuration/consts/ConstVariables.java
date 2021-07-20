package rtb.economy.plugin.configuration.consts;

public final class ConstVariables {
    private ConstVariables() { }

    /*
        DEFAULT CONFIG
    */
    public static final String ECONOMY_DEFAULT_MONEY = "Economy.defaultMoney";

    public static final String USE_MYSQL = "mysql.enable";
    public static final String MYSQL_HOSTNAME = "mysql.hostname";
    public static final String MYSQL_DATABASE = "mysql.database";
    public static final String MYSQL_USERNAME = "mysql.username";
    public static final String MYSQL_PASSWORD = "mysql.password";
    public static final String MYSQL_PORT = "mysql.port";

    /*
        MESSAGES CONFIG
    */
    public static final String ERROR_NO_PERMISSION_ERROR = "error.no-permission";
    public static final String ERROR_NOT_ENOUGH_ARGS = "error.not-enough-args";
    public static final String ERROR_INVALID_NUMBER = "error.invalid-number";
    public static final String ERROR_PLAYER_NOT_FOUND = "error.player-not-found";
    public static final String ERROR_UNKNOWN = "error.unknown";

    public static final String ERROR_SEND_MONEY_YOURSELF = "economy.error.send-money-yourself";
    public static final String ERROR_NOT_ENOUGH_MONEY = "economy.error.not-enough-money";
    public static final String ERROR_TOGGLE_ON = "economy.error.toggle-on";
    public static final String ERROR_NO_PLAYER_FOUND = "economy.error.no-player";

    public static final String MESSAGE_MONEY_YOUR = "economy.money-check.money-your";
    public static final String MESSAGE_MONEY_OTHER = "economy.money-check.money-other";

    public static final String MESSAGE_TRANSFER_SEND = "economy.transfer.send-money";
    public static final String MESSAGE_TRANSFER_RECEIVE = "economy.transfer.receive-money";

    public static final String MESSAGE_MODIFY_SET = "economy.modify.money-set";
    public static final String MESSAGE_MODIFY_ADD  = "economy.modify.money-add";
    public static final String MESSAGE_MODIFY_REMOVE  = "economy.modify.money-remove";

    public static final String MESSAGE_TOGGLE_ON = "economy.toggle.change-on";
    public static final String MESSAGE_TOGGLE_OFF = "economy.toggle.change-off";

    public static final String MESSAGE_NUMBER_FORMATTER_ON = "economy.number-formatter.change-on";
    public static final String MESSAGE_NUMBER_FORMATTER_OFF = "economy.number-formatter.change-off";

    public static final String MESSAGE_MONEY_TOP_TITLE = "economy.money-top.title";
    public static final String MESSAGE_MONEY_TOP_TEXT = "economy.money-top.text";

    public static final String REPLACE_POSITION = "{position}";
    public static final String REPLACE_MONEY = "{money}";
    public static final String REPLACE_PLAYER_NAME = "{player}";
}
