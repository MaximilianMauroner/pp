import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;


public class Transaction {
    private Map map;

    private ThreadLocal<HashMap<Point, ReentrantLock>> lockPosition = new ThreadLocal<HashMap<Point, ReentrantLock>>() {
        @Override
        protected HashMap<Point, ReentrantLock> initialValue() {
            return new HashMap<>();
        }
    };
    private ThreadLocal<Set<ReentrantLock>> locks = new ThreadLocal<Set<ReentrantLock>>() {
        @Override
        protected Set<ReentrantLock> initialValue() {
            return new HashSet<>();
        }
    };

    public Transaction(Map map) {
        this.map = map;
    }

    private void attainLock(int x, int y) {
        final ReentrantLock lock = map.getLocks()[y][x];
        lock.lock();
        locks.get().add(lock);
        lockPosition.get().put(new Point(x, y), lock);
    }

    private boolean tryAttainLock(int x, int y) {
        final ReentrantLock lock = map.getLocks()[y][x];
        if (lock.tryLock()) {
            locks.get().add(lock);
            lockPosition.get().put(new Point(x, y), lock);
            return true;
        }
        return false;
    }

    private void releaseLock(ReentrantLock lock) {
        final Set<ReentrantLock> lockSet = locks.get();

        if (!lockSet.contains(lock)) {
            throw new IllegalStateException("Locked");
        }
        lockSet.remove(lock);
        lock.unlock();
    }

    private void releaseLocks() {
        final Set<ReentrantLock> lockSet = new HashSet<ReentrantLock>(locks.get());
        for (ReentrantLock reentrantLock : lockSet) {
            releaseLock(reentrantLock);
        }
        lockPosition.get().clear();
    }

    public void setValueByID(int x, int y, char value) {
        attainLock(x, y);
        map.getPositions()[y][x].setType(value);
    }

//    public char getValueByID(int x, int y) {
//        attainLock(x, y);
//        return map.getPositions()[y][x].getType();
//    }

    public Position getPositionByID(int x, int y) {
        attainLock(x, y);
        return map.getPositions()[y][x];
    }

    public Position tryGetPositionByID(int x, int y) {
        if (tryAttainLock(x, y))
            return map.getPositions()[y][x];
        return null;
    }

    public void unsafeRelease(Position p) {
        Point point = new Point(p.getX(), p.getY());
        if (!lockPosition.get().containsKey(point)) return;
        ReentrantLock t = lockPosition.get().get(point);
        locks.get().remove(t);
        map.getLocks()[p.getY()][p.getX()].unlock();
    }

    void commit() {
        releaseLocks();
    }
}