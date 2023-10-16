# pp

## Configure 
Clone repo
```
git clone <repo url>
```

Open in IntelliJ and depending on task perform the following:
Right Click old task folder -> Mark Directory as -> Unmark as Sources Root
Right Click new task folder -> Mark Directory as -> Mark as Sources Root

Now IntelliJ will only compile this directory. All task specific files and packages should be contained within this directory

> Troubleshoot SDK Error: After pulling IntelliJ doesn't know which SDK to use. Click on the error message and select openjdk-18

## Compile
```
javac -cp lib/CodeDraw.jar -d out  model/*.java view/*.java controller/*.java Test.java
```

## Run
Since we included a external library we also need to specify classpath
```
java -cp out:lib/CodeDraw.jar src.Test
```
