package name.wind.common.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class SyntaxBums {

    public static <T> void with(Supplier<T> supplier, Consumer<T> consumer) {
        consumer.accept(supplier.get());
    }

    @FunctionalInterface public interface ReturningConsumer<T> {
        T acceptAndReturn(T value);
    }

    public static <T> T with(Supplier<T> supplier, ReturningConsumer<T> returningConsumer) {
        return returningConsumer.acceptAndReturn(supplier.get());
    }

}
