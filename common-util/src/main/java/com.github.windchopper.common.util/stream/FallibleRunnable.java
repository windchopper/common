package com.github.windchopper.common.util.stream;

@FunctionalInterface public interface FallibleRunnable extends Fallible {

    void run() throws Throwable;

}
