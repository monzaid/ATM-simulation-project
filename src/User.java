public class User {
    private static StringBuffer username = new StringBuffer();

    private User(){}

    public static String getUsername() {
        return username.toString();
    }

    public static void setUsername(String username) {
        User.username = new StringBuffer(username);
    }

}
