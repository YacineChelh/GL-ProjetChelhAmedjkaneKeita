package org.fr.ul.miage;

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
    }

