package be.ecam.pattern.creational.builder;


import java.util.Date;

/**
 * Represents an email.
 */
public class EMail {
    private final Header header;
    private final String body;

    // Cannot be instantiated through constructor. Use Builder.
    private EMail(Header header, String body) {
        this.header = header;
        this.body = body;
    }

    @Override
    public String toString() {
        return header.toString() +
                "\n" +
                body;
    }

    // Builder impl below:

    public static class Builder {
        private Date date;
        private String from;
        private String to;
        private String cc;
        private MimeType contentType;
        private ContentTransferEncoding contentTransferEncoding;
        private String body;

        public EMail build() {
            Header header = new Header(date, from, to, cc, contentType, contentTransferEncoding);
            return new EMail(header, body);
        }

        public Builder setDate(Date date) {
            this.date = date;
            return this;
        }

        public Builder setFrom(String from) {
            this.from = from;
            return this;
        }

        public Builder setTo(String to) {
            this.to = to;
            return this;
        }

        public Builder setCc(String cc) {
            this.cc = cc;
            return this;
        }

        public Builder setContentType(MimeType contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder setContentTransferEncoding(ContentTransferEncoding contentTransferEncoding) {
            this.contentTransferEncoding = contentTransferEncoding;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }
    }
}
