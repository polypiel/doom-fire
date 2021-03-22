# Doom fire
Implementation of the Doom fire with Kotlin and several graphic frameworks. Based on [Fabien Sanglard's article](http://fabiensanglard.net/doom_fire_psx/).

Building with [Maven](https://maven.apache.org/):
```bash
$ mvn package
```

Current implementations:
 * [Processing](#processing)
 * [Java FX](#java-fx)

## Processing
![demo](doc/doom.gif)

### Running
```bash
$ java -jar doomfire-1.0-jar-with-dependencies.jar processing
```

Restart the animation pressing the key `R`

### Issues
 * [processing] Processing package is so old but there isn't more recent ones in Maven repositories
 * [processing] Fire rescaling is too smooth


### Java FX
![demo](doc/doomfx.gif)

### Running
```bash
$ java -jar doomfire-1.0-jar-with-dependencies.jar processing
```
