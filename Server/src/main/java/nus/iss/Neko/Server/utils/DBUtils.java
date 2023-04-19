package nus.iss.Neko.Server.utils;

import java.util.Random;

public class DBUtils {

    public static String getUserIDNumber() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}
