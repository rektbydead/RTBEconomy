package rtb.economy.plugin.configuration.consts;

public final class ConstVariables {
    /*
        DATABASE
    */
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
    public static final String ERROR_CANCELLED = "economy.error.canceled";
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
    public static final String MESSAGE_MONEY_TOP_REFRESH = "economy.money-top.refresh-message";

    public static final String MESSAGE_ON = "replace.enable";
    public static final String MESSAGE_OFF = "replace.disable";

    public static final String MESSAGE_RELOADED = "economy.reload.succesful";

    /*
        INVENTORY CONFIG
     */
    public static final String INVENTORY_ROWS = "inventories.config.rows";
    public static final String INVENTORY_TITLE = "inventories.config.title";

    public static final String INVENTORY_TOGGLE_POSITION = "inventories.config.items.toggle.position";
    public static final String INVENTORY_TOGGLE_NAME = "inventories.config.items.toggle.name";
    public static final String INVENTORY_TOGGLE_MATERIAL = "inventories.config.items.toggle.material";
    public static final String INVENTORY_TOGGLE_LORE = "inventories.config.items.toggle.lore";

    public static final String INVENTORY_NUMBER_FORMAT_POSITION = "inventories.config.items.numberFormat.position";
    public static final String INVENTORY_NUMBER_FORMAT_NAME = "inventories.config.items.numberFormat.name";
    public static final String INVENTORY_NUMBER_FORMAT_MATERIAL = "inventories.config.items.numberFormat.material";
    public static final String INVENTORY_NUMBER_FORMAT_LORE = "inventories.config.items.numberFormat.lore";

    /*
        REPLACE VARIABLES
     */
    public static final String REPLACE_POSITION = "{position}";
    public static final String REPLACE_MONEY = "{money}";
    public static final String REPLACE_PLAYER_NAME = "{player}";
    public static final String REPLACE_ENABLE_DISABLE = "{EnableOrDisable}";

    /*
        Economy CONFIG
     */
    public static final String ECONOMY_DEFAULT_MONEY = "economy.defaultMoney";
    public static final String ECONOMY_SUFFIXES_LIST = "economy.format-money-suffixes";
    public static final String ECONOMY_MONEY_TOP_SIZE = "economy.money-top.size";
    public static final String ECONOMY_MONEY_TOP_SHOW_MESSAGE = "economy.money-top.on-refresh-show-message";
    public static final String ECONOMY_MONEY_TOP_SHOW_MESSAGE_IF_TOP1_CHANGE = "economy.money-top.only-show-message-if-top1-change";
    public static final String ECONOMY_MONEY_TOP_SHOW_PREFIX = "economy.money-top.show-prefix";
    public static final String ECONOMY_MONEY_TOP_SHOW_TOP1_TAG = "economy.money-top.show-prefix";
    public static final String ECONOMY_MONEY_TOP_TOP1_TAG = "economy.money-top.player-tag";
    
    public static final String ECONOMY_MONEY_PAY_SOUND_ACTIVE = "economy.money-pay.sound.active";
    public static final String ECONOMY_MONEY_PAY_SOUND_NAME = "economy.money-pay.sound.sound-name";
    public static final String ECONOMY_MONEY_PAY_SOUND_PITCH = "economy.money-pay.sound.sound.pitch";
    public static final String ECONOMY_MONEY_PAY_SOUND_VOLUME = "economy.money-pay.sound.sound-volume";
    public static final String ECONOMY_MONEY_PAY_ACTIONBAR_ACTIVE = "economy.money-pay.actionbar.active";
    public static final String ECONOMY_MONEY_PAY_ACTIONBAR_TEXT_PAY = "economy.money-pay.actionbar.text-pay";
    public static final String ECONOMY_MONEY_PAY_ACTIONBAR_TEXT_RECEIVE = "economy.money-pay.actionbar.text-receive";
    /*
       Packages name
     */
    public static final String PACKAGE_COMMANDS = "rtb.economy.plugin.commands.subcommand.cmd";
    public static final String PACKAGE_EVENTS = "rtb.economy.plugin.listeners.events";


    private ConstVariables() { }
}
