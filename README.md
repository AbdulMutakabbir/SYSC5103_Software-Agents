# SYSC5103_Software-Agents
## SYSC5103 Assinment 1 - Question 2

### Problem Statement
Come up with a state-based soccer-playing agent. Ideally, the behaviour of the agent (i.e., the state machine) should again be stored in an editable text file separate from the code, but we're also ok with a hard-coded state machine as long as it is easy to modify it. Again, in a short guide (up to a page or two this time) tell us how to read, edit and make sense of your agent function definition, provide a human-readable finite state machine diagram (ideally in the extended UML FSM format discussed in class) corresponding to your implementation, describe how your code executes the agent definition, how to run your code, and what kind of behavior to expect when running your code. In addition, the guide should PROVE, using runs derived manually from the state machine or from logs obtained from running the agent, that the behaviour is indeed state-based and not reactive.  

### How to Read State Machine Config File:
> File is located in Krislet Folder. 
> File Name: StateAgent_Table.csv

The file has the following format
* Line 1: Start States of the State Machine [Should contain only one State name. Eg: Q1]
* Line 2: Acceptance States of the State Machine in CSV format [The states should be present in Line 3 of the file. Eg: Q2,Q3]
* Line 3: Set of States in CSV format [Eg: Q1,Q1,Q3,Q4]
* Line 4: Set of Alphabets in CSV format [Eg: a1,a2,a3]
* Line 5 - EOF: Containts the transitons result (Ac,Q) in tabulated format. Action and State should be seperated by a ",". Transitions should be seperated by a ";". [Eg: Ac1,Q1; Ac2,Q2; Ac3,Q3; ...]
> Line 5 to EOF contains a table format for the transitions where each line corresponds to the specific index of the State defined in Line 3 and colums (";" seperated in each line) correstponds to the specific index of alphabet definde in Line 4

### How to Edit State Machine Config File:
Points to remember during editing the file:
* There should be a minimum of 5 line in the file.
* The 1st line contains only one State.
* All the State present in Line 1, Line 2 and from Line 5 onwards should be present in Line 3.
* All the Alphabets present from line 5 onwards should be present in Line 4.
* Number of Line from Line 5 onwards to the EOF should be equal to the the number of state present in Line 3 and the number of transitions (";" seperated) in each line should be eaqual to the number of alphabets defined in Line 4
> Edit actions and States from Line 5 to EOF based on the above rules.

### State Machine Diagram
![State Machine](https://raw.githubusercontent.com/AbdulMutakabbir/SYSC5103_Software-Agents/assignment_1_q2/assets/Software_Agents%20-%20State_Based_Agents.svg)

> The State Machine has 2 States
> * Performing One Step Action: This state reminds the agent that the current action it will take is not depend on its past actions.
> * Performing Multi Step Action: This state reminds the agent that thr current action it will take will be dependent on his past action.

> Agents can performs 3 actions:
> * Turn: rotates the agent by a certain angel.
> * Dash: move the agent forwards.
> * Kick: agent kicks the ball.

> The agent Senses 2 factors from the Envirenment:
> * Ball: which can be in 3 possible situations
>   * at a distance of "1" 
>   * "Far" from the agent
>   * the ball is "unknown" to the agent
> * Goal: which can be in 2 possible situations
>   * agent "Knows" where the goal is
>   * the goal is "UnKnow to the agent
> Base on the above 2 factors the envirment can have the following 6 possabilities.
> * Ball and Goal are Unknown
> * Ball is Unknown but the goal is Known
> * Ball is far and the goal is Unknown
> * Ball is far and the goal is Known 
> * Ball is at a distance of 1 and the goal is Unknown
> * Ball is at a distance of 1 and the goal is Known