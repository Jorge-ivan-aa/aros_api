package accrox.aros.api.application.exceptions.auth;

public class InsecurePasswordException extends RuntimeException {

    public InsecurePasswordException() {
        super(
                "\n***************************\n" +
                        "CONFIGURATION ERROR\n" +
                        "***************************\n" +
                        "\n" +
                        "Description:\n" +
                        "\n" +
                        "Admin password is missing or empty in application.properties\n" +
                        "\n" +
                        "Action:\n" +
                        "\n" +
                        "Consider setting 'admin.password' property in your configuration.\n" +
                        "\n" +
                        "***************************");
    }

    public InsecurePasswordException(String email, String hashedPassword) {
        super(

                "\n***************************\n" +
                        "SECURITY ALERT\n" +
                        "***************************\n" +
                        "\n" +
                        "Description:\n" +
                        "\n" +
                        "Admin password is stored in plain text configuration\n" +
                        "\n" +
                        "Action:\n" +
                        "\n" +
                        "Consider updating application.properties with the generated hash:\n" +
                        "\n" +
                        "    admin.password=" + hashedPassword + "\n" +
                        "\n" +
                        "Admin account: " + email + "\n" +
                        "Generated hash: " + hashedPassword + "\n" +
                        "\n" +
                        "***************************");
    }
}