import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class Transaction {
    private Map map;

    public Transaction(Map map) {
        this.map = map;
    }

    private ThreadLocal<Set<ReentrantLock>> locks = new ThreadLocal<Set<ReentrantLock>>() {
        @Override
        protected Set<ReentrantLock> initialValue() {
            return new HashSet<>();
        }
    };

    private void attainLock(int x, int y) {
        final ReentrantLock lock = map.getLocks()[y][x];
        if(lock.isLocked()){
//            System.out.println("X:" +x+ " Y:" +y +" is Locked:" + lock.isLocked());
        }
        lock.lock();
        locks.get().add(lock);
    }

    private void releaseLock(int x, int y) {
        final ReentrantLock lock = map.getLocks()[y][x];
        releaseLock(lock);
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
    }

    public void setValueByID(int x, int y, char value) {
        attainLock(x, y);
        map.getPositions()[y][x].setType(value);
        releaseLock(x, y);
    }

    public void setPositionByID(int x, int y, Position value) {
        attainLock(x, y);
        map.getPositions()[y][x] = value;
        releaseLock(x, y);
    }

    public char getValueByID(int x, int y) {
        attainLock(x,y);
        return map.getPositions()[y][x].getType();
    }

    public Position getPositionByID(int x, int y) {
        attainLock(x,y);
        return map.getPositions()[y][x];
    }

    void commit() {
        releaseLocks();
    }
}