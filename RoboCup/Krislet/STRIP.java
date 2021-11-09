//	File:			STRIP.java
//	Author:		    Mutakabbir
//	Date:			11/09/2020

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * The STRIP class performs the deductive reasoning planning for the agent 
 *
 * @author  Mutakabbir
 */
public class STRIP {
    public HashMap<String, RelationalSentences> model;
    public HashMap<String, RelationalSentences> goals;
    public HashMap<String, STRIPOperator> operators;

    // public static void main(String args[]) {
    //     STRIP strip = new STRIP(null, null);
    //     System.out.println(strip.plan().toString());
    // }

    /**
     * Constructor 
     *
     * @param  ball   contails ball info
     * @param  goal   contails goal info
     */
    public STRIP(ObjectInfo ball, ObjectInfo goal) {
        this.goals = this.initGoals();
        this.operators = this.initOperators();
        this.model = this.initModel(ball, goal);
    }

    /**
     * initializes the goals  
     *
     * @return goal mapping
     */
    public HashMap<String, RelationalSentences> initGoals() {
        HashMap<String, RelationalSentences> initGoals = new HashMap<String, RelationalSentences>();

        RelationalSentences kickedBall = RelationalSentences.getRelationFromType("kicked");
        RelationalSentences unknownBall = RelationalSentences.getRelationFromType("unknownBall");
        RelationalSentences goalTheta = RelationalSentences.getRelationFromType("goalTheta");
        RelationalSentences goalDistance = RelationalSentences.getRelationFromType("goalDistance");

        initGoals.put(kickedBall.getHashCode(), kickedBall);
        initGoals.put(unknownBall.getHashCode(), unknownBall);
        initGoals.put(goalTheta.getHashCode(), goalTheta);
        initGoals.put(goalDistance.getHashCode(), goalDistance);

        return initGoals;
    }

    /**
     * initializes the Model  
     *
     * @param  ball   contails ball info
     * @param  goal   contails goal info
     * @return model mapping
     */
    public HashMap<String, RelationalSentences> initModel(ObjectInfo ball, ObjectInfo goal) {
        HashMap<String, RelationalSentences> initModel = new HashMap<String, RelationalSentences>();

        if (ball == null) {
            RelationalSentences unknownBall = RelationalSentences.getRelationFromType("unknownBall");

            initModel.put(unknownBall.getHashCode(), unknownBall);
        } else {
            RelationalSentences ballTheta = RelationalSentences.getRelationFromType("ballTheta");
            RelationalSentences ballDistance = RelationalSentences.getRelationFromType("ballDistance");

            initModel.put(ballTheta.getHashCode(), ballTheta);
            initModel.put(ballDistance.getHashCode(), ballDistance);
        }

        if (goal == null) {
            RelationalSentences unknownGoal = RelationalSentences.getRelationFromType("unknownGoal");

            initModel.put(unknownGoal.getHashCode(), unknownGoal);
        } else {
            RelationalSentences goalTheta = RelationalSentences.getRelationFromType("goalTheta");
            RelationalSentences goalDistance = RelationalSentences.getRelationFromType("goalDistance");

            initModel.put(goalTheta.getHashCode(), goalTheta);
            initModel.put(goalDistance.getHashCode(), goalDistance);
        }

        return initModel;
    }

    /**
     * initializes the Operators  
     *
     * @return operator mapping
     */
    public HashMap<String, STRIPOperator> initOperators() {
        HashMap<String, STRIPOperator> operators = new HashMap<String, STRIPOperator>();

        operators.put(OperatorLookForAll.name, new OperatorLookForAll());
        operators.put(OperatorLookForBall.name, new OperatorLookForBall());
        operators.put(OperatorLookForGoal.name, new OperatorLookForGoal());
        operators.put(OperatorAlignWithBall.name, new OperatorAlignWithBall());
        operators.put(OperatorMoveTowardsBall.name, new OperatorMoveTowardsBall());
        operators.put(OperatorKickBall.name, new OperatorKickBall());

        return operators;
    }

    /**
     * returns the plan for agent  
     *
     * @return a list of opertator in sequence
     */
    public ArrayList<STRIPOperator> plan() {
        
        // initi steps
        ArrayList<STRIPOperator> steps = new ArrayList<STRIPOperator>();

        // init loacl goal states 
        HashMap<String, RelationalSentences> completeGoals, incompleteGoals;
        completeGoals = new HashMap<String, RelationalSentences>();
        incompleteGoals = new HashMap<String, RelationalSentences>();

        // loop over goals
        Iterator<String> goalIterator = this.goals.keySet().iterator();
        while(goalIterator.hasNext()){
            String goal = goalIterator.next();
            // if goal completed add to the completed list
            if(this.model.containsKey(goal)){
                completeGoals.put(goal, this.goals.get(goal));
            }
            // else add to incomplete list
            else{
                incompleteGoals.put(goal, this.goals.get(goal));
            }
        }

        // do till all goals are satisfied
        while(incompleteGoals.size() != 0){
            
            // init possible operator list 
            ArrayList<STRIPOperator> possiblOperator = new ArrayList<STRIPOperator>();

            // loop over operators
            Iterator<String> operatorIterator = this.operators.keySet().iterator();
            while(operatorIterator.hasNext()){
                String operator = operatorIterator.next();
                Iterator<String> addIterator = this.operators.get(operator).getAddList().keySet().iterator();
                while(addIterator.hasNext()){
                    String relation = addIterator.next();
                    // if the operator contains relations in addList which are also in incomplete list
                    // add it to possible operators
                    if (incompleteGoals.containsKey(relation)){
                        possiblOperator.add(this.operators.get(operator));
                        break;
                    }
                }
            }

            STRIPOperator operator = null;
            if(possiblOperator.size() > 0){
                // pick randomly operator from possible operators
                // here last one is choosen 
                operator = possiblOperator.get(possiblOperator.size()-1);
            }
            else{
                // there are no possible final plan. 
                // return the partial path
                Collections.reverse(steps);
                return steps;
            }

            // add operator to the plan 
            steps.add(operator);

            // add the completed goals form the operators to complete list
            Iterator<String> incompleteGoalsIterator = incompleteGoals.keySet().iterator();
            while (incompleteGoalsIterator.hasNext()) {
                String condition = incompleteGoalsIterator.next();
                if (operator.getAddList().containsKey(condition)){
                    completeGoals.put(condition, operator.getAddList().get(condition));
                }
            }

            // remove the completed operators from the incomplete list
            incompleteGoals.keySet().removeAll(operator.getAddList().keySet());

            // add incomplete preconditions to incompleat list 
            Iterator<String> preconditionIterator = operator.getPrecondition().keySet().iterator();
            while(preconditionIterator.hasNext()){
                String condition = preconditionIterator.next();
                if(!completeGoals.containsKey(condition)) {
                    incompleteGoals.put(condition, operator.getPrecondition().get(condition));
                }
            } 

        }

        // return the completed path
        Collections.reverse(steps);
        return steps;
    }

}