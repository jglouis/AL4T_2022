package be.ecam.pattern.creational.builder;

public enum MimeType {
    TEXT_PLAIN("text/plain; charset=UTF-8"),
    TEXT_HTML("text/html");

    private final String str;

    MimeType(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
