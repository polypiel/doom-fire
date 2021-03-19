# Doom fire
Implementation of the Doom fire with Kotlin and Processing. Based on [Fabien Sanglard's article](http://fabiensanglard.net/doom_fire_psx/).

![demo](doom.gif)

## Running
```bash
# Compile
$ mvn package

# Run$ java doomfire-1.0-jar-with-dependencies.jar
```

Restart the animation pressing the key `R`

## Requisites
* Maven 3
* Kotlin 1.4

## Issues
 * Processing package is so old but there isn't more recent ones in Maven repositories
 * Fire rescaling is too smooth
 