package com.github.windchopper.common.util;

import com.github.windchopper.common.util.stream.FallibleSupplier;

public interface Reference<T> extends FallibleSupplier<T> {

    void invalidate();

}
