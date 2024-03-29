# pp

## Configure

Clone repo

```
git clone <repo url>
```

Open in IntelliJ and depending on task perform the following:
Right Click old task folder -> Mark Directory as -> Unmark as Sources Root
Right Click new task folder -> Mark Directory as -> Mark as Sources Root

Now IntelliJ will only compile this directory. All task specific files and packages should be contained within this
directory

> Troubleshoot SDK Error: After pulling IntelliJ doesn't know which SDK to use. Click on the error message and select
> openjdk-18

## Compile

### Normal javac 

Just use the standard IntelliJ compile configuration or run the following command in the corresponding directory. 
For Aufgabe1-3 it looks like this:
```
javac -cp lib/CodeDraw.jar -d out  model/*.java view/*.java controller/*.java Test.java
```

### AspectJ Compiler
1) Download AspectJ Compiler from https://www.eclipse.org/aspectj/
2) Install the AspectJ Plugin in IntelliJ
3) Go to Settings -> Build, Execution, Deployment -> Compiler -> Java Compiler and set the Project Compiler to AspectJ
4) Set the path to aspectjtools.jar in the settings

Command line instructions will be added later

## Run

Since we included an external library we also need to specify classpath

```
java -cp out:lib/CodeDraw.jar src.Test
```

# Aufgabe 3

## Zusicherungen und Analyse
- Methodenköpfe anpassen, sodass man Zusicherungen daraus bilden kann
  - Vorbedingungen haben wir in sehr wenigen Stellen, weil die methoden selbst viel einschränken
  - Nachbedingungen könne wir aus den Methodenköpfen ableiten
- Invarianten (bzw. History-Constraints) müssen wir noch dazuschreiben bei einzelnen Variablen
- 3 GOOD Kommentare für objektorientierten Teil (ein paar Kandidaten wo wir das machen könnten)
  - Klassenzusammenhalt: Entities + Gamestate, PathManager, ...
  - Objektkopplung: DataManager, View, ...
  - Dynamisches Binden: Point wo Entities anstatt konkreter Klassen stehen, Operation 
- 3 BAD Kommentare für objektorientierten Teil 
  - Klassenzusammenhalt: DataManager
  - Objektkopplung: Path und OptimalPathPoint, ... 
  - Dynamisches Binden: Generator (ev könnten die advanced Funktionen generic sein)
- 3 GOOD Kommentare für prozeduralen Teil
  - A* Algorithmus
  - ClusterGenerator
- 3 BAD Kommentare für prozeduralen Teil
  - Ant Logic
  - Status
  - Parameters 

## Funktionaler Teil
- schon viel verwendet 
- noch mehr verwenden
  - Game (speziell wo wir den GameState anlegen)
  - View
- Feature finden was ausschließlich funktional programmiert werden kann
- STYLE Kommentar hinzufügen

## Paralleler/Nebenläufiger Teil
- auch schon verwendet (GameLoop)
- ev. noch mehr verwenden
  - ev. PathManager ganz nebenläufig machen
  - ev. noch ein View fenster für Stats (das könnte dann parallel auf Gamestate zugreifen bzw. EventLoop mit notify)
- STYLE Kommentar hinzufügen

# Aufgabe 2

# Improvements:

1. Duftspuren durch Regengüsse ausgedünnt
2. Trägermaterialien durch Umwelt verfrachtet (Wind, Wasser, Tiere)
3. Handle Natural occurrences locally and globally

**Helfen die Störfaktoren dadurch dass neue Wege geschaffen werden?**

- Raum ausbreiten unendlich
- Tageszeiten Implementieren
    - Ameisen kommen am abend nach in den bau
- display only interesting part (or make interactive with inputs {arrow keys})
- Raum ist 3D
    - 3D pathfinding algo
    - 3D objects
    - compare optimal path to chosen path $\to$
- Machine Learning to find path for each ant
- Food can run out
    - do we create new food if there is none left, or does the simulation end?
- Multiple ant colonies (so different odour for each colony to identify)
    - fights with other colonies
    - interaction with odours from other colonies
- new odour calculation
    - keep track of last "odour update" and modify it based on the last time it was changed
    - if recent change, degrade slowly (Sigmoid function?)
- implement function to get odour based on distance
    - maybe only odour check while not moving, and move ant for a "set" distance every time
        - so like 5steps left, pause, odour check, continue with "ideal" path
- socialization
    - create state
    - create home,
    - new ants
        - queen?
    - protection
    - ...
- connection with DB, or some way to save and visualize data
- Paradigmen erläutern (min 5x)
- comment explaining the entire process
-

## Implementierungsplan

### Maxi

- Infinite Positions
- Beschränkung im Point entfernen

- Hive decay
    - nach bestimmter Anzahl an Ameisen
    - wenn Ameisen Futter bringen wird neues Hive Objekt generiert
- new Trails Logic
    - trail for colony and stuff
    - handle multiple colonies for trail
- Fix bau nicht essen
- Ants vereinigen fix Maxi
    - wenn 2 Ameisen aufeinander treffen, werden sie zu einer

### Lukas

- Simulation Metrik
    - Optimale Pfade berechnen A*
    - Abweichung von Pfaden berechnen
    - Speichern von Daten
- Ant movement
    - Ants move for a few steps
    - then check for odour
    - then move again
    - needs view of ant (kinda hard meh)
- add entity priorities
    - maybe needs compareTo() function
- Ants fight each other (maybe); call antDies() in View
    - wenn zwei von unterschiedlichen Kolonien aufeinander treffen, kämpfen sie
    - Ant Objekt entfernen Corpse spawnen
    - View hat Observer
- Test Cases (simulation)
- Paradigmen erläutern (min 5x)
- comment explaining the entire process

### Chris

- Tageszeit simulation~~
    - Game-State Clock abfragen "gameState.getTime();"
    - Hintergrund auf Basis der Zeit verändern
- Statistik End Screen
- Effiziente Darstellung in View
- Entity View Priorities
    - Verschiedene Entities haben verschiedene Priorities
    - Mit höherer Priority werden vor niedriger Priority dargestellt
- Explosion when ant dies >_<
    - Class Corpse implements Entity
- generation organisch
    - Generate Function ändern

### Future

- Food Objekte De-Spawnen (Lukas/Maxi)
    - neue werden random generiert
- Event Loop (prio? )
    - Event subscriber
    - event publisher
    - Event name
- Trail Einfluss
    - Wind trägt randomly den trail weg
    - in run Funktion wird random ein Störfaktor aufgerufen