# PIBIC-C2
Scientific iniciation project in Command and Control study area with a Drone and Tasks scenario.

## Alpha Phase (Version 1) - 13/12/2019

Modify an existing project of the Repast's examples to introduce Drones with Tasks context. This version provides a scenario with drones and tasks randomly distribuited. The drones will persue the nearest task. Tasks do not consume any effort to be executed and also the drones have infinite energy to move around and to execute tasks. All drones are capable to execute any given task.

## Beta Phase (Version 2) - 19/12/2019

Added to the previous version a variety of sensors to the drones. More over, each task has a specific type, which matches a especific type of sensor. The distribution of the types of sensors on board of each drone is random. Hence, it's possible that in a given context, drones are not capable of executing any available task. Finally, a finite amount of energy (fuel) was added to each drone. Each type of task demands a different amount of energy to be executed.

## Gamma Phase (Version 3) - 26/12/2019

Each drone must have only one type of each sensor on board. Show on the screen the current C2-Approach (Network topology). Thus, it was removed the parallelism structure of Repast, adding a Token class, which contains tasks to do and different lists of drones. The drone will only be capable of allocating a task after receives the token.

## Delta Phase (Version 4) - 07/01/2020

Each drone is capable of allocating more than 1 task. Switch the arrays of sensors (integers) to a proper Sensor class with specific information. The criteria of chosing the best task for each drone also changed. With the Sensor Class the new criteria uses a manipulation that combines distance and quality. Add a dinamic change, with a random drop of an entity (drone). More over, additional details were added to the network communication's topology.

## Epsilon Phase (Version 5) - 14/01/2020

Created a new class, C2Network, adding more modulality. Thus, this information was removed from the Token class. The Sensor class was evolved to be active/inactive and dynamically changes executing different types of tasks. In this version, changing approachs can happen even in normal scenarios, not only worst cases. Added in the Token an array of saturated agents, which contains drones that are not capable anymore of executing tasks. The new observer class is a Controller class, which has the Schedule method and check/pick dropped drones. A new DynamicChange class was developed to add more types of dynamic changes, like dropping drones. Evolved Token with a TimeOut/TimeStamp feature adding agility to the system. More over, each drone will have a redundancy Token to add even more agility to the group of drones. Everything was tested.

## Zeta Phase (Version 6) - 31/01/2020

Phase dedicated to refactoring and renaming. Added a new dynamic change, related with the environment (sensor qualities).

## Eta Phase (Version 7) - 14/02/2020

In this version, it was added more agility to the agents, refueling tasks that were allocated to dropped drones. More over, the communication's structure changed to be more similiar to real life's circular communication (manage token and send token in different ticks). Finally, saturated drones are not useful anymore, so this data strucutre was removed.

## Theta Phase (Version 8) - 27/02/2020

This phase was dedicated to add one more C2 Approach (Coordinated without allocation algorithm). First, the Token class was modified to a super abstract class. Each C2 Approach has a different type of token with its own specific information. More over, a Role abstract class was added to encapsulate specific roles's functionalities to the agents and providing runtime changes. TaskAllocator, Executor and C2ApproachSelector classes were added overriding methods from the Role class. Finally, the current C2 Approach is a key information to execute each role.

## Iota Phase (Version 9) - 04/03/2020

Changed the way drones get next drones to communicate (getNextActiveDrones method). The sendToken method is not responsable anymore of selecting active drones. In the deconflicted approach it was added a limit to the range of drones's communications to simulate a more realistic scenario. More over, it is now possible to separate the agents in different deconflicted teams. It was decided the allocation algorithm for the coordinated approach. It is based on the same strategy used in the deconflicted approach, with the master knowing all the necessary local information.

## Kappa Phase (Version 10) - 12/03/2020

It was reduced the size and complexity of the Token. Hence, token's functionalities was re-distribuited to the Drone's class and TaskAllocator's class. The reallocation process is now occuring inside the Drone's class (verifying first that the Drone possess the TaskAllocator role). The method getNextActiveDrones was removed and all the intelligence was moved to the sendToken method. This method is now overloaded with the current C2 Approach as the key information. Removing dropped drones is now an agent responsability. In the deconflicted approach isn't possible to occur different teams, because this feature would interfer to future implementation of manuver operation. More over, this was a refactor phase.

## Lambda Phase (Version 11) - 18/08/2020

Xxxxxxxx


