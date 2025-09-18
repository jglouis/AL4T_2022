package be.ecam.basics.exercises;

public class NameFormatter {
    public static class Person {
        private final String firstName;
        private final String middleName;
        private final String lastName;

        public Person(String firstName, String middleName, String lastName) {
            this.firstName = firstName;
            this.middleName = middleName;
            this.lastName = lastName;
        }

        public String getFirstName() { return firstName; }
        public String getMiddleName() { return middleName; }
        public String getLastName() { return lastName; }
    }

    public static String displayName(Person p) {
        String s = p.getFirstName().trim();
        if (!p.getMiddleName().isEmpty()) {
            s += " " + p.getMiddleName().trim();
        }
        s += " " + p.getLastName().trim();
        return s;
    }
}
