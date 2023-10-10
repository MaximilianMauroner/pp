package src;

import src.model.Ant;
import src.model.Entity;
import src.model.Point;
import src.model.Position;
import src.view.View;

import java.util.List;

public class StartProgramm {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        View view = new View();


        Entity ant1 = new Ant();
        Entity ant2 = new Ant();
        Entity ant3 = new Ant();
        Entity ant4 = new Ant();

        Position position1 = new Position(1, 1);
        Position position2 = new Position(2, 2);
        Position position3 = new Position(3, 3);
        Position position4 = new Position(4, 4);


        List<Point> points = List.of(
                new Point(position1, new Entity[]{ant1}),
                new Point(position2, new Entity[]{ant2}),
                new Point(position3, new Entity[]{ant3}),
                new Point(position4, new Entity[]{ant4})
        );

        view.draw(points);
    }
}


