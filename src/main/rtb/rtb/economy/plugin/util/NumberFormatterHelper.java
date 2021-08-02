package rtb.economy.plugin.util;

import rtb.economy.plugin.configuration.Config;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static rtb.economy.plugin.configuration.consts.Constants.ECONOMY_SUFFIXES_LIST;

public final class NumberFormatterHelper {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###.####", new DecimalFormatSymbols());
    private static final Pattern PATTERN = Pattern.compile("^(\\d+\\.?\\d*)(\\D+)");
    private static final BigDecimal divisor = new BigDecimal("1000");

    private static List<String> suffixes;

    static {
        NumberFormatterHelper.suffixes = Config.getInstance().getConfigList(ECONOMY_SUFFIXES_LIST);
    }

    private NumberFormatterHelper() {
    }

    public static String numberFormat(BigDecimal bigDecimal) {
        return decimalFormat.format(bigDecimal);
    }

    public static String numberToString(BigDecimal value, boolean suffixEnabled) {
        if (!suffixEnabled) {
            return numberFormat(value);
        }

        int index = 0;

        BigDecimal aux = value;
        BigDecimal tmp;

        while ((tmp = aux.divide(divisor)).compareTo(BigDecimal.ONE) >= 0 && index < suffixes.size() - 1) {
            aux = tmp;
            ++index;
        }

        return numberFormat(aux) + suffixes.get(index);
    }

    public static BigDecimal stringToNumber(String value) {
        try {
            Matcher matcher = PATTERN.matcher(value);
            if (!matcher.find()) {
                return new BigDecimal("-1");
            }

            BigDecimal amount = new BigDecimal(matcher.group(1));
            String suffix = matcher.group(2);

            int index = suffixes.indexOf(suffix.toUpperCase());

            return amount.multiply(divisor.pow(index));
        } catch (Exception e) {
            return new BigDecimal("-1");
        }
    }

    public static boolean isFormatted(String value) {
        Matcher matcher = PATTERN.matcher(value);

        if (!matcher.find())
            return false;

        char lastChar = value.charAt(value.length() - 1);
        if (!Character.isLetter(lastChar))
            return false;

        for (int i = 0; i < value.length(); i++) {
            boolean isDigit = Character.isDigit(value.charAt(i - 1));
            boolean isLetter = Character.isLetter(value.charAt(i));

            if (isDigit && !isLetter) {
                return false;
            }
        }

        return true;
    }
}
