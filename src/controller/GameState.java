package src.controller;

import src.model.Point;

import java.util.List;

public class GameState {

    List<Point> points;

    public GameState(List<Point> points) {
        this.points = points;
    }

    public void getNextFrame() {
        System.out.println("Next frame");

//        TODO: implement
    }

    public List<Point> getPoints() {
        return points;
    }
}
