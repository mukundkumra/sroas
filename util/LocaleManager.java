package restaurant.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LocaleManager {

    private static final String BUNDLE_NAME = "messages";
    private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

    private Locale activeLocale = DEFAULT_LOCALE;
    private ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, DEFAULT_LOCALE);

    public void setLocale(String lang) {
        if (lang == null || lang.isBlank()) {
            activeLocale = DEFAULT_LOCALE;
            bundle = ResourceBundle.getBundle(BUNDLE_NAME, DEFAULT_LOCALE);
            return;
        }

        Locale requested = Locale.forLanguageTag(lang.trim());
        try {
            ResourceBundle requestedBundle = ResourceBundle.getBundle(BUNDLE_NAME, requested);
            activeLocale = requestedBundle.getLocale();
            bundle = requestedBundle;
        } catch (MissingResourceException ex) {
            activeLocale = DEFAULT_LOCALE;
            bundle = ResourceBundle.getBundle(BUNDLE_NAME, DEFAULT_LOCALE);
        }
    }

    public String text(String key, Object... args) {
        String pattern = bundle.containsKey(key) ? bundle.getString(key) : key;
        return MessageFormat.format(pattern, args);
    }

    public Locale getActiveLocale() {
        return activeLocale;
    }

    public void printWelcome() {
        System.out.println(text("welcome"));
    }
}
