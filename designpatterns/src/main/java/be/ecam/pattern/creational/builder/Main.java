package be.ecam.pattern.creational.builder;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        EMail.Builder emailBuilder = new EMail.Builder();
        EMail email = emailBuilder
                .setDate(new Date())
                .setFrom("j3l@ecam.be")
                .setTo("claco@ecam.be")
                .setContentType(MimeType.TEXT_PLAIN)
                .setBody("""
                        Hello world!
                        """)
                .build();
        System.out.println("--- EMail ---");
        System.out.println(email);
        System.out.println("--- EMail ---");
    }
}
