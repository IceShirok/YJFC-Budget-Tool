package src.code.model;

public class LoginStore {

    private static LoginStore store;

    private String username;
    private String userType;

    private LoginStore(String username, String userType) {
        this.username = username;
        this.userType = userType;
    }

    public static void logIn(String username, String userType) {
        if (store == null) {
            store = new LoginStore(username, userType);
        }
    }

    public static boolean logOut() {
        if (store != null) {
            store = null;
            return true;
        }
        return false;
    }

    public static String getUsername() {
        return store.username;
    }

    public static String getUserType() {
        return store.userType;
    }

}
