import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class STRIP {
    public HashMap<String, RelationalSentences> model;
    public HashMap<String, RelationalSentences> goals;
    public HashMap<String, STRIPOperator> operators;
    public ArrayList<STRIPOperator> steps;

    public static void main(String args[]) {
        STRIP strip = new STRIP(null, null);
        strip.plan();

        System.out.println(strip.steps.toString());
    }

    public STRIP(ObjectInfo ball, ObjectInfo goal) {
        this.goals = this.initGoals();
        this.operators = this.initOperators();
        this.model = this.initModel(ball, goal);
        System.out.println(model);
        this.steps = new ArrayList<STRIPOperator>();
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

        STRIPOperator s = new OperatorLookForAll();
        operators.put(OperatorLookForAll.name, s);
        operators.put(OperatorLookForBall.name, new OperatorLookForBall());
        operators.put(OperatorLookForGoal.name, new OperatorLookForGoal());
        operators.put(OperatorAlignWithBall.name, new OperatorAlignWithBall());
        operators.put(OperatorMoveTowardsBall.name, new OperatorMoveTowardsBall());
        operators.put(OperatorKickBall.name, new OperatorKickBall());

        return operators;
    }

    public void plan() {

        HashMap<String, RelationalSentences> completeGoals, incompleteGoals;
        completeGoals = new HashMap<String, RelationalSentences>();
        incompleteGoals = new HashMap<String, RelationalSentences>();

        do {
            incompleteGoals.clear();
            completeGoals.clear();

            Iterator<String> goalsIterator = this.goals.keySet().iterator();
            while (goalsIterator.hasNext()) {
                String goal = goalsIterator.next();
                if (this.model.containsKey(goal))
                    completeGoals.put(goal, this.goals.get(goal));
                else
                    incompleteGoals.put(goal, this.goals.get(goal));
            }

            if (incompleteGoals.size() == 0)
                break;

            HashMap<Double, String> ranking = new HashMap<Double, String>();
            Iterator<String> operatorIterator = this.operators.keySet().iterator();
            while (operatorIterator.hasNext()) {
                String operator = operatorIterator.next();
                double score = this.operators.get(operator).getScore(this.model, incompleteGoals, completeGoals);
                ranking.put(score, operator);
            }
            System.out.println("\n" + incompleteGoals.values().toString());
            System.out.println(completeGoals.values().toString());

            double maxScore = Collections.max(ranking.keySet());

            STRIPOperator operator = this.operators.get(ranking.get(maxScore));
            this.steps.add(operator);

            operator.updateGoals(this.goals);
            operator.updateModel(this.model);

            System.out.println(operator.toString());
            System.out.println(model.values().toString());
            System.out.println(goals.values().toString());

            
             int x = 0;

        } while (incompleteGoals.size() > 0);
    }

}