## Lab Discussion

### Team 07

### Names

Wei Wu (ww148), Luke Turkovich, Gordon Kim, Henry Huynh

### Issues in Current Code

There are numerous issues in our code that primarily is related to having clean code. Methods and
variables should be renamed. There is a lot of repeated code. Some magic values exist and some
methods and instance data has incorrect privacy.

The backend model is not fully cleared up, and models sometimes share very similar even exactly the
same code.

#### Method or Class

* Design issues

The backend models within "model" folder are very similar and should require a superclass as "
CAModel". Repetitive code undermines the readability of the code and in general makes the debugging
much harder.

#### Method or Class

View package

* Design issues No comments repeated code in HomeScreen poorly named variables magic calues
  throughout

* Design issue unclear responsibilities between classes in inheritance structure

### Refactoring Plan

* What are the code's biggest issues? As of the beginning of this lab, the code had some readability
  issues and didn't have comments or tests. We aimed to fix some of that during lab.

* Which issues are easy to fix and which are hard? Comments just take time but aren't hard. Changing
  code organization is difficult. There is also a bug I've been working on fixing for a long time
  which hasn't been solved.

* What is your plan to implement the changes without losing control of the process? We will test the
  code to make sure all features work before pushing to a branch.

### Refactoring Work

* Issue chosen: Fix and Alternatives Made the code much more readable, realigned some structure in
  View class, fixed variable names, removed repetitive code.


* Issue chosen: Fix and Alternatives

I created an abstract CAModel class that serves as the parent class for all 5 models. Currently, I
have refactored GameOfLifeModel class since this class is well tested. I would expect the
refactoring process for the rest of four classes to be approximately the same, and after the
frontends and backends working out together, I can easily refactor the remaining 4 classes without
being worried about potential bugs unsolved before refactorization. 