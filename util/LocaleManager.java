package restaurant.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleManager {

    public void printWelcome(String lang) {
        Locale locale = Locale.of(lang);
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        System.out.println(bundle.getString("welcome"));
    }
}