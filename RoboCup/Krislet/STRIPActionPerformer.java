//	File:			STRIP.java
//	Author:		    Mutakabbir
//	Date:			11/09/2020

import java.util.ArrayList;

/*
*   This helper class is used to perform actions when the environment state is known 
*   
*   @author  Mutakabbir
*/
public class STRIPActionPerformer {
    ArrayList<STRIPOperator> plan;
    Float lastSeenBallTheta;
    Float lastSeenGoalTheta;
    Float agentRoation;
    Double agentRotationOnBall;

    int currentStep;

    public STRIPActionPerformer(ObjectInfo ball, ObjectInfo goal, ArrayList<STRIPOperator> plan) {
        this.currentStep = 0;
        this.agentRoation = 0.0F;
        this.plan = plan;
        this.agentRotationOnBall = null;

        if (ball != null) {
            this.lastSeenBallTheta = ball.m_direction;
        } else {
            this.lastSeenBallTheta = null;
        }

        if (goal != null) {
            this.lastSeenGoalTheta = goal.m_direction;
        } else {
            this.lastSeenGoalTheta = null;
        }

    }

    public Actions nextAction(ObjectInfo ball, ObjectInfo goal) {

        // loop back to initial point if the plan execution is complete
        if (currentStep >= plan.size()) {
            currentStep = 0 ;
            return new ActionUnknown();
        }

        // get current operation to perform
        STRIPOperator currentOperator = plan.get(currentStep);

        // if the operation is look for
        // look left and right and locate the ball and goal
        if (currentOperator.getOperatorName() == OperatorLookForAll.name
                || currentOperator.getOperatorName() == OperatorLookForBall.name
                || currentOperator.getOperatorName() == OperatorLookForGoal.name) {
            if (this.lastSeenBallTheta == null && ball != null) {
                this.lastSeenBallTheta = this.agentRoation + ball.m_direction;
            }

            if (this.lastSeenGoalTheta == null && goal != null) {
                this.lastSeenGoalTheta = this.agentRoation + goal.m_direction;
            }

            if (this.lastSeenBallTheta == null || this.lastSeenGoalTheta == null) {
                if (agentRoation == 0) {
                    agentRoation = 90.0F;
                    return new ActionTurn(90);
                }
                if (agentRoation == 90) {
                    agentRoation = -90.0F;
                    return new ActionTurn(180);
                } else {
                    agentRoation = 0.0F;
                    return new ActionUnknown();
                }
            }
            if (agentRoation == -90.0F) {
                agentRoation = 0.0F;
                double turnAngle = ((this.lastSeenBallTheta + this.lastSeenGoalTheta) / 2) + 90.0F;
                return new ActionTurn(turnAngle);
            }
            if (this.lastSeenBallTheta != null && this.lastSeenGoalTheta != null) {
                currentStep++;
            }
        }

        // if the operation is Align with ball
        // turn towards the ball and 
        // calculate the angle that the agent needs to turn after it reaches the ball
        if (currentOperator.getOperatorName() == OperatorAlignWithBall.name) {

            if (ball != null && goal != null) {
                boolean ballThetaPositive = ball.m_direction >= 0;
                boolean goalThetaPositive = goal.m_direction >= 0;

                double ballGolaTheta;
                if ((ballThetaPositive && goalThetaPositive) || (!ballThetaPositive && !goalThetaPositive)) {
                    ballGolaTheta = Math.max(Math.abs(ball.m_direction), Math.abs(goal.m_direction))
                            - Math.min(Math.abs(ball.m_direction), Math.abs(goal.m_direction));
                } else {
                    ballGolaTheta = Math.abs(ball.m_direction) + Math.abs(goal.m_direction);
                }

                double xGoalDistance = Math.abs(ball.m_distance) * Math.cos(ballGolaTheta);
                double yGoalDistance = Math.abs(goal.m_distance) - xGoalDistance;
                double hGoalDistance = Math.abs(goal.m_distance) * Math.sin(ballGolaTheta);

                double agentBallTheta = Math.atan(hGoalDistance / yGoalDistance);

                double agentGoalTheta = 180 - (agentBallTheta + ballGolaTheta);

                this.agentRotationOnBall = agentGoalTheta;

                currentStep++;

                return new ActionTurn(ball.m_direction);
            }
            // currentStep--;
            return new ActionUnknown();

        }

        // if operator is moveTowardsBall
        // dash till the agent reaches the ball
        if (currentOperator.getOperatorName() == OperatorMoveTowardsBall.name) {
            if (ball != null) {
                if (Math.ceil(ball.m_distance) > 1) {
                    return new ActionDash(100);
                } else {
                    currentStep++;
                    return new ActionTurn(agentRotationOnBall);
                }
            }
            // currentStep--;
            return new ActionUnknown();
        }

        // if operator is kick the kick the ball
        if (currentOperator.getOperatorName() == OperatorKickBall.name) {
            currentStep++;
            return new ActionKick(100, 0);
        }

        return new ActionUnknown();
    }

}