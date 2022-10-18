package be.ecam.pattern.creational.builder;

public enum ContentTransferEncoding {
    BASE64("base64"),
    BIT7("7bit"),
    QUOTED_PRINTABLE("quoted-printable");

    private final String str;

    ContentTransferEncoding(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
