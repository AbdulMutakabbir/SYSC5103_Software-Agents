//	File:			STRIP.java
//	Author:		    Mutakabbir
//	Date:			11/09/2020

import java.util.ArrayList;

/*
*   This class holds the relation data 
*   
*   @author  Mutakabbir
*/
public class RelationalSentences {

    @Override
    public String toString(){
        return this.type[0] + "(" + this.terms.toString() + ") ";//-> " + this.terms.toString();
    }

    static Object[] REL_KICKED = new Object[] { "Kicked", 0 };
    static Object[] REL_UNKNOWN = new Object[] { "Unknown", 1 };
    static Object[] REL_BALL_THETA = new Object[] { "BallTheta", 2 };
    static Object[] REL_GOAL_THETA = new Object[] { "GoalTheta", 3 };
    static Object[] REL_BALL_DISTANCE = new Object[] { "BallDistance", 4 };
    static Object[] REL_GOAL_DISTANCE = new Object[] { "GoalDistance", 5 };

    int arity;
    ArrayList<Object> terms;
    Object[] type;

    public RelationalSentences(Object[] type, int arity, ArrayList<Object> terms)
            throws ArrayIndexOutOfBoundsException {
        this.type = type;
        this.arity = arity;
        this.terms = terms;
        // System.out.println((this.terms.size() == this.arity) + " " + (this.terms.size() == this.arity) + " " + type[0].toString() + arity + terms.toString());
        if (!this.validRealtion())
            throw new ArrayIndexOutOfBoundsException("Arity does not match the number of terms");
    }

    public Boolean validRealtion() {
        if (this.terms.size() == this.arity && (int) this.type[1] <= 5 && (int) this.type[1] >= 0)
            return true;
        else
            return false;
    }

    public String getHashCode() {
        String hashCode = "" + (int) this.type[1];
        for (int index = 0; index < this.arity; index++) {
            hashCode += "" + index + this.terms.get(index);
        }
        return hashCode;
    }

    public static RelationalSentences getRelationFromType(String name) {
        RelationalSentences relation;
        ArrayList<Object> realtionTerms = new ArrayList<Object>();

        switch (name) {
        case "unknownBall":
            realtionTerms.add("ball");
            int unknownBallArity = 1;
            relation = new RelationalSentences(RelationalSentences.REL_UNKNOWN, unknownBallArity, realtionTerms);
            break;
        case "unknownGoal":
            realtionTerms.add("goal");
            int unknownGoalArity = 1;
            relation = new RelationalSentences(RelationalSentences.REL_UNKNOWN, unknownGoalArity, realtionTerms);
            break;
        case "ballTheta":
            realtionTerms.add("theta");
            int ballThetaArity = 1;
            relation = new RelationalSentences(RelationalSentences.REL_BALL_THETA, ballThetaArity, realtionTerms);
            break;
        case "ballDistance":
            realtionTerms.add("d");
            int ballDistanceArity = 1;
            relation = new RelationalSentences(RelationalSentences.REL_BALL_DISTANCE, ballDistanceArity, realtionTerms);
            break;
        case "ballTheta0":
            realtionTerms.add("theta0");
            int ballTheta0Arity = 1;
            relation = new RelationalSentences(RelationalSentences.REL_BALL_THETA, ballTheta0Arity, realtionTerms);
            break;
        case "ballDistance1":
            realtionTerms.add("d1");
            int ballDistance1Arity = 1;
            relation = new RelationalSentences(RelationalSentences.REL_BALL_DISTANCE, ballDistance1Arity, realtionTerms);
            break;
        case "goalTheta":
            realtionTerms.add("theta");
            int goalThetaArity = 1;
            relation = new RelationalSentences(RelationalSentences.REL_GOAL_THETA, goalThetaArity, realtionTerms);
            break;
        case "goalDistance":
            realtionTerms.add("d");
            int goalDistanceArity = 1;
            relation = new RelationalSentences(RelationalSentences.REL_GOAL_DISTANCE, goalDistanceArity, realtionTerms);
            break;
        case "kicked":
            realtionTerms.add("ball");
            int kickedArity = 1;
            relation = new RelationalSentences(RelationalSentences.REL_KICKED, kickedArity, realtionTerms);
            break;
        default:
            realtionTerms.add("null");
            int arity = 1;
            relation = new RelationalSentences(RelationalSentences.REL_UNKNOWN, arity, realtionTerms);
            break;
        }

        return relation;
    }

}