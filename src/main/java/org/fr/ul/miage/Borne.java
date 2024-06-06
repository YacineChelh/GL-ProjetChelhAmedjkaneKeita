package org.fr.ul.miage;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
    public class Borne {
        private int id;
        private boolean disponible;
        public String immatriculationVehicule;
        public int dureeRecharge;
        private int rechargesCommencees;

        public Borne(int id) {
            this.id = id;
            this.disponible = true;
        }

        public int getId() {
            return id;
        }

        public boolean estDisponible() {
            return disponible;
        }

        public void commencerRecharge(String immatriculationVehicule, int dureeRecharge) {
            this.immatriculationVehicule = immatriculationVehicule;
            this.dureeRecharge = dureeRecharge;
            this.disponible = false;
            this.rechargesCommencees++;
        }

        public void terminerRecharge() {
            this.immatriculationVehicule = null;
            this.dureeRecharge = 0;
            this.disponible = true;
        }
        public int getRechargesCommencees() {
            return rechargesCommencees;
        }


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

