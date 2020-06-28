package com.github.windchopper.common.io;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.util.function.IntConsumer;

import static java.util.Arrays.copyOf;

public class FileMultiLineText implements MultiLineText, AutoCloseable {

    private static final int DEFAULT_INITIAL_LINE_COUNT_LIMIT = 1000;
    private static final int DEFAULT_LINE_COUNT_INCREMENT = 1000;

    private final FileChannel fileChannel;
    private final MappedByteBuffer mappedByteBuffer;

    private final int[] positions;
    private final int[] sizes;

    private final Charset charset;

    public FileMultiLineText(FileChannel fileChannel, Charset charset, int initialLineCountLimit, int lineCountIncrement, IntConsumer progressMonitor) throws IOException {
        var positions = new int[initialLineCountLimit];
        var sizes = new int[initialLineCountLimit];

        var positionIndex = 0;
        var position = -1;
        var sizeIndex = 0;
        var size = -1;
        var previousPercentCount = -1;
        var previousFeed = true;
        var feed = false;

        mappedByteBuffer = fileChannel.map(MapMode.READ_ONLY, 0, fileChannel.size());

        while (mappedByteBuffer.hasRemaining()) {
            var bufferPosition = mappedByteBuffer.position();
            var character = mappedByteBuffer.get();

            if (progressMonitor != null) {
                var percent = mappedByteBuffer.limit() / 100;
                var percentCount = 0;

                if (percent > 0) {
                    percentCount = bufferPosition / percent;
                }

                if (percentCount != previousPercentCount) {
                    progressMonitor.accept(previousPercentCount = percentCount);
                }
            }

            feed = System.lineSeparator().indexOf(character) >= 0;

            if (feed && position >= 0) {
                size = bufferPosition - position;
            } else if (previousFeed) {
                position = bufferPosition;
            }

            previousFeed = feed;

            if (position < 0 || size < 0) {
                continue;
            }

            if (positionIndex == positions.length) {
                positions = copyOf(positions, positions.length + lineCountIncrement);
            }

            positions[positionIndex++] = position;
            position = -1;

            if (sizeIndex == sizes.length) {
                sizes = copyOf(sizes, sizes.length + lineCountIncrement);
            }

            sizes[sizeIndex++] = size;
            size = -1;
        }

        this.sizes = copyOf(sizes, sizeIndex);
        this.positions = copyOf(positions, positionIndex);
        this.fileChannel = fileChannel;
        this.charset = charset;
    }

    public FileMultiLineText(FileChannel fileChannel, Charset charset, int initialLineCountLimit, int lineCountIncrement) throws IOException {
        this(fileChannel, charset, initialLineCountLimit, lineCountIncrement, null);
    }

    public FileMultiLineText(FileChannel fileChannel, Charset charset, IntConsumer progressMonitor) throws IOException {
        this(fileChannel, charset, DEFAULT_INITIAL_LINE_COUNT_LIMIT, DEFAULT_LINE_COUNT_INCREMENT, progressMonitor);
    }

    public FileMultiLineText(FileChannel fileChannel, Charset charset) throws IOException {
        this(fileChannel, charset, DEFAULT_INITIAL_LINE_COUNT_LIMIT, DEFAULT_LINE_COUNT_INCREMENT, null);
    }

    @Override public String lineAt(int lineIndex) {
        var bytes = new byte[sizes[lineIndex]];

        mappedByteBuffer
            .position(positions[lineIndex])
            .get(bytes);

        return new String(bytes, charset);
    }

    @Override public int lineCount() {
        return positions.length;
    }

    @Override public void close() throws IOException {
        fileChannel.close();
    }

}
