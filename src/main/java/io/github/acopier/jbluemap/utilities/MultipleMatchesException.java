package io.github.acopier.jbluemap.utilities;

public class MultipleMatchesException extends RuntimeException {
    public MultipleMatchesException(String key) {
        super(key + ": Multiple matches found");
    }
}
