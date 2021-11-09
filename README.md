# SYSC5103_Software-Agents
## SYSC5103 Assignment 2 - Question 1

### Problem Statement:
Create a RoboCup agent that uses either an expert system or a planner or a theorem prover. You can use sample code from the AI textbook [4] (probably the easiest way), implement the algorithm yourself (but that is additional burden on you to implement the algorithms correctly!), or use code from any other place (as long as you have permission and that you include a link to the site you took it from!). Note that the agent doesn't need to really play soccer; all it needs to do is exhibit the fact that it can take the input from the Soccer Server, use the reasoning algorithm, and do something meaningful. To prove that your agent is indeed reasoning and not just reacting:

  * if you use a planner, the resulting plan to execute should involve at least two STRIPS operators;
  * if you use an expert system, the forward or backward chaining engine should chain at least two rules;
  * if you use a theorem prover, at least one proof should have a depth of at least 2 steps.

give a one page description of your approach: the justification for your choice, the input you used, the output of the algorithm, how to run your code, what is the code doing, etc. Only Java code is accepted. I should be able to run your code just like I run Krislet. Code that is hard to compile and/or run will lead to severe mark deduction..  

### Option Chosen: 
Planner with 6 operators


### Choice reasoning: 
The reasoning behind choosing planner was to have a step wise approach solve a deductive reasoning problem.
> Inputs used: The agent uses two inputs ball and goal of the type ObjectInfo.


### STRIP Operators Diagram:
![STRIP Operators](https://github.com/AbdulMutakabbir/SYSC5103_Software-Agents/blob/assignment_2_q1/assets/operarators.png)

> The first theree operators (LookForAll, LookForBall LookForGoal) are there for the agent to find the objects in the environment.

> The next operator (AligintowardsBall) makes the agent turn towards the ball.

> The fifth operator (MoveTowardsBall) gets the agent to move in the direction of the ball.

> The final operator (KickBall) gets the agent to kick the ball.

![Goal wff](https://github.com/AbdulMutakabbir/SYSC5103_Software-Agents/blob/assignment_2_q1/assets/goal.png)

> The goal here for the agent is find a sequence of operations that will lead it to know where the goal is while the agent kicks the ball.


### Code Description:
The program works in two parts
 1.	Get the sequence of operators form STRIP approach:
      > Here the agent is given an overview of the environment for which the agent has decide a sequence of operations (i.e., a sequential group different actions) that it will carry out in the future without knowing the complete future environment for his actions.
 2.	For each operation perform the appropriate actions to complete the steps:
      > Now the agents perform the sequence of actions in each operation and gets the exact environment input to fine tune his actions to reach the goal.

If working correctly the agent should choose one of the following sequence of operations:
 *	LookForAll -> AligintowardsBall -> MoveTowardsBall -> KickBall -> ….
 *	LookForBall -> AligintowardsBall -> MoveTowardsBall -> KickBall -> …
 *	LookForGoal -> AligintowardsBall -> MoveTowardsBall -> KickBall -> …

> Note: The agent may choose some random operation to do in the middle of the above the sequence, but the overall flow will be the among the 3 on top. 


### Execution:
> Exactly same as Krislet execution.
> Refer the read me on main branch for further information.

### Expected Behaviour:
* Agent will first find the ball.
* Once the agent finds the ball it will align itself with the goal.
* Then the agent will move towards the ball and aligin it self whith the goal.
* Fianlly the agent will kick the ball towards the goal when it is near it.

> Note: Agent may sometimes not align itself properly with the goal in those situations the agents may end up kicking the ball back and forth without it ever reaching the goal.
