# CA
====

This project implements a cellular automata simulator.

Names:

Gordon Kim

Luke Turkovich

Henry Hyunh

David Wu

### Timeline

Start Date: Oct. 16 (aprox.)

Finish Date: Nov. 1

Hours Spent:

In general, we each spent around 50 hrs.

### Primary Roles

David - Backend models for GameOfLife, Percolation, Segregation, and SpreadOfFire.

Gordon - Frontend, Backend Adaptation, Refactoring

Luke - Control, Arrangement, Refactoring

Henry - Generalist

### Resources Used

https://www.java67.com/2012/08/string-to-enum-in-java-conversion.html
previous projects Professor Duvall's code examples

### Running the Program

Main class: Main

Data files needed: view/resources/*

Features implemented:

All basic features implemented except for reading optional keys from sim files.

Files used to test the program are within the test folder.

### Notes/Assumptions

Assumptions or Simplifications:

* (please verify this, my other teammates) For Percolation, the edge where the percolation
  originates is assumed to be the top edge. This is because the CS201 and the paper ruleset differ a
  bit so we had to make a choice. Another difference between the rulesets is with the amt of
  neighbors. I believe we went with 4 neighbors to determine if they percolate instead of 8
* For WaTor, it is assumed that the fish cannot move to the tile in which it birthed another fish.
  It is also assumed that a shark that has eaten a fish can move there in the same turn.

Interesting data files:

* The way our Arrangement file is setup to account for a dynamically changing number of neighbors is
  a cool feature we implemented late in the project that has a great deal of functionality. It helps
  the project work as intended and has a neat behavior in our eyes.

Known Bugs:

* I believe not all the colors change on the text to fit well within the color theme although this
  is very minor
* We may or may not be able to throw an error with a file that has incorrect or poorly formatted
  info. So far, it seems to be able to properly catch the error , but the testing for this has not
  been too rigorous.
* To run a simulation using a sim configuration file, user must first choose the correct model to
  run, or it will run the current model on the uploaded initial states.

Noteworthy Features:

* We like the ability to change all the colors from the original white, black and blue.

### Impressions

* We think the program is intuitive to use. For the most part, all features are implemented and work
  without throwing any errors. The UI is pretty clean, our code is well designed, commented, and
  clean. We are happy with the final outcome of the project.
