package src.view;

import codedraw.CodeDraw;
import src.controller.GameState;
import src.model.Point;

import java.util.List;

public class View {
    private int width, height;
    private final CodeDraw cd;

    public View(int width, int height) {
        this.width = width;
        this.height = height;
        cd = new CodeDraw(width, height);
    }

    public void draw(GameState gameState) {
        cd.show();
    }
}