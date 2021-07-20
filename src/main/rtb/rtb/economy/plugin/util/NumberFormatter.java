package rtb.economy.plugin.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberFormatter {
    private NumberFormatter() { }

    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###", new DecimalFormatSymbols());
    private static final Pattern PATTERN = Pattern.compile("^(\\d+\\.?\\d*)(\\D+)");

    private static final BigDecimal divisor = new BigDecimal("1000");

    private static List<String> suffixes;

    static {
        suffixes = Arrays.asList("", "K", "M", "B", "T", "Q", "L");
    }

    public static void setSuffixes(List<String> suffixes) {
        NumberFormatter.suffixes = suffixes;
    }

    public static String numberFormat(BigDecimal bigDecimal) {
        return decimalFormat.format(bigDecimal);
    }

    public static String numberToString(BigDecimal value, boolean suffixEnabled) {
        if (!suffixEnabled) {
            return numberFormat(value);
        }

        int index = 0;

        BigDecimal tmp;
        while ((tmp = value.divide(divisor)).compareTo(BigDecimal.ONE) >= 0) {
            value = tmp;
            ++index;
        }

        return numberFormat(value) + suffixes.get(index);
    }

    public static BigDecimal stringToNumber(String value) {
        Matcher matcher = PATTERN.matcher(value);
        if (!matcher.find()) {
            return new BigDecimal("-1");
        }

        BigDecimal amount = new BigDecimal(matcher.group(1));
        String suffix = matcher.group(2);

        int index = suffixes.indexOf(suffix.toUpperCase());

        return amount.multiply(divisor.pow(index));
    }

    public static boolean isFormatted(String value) {
        Matcher matcher = PATTERN.matcher(value);

        return matcher.find();
    }
}
