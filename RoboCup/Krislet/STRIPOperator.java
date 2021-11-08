import java.util.HashMap;
import java.util.Iterator;

public class STRIPOperator {

    protected HashMap<String, RelationalSentences> addList;
    protected HashMap<String, RelationalSentences> deleteList;
    protected HashMap<String, RelationalSentences> precondition;

    public STRIPOperator() {
        addList = new HashMap<String, RelationalSentences>();
        deleteList = new HashMap<String, RelationalSentences>();
        precondition = new HashMap<String, RelationalSentences>();
    }

    public void updateModel(HashMap<String, RelationalSentences> model) {

        Iterator<String> addIterator = this.addList.keySet().iterator();
        while (addIterator.hasNext()) {
            String relation = addIterator.next();
            if (!model.containsKey(relation))
                model.put(relation, this.addList.get(relation));
        }

        Iterator<String> delIteraotor = this.deleteList.keySet().iterator();
        while (delIteraotor.hasNext()) {
            String relation = delIteraotor.next();
            if (model.containsKey(relation))
                model.remove(relation);
        }

    }

    public void updateGoals(HashMap<String, RelationalSentences> goals) {

        Iterator<String> conditionIterator = this.precondition.keySet().iterator();
        while (conditionIterator.hasNext()) {
            String condition = conditionIterator.next();
            if (!goals.containsKey(condition))
                goals.put(condition, this.precondition.get(condition));
        }
    }

    public double getScore(HashMap<String, RelationalSentences> model,
            HashMap<String, RelationalSentences> incompleteGoals, HashMap<String, RelationalSentences> completeGoals) {
        double score;

        // Iterator<String> delIterator = this.deleteList.keySet().iterator();
        // while(delIterator.hasNext()){
        //     String delRelation = delIterator.next();
        //     if(!model.containsKey(delRelation)) return 0;
        // }

        int addListGoalCompair = 0;
        Iterator<String> goalsIterator = incompleteGoals.keySet().iterator();
        while (goalsIterator.hasNext()) {
            String goal = goalsIterator.next();
            if (this.addList.containsKey(goal))
                addListGoalCompair++;
        }
        double addListGoalCompairScore = 1.0 * addListGoalCompair / incompleteGoals.size();

        int preconditionModelCompare = 0;
        Iterator<String> preconditionIterator = this.precondition.keySet().iterator();
        while (preconditionIterator.hasNext()) {
            String condition = preconditionIterator.next();
            if (model.containsKey(condition))
                preconditionModelCompare++;
        }
        double preconditionModelCompareScore = 1.0 * preconditionModelCompare / this.precondition.size();

        int deleteListGoalCompare = 0;
        Iterator<String> delGoalsIterator = completeGoals.keySet().iterator();
        while (delGoalsIterator.hasNext()) {
            String goal = delGoalsIterator.next();
            if (this.deleteList.containsKey(goal))
                deleteListGoalCompare++;
        }
        double deleteListGoalCompareScore;
        if (completeGoals.size() == 0) {
            deleteListGoalCompareScore = 0;
        } else {
            deleteListGoalCompareScore = 1.0 * deleteListGoalCompare / completeGoals.size();
        }

        if (addListGoalCompairScore != 0 || preconditionModelCompareScore != 0) {
            score = addListGoalCompairScore + preconditionModelCompareScore - deleteListGoalCompareScore;
        }
        else{
            score = 0;
        }
        return score;
    }

}

class OperatorLookForAll extends STRIPOperator {

    @Override
    public String toString() {
        return name;
    }

    static String name = "LookForAll";

    public OperatorLookForAll() {

        RelationalSentences unknownBall = RelationalSentences.getRelationFromType("unknownBall");
        RelationalSentences unknownGoal = RelationalSentences.getRelationFromType("unknownGoal");
        RelationalSentences ballTheta = RelationalSentences.getRelationFromType("ballTheta");
        RelationalSentences ballDistance = RelationalSentences.getRelationFromType("ballDistance");
        RelationalSentences goalTheta = RelationalSentences.getRelationFromType("goalTheta");
        RelationalSentences goalDistance = RelationalSentences.getRelationFromType("goalDistance");

        this.precondition.put(unknownBall.getHashCode(), unknownBall);
        this.precondition.put(unknownGoal.getHashCode(), unknownGoal);

        this.deleteList.put(unknownBall.getHashCode(), unknownBall);
        this.deleteList.put(unknownGoal.getHashCode(), unknownGoal);

        this.addList.put(ballTheta.getHashCode(), ballTheta);
        this.addList.put(goalTheta.getHashCode(), goalTheta);
        this.addList.put(ballDistance.getHashCode(), ballDistance);
        this.addList.put(goalDistance.getHashCode(), goalDistance);

    }

}

class OperatorLookForBall extends STRIPOperator {

    @Override
    public String toString() {
        return name;
    }

    static String name = "LookForBall";

    public OperatorLookForBall() {
        RelationalSentences unknownBall = RelationalSentences.getRelationFromType("unknownBall");
        RelationalSentences ballTheta = RelationalSentences.getRelationFromType("ballTheta");
        RelationalSentences ballDistance = RelationalSentences.getRelationFromType("ballDistance");

        this.precondition.put(unknownBall.getHashCode(), unknownBall);

        this.deleteList.put(unknownBall.getHashCode(), unknownBall);

        this.addList.put(ballTheta.getHashCode(), ballTheta);
        this.addList.put(ballDistance.getHashCode(), ballDistance);

    }

}

class OperatorLookForGoal extends STRIPOperator {

    @Override
    public String toString() {
        return name;
    }

    static String name = "LookForGoal";

    public OperatorLookForGoal() {
        RelationalSentences unknownGoal = RelationalSentences.getRelationFromType("unknownGoal");
        RelationalSentences goalTheta = RelationalSentences.getRelationFromType("goalTheta");
        RelationalSentences goalDistance = RelationalSentences.getRelationFromType("goalDistance");

        this.precondition.put(unknownGoal.getHashCode(), unknownGoal);

        this.deleteList.put(unknownGoal.getHashCode(), unknownGoal);

        this.addList.put(goalTheta.getHashCode(), goalTheta);
        this.addList.put(goalDistance.getHashCode(), goalDistance);

    }

}

class OperatorAlignWithBall extends STRIPOperator {

    @Override
    public String toString() {
        return name;
    }

    static String name = "AliginWithBall";

    public OperatorAlignWithBall() {
        RelationalSentences ballTheta = RelationalSentences.getRelationFromType("ballTheta");
        RelationalSentences ballDistance = RelationalSentences.getRelationFromType("ballDistance");
        RelationalSentences ballTheta0 = RelationalSentences.getRelationFromType("ballTheta0");
        RelationalSentences goalTheta = RelationalSentences.getRelationFromType("goalTheta");
        RelationalSentences goalDistance = RelationalSentences.getRelationFromType("goalDistance");

        this.precondition.put(ballTheta.getHashCode(), ballTheta);
        this.precondition.put(ballDistance.getHashCode(), ballDistance);
        this.precondition.put(goalTheta.getHashCode(), goalTheta);
        this.precondition.put(goalDistance.getHashCode(), goalDistance);

        this.deleteList.put(ballTheta.getHashCode(), ballTheta);
        this.deleteList.put(ballDistance.getHashCode(), ballDistance);
        this.deleteList.put(goalTheta.getHashCode(), goalTheta);
        this.deleteList.put(goalDistance.getHashCode(), goalDistance);

        this.addList.put(ballTheta0.getHashCode(), ballTheta0);
        this.addList.put(ballDistance.getHashCode(), ballDistance);
        this.addList.put(goalTheta.getHashCode(), goalTheta);
        this.addList.put(goalDistance.getHashCode(), goalDistance);

    }
}

class OperatorMoveTowardsBall extends STRIPOperator {

    @Override
    public String toString() {
        return name;
    }

    static String name = "MoveTowardsBall";

    public OperatorMoveTowardsBall() {
        RelationalSentences ballDistance = RelationalSentences.getRelationFromType("ballDistance");
        RelationalSentences ballTheta0 = RelationalSentences.getRelationFromType("ballTheta0");
        RelationalSentences ballDistance1 = RelationalSentences.getRelationFromType("ballDistance1");

        this.precondition.put(ballTheta0.getHashCode(), ballTheta0);

        this.deleteList.put(ballDistance.getHashCode(), ballDistance);

        this.addList.put(ballDistance1.getHashCode(), ballDistance1);

    }
}

class OperatorKickBall extends STRIPOperator {

    @Override
    public String toString() {
        return name;
    }

    static String name = "KickBall";

    public OperatorKickBall() {
        RelationalSentences kickedBall = RelationalSentences.getRelationFromType("kicked");
        RelationalSentences unknownBall = RelationalSentences.getRelationFromType("unknownBall");
        RelationalSentences ballTheta0 = RelationalSentences.getRelationFromType("ballTheta0");
        RelationalSentences ballDistance1 = RelationalSentences.getRelationFromType("ballDistance1");

        this.precondition.put(ballTheta0.getHashCode(), ballTheta0);
        this.precondition.put(ballDistance1.getHashCode(), ballDistance1);

        this.deleteList.put(ballTheta0.getHashCode(), ballTheta0);
        this.deleteList.put(ballDistance1.getHashCode(), ballDistance1);

        this.addList.put(kickedBall.getHashCode(), kickedBall);
        this.addList.put(unknownBall.getHashCode(), unknownBall);
    }
}