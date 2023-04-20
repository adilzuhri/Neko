package nus.iss.Neko.Server.utils;

public class UserNotCreatedException extends Exception {

    public UserNotCreatedException() {
        super("Error found; please try again");
    }

    public UserNotCreatedException(String message) {
        super(message);
    }
}
