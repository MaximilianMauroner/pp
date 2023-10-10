package src;

import src.controller.GameState;
import src.controller.GameplayLoop;
import src.model.*;
import src.view.View;

import java.util.HashMap;
import java.util.List;

public class StartProgramm {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        HashMap<String, Double>[] parameters = new HashMap[1];
        parameters[0] = new HashMap<>();
        parameters[0].put("width", 1000.0);
        parameters[0].put("height", 1000.0);
        parameters[0].put("antCount", 100.0);
        parameters[0].put("antViewDistance", 10.0);
        parameters[0].put("foodCount", 100.0);
        parameters[0].put("traildecay", 0.1);

        View view = new View(parameters[0].get("width").intValue(),parameters[0].get("height").intValue());

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
        ));

        GameplayLoop gameplayLoop = new GameplayLoop(view, gs);
        gameplayLoop.run();
    }
}


