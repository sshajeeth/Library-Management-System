package Library_Classes;

public class Messages {

    private static String message;

    public Messages(String message) {
        Messages.message = message;
    }


    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        Messages.message = message;
    }
}
