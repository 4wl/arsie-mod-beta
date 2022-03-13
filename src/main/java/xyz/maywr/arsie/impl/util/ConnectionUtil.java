package xyz.maywr.arsie.impl.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class ConnectionUtil {

    public static boolean isInternetConnected(){
        try {
            URL url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
