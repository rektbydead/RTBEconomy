package rtb.economy.plugin.util;

import java.math.BigDecimal;

public final class BigDecimalHelper {
    private BigDecimalHelper() {}

    public static BigDecimal getDecimal(String value) {
        try {
            return new BigDecimal(value);
        } catch (Exception exception) {
            return new BigDecimal("-1.0");
        }
    }
}
