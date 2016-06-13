package name.wind.common.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class SyntaxBums {

    public static <T> T with(Supplier<T> supplier, Consumer<T> consumer) {
        T value = supplier.get();
        consumer.accept(value);
        return value;
    }

}
