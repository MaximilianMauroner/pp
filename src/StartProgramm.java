package src;

import src.controller.GameState;
import src.controller.GameplayLoop;
import src.model.*;
import src.view.View;

import java.util.List;


public class StartProgramm {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Status s = new Status(1000, 1000, 100, 10, 100, 0.1);

        View view = new View(s.getWidth(), s.getHeight());

        Entity ant1 = new Ant();
        Entity ant2 = new Ant();
        Entity ant3 = new Ant();
        Entity ant4 = new Ant();

        Position position1 = new Position(1, 1);
        Position position2 = new Position(2, 2);
        Position position3 = new Position(3, 3);
        Position position4 = new Position(4, 4);


        GameState gs = new GameState(List.of(
                new Point(position1, new Entity[]{ant1}),
                new Point(position2, new Entity[]{ant2}),
                new Point(position3, new Entity[]{ant3}),
                new Point(position4, new Entity[]{ant4})
        ), s);

        GameplayLoop gameplayLoop = new GameplayLoop(view, gs);
        gameplayLoop.start();
    }
}


