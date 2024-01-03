import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;


public class Transaction {
    private Map map;
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
        if (locks.get().contains(map.getLocks()[y][x])) {
            return;
        }
        final ReentrantLock lock = map.getLocks()[y][x];
        lock.lock();
        locks.get().add(lock);
    }

    private boolean tryAttainLock(int x, int y) {
        final ReentrantLock lock = map.getLocks()[y][x];
        if (lock.tryLock()) {
            locks.get().add(lock);
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
        //System.out.println("release lock");
    }

    private void releaseLocks() {
        final Set<ReentrantLock> lockSet = new HashSet<>(locks.get());
        //System.out.println(lockSet.size());
        for (ReentrantLock reentrantLock : lockSet) {
            releaseLock(reentrantLock);
        }
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

    void commit() {
        releaseLocks();
    }
}