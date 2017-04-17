
Karl Preisner, Michael Esposito, Joe Crandall

Continuous Algorithms Final Project Topic
Algorithmic goal:
Optimize the initial population values so that all of the species stay alive for the longest amount of time. As soon as one species is wiped out, the simulation is over. 
What population values give us equilibrium? (Needs reproduction)

Necessary features:
Visibility range for animals (we should keep this the same for all animals)
Random walking algorithm if no food is in an animal’s sight range
Animal sees food and moves towards it.

Additional Features for future work:
Prey runs away from predators (smaller visibility range to notice that it is being pursued).
Animals have to accelerate to top running speed. (Make it simple)
Animal reproduction & age death
Watering holes
Mice and rabbits hiding in holes (and only reproducing in them)
Wolves like roam to be in packs
Deer like to roam in packs

Food Chain:
Mice, rabbits, and deer eat plants.
Foxes eat mice and rabbits.
Wolves eat deer (and foxes when really hungry).



Plant
Mouse
Rabbit
Fox
Deer
Wolf
Max HP
100
5
10
30
100
80
Hunger HP 
(begin searching for food)
Always
3
5
15
50
40 - deer
30 - fox & deer
Health lost per game tick
-1.0
0.20
0.25
0.50
1.0
1.0
Health gained per game tick while eating a plant
n/a
1
2
n/a
10
n/a
Top Running Speed
0
[0,1]
(8 mph)
[0,5]
(35 mph)
[0,4]
(30 mph)
[0,5]
(35 mph)
[0,5]
(35 mph)
Time taken to consume
n/a
2
4
12
40
n/a
HP gained for consuming
n/a
4
8
24
80
n/a


Eating an animal takes (0.4) * (animal’s Max HP) game ticks and gives (0.8) * (animal’s Max HP) health points.

When a predator is eating another animal:
It takes a set amount of time for the prey to be consumed.
The predator does not lose any health while it is eating. 
The predator’s health points is replenished after the consumption timer has finished. 
They prey will disappear after it has been consumed.



Old stuff below

Plant
Heath from 0-100. Every time tick it regrows 1 value until it caps out at 100.
If heath hits 0, the plant dies and is removed from the screen. They lose health when an animal eats them.
All plants are initialized in a random location and do not move. 
Mouse
Have a health of 5, if it drops below 2 they search for food. If it drops below 1 they die. They lose 1 health value every 5 game ticks.
They can move 1 square per game tick. (real mph = 8)
They eat 1 plant value per game tick.
Eating a mouse takes 2 game ticks. Gives predator 4 health points. 
Reproduction:
TODO
Hiding in holes:
TODO
Rabbit
Health = 10, if it drops below 5 they search for food. If it drops below 1 they die. They lose 1 health value every 4 game ticks.
They can move 5 squares per game tick. (real mph = 35)
They eat 2 plant value per game tick. 
Eating a rabbit takes 4 game ticks. Gives predator 8 health points.
Reproduction:
TODO
Hiding in holes:
TODO
Fox
Health = 40, if it drops below 20 they search for food. If it drops below 1 they die. They lose 1 health value every 2 game ticks.
They eat 5 health value per game tick.
They can move 4 squares per game tick. (real mph = 31)
Eating a fox takes 12 game ticks. Gives predator 24 health points.
Reproduction:
TODO
Deer
Health = 100, if it drops below 50 they search for food. If it drops below 1 they die. They lose 1 health value every game tick.
They eat 10 plant value per game tick.
They can move 5 squares per game tick. (real mph = 35)
Eating a deer takes 40 game ticks. Gives predator 80 health points.
Reproduction:
TODO	
Wolf
Health = 80, if it drops below 35 they search for food. If it drops below 1 they die. They lose 1 health value every game tick.
They eat 10 health value per game tick.
They can move 5 squares per game tick. (real mph = 35)
Reproduction:
TODO	
Birds
Fly around and poop on other animals. Poop blinds them. Also slows movement speed
Removes 6 health


