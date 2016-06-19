package name.wind.common.fx.spinner;

import javafx.util.StringConverter;

import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SpinnableTypeConverter<T> extends StringConverter<T> {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("common.i18n.messages");
    private static final Logger logger = Logger.getLogger("name.wind.common.fx.spinner");

    private final SpinnableType<T> spinnableType;

    public SpinnableTypeConverter(SpinnableType<T> spinnableType) {
        this.spinnableType = spinnableType;
    }

    @Override @SuppressWarnings("unchecked") public T fromString(String string) {
        try {
            return (T) spinnableType.format().parseObject(string);
        } catch (ParseException thrown) {
            if (logger.isLoggable(Level.FINE)) {
                logger.log(Level.FINE, String.format(bundle.getString("common.fx.spinner.SpinnableTypeConverted.parseError"), string), thrown);
            } else if (logger.isLoggable(Level.WARNING)) {
                logger.log(Level.WARNING, String.format(bundle.getString("common.fx.spinner.SpinnableTypeConverted.parseError"), string));
            }

            return null;
        }
    }

    @Override public String toString(T object) {
        return spinnableType.format().format(object);
    }

}
