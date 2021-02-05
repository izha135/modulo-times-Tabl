/**
 * You do not need to worry about this file.
 * However if you are curious this class is used to
 * verify that a user only inputs a valid decimal number.
 */
package jhaugh.timestables;

import javafx.scene.control.TextFormatter;

import java.text.DecimalFormat;
import java.text.ParsePosition;

    public class DecimalTextVerifier {

        /**
         * This TextFormatter makes sure that the text being entered is a
         * valid decimal number.
         */
        public static <V> TextFormatter getFormatter() {
            TextFormatter<V> tf = new TextFormatter<>(c -> {
                DecimalFormat dfNp = new DecimalFormat("#");
                if ( c.getControlNewText().isEmpty() )
                {
                    return c;
                }

                ParsePosition parsePosition = new ParsePosition( 0 );
                Object object = dfNp.parse( c.getControlNewText(), parsePosition );

                if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
                {
                    return null;
                }
                else
                {
                    return c;
                }
            });
            return tf;
        }
    }
