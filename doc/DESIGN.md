# Cell Society Design Final

### Names
Wei Wu(ww148)
## Team Roles and Responsibilities

* Team Member #1 Wei Wu

Developed and tested the backend models for GameOfLife, Percolation, Segregation, and SpreadOfFire. 
I also implemented the Cell data structure and a helper object for Union-Find algorithms for percolation.

* Team Member #2

* Team Member #3

* Team Member #4

## Design goals

For the backend models, I am trying to ensure that the class will not take any inputs directly from the front end or direct user inputs but rather take the inputs sent
from the controller. The only thing backend model should worry about is to take an input arrangement grid, and process all the rules for the game to update the statuses 
for each cell in the next step. By using a super class CAModel, we can pass extremely similar inputs to all the backend models without having to modifying inputs in the controllers
dramatically.

#### What Features are Easy to Add

For backend models, since the models only need the arrangements with specific sets of policies as defined in the superclass CAModel, we can easily modify the shape of the grids by assigning more 
cell shapes, neighbor policies, or edge policies. With the super class model CAModel, we might also have the freedom to implement other models that uses a CA model since they are very likely to be 
a child class of CAModel. 

## High-level Design

#### Core Classes

## Assumptions that Affect the Design

For backend models, we always assume that the input grids, despite different cell shapes, edge policies, or neighbor policies, are still a 2-D matrix. This ensures that the fundamental 
data structure behind our grid system is still a 2-D array. 

#### Features Affected by Assumptions

## Significant differences from Original Plan

## New Features HowTo

#### Easy to Add Features

#### Other Features not yet Done

