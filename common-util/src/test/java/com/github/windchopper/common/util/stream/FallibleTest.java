package com.github.windchopper.common.util.stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FallibleTest {

    @Mock Logger logger;

    @Test @Disabled public void testFallibleRunnable() {

    }

    @Test @Disabled public void testFallibleSupplier() {

    }

    @Test public void testFallibleFunction() {
        List<Long> decodedLongs = Stream.of("1", "2", "3", "4", "five")
            .map(Fallible.infallible(Long::decode))
            .peek(result -> result.ifFailed(failedResult -> logger.log(
                Level.SEVERE,
                String.format("Could not threat as number: %s", failedResult.incomingValue()
                    .orElse(null)),
                failedResult.fault())))
            .map(FallibleFunctionResult::successfulResult)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(SuccessfulFunctionResult::outgoingValue)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(toList());

        assertEquals(
            List.of(1L, 2L, 3L, 4L),
            decodedLongs);

        verify(logger, times(1)).log(
            eq(Level.SEVERE),
            eq("Could not threat as number: five"),
            any(Throwable.class));
    }

    @Test public void testRethrow() {
        assertThrows(UndeclaredThrowableException.class, () -> {
            Fallible.rethrow(UndeclaredThrowableException::new, (FallibleRunnable) () -> {
                throw new RuntimeException("runnable error");
            });

            Fallible.rethrow(UndeclaredThrowableException::new, (FallibleSupplier<String>) () -> {
                throw new RuntimeException("supplier error");
            });

            Fallible.rethrow("input", UndeclaredThrowableException::new, input -> {
                throw new RuntimeException("function error");
            });
        });
    }

}
