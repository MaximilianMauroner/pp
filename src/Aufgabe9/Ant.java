public class Ant implements Runnable {
    private final Map map;
    private final int wait;
    private Direction direction;

    public Ant(Map map, int wait) {
        this.map = map;
        this.wait = wait;
    }

    @Override
    public void run() {
        while (true) {
            // ToDo: Do something

            try {
                Thread.sleep((int) (Math.random() * wait));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
enum  Direction {
    LEFT,
    RIGHT,
    UP,

};