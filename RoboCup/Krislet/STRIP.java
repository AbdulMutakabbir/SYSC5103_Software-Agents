import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class STRIP {
    public HashMap<String, RelationalSentences> model;
    public HashMap<String, RelationalSentences> goals;
    public HashMap<String, STRIPOperator> operators;

    // public static void main(String args[]) {
    //     STRIP strip = new STRIP(null, null);
    //     System.out.println(strip.plan().toString());
    // }

    public STRIP(ObjectInfo ball, ObjectInfo goal) {
        this.goals = this.initGoals();
        this.operators = this.initOperators();
        this.model = this.initModel(ball, goal);
    }

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

    public ArrayList<STRIPOperator> plan() {
        
        ArrayList<STRIPOperator> steps = new ArrayList<STRIPOperator>();

        HashMap<String, RelationalSentences> completeGoals, incompleteGoals;
        completeGoals = new HashMap<String, RelationalSentences>();
        incompleteGoals = new HashMap<String, RelationalSentences>();

        Iterator<String> goalIterator = this.goals.keySet().iterator();
        while(goalIterator.hasNext()){
            String goal = goalIterator.next();
            if(this.model.containsKey(goal)){
                completeGoals.put(goal, this.goals.get(goal));
            }
            else{
                incompleteGoals.put(goal, this.goals.get(goal));
            }
        }

        while(incompleteGoals.size() != 0){
            ArrayList<STRIPOperator> possiblOperator = new ArrayList<STRIPOperator>();

            Iterator<String> operatorIterator = this.operators.keySet().iterator();
            while(operatorIterator.hasNext()){
                String operator = operatorIterator.next();
                Iterator<String> addIterator = this.operators.get(operator).getAddList().keySet().iterator();
                while(addIterator.hasNext()){
                    String relation = addIterator.next();
                    if (incompleteGoals.containsKey(relation)){
                        possiblOperator.add(this.operators.get(operator));
                        break;
                    }
                }
            }

            STRIPOperator operator = null;
            if(possiblOperator.size() > 0){
                operator = possiblOperator.get(possiblOperator.size()-1);
            }
            else{
                Collections.reverse(steps);
                return steps;
            }

            steps.add(operator);

            Iterator<String> incompleteGoalsIterator = incompleteGoals.keySet().iterator();
            while (incompleteGoalsIterator.hasNext()) {
                String condition = incompleteGoalsIterator.next();
                if (operator.getAddList().containsKey(condition)){
                    completeGoals.put(condition, operator.getAddList().get(condition));
                }
            }

            incompleteGoals.keySet().removeAll(operator.getAddList().keySet());

            Iterator<String> preconditionIterator = operator.getPrecondition().keySet().iterator();
            while(preconditionIterator.hasNext()){
                String condition = preconditionIterator.next();
                if(!completeGoals.containsKey(condition)) {
                    incompleteGoals.put(condition, operator.getPrecondition().get(condition));
                }
            } 

        }

        Collections.reverse(steps);
        return steps;
    }

}