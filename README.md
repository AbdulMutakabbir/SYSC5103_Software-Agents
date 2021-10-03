# SYSC5103_Software-Agents
## SYSC5103 Assignment 1 - Question 1

###  Problem Statement:
Come up with a reactive soccer-playing agent for RoboCup, as per the definition of a reactive agent found in the Wooldridge book. Use Krislet as the starting point for your code. The agent function, i.e. the mapping between the environment state and the action, should be stored in an editable text file separate from the code. One should be able to modify the behavior of your agent by simply editing the text file and without recompiling your code. In a short guide (about half a page and up to a page) tell us how to read, edit and make sense of your agent function definition, give a brief intelligible description of the function, describe how your code executes the agent definition, how to run your code, and what kind of behavior to expect when running your code. 

### Read Config File:
> File is located in Krislet Folder. File Name: ReactiveAgent_Mapping.csv
Each line contains a pair of envirenment and action seperated by a "," which represents a single maping [Eg: E1,Ac1]

### Edit Config File:
Create a mapping of "Environment , Action" in each Line to add the behaviour to the agent.
> Allowed actions: "Turn", "KickBall" and "MoveTowardsBall"
> Allowed environments: "Ball_Unknown", "Ball_Kickable" and "Ball_Far"

### Agent Function Definition:
* The agent "Turns" when the "Ball is unknown".
* The agent "Kicks the ball" when the "Ball is kickable".
* The agent "Moves towards the ball" when the "Ball is far"
![Agent Mapping](https://raw.githubusercontent.com/AbdulMutakabbir/SYSC5103_Software-Agents/assignment_1_q1/assets/Software_Agents%20-%20Reactive_Agents.svg)

### Code Description:
* Abstract "Action" class structures the agents actions. This is extended by "ActionTurn", "ActionMoveTowardsBall", "ActionKick" classes which implement their own "do_action" method which performs the action for the agent.
* The "Environment" class stores the environment variables and returns the proper environment when passed the parameters to it.
* The "ReactiveMapper" class does the following
    * Reads the mapping from the config file.
    * Prints the mapping.
    * Returns the action to performend when passed a state to it.

### Code Execution:
> Exactly same as Krislet execution.

### Expected Behaviour:
Agent runs towards the ball when it knows where it and kicks the ball when the agent is close to it. I case the agent is not aware of the ball it will turn to find the ball.