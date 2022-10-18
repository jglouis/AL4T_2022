package be.ecam.pattern.creational.builder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Holds header data from an email.
 * Immutable.
 */
public class Header {
    // Mandatory
    @NotNull
    private final Date date;
    @NotNull
    private final String from;

    // Optional
    @Nullable
    private final String to;
    @Nullable
    private final String cc;
    @Nullable
    private final MimeType contentType;
    @Nullable
    private final ContentTransferEncoding contentTransferEncoding;


    protected Header(@NotNull Date date, @NotNull String from, @Nullable String to, @Nullable String cc, @Nullable MimeType contentType, @Nullable ContentTransferEncoding contentTransferEncoding) {
        this.date = date;
        this.from = from;
        this.to = to;
        this.cc = cc;
        this.contentType = contentType;
        this.contentTransferEncoding = contentTransferEncoding;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
        String dateStr = dateFormat.format(date);
        StringBuilder sb = new StringBuilder(String.format("""
                Date: %s
                From: %s
                """, dateStr, from));
        if (to != null) {
            sb.append(String.format("To: %s%n", to));
        }
        if (cc != null) {
            sb.append(String.format("CC: %s%n", cc));
        }
        if (contentType != null) {
            sb.append(String.format("Content-Type: %s%n", contentType));
        }
        if (contentTransferEncoding != null) {
            sb.append(String.format("Content-Transfer-Encoding: %s%n", contentTransferEncoding));
        }
        return sb.toString();
    }


}
