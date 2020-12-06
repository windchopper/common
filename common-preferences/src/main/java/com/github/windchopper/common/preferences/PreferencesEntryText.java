package com.github.windchopper.common.preferences;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreferencesEntryText {

    private static final DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private static final Pattern encodedStringPattern = Pattern.compile("(?<timestamp>[0-9]{17})[|](?<text>.*)");

    private LocalDateTime modificationTime;
    private String text;

    public PreferencesEntryText() {
        this(LocalDateTime.MIN, null);
    }

    public PreferencesEntryText(LocalDateTime modificationTime) {
        this(modificationTime, null);
    }

    public PreferencesEntryText(String text) {
        this(LocalDateTime.MIN, text);
    }

    public PreferencesEntryText(LocalDateTime modificationTime, String text) {
        this.modificationTime = modificationTime;
        this.text = text;
    }

    public LocalDateTime modificationTime() {
        return modificationTime;
    }

    public String text() {
        return text;
    }

    public PreferencesEntryText decodeFromString(String encodedToString) {
        Matcher matcher = encodedStringPattern.matcher(encodedToString);

        if (matcher.matches()) {
            modificationTime = LocalDateTime.parse(matcher.group("timestamp"), timestampFormatter);
            text = matcher.group("text");
        } else {
            modificationTime = LocalDateTime.MIN;
            text = encodedToString;
        }

        if ("null".equals(text)) {
            text = null;
        }

        return this;
    }

    public String encodeToString() {
        return String.format("%s|%s", timestampFormatter.format(modificationTime), text);
    }

}
