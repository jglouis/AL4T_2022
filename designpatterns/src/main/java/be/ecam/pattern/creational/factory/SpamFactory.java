package be.ecam.pattern.creational.factory;

import be.ecam.pattern.creational.builder.ContentTransferEncoding;
import be.ecam.pattern.creational.builder.EMail;
import be.ecam.pattern.creational.builder.MimeType;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class SpamFactory {
    private final String from;
    private final String[] mailingList;
    private final String messageBody;


    public SpamFactory(@NotNull String from, @NotNull String[] mailingList, @NotNull String messageBody) {
        this.from = from;
        this.mailingList = mailingList;
        this.messageBody = messageBody;
    }

    /**
     * Create an array of SPAM!
     *
     * @return An array of Spam {@link EMail}s
     */
    public EMail[] create() {
        EMail[] emails = new EMail[mailingList.length];
        Date now = new Date();
        EMail.Builder emailBuilder = new EMail.Builder()
                .setFrom(from)
                .setDate(now)
                .setContentType(MimeType.TEXT_PLAIN)
                .setContentTransferEncoding(ContentTransferEncoding.BIT7)
                .setBody(messageBody);
        int i = 0;
        for (String to: mailingList) {
            EMail newEmail = emailBuilder.setTo(to).build();
            emails[i++] = newEmail;
        }
        return emails;
    }
}
