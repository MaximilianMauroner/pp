# pp

## Compile
```
javac -cp lib/CodeDraw.jar -d out src/*.java src/model/*.java src/view/*.java src/controller/*.java
```

## Run
Since we included a external library we also need to specify classpath
```
java -cp out:lib/CodeDraw.jar src.Test
```
