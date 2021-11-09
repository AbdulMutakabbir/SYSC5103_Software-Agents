import java.util.HashMap;
import java.util.Iterator;

public abstract class STRIPOperator {

    private String operatorName;
    private HashMap<String, RelationalSentences> addList;
    private HashMap<String, RelationalSentences> deleteList;
    private HashMap<String, RelationalSentences> precondition;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public HashMap<String, RelationalSentences> getAddList() {
        return addList;
    }

    public void setAddList(HashMap<String, RelationalSentences> addList) {
        this.addList = addList;
    }

    public HashMap<String, RelationalSentences> getDeleteList() {
        return deleteList;
    }

    public void setDeleteList(HashMap<String, RelationalSentences> deleteList) {
        this.deleteList = deleteList;
    }

    public HashMap<String, RelationalSentences> getPrecondition() {
        return precondition;
    }

    public void setPrecondition(HashMap<String, RelationalSentences> precondition) {
        this.precondition = precondition;
    }

    public STRIPOperator(String name) {
        this.operatorName = name;
        this.addList = new HashMap<String, RelationalSentences>();
        this.deleteList = new HashMap<String, RelationalSentences>();
        this.precondition = new HashMap<String, RelationalSentences>();
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
        
    @Override
    public String toString() {
        return this.getOperatorName() + "(p[" + precondition.size() + "],d[" + deleteList.size() + "],a[" + addList.size() + "])";
    }

}

class OperatorLookForAll extends STRIPOperator {

    public static String name = "LookForAll";

    public OperatorLookForAll() {
        super(OperatorLookForAll.name);

        RelationalSentences unknownBall = RelationalSentences.getRelationFromType("unknownBall");
        RelationalSentences unknownGoal = RelationalSentences.getRelationFromType("unknownGoal");
        RelationalSentences ballTheta = RelationalSentences.getRelationFromType("ballTheta");
        RelationalSentences ballDistance = RelationalSentences.getRelationFromType("ballDistance");
        RelationalSentences goalTheta = RelationalSentences.getRelationFromType("goalTheta");
        RelationalSentences goalDistance = RelationalSentences.getRelationFromType("goalDistance");

        this.getPrecondition().put(unknownBall.getHashCode(), unknownBall);
        this.getPrecondition().put(unknownGoal.getHashCode(), unknownGoal);

        this.getDeleteList().put(unknownBall.getHashCode(), unknownBall);
        this.getDeleteList().put(unknownGoal.getHashCode(), unknownGoal);

        this.getAddList().put(ballTheta.getHashCode(), ballTheta);
        this.getAddList().put(goalTheta.getHashCode(), goalTheta);
        this.getAddList().put(ballDistance.getHashCode(), ballDistance);
        this.getAddList().put(goalDistance.getHashCode(), goalDistance);

    }

}

class OperatorLookForBall extends STRIPOperator {

    static String name = "LookForBall";

    public OperatorLookForBall() {
        super(OperatorLookForBall.name);

        RelationalSentences unknownBall = RelationalSentences.getRelationFromType("unknownBall");
        RelationalSentences ballTheta = RelationalSentences.getRelationFromType("ballTheta");
        RelationalSentences ballDistance = RelationalSentences.getRelationFromType("ballDistance");

        this.getPrecondition().put(unknownBall.getHashCode(), unknownBall);

        this.getDeleteList().put(unknownBall.getHashCode(), unknownBall);

        this.getAddList().put(ballTheta.getHashCode(), ballTheta);
        this.getAddList().put(ballDistance.getHashCode(), ballDistance);

    }

}

class OperatorLookForGoal extends STRIPOperator {

    static String name = "LookForGoal";

    public OperatorLookForGoal() {
        super(OperatorLookForGoal.name);
        
        RelationalSentences unknownGoal = RelationalSentences.getRelationFromType("unknownGoal");
        RelationalSentences goalTheta = RelationalSentences.getRelationFromType("goalTheta");
        RelationalSentences goalDistance = RelationalSentences.getRelationFromType("goalDistance");

        this.getPrecondition().put(unknownGoal.getHashCode(), unknownGoal);

        this.getDeleteList().put(unknownGoal.getHashCode(), unknownGoal);

        this.getAddList().put(goalTheta.getHashCode(), goalTheta);
        this.getAddList().put(goalDistance.getHashCode(), goalDistance);

    }

}

class OperatorAlignWithBall extends STRIPOperator {

    static String name = "AliginWithBall";

    public OperatorAlignWithBall() {
        super(OperatorAlignWithBall.name);
        
        RelationalSentences ballTheta = RelationalSentences.getRelationFromType("ballTheta");
        RelationalSentences ballDistance = RelationalSentences.getRelationFromType("ballDistance");
        RelationalSentences ballTheta0 = RelationalSentences.getRelationFromType("ballTheta0");
        RelationalSentences goalTheta = RelationalSentences.getRelationFromType("goalTheta");
        RelationalSentences goalDistance = RelationalSentences.getRelationFromType("goalDistance");

        this.getPrecondition().put(ballTheta.getHashCode(), ballTheta);
        this.getPrecondition().put(ballDistance.getHashCode(), ballDistance);
        this.getPrecondition().put(goalTheta.getHashCode(), goalTheta);
        this.getPrecondition().put(goalDistance.getHashCode(), goalDistance);

        this.getDeleteList().put(ballTheta.getHashCode(), ballTheta);
        // this.getDeleteList().put(ballDistance.getHashCode(), ballDistance);
        // this.getDeleteList().put(goalTheta.getHashCode(), goalTheta);
        // this.getDeleteList().put(goalDistance.getHashCode(), goalDistance);

        this.getAddList().put(ballTheta0.getHashCode(), ballTheta0);
        // this.getAddList().put(ballDistance.getHashCode(), ballDistance);
        // this.getAddList().put(goalTheta.getHashCode(), goalTheta);
        // this.getAddList().put(goalDistance.getHashCode(), goalDistance);

    }
}

class OperatorMoveTowardsBall extends STRIPOperator {

    static String name = "MoveTowardsBall";

    public OperatorMoveTowardsBall() {
        super(OperatorMoveTowardsBall.name);
        
        RelationalSentences ballDistance = RelationalSentences.getRelationFromType("ballDistance");
        RelationalSentences ballTheta0 = RelationalSentences.getRelationFromType("ballTheta0");
        RelationalSentences ballDistance1 = RelationalSentences.getRelationFromType("ballDistance1");

        this.getPrecondition().put(ballTheta0.getHashCode(), ballTheta0);

        this.getDeleteList().put(ballDistance.getHashCode(), ballDistance);

        this.getAddList().put(ballDistance1.getHashCode(), ballDistance1);

    }
}

class OperatorKickBall extends STRIPOperator {

    static String name = "KickBall";

    public OperatorKickBall() {
        super(OperatorKickBall.name);
        
        RelationalSentences kickedBall = RelationalSentences.getRelationFromType("kicked");
        RelationalSentences unknownBall = RelationalSentences.getRelationFromType("unknownBall");
        RelationalSentences ballTheta0 = RelationalSentences.getRelationFromType("ballTheta0");
        RelationalSentences ballDistance1 = RelationalSentences.getRelationFromType("ballDistance1");

        this.getPrecondition().put(ballTheta0.getHashCode(), ballTheta0);
        this.getPrecondition().put(ballDistance1.getHashCode(), ballDistance1);

        // this.getDeleteList().put(ballTheta0.getHashCode(), ballTheta0);
        // this.getDeleteList().put(ballDistance1.getHashCode(), ballDistance1);

        this.getAddList().put(kickedBall.getHashCode(), kickedBall);
        this.getAddList().put(unknownBall.getHashCode(), unknownBall);
    }
}
