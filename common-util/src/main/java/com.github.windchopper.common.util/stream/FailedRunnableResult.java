package com.github.windchopper.common.util.stream;

public interface FailedRunnableResult extends FallibleRunnableResult, FailedResult<FailedRunnableResult, SuccessfulRunnableResult> {
}
