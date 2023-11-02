package controller;

import model.Entity.Entity;
import model.Position;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameBuffer {


    public static void add(BlockingQueue<BufferElement> buffer, Entity entity, Position oldPosition) {
        try {
            BufferElement bufferElement = new BufferElement(entity);
            buffer.put(bufferElement);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

