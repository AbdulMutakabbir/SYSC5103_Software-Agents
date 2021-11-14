# SYSC5103_Software-Agents
## SYSC5103 Assignment 2 - Question 2

### Problem Statement:
Suppose that the soccer-playing agent has to choose between 2 or more actions, where the outcome of the actions on certain factors (those that determine the welfare of the agent) is uncertain.

 1. Come up with a simple yet realistic (in terms of dependencies and probability figures) Decision Network (see R & N 16.5) describing probabilistic dependencies among various factors as well as between actions and those factors relevant to the "well-being" of the agent.
 2. Using the utilities attached to those "well-being" factors, show (i.e., calculate!) how the introduction of evidence about the environment (i.e., P(X|A) vs P(X|A,e)) changes the choice of the action that the agent will make. (note that this means that your hand-crafted probabilities should be such that the choice of the agent does change with the introduction of evidence) 

### Decision Network  
![Decision Network](https://github.com/AbdulMutakabbir/SYSC5103_Software-Agents/blob/assignment_2_q2/assets/DecisionNetwork.drawio.svg) 

Decision Nodes:
> There is only 1 decision node in the network named "Action".
>
> Action node has 3 values it can take {Turn, Dash, Kick}

Current State Node:
> There are 2 Current State Nodes named
> * SeeBall (SB)
>   > SeeBall takes two vales {T, F}
> * SeeGoal (SG)
>   > SeeGoal takes two vales {T, F}
> 

Outcome Nodes:
> There are 3 Outcome Nodes named
> * WillSeeBall (WSB)
>   > WillSeeBall takes two values {T, F}
> * WillSeeGoal (WSG)
>   > WillSeeGoal takes two values {T, F}
> * BallDistance (BD)
>   > BallDistance takes three values {Unknown, Near, Far}
> The Conditional Probablity Table (CBT) are give for each in the figure.

Utility Node:
> Calculates the utility of the network for actions.

### Utility Calcuation
![Expected Utility Function](https://github.com/AbdulMutakabbir/SYSC5103_Software-Agents/blob/assignment_2_q2/assets/ExpectedUtility.png)

> Where 
> * W => weights
> * U() => utility function for environment
> * EU() => expected utility for an action
> * {SB, SG} => are Evidence/Current State Nodes
> * {WSG, WSB, BD} => are Environment/Outcome Nodes

![Weight Function](https://github.com/AbdulMutakabbir/SYSC5103_Software-Agents/blob/assignment_2_q2/assets/Weight%20Function.png)
> above figure shows how weights are obtained.

![Utility Function](https://github.com/AbdulMutakabbir/SYSC5103_Software-Agents/blob/assignment_2_q2/assets/Utility%20Function.png)
> above figure shows how utilities are obtained.


### Code Execution 
* Running Robocup
  > Same as Krizlet.
* Introduction of Evidence Output
  1. CD into "RoboCup\Krislet"  
  2. Execute "java RoboCupDeductiveNetwork"

