package com.github.windchopper.common.util.stream;

public interface FailableResult<T> {

    boolean succeeded();
    boolean failed();

}
