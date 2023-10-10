package src.model;

public class Trail implements Entity {

        private int x;
        private int y;
        private int strength;

        public Trail(int x, int y, int strength) {
            this.x = x;
            this.y = y;
            this.strength = strength;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getStrength() {
            return this.strength;
        }

        public void run() {

        }
}
