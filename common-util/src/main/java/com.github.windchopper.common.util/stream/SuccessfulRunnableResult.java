package com.github.windchopper.common.util.stream;

public interface SuccessfulRunnableResult extends FallibleRunnableResult, SuccessfulResult<FailedRunnableResult, SuccessfulRunnableResult> {
}
