//	File:			DeductiveNetwork.java
//	Author:		    Mutakabbir
//	Date:			12/11/2021

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;



/**
 * DeductiveNetwork abstract class holds the logic of DeductiveNetwork 
 *
 * @author  Mutakabbir
 */
public abstract class DeductiveNetwork {

    private HashMap<String, ChanceNode> graph;
    private ArrayList<DecisionNode> decisionNodes;
    private ArrayList<CurrentStateNode> currentStateNodes;
    private ArrayList<OutcomeStateNode> outcomeStateNodes;

    //getter and setters
    protected HashMap<String, ChanceNode> getGraph() {
        return graph;
    }

    protected void initGraph(HashMap<String, ChanceNode> graph) {
        if (this.graph == null) {
            this.graph = graph;
        }

    }

    protected ArrayList<DecisionNode> getDecisionNodes() {
        return decisionNodes;
    }

    protected void initDecisionNodes(ArrayList<DecisionNode> decisionNodes) {
        if (this.decisionNodes == null) {
            this.decisionNodes = decisionNodes;
        }
    }

    protected ArrayList<CurrentStateNode> getCurrentStateNodes() {
        return currentStateNodes;
    }

    protected void initCurrentStateNodes(ArrayList<CurrentStateNode> currentStateNodes) {
        if (this.currentStateNodes == null) {
            this.currentStateNodes = currentStateNodes;
        }
    }

    protected ArrayList<OutcomeStateNode> getOutcomeStateNodes() {
        return outcomeStateNodes;
    }

    protected void initOutcomeStateNodes(ArrayList<OutcomeStateNode> outcomeStateNodes) {
        if (this.outcomeStateNodes == null) {
            this.outcomeStateNodes = outcomeStateNodes;
        }
    }

    // constructors
    public DeductiveNetwork() {
        this.graph = null;
        this.decisionNodes = null;
        this.currentStateNodes = null;
        this.outcomeStateNodes = null;
    }

    public DeductiveNetwork(HashMap<String, ChanceNode> graph, ArrayList<DecisionNode> decisionNodes,
            ArrayList<CurrentStateNode> currentStateNodes, ArrayList<OutcomeStateNode> outcomeStateNodes) {
        this.graph = graph;
        this.decisionNodes = decisionNodes;
        this.currentStateNodes = currentStateNodes;
        this.outcomeStateNodes = outcomeStateNodes;
    }

    /**
     * returns the expected utility for diffrent decision node types  
     *
     * @param  decisionNodesChoice   
     * @param  currentStateChoices   
     * @return expected utility for that decision node
     */
    protected Double getExpectedUtilityForDecisionNodesValue(HashMap<String, String> decisionNodesChoice,
            HashMap<String, String> curentStateChoices) {
        Double expectedUtility = 0.0;

        Iterator<OutcomeStateNode> outcomeStatesIterator = this.getOutcomeStateNodes().iterator();
        while (outcomeStatesIterator.hasNext()) {
            OutcomeStateNode outcomeState = outcomeStatesIterator.next();
            Double outcomeStateWeight = outcomeState.getWeight();
            Double outcomeStateUtility = outcomeState.getUtility(decisionNodesChoice, curentStateChoices);
            expectedUtility += outcomeStateWeight * outcomeStateUtility;
        }

        return expectedUtility;
    }
}

/**
 * RoboCupDeductiveNetwork class executes logic of DeductiveNetwork for robocup 
 *
 * @author  Mutakabbir
 */
class RoboCupDeductiveNetwork extends DeductiveNetwork {

    // constructor 
    public RoboCupDeductiveNetwork() {
        ArrayList<DecisionNode> roboCupDecisionNodes = new ArrayList<DecisionNode>();
        roboCupDecisionNodes.add(new ActionDecisionNode());
        this.initDecisionNodes(roboCupDecisionNodes);

        ChanceNode sBNode, sGNode, wSBNode, wSGNode, bDNode;
        sBNode = new SeeBallState();
        sGNode = new SeeGoalState();
        bDNode = new BallDistanceNode();
        wSBNode = new WillSeeBallNode();
        wSGNode = new WillSeeGoalNode();

        HashMap<String, ChanceNode> roboCupGraph = new HashMap<String, ChanceNode>();
        roboCupGraph.put(sBNode.toString(), sBNode);
        roboCupGraph.put(sGNode.toString(), sGNode);
        roboCupGraph.put(bDNode.toString(), bDNode);
        roboCupGraph.put(wSBNode.toString(), wSBNode);
        roboCupGraph.put(wSGNode.toString(), wSGNode);
        this.initGraph(roboCupGraph);

        ArrayList<CurrentStateNode> currentStateNodes = new ArrayList<CurrentStateNode>();
        currentStateNodes.add((CurrentStateNode) sBNode);
        currentStateNodes.add((CurrentStateNode) sGNode);
        this.initCurrentStateNodes(currentStateNodes);

        ArrayList<OutcomeStateNode> outcomeStateNodes = new ArrayList<OutcomeStateNode>();
        outcomeStateNodes.add((OutcomeStateNode) wSBNode);
        outcomeStateNodes.add((OutcomeStateNode) wSGNode);
        outcomeStateNodes.add((OutcomeStateNode) bDNode);
        this.initOutcomeStateNodes(outcomeStateNodes);
    }

    // main function to check introduction of evidence
    public static void main(String args[]) {
        Double Eu;

        DeductiveNetwork robocup = new RoboCupDeductiveNetwork();

        HashMap<String, String> turn = new HashMap<String, String>();
        turn.put("ActionDecisionNode", ActionDecisionNode.TYPE_TURN);

        HashMap<String, String> dash = new HashMap<String, String>();
        dash.put("ActionDecisionNode", ActionDecisionNode.TYPE_DASH);

        HashMap<String, String> kick = new HashMap<String, String>();
        kick.put("ActionDecisionNode", ActionDecisionNode.TYPE_KICK);

        HashMap<String, String> parChoice1 = new HashMap<String, String>();
        parChoice1.put("SeeBallState", SeeBallState.TYPE_TRUE);
        parChoice1.put("SeeGoalState", SeeGoalState.TYPE_TRUE);

        System.out.println("\n********* All Evidence Known *********");
        System.out.println("Expected Utility");

        Eu = robocup.getExpectedUtilityForDecisionNodesValue(turn, parChoice1);
        System.out.println("Turn: " + Eu);

        Eu = robocup.getExpectedUtilityForDecisionNodesValue(dash, parChoice1);
        System.out.println("Dash: " + Eu);

        Eu = robocup.getExpectedUtilityForDecisionNodesValue(kick, parChoice1);
        System.out.println("Kick: " + Eu);

        HashMap<String, String> parChoice2 = new HashMap<String, String>();
        parChoice2.put("SeeBallState", SeeBallState.TYPE_TRUE);
        parChoice2.put("SeeGoalState", null);

        System.out.println("\n********* SG Not Known *********");
        System.out.println("Expected Utility");

        Eu = robocup.getExpectedUtilityForDecisionNodesValue(turn, parChoice2);
        System.out.println("Turn: " + Eu);

        Eu = robocup.getExpectedUtilityForDecisionNodesValue(dash, parChoice2);
        System.out.println("Dash: " + Eu);

        Eu = robocup.getExpectedUtilityForDecisionNodesValue(kick, parChoice2);
        System.out.println("Kick: " + Eu);
    }

}

/**
 * Node class holds the abstract logic of nodes
 *
 * @author  Mutakabbir
 */
class Node {
    private ArrayList<String> nodeTypes;

    //getter and setters
    public ArrayList<String> getNodeTypes() {
        return nodeTypes;
    }

    public void initNodeTypes(ArrayList<String> nodeTypes) {
        if (this.nodeTypes == null) {
            this.nodeTypes = nodeTypes;
        }
    }

    public Node() {
        this.nodeTypes = null;
    }

    public Node(ArrayList<String> nodeTypes) {
        this.nodeTypes = nodeTypes;
    }

    //to string override
    @Override
    public String toString() {
        return this.getClass().getName();
    }
}

/**
 * ChanceNode holds the abstract logic for this type of nodes
 *
 * @author  Mutakabbir
 */
abstract class ChanceNode extends Node {

    private ArrayList<ArrayList<Double>> conditionalProbablityDistribution;

    protected abstract Boolean validate();

    // getters and setters
    public ArrayList<ArrayList<Double>> getConditionalProbablityDistribution() {
        return conditionalProbablityDistribution;
    }

    public void initConditionalProbablityDistribution(ArrayList<ArrayList<Double>> conditionalProbablityDistribution) {
        if (this.conditionalProbablityDistribution == null) {
            this.conditionalProbablityDistribution = conditionalProbablityDistribution;
        }
    }

    //constructor
    public ChanceNode() {
        super();
        this.conditionalProbablityDistribution = null;
    }

    public ChanceNode(ArrayList<String> nodeTypes, ArrayList<ArrayList<Double>> conditionalProbablityDistribution) {
        super(nodeTypes);
        this.conditionalProbablityDistribution = conditionalProbablityDistribution;
    }
}

/**
 * CurrentStateNode holds the abstract logic for this type of nodes
 *
 * @author  Mutakabbir
 */
class CurrentStateNode extends ChanceNode {

    // constructors
    public CurrentStateNode() {
        super();
    }

    public CurrentStateNode(ArrayList<String> nodeTypes,
            ArrayList<ArrayList<Double>> conditionalProbablityDistribution) {
        super(nodeTypes, conditionalProbablityDistribution);
    }

    /**
     * return joint probablity  
     *
     * @param  nodetype   
     * @return probablity
     */
    public Double getJointProbablity(String nodeType) {
        int rowIndex = 0;
        int colIndex = this.getNodeTypes().indexOf(nodeType);

        if (colIndex == -1) {
            return null;
        }

        return this.getConditionalProbablityDistribution().get(rowIndex).get(colIndex);

    }

    // validator implementation
    @Override
    protected Boolean validate() {
        if (this.getConditionalProbablityDistribution().size() == 1
                && this.getConditionalProbablityDistribution().get(0).size() == this.getNodeTypes().size()) {
            return true;
        } else {
            return false;
        }
    }

}

/**
 * SeeBallState executes the functionlaity of this type of State node
 *
 * @author  Mutakabbir
 */
class SeeBallState extends CurrentStateNode {

    public static String TYPE_TRUE = "True";
    public static String TYPE_FALSE = "False";

    // constructor
    public SeeBallState() {
        super();

        ArrayList<String> seeBallTypes = new ArrayList<String>();
        seeBallTypes.add(SeeBallState.TYPE_TRUE);
        seeBallTypes.add(SeeBallState.TYPE_FALSE);
        this.initNodeTypes(seeBallTypes);

        ArrayList<ArrayList<Double>> seeBallCPD = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> seeBallProbablity = new ArrayList<Double>();
        seeBallProbablity.add(0.25);
        seeBallProbablity.add(0.75);
        seeBallCPD.add(seeBallProbablity);
        this.initConditionalProbablityDistribution(seeBallCPD);
    }

}

/**
 * SeeGoalState executes the functionlaity of this type of State node
 *
 * @author  Mutakabbir
 */
class SeeGoalState extends CurrentStateNode {

    public static String TYPE_TRUE = "True";
    public static String TYPE_FALSE = "False";

    // constructor
    public SeeGoalState() {
        super();

        ArrayList<String> seeGoalTypes = new ArrayList<String>();
        seeGoalTypes.add(SeeGoalState.TYPE_TRUE);
        seeGoalTypes.add(SeeGoalState.TYPE_FALSE);
        this.initNodeTypes(seeGoalTypes);

        ArrayList<ArrayList<Double>> seeGoalCPD = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> seeGoalProbablity = new ArrayList<Double>();
        seeGoalProbablity.add(0.25);
        seeGoalProbablity.add(0.75);
        seeGoalCPD.add(seeGoalProbablity);
        this.initConditionalProbablityDistribution(seeGoalCPD);
    }
}

/**
 * OutcomeStateNode holds logic for this type of nodes
 *
 * @author  Mutakabbir
 */
class OutcomeStateNode extends ChanceNode {

    private Double weight;
    private HashMap<String, Double> utilityFactor;
    private ArrayList<DecisionNode> decisionNodes;
    private ArrayList<CurrentStateNode> parentNodes;

    //getters and setters
    public Double getWeight() {
        return weight;
    }

    public void initWeight(Double weight) {
        if (this.weight == null) {
            this.weight = weight;
        }
    }

    public HashMap<String, Double> getUtilityFactor() {
        return utilityFactor;
    }

    public void initUtilityFactor(HashMap<String, Double> utilityFactor) {
        if (this.utilityFactor == null) {
            this.utilityFactor = utilityFactor;
        }
    }

    public ArrayList<CurrentStateNode> getParentNodes() {
        return parentNodes;
    }

    public void initParentNodes(ArrayList<CurrentStateNode> parentNodes) {
        if (this.parentNodes == null) {
            this.parentNodes = parentNodes;
        }
    }

    public ArrayList<DecisionNode> getDecisionNodes() {
        return decisionNodes;
    }

    public void initDecisionNodes(ArrayList<DecisionNode> decisionNodes) {
        if (this.decisionNodes == null) {
            this.decisionNodes = decisionNodes;
        }
    }

    //constructors
    public OutcomeStateNode() {
        super();
        this.weight = null;
        this.parentNodes = null;
        this.decisionNodes = null;
        this.utilityFactor = null;
    }

    public OutcomeStateNode(Double weight, ArrayList<String> nodeTypes, HashMap<String, Double> utilityFactor,
            ArrayList<ArrayList<Double>> conditionalProbablityDistribution, ArrayList<DecisionNode> decisionNodes,
            ArrayList<CurrentStateNode> parentNodes) {
        super(nodeTypes, conditionalProbablityDistribution);
        this.weight = weight;
        this.parentNodes = parentNodes;
        this.decisionNodes = decisionNodes;
        this.utilityFactor = utilityFactor;
    }

    /**
     * returns parrent choice array list from mapping  
     *
     * @param  parentMapping 
     * @return parentChioceList
     */    
    private ArrayList<String> getParentNodesChoiceArray(HashMap<String, String> parentNodeChoices) {
        ArrayList<String> parentChoiceList = new ArrayList<>();

        Iterator<CurrentStateNode> parentIterator = this.getParentNodes().iterator();
        while (parentIterator.hasNext()) {
            String parentName = parentIterator.next().toString();
            parentChoiceList.add(parentNodeChoices.get(parentName));
        }
        return parentChoiceList;
    }

    /**
     *  returns decision choice array list from mapping 
     *
     * @param  decisionMapping
     * @return DecisionChoiceList
     */
    private ArrayList<String> getDecisionNodesChoiceArray(HashMap<String, String> decisionNodeChoices) {
        ArrayList<String> decisionChoiceList = new ArrayList<>();

        Iterator<DecisionNode> decisionNodesIterator = this.getDecisionNodes().iterator();
        while (decisionNodesIterator.hasNext()) {
            String decisionName = decisionNodesIterator.next().toString();
            decisionChoiceList.add(decisionNodeChoices.get(decisionName));
        }
        return decisionChoiceList;
    }

    /**
     *  returns utility 
     *
     * @param  decisionChoiceMapping
     * @param  parentChoiceMapping
     * @return utility
     */
    public Double getUtility(HashMap<String, String> decisionNodesChoice, HashMap<String, String> parentNodesChoice) {
        Double utility = 0.0;

        ArrayList<CurrentStateNode> nullParents = new ArrayList<>();
        if (parentNodesChoice.containsValue(null)) {
            Iterator<CurrentStateNode> parentIterator = this.getParentNodes().iterator();
            while (parentIterator.hasNext()) {
                CurrentStateNode parent = parentIterator.next();
                String parentName = parent.toString();
                if (parentNodesChoice.get(parentName) == null) {
                    nullParents.add(parent);
                }
            }
        }

        ArrayList<String> nodeTypes = this.getNodeTypes();
        Iterator<String> typeIterator = nodeTypes.iterator();
        while (typeIterator.hasNext()) {
            String nodeType = typeIterator.next();
            ArrayList<String> decisionTypes = this.getDecisionNodesChoiceArray(decisionNodesChoice);
            if (nullParents.size() == 0) {
                ArrayList<String> parentTypes = this.getParentNodesChoiceArray(parentNodesChoice);
                utility += this.getUtilityFactor().get(nodeType)
                        * this.getJointProbablityOfNode(nodeType, decisionTypes, parentTypes);
            } else {
                CurrentStateNode parent = nullParents.get(0);
                for (int typeIndex = 0; typeIndex < parent.getNodeTypes().size(); typeIndex++) {
                    parentNodesChoice.replace(parent.toString(), parent.getNodeTypes().get(typeIndex));
                    ArrayList<String> parentTypes = this.getParentNodesChoiceArray(parentNodesChoice);
                    utility += this.getUtilityFactor().get(nodeType)
                            * this.getJointProbablityOfNode(nodeType, decisionTypes, parentTypes);
                }
            }
        }

        return utility;
    }

    /**
     *  returns probablity 
     *
     * @param  nodeType
     * @param  decisionChoiceMapping
     * @param  parentChoiceMapping
     * @return probablity
     */
    public Double getJointProbablityOfNode(String chanceNodeType, ArrayList<String> decisionNodeType,
            ArrayList<String> parentTypes) {

        int colIndex = this.getNodeTypes().indexOf(chanceNodeType);
        if (colIndex == -1) {
            return null;
        }

        int decisionTypeindex = this.getDecisionNodes().get(0).getNodeTypes().indexOf(decisionNodeType.get(0));
        ArrayList<Integer> parentTypesIndexes = new ArrayList<Integer>();
        if (parentTypes.size() == this.parentNodes.size()) {
            ArrayList<Integer> parentSizes = new ArrayList<Integer>();
            for (int index = 0; index < this.parentNodes.size(); index++) {
                parentSizes.add(this.parentNodes.get(index).getNodeTypes().size());
                parentTypesIndexes.add(this.parentNodes.get(index).getNodeTypes().indexOf(parentTypes.get(index)));
            }

            int rowIndex = 0;
            for (int i = 0; i < parentSizes.size() + 1; i++) {
                int multiplier = 1;
                for (int j = i; j < parentSizes.size(); j++) {
                    multiplier *= parentSizes.get(j);
                }
                if (i == 0) {
                    rowIndex += multiplier * decisionTypeindex;
                } else {
                    rowIndex += multiplier * parentTypesIndexes.get(i - 1);
                }
            }
            return this.getConditionalProbablityDistribution().get(rowIndex).get(colIndex);

        } else {
            return null;
        }
    }

    // validator implementation
    @Override
    protected Boolean validate() {
        if (this.getConditionalProbablityDistribution().size() > 1
                && this.getConditionalProbablityDistribution().get(0).size() == this.getNodeTypes().size()) {
            return true;
        } else {
            return false;
        }
    }
}

/**
 * WillSeeBallNode executes the functionlaity of this type of Outcome node
 *
 * @author  Mutakabbir
 */
class WillSeeBallNode extends OutcomeStateNode {

    public static String TYPE_TRUE = "True";
    public static String TYPE_FALSE = "False";

    //constructor
    public WillSeeBallNode() {
        super();

        this.initWeight(0.10);

        ArrayList<CurrentStateNode> willSeeBallParents = new ArrayList<CurrentStateNode>();
        willSeeBallParents.add(new SeeBallState());
        this.initParentNodes(willSeeBallParents);

        ArrayList<DecisionNode> willSeeBallDecisionNodes = new ArrayList<DecisionNode>();
        willSeeBallDecisionNodes.add(new ActionDecisionNode());
        this.initDecisionNodes(willSeeBallDecisionNodes);

        ArrayList<String> willSeeBallTypes = new ArrayList<String>();
        willSeeBallTypes.add(WillSeeBallNode.TYPE_TRUE);
        willSeeBallTypes.add(WillSeeBallNode.TYPE_FALSE);
        this.initNodeTypes(willSeeBallTypes);

        HashMap<String, Double> wSBUtilityFactor = new HashMap<String, Double>();
        wSBUtilityFactor.put(WillSeeBallNode.TYPE_TRUE, 1.0);
        wSBUtilityFactor.put(WillSeeBallNode.TYPE_FALSE, -1.0);
        this.initUtilityFactor(wSBUtilityFactor);

        ArrayList<ArrayList<Double>> willSeeBallCPD = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> turnTrue = new ArrayList<Double>();
        turnTrue.add(0.60);
        turnTrue.add(0.40);
        ArrayList<Double> turnFalse = new ArrayList<Double>();
        turnFalse.add(0.80);
        turnFalse.add(0.20);
        ArrayList<Double> dashTrue = new ArrayList<Double>();
        dashTrue.add(0.90);
        dashTrue.add(0.10);
        ArrayList<Double> dashFalse = new ArrayList<Double>();
        dashFalse.add(0.00);
        dashFalse.add(1.00);
        ArrayList<Double> kickTrue = new ArrayList<Double>();
        kickTrue.add(0.95);
        kickTrue.add(0.05);
        ArrayList<Double> kickFalse = new ArrayList<Double>();
        kickFalse.add(0.00);
        kickFalse.add(1.00);
        willSeeBallCPD.add(turnTrue);
        willSeeBallCPD.add(turnFalse);
        willSeeBallCPD.add(dashTrue);
        willSeeBallCPD.add(dashFalse);
        willSeeBallCPD.add(kickTrue);
        willSeeBallCPD.add(kickFalse);
        this.initConditionalProbablityDistribution(willSeeBallCPD);
    }
}

/**
 * WillSeeGoalNode executes the functionlaity of this type of Outcome node
 *
 * @author  Mutakabbir
 */
class WillSeeGoalNode extends OutcomeStateNode {

    public static String TYPE_TRUE = "True";
    public static String TYPE_FALSE = "False";

    //constructor
    public WillSeeGoalNode() {
        super();

        this.initWeight(0.4);

        ArrayList<CurrentStateNode> willSeeGoalParents = new ArrayList<CurrentStateNode>();
        willSeeGoalParents.add(new SeeGoalState());
        this.initParentNodes(willSeeGoalParents);

        ArrayList<DecisionNode> willSeeGoalDecisionNodes = new ArrayList<DecisionNode>();
        willSeeGoalDecisionNodes.add(new ActionDecisionNode());
        this.initDecisionNodes(willSeeGoalDecisionNodes);

        ArrayList<String> willSeeGoalTypes = new ArrayList<String>();
        willSeeGoalTypes.add(WillSeeGoalNode.TYPE_TRUE);
        willSeeGoalTypes.add(WillSeeGoalNode.TYPE_FALSE);
        this.initNodeTypes(willSeeGoalTypes);

        HashMap<String, Double> wSGUtilityFactor = new HashMap<String, Double>();
        wSGUtilityFactor.put(WillSeeGoalNode.TYPE_TRUE, 1.0);
        wSGUtilityFactor.put(WillSeeGoalNode.TYPE_FALSE, -1.0);
        this.initUtilityFactor(wSGUtilityFactor);

        ArrayList<ArrayList<Double>> willSeeGoalCPD = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> turnTrue = new ArrayList<Double>();
        turnTrue.add(0.60);
        turnTrue.add(0.40);
        ArrayList<Double> turnFalse = new ArrayList<Double>();
        turnFalse.add(0.80);
        turnFalse.add(0.20);
        ArrayList<Double> dashTrue = new ArrayList<Double>();
        dashTrue.add(0.90);
        dashTrue.add(0.10);
        ArrayList<Double> dashFalse = new ArrayList<Double>();
        dashFalse.add(0.00);
        dashFalse.add(1.00);
        ArrayList<Double> kickTrue = new ArrayList<Double>();
        kickTrue.add(0.95);
        kickTrue.add(0.05);
        ArrayList<Double> kickFalse = new ArrayList<Double>();
        kickFalse.add(0.00);
        kickFalse.add(1.00);
        willSeeGoalCPD.add(turnTrue);
        willSeeGoalCPD.add(turnFalse);
        willSeeGoalCPD.add(dashTrue);
        willSeeGoalCPD.add(dashFalse);
        willSeeGoalCPD.add(kickTrue);
        willSeeGoalCPD.add(kickFalse);
        this.initConditionalProbablityDistribution(willSeeGoalCPD);
    }
}

/**
 * BallDistanceNode executes the functionlaity of this type of Outcome node
 *
 * @author  Mutakabbir
 */
class BallDistanceNode extends OutcomeStateNode {

    public static String TYPE_FAR = "Far";
    public static String TYPE_NEAR = "Near";
    public static String TYPE_UNKOWN = "Unknown";

    // constructor
    public BallDistanceNode() {
        super();

        this.initWeight(0.5);

        ArrayList<CurrentStateNode> ballDistanceParents = new ArrayList<CurrentStateNode>();
        ballDistanceParents.add(new SeeBallState());
        this.initParentNodes(ballDistanceParents);

        ArrayList<DecisionNode> ballDistanceDecisionNodes = new ArrayList<DecisionNode>();
        ballDistanceDecisionNodes.add(new ActionDecisionNode());
        this.initDecisionNodes(ballDistanceDecisionNodes);

        ArrayList<String> ballDistanceTypes = new ArrayList<String>();
        ballDistanceTypes.add(BallDistanceNode.TYPE_UNKOWN);
        ballDistanceTypes.add(BallDistanceNode.TYPE_FAR);
        ballDistanceTypes.add(BallDistanceNode.TYPE_NEAR);
        this.initNodeTypes(ballDistanceTypes);

        HashMap<String, Double> bDUtilityFactor = new HashMap<String, Double>();
        bDUtilityFactor.put(BallDistanceNode.TYPE_UNKOWN, 0.00);
        bDUtilityFactor.put(BallDistanceNode.TYPE_FAR, 0.50);
        bDUtilityFactor.put(BallDistanceNode.TYPE_NEAR, 1.0);
        this.initUtilityFactor(bDUtilityFactor);

        ArrayList<ArrayList<Double>> ballDistanceCPD = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> turnTrue = new ArrayList<Double>();
        turnTrue.add(0.20);
        turnTrue.add(0.50);
        turnTrue.add(0.30);
        ArrayList<Double> turnFalse = new ArrayList<Double>();
        turnFalse.add(0.60);
        turnFalse.add(0.30);
        turnFalse.add(0.10);
        ArrayList<Double> dashTrue = new ArrayList<Double>();
        dashTrue.add(0.10);
        dashTrue.add(0.30);
        dashTrue.add(0.60);
        ArrayList<Double> dashFalse = new ArrayList<Double>();
        dashFalse.add(1.00);
        dashFalse.add(0.00);
        dashFalse.add(0.00);
        ArrayList<Double> kickTrue = new ArrayList<Double>();
        kickTrue.add(0.10);
        kickTrue.add(0.90);
        kickTrue.add(0.00);
        ArrayList<Double> kickFalse = new ArrayList<Double>();
        kickFalse.add(1.00);
        kickFalse.add(0.00);
        kickFalse.add(0.00);
        ballDistanceCPD.add(turnTrue);
        ballDistanceCPD.add(turnFalse);
        ballDistanceCPD.add(dashTrue);
        ballDistanceCPD.add(dashFalse);
        ballDistanceCPD.add(kickTrue);
        ballDistanceCPD.add(kickFalse);
        this.initConditionalProbablityDistribution(ballDistanceCPD);
    }
}

/**
 * DecisionNode holds logic for this type of nodes
 *
 * @author  Mutakabbir
 */
abstract class DecisionNode extends Node {

    //constructors
    public DecisionNode() {
        super();
    }

    public DecisionNode(ArrayList<String> nodeTypes) {
        super(nodeTypes);
    }

}

/**
 * ActionDecisionNode executes the functionlaity of this type of Decision node
 *
 * @author  Mutakabbir
 */
class ActionDecisionNode extends DecisionNode {

    public static String TYPE_TURN = "Turn";
    public static String TYPE_DASH = "Dash";
    public static String TYPE_KICK = "Kick";

    //constructors
    public ActionDecisionNode() {
        super();

        ArrayList<String> actionTypes = new ArrayList<String>();
        actionTypes.add("Turn");
        actionTypes.add("Dash");
        actionTypes.add("Kick");
        this.initNodeTypes(actionTypes);
    }

}