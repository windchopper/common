package com.github.windchopper.common.util;

import com.github.windchopper.common.util.stream.FailableSupplier;

public interface Reference<T, E extends Throwable> extends FailableSupplier<T, E> {

    void invalidate();

}
