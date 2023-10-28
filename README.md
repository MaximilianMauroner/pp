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

```
javac -cp lib/CodeDraw.jar -d out  model/*.java view/*.java controller/*.java Test.java
```

## Run

Since we included an external library we also need to specify classpath

```
java -cp out:lib/CodeDraw.jar src.Test
```

# Aufgabe 2

# Improvements:

1. Duftspuren durch Regengüsse ausgedünnt
2. Trägermaterialien durch Umwelt verfrachtet (Wind, Wasser, Tiere)
3. Handle Natural occurrences locally and globally

**Helfen die Störfaktoren dadurch dass neue Wege geschaffen werden?**

- Raum ausbreiten unendlich (easy)
- Tageszeiten Implementieren (easy)
    - Ameisen kommen am abend nach in den bau
- display only interesting part (or make interactive with inputs {arrow keys}) (medium)
- Raum ist 3D (hard)
    - 3D pathfinding algo
    - 3D objects
    - compare optimal path to chosen path $\to$
- Machine Learning to find path for each ant
- Food can run out (can be finished)
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

~~- Infinite Positions~~
~~- Beschränkung im Point entfernen~~

- Hive decay
    - nach bestimmter Anzahl an Ameisen
      ~~- wenn Ameisen Futter bringen wird neues Hive Objekt generiert~~
- new Trails Logic
    - trail for colony and stuff
    - handle multiple colonies for trail
- Fix bau nicht essen
- Ants vereinigen fix Maxi
    - wenn 2 Ameisen aufeinander treffen, werden sie zu einer
- Event Loop (prio? )
  - Event subscriber
  - event publisher
  - Event name

### Lukas

- ~~Simulation Metrik~~
    - ~~Optimale Pfade berechnen A*~~
    - ~~Abweichung von Pfaden berechnen~~
    - ~~Speichern von Daten~~
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

### Chris

- ~~Tageszeit simulation~~
    - ~~(Chris) Gamestate Clock abfragen "gameState.getTime();"~~
    - ~~(Chris) Hintergrund auf Basis der Zeit verändern~~
- (Chris) Statistik End Screen (low Priority)
- (Chris) Effiziente Darstellung in View
- ~~(Chris) Entity View Priorities~~
    - ~~Verschiedene Entities haben verschiedene Priorities~~
    - ~~Mit höherer Priority werden vor niedriger Priority dargestellt~~
- (Chris) Explosion when ant dies >_<
  - ~~(Chris) Class Corpse implements Entity~~

### Shared

- Food Objekte despawnen (Lukas/Maxi)
    - neue werden random generiert
    - ~~(Chris) generation organisch~~
        - ~~Generate Function ändern~~

### Unassigned

- Trail Einfluss (eher schwierig, low priority)
    - Wind trägt randomly den trail weg
    - in run Funktion wird random ein Störfaktor aufgerufen
- Test Cases (meh)
- Paradigmen erläutern (min 5x)
- comment explaining the entire process
