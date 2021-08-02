package rtb.economy.plugin.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

public final class UUIDHelper {

    private UUIDHelper() { }

    public static UUID convertStream(byte[] array) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put(array);
        buffer.flip();
        return new UUID(buffer.getLong(), buffer.getLong());
    }

    public static InputStream convertUniqueId(UUID uuid) {
        byte[] bytes = new byte[16];
        ByteBuffer.wrap(bytes)
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits());
        return new ByteArrayInputStream(bytes);
    }
}
