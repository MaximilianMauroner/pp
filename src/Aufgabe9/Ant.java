public class Ant implements Runnable {
    private final int wait;

    public Ant(int wait) {
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
