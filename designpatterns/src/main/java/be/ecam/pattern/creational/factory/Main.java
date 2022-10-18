package be.ecam.pattern.creational.factory;

import be.ecam.pattern.creational.builder.EMail;

public class Main {
    private static final String[] E_MAIL_ADDRESSES = new String[]{
            "amimojo@comcast.net",
            "fwitness@yahoo.com",
            "ebassi@icloud.com",
            "maradine@live.com",
            "crusader@yahoo.com",
            "chrisk@gmail.com",
            "starstuff@yahoo.ca",
            "dburrows@msn.com",
            "improv@outlook.com",
            "hellfire@aol.com",
            "natepuri@me.com",
            "amcuri@sbcglobal.net",
    };

    public static void main(String[] args) {
        SpamFactory spamFactory = new SpamFactory("promo@ecam.be", E_MAIL_ADDRESSES, """
                New ECAM goodies to buy""");
        for (EMail spam : spamFactory.create()) {
            System.out.println("--- SPAM ---");
            System.out.println(spam);
            System.out.println("--- SPAM ---");
        }
    }
}
