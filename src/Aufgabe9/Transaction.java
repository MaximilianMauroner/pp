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

    // Pre: map != null
    // Post: creates a new Transaction object with the given map
    public Transaction(Map map) {
        this.map = map;
    }

    // Pre: 0 <= x < width, 0 <= y < height
    // Post: locks the position at the given coordinates
    private void attainLock(int x, int y) {
        if (locks.get().contains(map.getLocks()[y][x])) {
            return;
        }
        final ReentrantLock lock = map.getLocks()[y][x];
        lock.lock();
        locks.get().add(lock);
    }

    // Pre: 0 <= x < width, 0 <= y < height
    // Post: tries to lock the position at the given coordinates, returns true if successful, otherwise false
    private boolean tryAttainLock(int x, int y) {
        final ReentrantLock lock = map.getLocks()[y][x];
        if (lock.tryLock()) {
            locks.get().add(lock);
            return true;
        }
        return false;
    }

    // Pre: lock != null
    // Post: releases the given lock
    private void releaseLock(ReentrantLock lock) {
        final Set<ReentrantLock> lockSet = locks.get();
        if (!lockSet.contains(lock)) {
            throw new IllegalStateException("Locked");
        }
        lockSet.remove(lock);
        lock.unlock();
        //System.out.println("release lock");
    }

    // Pre: -
    // Post: releases all locks
    private void releaseLocks() {
        final Set<ReentrantLock> lockSet = new HashSet<>(locks.get());
        //System.out.println(lockSet.size());
        for (ReentrantLock reentrantLock : lockSet) {
            releaseLock(reentrantLock);
        }
    }

    // Pre: 0 <= x < width, 0 <= y < height, value is valid (see spec.)
    // Post: sets the value of the position at the given coordinates
    public void setValueByID(int x, int y, char value) {
        attainLock(x, y);
        map.getPositions()[y][x].setType(value);
    }

//    public char getValueByID(int x, int y) {
//        attainLock(x, y);
//        return map.getPositions()[y][x].getType();
//    }

    // Pre: 0 <= x < width, 0 <= y < height
    // Post: returns the position at the given coordinates
    public Position getPositionByID(int x, int y) {
        attainLock(x, y);
        return map.getPositions()[y][x];
    }

    // Pre: 0 <= x < width, 0 <= y < height
    // Post: returns the position at the given coordinates if not locked, otherwise null
    public Position tryGetPositionByID(int x, int y) {
        if (tryAttainLock(x, y)) {
            return map.getPositions()[y][x];
        }
        return null;
    }

    // Pre: 0 <= x < width, 0 <= y < height
    // Post: returns the position at the given coordinates if not locked, otherwise null
    void commit() {
        releaseLocks();
    }
}