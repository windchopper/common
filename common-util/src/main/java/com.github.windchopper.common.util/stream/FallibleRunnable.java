package com.github.windchopper.common.util.stream;

public interface FallibleRunnable extends Fallible {

    void run() throws Throwable;

}
