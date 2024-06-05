package org.fr.ul.miage;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Borne {
    public static boolean validateTimestampFormat(String timestampString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);

        try {
            Timestamp timestamp = new Timestamp(dateFormat.parse(timestampString).getTime());
            // Si le timestamp est bien parsé, c'est que le format est correct
            return true;
        } catch (ParseException e) {
            // Si une exception est levée, le format est incorrect
            return false;
        }
    }
}
