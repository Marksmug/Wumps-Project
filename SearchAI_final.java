package fullObservability;

import wumpus.Agent;
import wumpus.World;

import java.util.*;

class State implements Cloneable{
    int[] agentTile;    //current position of Agent
    int[] wumpsTile;    //position of wumps
    int[] goldTile;     //position of gold
    int[][] pitsTile;   //positions of pits
    int agentDir;       //The direction the agent is facing: 0 - right, 1 - down, 2 - left, 3 - up
    int pitNumber;      //number of pits
    int colDimension;   //dimension of column
    int rowDimension;   //dimension of row

    public State(){
        this.agentTile = new int[2];
        this.goldTile = new int[2];
        this.wumpsTile = new int[2];
        this.pitsTile = null;
        this.agentDir = -1;
        this.pitNumber = -1;
        this.colDimension = 4;
        this.rowDimension = 4;
    }
    public int[] getAgentTile() {
        return agentTile;
    }

    public int[] getWumpsTile() {
        return wumpsTile;
    }

    public int[] getGoldTile() {
        return goldTile;
    }

    public int[][] getPitsTile() {
        return pitsTile;
    }

    public int getPitNumber() {
        return pitNumber;
    }

    public int getAgentDir() {
        return agentDir;
    }

    public int getColDimension() {
        return colDimension;
    }

    public int getRowDimension() {
        return rowDimension;
    }

    public void setAgentTile(int col, int row) {
        this.agentTile[0] = col;
        this.agentTile[1] = row;

    }

    public void setWumpsTile(int col, int row) {
        this.wumpsTile[0] = col;
        this.wumpsTile[1] = row;
    }

    public void setGoldTile(int col, int row) {
        this.goldTile[0] = col;
        this.goldTile[1] = row;
    }

    public void setPitsTile(int[][] pitsTile) {
        this.pitsTile = pitsTile;
    }

    public void setPitNumber(int pitNumber) {
        this.pitNumber = pitNumber;
    }

    public void setAgentDir(int agentDir) {
        this.agentDir = agentDir;
    }

    public void setColDimension(int colDimension) {
        this.colDimension = colDimension;
    }

    public void setRowDimension(int rowDimension) {
        this.rowDimension = rowDimension;
    }

    //set wumpsTile to null
    public void killWumps(){
        setWumpsTile(-1, -1);
    }

    //set goldTile to null
    public void grabGold() {this.goldTile = null;}

    @Override
    //compare the members value between two State objects
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return agentDir == state.agentDir && pitNumber == state.pitNumber && colDimension == state.colDimension && rowDimension == state.rowDimension && Arrays.equals(agentTile, state.agentTile) && Arrays.equals(wumpsTile, state.wumpsTile) && Arrays.equals(goldTile, state.goldTile) && Arrays.equals(pitsTile, state.pitsTile);
    }

    @Override
    //compute the hashcode of a State object
    public int hashCode() {
        int result = Objects.hash(agentDir, pitNumber, colDimension, rowDimension);
        result = 31 * result + Arrays.hashCode(agentTile);
        result = 31 * result + Arrays.hashCode(wumpsTile);
        result = 31 * result + Arrays.hashCode(goldTile);
        result = 31 * result + Arrays.hashCode(pitsTile);
        return result;
    }

    @Override
    //deep clone a State object
    protected Object clone() {
        State s = null;
        try {
            s = (State) super.clone();
            s.agentTile = agentTile.clone();
            s.goldTile = goldTile.clone();
            s.wumpsTile =wumpsTile.clone();
            s.pitsTile = pitsTile.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return s;
    }
}

class Problem extends Agent{
    State INITIAL;

    //Goal test function: return true if there is no gold in current state, otherwise false
    public void setINITIAL(State s){
        this.INITIAL = s;
    }

    public boolean isGoal(State s){
        if (s.goldTile == null){
            return  true;
        }
        else{
            return false;
        }
    }

    //Available actions function: return a set of available actions for current state
    public ArrayList<Action> Actions(State s){

        ArrayList<Action> availableActions = new ArrayList<Action>();
        availableActions.add(Action.TURN_LEFT);
        availableActions.add(Action.TURN_RIGHT);
        availableActions.add(Action.FORWARD);
        availableActions.add(Action.GRAB);
        availableActions.add(Action.SHOOT);
        availableActions.add(Action.CLIMB);

        //case: agent is in the gold tile
        //agent can only grab the gold
        if (s.agentTile[0] == s.goldTile[0] && s.agentTile[1] == s.goldTile[1]){
            availableActions.clear();
            availableActions.add(Action.GRAB);
            return availableActions;
        }

        //case below: agent is not in the gold tile
        //agent can not grab
        availableActions.remove(Action.GRAB);


        if (s.agentTile[0] != 0 || s.agentTile[1] != 0){
            availableActions.remove(Action.CLIMB);
        }
        //case: there is a wumps in front of agent
        //agent can not move forward

        if (s.agentDir == 0) {
            if (s.wumpsTile != null){
                if ((s.agentTile[0] + 1) == s.wumpsTile[0] && s.agentTile[1]  == s.wumpsTile[1]) {
                    availableActions.remove(Action.FORWARD);
                }
            }
            for (int i = 0; i < s.pitNumber; i++) {
                if ((s.agentTile[0] + 1) == s.pitsTile[i][0] && s.agentTile[1]  == s.pitsTile[i][1]) {
                    if (availableActions.contains(Action.FORWARD))
                        availableActions.remove(Action.FORWARD);
                }
            }
        }
        if (s.agentDir == 1) {
            if (s.wumpsTile != null) {
                if (s.agentTile[0] == s.wumpsTile[0] && (s.agentTile[1] - 1) == s.wumpsTile[1]) {
                    availableActions.remove(Action.FORWARD);
                }
            }
            for (int i = 0; i < s.pitNumber; i++) {
                if (s.agentTile[0] == s.pitsTile[i][0] && (s.agentTile[1] - 1)  == s.pitsTile[i][1]) {
                    if (availableActions.contains(Action.FORWARD))
                        availableActions.remove(Action.FORWARD);
                }
            }
        }
        if (s.agentDir == 2) {
            if (s.wumpsTile != null) {
                if ((s.agentTile[0] - 1) == s.wumpsTile[0] && s.agentTile[1] == s.wumpsTile[1]) {
                    availableActions.remove(Action.FORWARD);
                }
            }
            for (int i = 0; i < s.pitNumber; i++) {
                if ((s.agentTile[0] - 1) == s.pitsTile[i][0] && s.agentTile[1]   == s.pitsTile[i][1]) {
                    if (availableActions.contains(Action.FORWARD))
                        availableActions.remove(Action.FORWARD);
                }
            }
        }
        if (s.agentDir == 3) {
            if (s.wumpsTile != null) {
                if (s.agentTile[0] == s.wumpsTile[0] && (s.agentTile[1] + 1) == s.wumpsTile[1]) {
                    availableActions.remove(Action.FORWARD);
                }
            }
            for (int i = 0; i < s.pitNumber; i++) {
                if (s.agentTile[0] == s.pitsTile[i][0] && (s.agentTile[1] + 1)  == s.pitsTile[i][1]) {
                    if (availableActions.contains(Action.FORWARD))
                        availableActions.remove(Action.FORWARD);
                }
            }
        }
        //case:agent is in the left most column and it faces left
        // agent can not move forward
        if (s.agentTile[0] == 0 && s.agentDir == 2) {
            if (availableActions.contains(Action.FORWARD))
                availableActions.remove(Action.FORWARD);
        }
        //case:agent is in the right most column and it faces right
        // agent can not move forward
        if (s.agentTile[0] == s.colDimension - 1 && s.agentDir == 0){
            if (availableActions.contains(Action.FORWARD))
                availableActions.remove(Action.FORWARD);
        }
        //case:agent is in the bottom row and it faces down
        // agent can not move forward
        if (s.agentTile[1] == 0 && s.agentDir == 1) {
            if (availableActions.contains(Action.FORWARD))
                availableActions.remove(Action.FORWARD);
        }
        //case:agent is in the top row and it faces up
        // agent can not move forward
        if (s.agentTile[1] == s.rowDimension - 1 && s.agentDir == 3) {
            if (availableActions.contains(Action.FORWARD))
                availableActions.remove(Action.FORWARD);
        }

        //case: wumps is not in the same row or column
        if (s.wumpsTile != null) {
            if (s.agentTile[0] != s.wumpsTile[0] && s.agentTile[1] != s.wumpsTile[1]) {
                availableActions.remove(Action.SHOOT);
            }
            //case below: wumps is in the same row or colum
            //case: wumps is not in front of agent
            if (!(
                    (s.agentDir == 0 && s.agentTile[0] < s.wumpsTile[0]) ||
                            (s.agentDir == 1 && s.agentTile[1] > s.wumpsTile[1]) ||
                            (s.agentDir == 2 && s.agentTile[0] > s.wumpsTile[0]) ||
                            (s.agentDir == 3 && s.agentTile[1] < s.wumpsTile[1])
            )) {
                availableActions.remove(Action.SHOOT);
            }
        }









        return availableActions;
    }

    //Result function: return a resulting state given a state and a action
    public State Result(State s, Action a)  {
        State newS = (State) s.clone();
        //if action is turn right, agent turn right
        //0 -> 1, 1 -> 2, 2 -> 3, 3 -> 1
        if (a == Action.TURN_RIGHT){
            newS.setAgentDir((s.agentDir + 1)%4);
        }
        //if action is turn left, agent turn left
        //0 -> 3, 1 -> 0, 2 -> 1, 3 -> 2
        if (a == Action.TURN_LEFT){
            newS.setAgentDir((s.agentDir + 3)%4);
        }
        //if action is move forward
        if (a == Action.FORWARD){
            int oldCol = newS.agentTile[0];
            int oldRow = newS.agentTile[1];

            if (s.agentDir == 0) {
                newS.setAgentTile(oldCol + 1, oldRow);
            }
            if (s.agentDir == 1) {
                newS.setAgentTile(oldCol, oldRow - 1);
            }
            if (s.agentDir == 2) {
                newS.setAgentTile(oldCol - 1, oldRow);
            }
            if (s.agentDir == 3) {
                newS.setAgentTile(oldCol, oldRow + 1);
            }


        }
        //if action is grab
        if (a == Action.GRAB){
            //grab the gold if there is gold in the current position of agent, otherwise do nothing
            if (s.agentTile[0] == s.goldTile[0] && s.agentTile[1] == s.goldTile[1]){
                newS.grabGold();
            }
        }
        // if action is shoot
        // kill the wumps if the wumps is in front of agent
        if (a == Action.SHOOT){
            if (s.agentTile[0] == s.wumpsTile[0]){
                if (s.agentDir == 1 && s.agentTile[1] > s.wumpsTile[1]
                        || s.agentDir == 3 && s.agentTile[1] < s.wumpsTile[1]){
                    newS.killWumps();
                }
            }
            if(s.agentTile[1] == s.wumpsTile[1]){
                if((s.agentDir == 0 && s.agentTile[0] < s.wumpsTile[0])
                ||(s.agentDir == 2 && s.agentTile[0] > s.wumpsTile[0])){
                    newS.killWumps();
                }

            }
        }


        return  newS;
    }

    //Actioncost function: return a cost given a action
    public int Actioncost(Action a){
        //if action is shoot, cost is 10, otherwise 1
        if (a == Action.SHOOT){
            return 10;
        }
        else{
            return 1;
        }
    }

    @Override
    public Action getAction(boolean stench, boolean breeze, boolean glitter, boolean bump, boolean scream) {
        return null;
    }
}

//Node class for search algorithm
class Node implements Comparable<Node> {
    private State state;
    private Agent.Action action;
    private Node parent = null;
    private int pathCost = 0;

    public Node(State state, Agent.Action action, Node parent, int pathCost) {
        this.state = state;
        this.action = action;
        this.parent = parent;
        this.pathCost = pathCost;
    }

    public Node(State state, Agent.Action action) {
        this.state = state;
        this.action = action;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Agent.Action getAction() {
        return action;
    }

    public void setAction(Agent.Action action) {
        this.action = action;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    @Override
    public int compareTo(Node node) {
        return 0;
    }
}


public class SearchAI extends Agent {
    private ListIterator<Action> planIterator;

    public SearchAI(World.Tile[][] board) {

        /* The world is board[coloumn][row] with initial position (bottom left) being board[0][0] */
        /* Set the intial state for the problem */
        Problem p = new Problem();
        State s = new State();
        int[][] pits = new int[16][2];
        int pitNumber = 0;
        //agent always start from (0,0)
        s.setAgentTile(0, 0);
        //agent always faces right at beginning
        s.setAgentDir(0);
        //extract goldTile, wumpsTile, pitTiles from the board
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j].getGold() == true) {
                    s.setGoldTile(i, j);
                }
                if (board[i][j].getWumpus() == true) {
                    s.setWumpsTile(i, j);
                }
                if (board[i][j].getPit() == true) {
                    pits[pitNumber][0] = i;
                    pits[pitNumber][1] = j;
                    pitNumber++;
                }
            }
        }

        /*
         */
        s.setPitNumber(pitNumber);
        s.setPitsTile(pits);
        p.setINITIAL(s);

        /* Start the algorithm
        //below are test code
        /*
        Problem p = new Problem();
        State s = new State();
        int[][] pit = {{0,1}, {3,2}};
        s.setPitsTile(pit);
        s.setPitNumber(pit.length);
        s.setGoldTile(1,3);
        s.setAgentDir(2);
        s.setAgentTile(2,2);
        s.setWumpsTile(1,2);
        System.out.println("goldtile is : "+ s.goldTile[0] +"，"+ s.goldTile[1]);
        System.out.println("wumpsTile is : "+ s.wumpsTile[0] +"，"+ s.wumpsTile[1]);
        System.out.println("agentTile is : "+ s.agentTile[0] + ", " + s.agentTile[1]);
        State s1 = new State();
        s1 = p.Result(s, Action.GRAB);
        //s1 = p.Result(s1, Action.TURN_RIGHT);
        //s1 = p.Result(s1, Action.TURN_RIGHT);
        //s1 = p.Result(s1, Action.TURN_RIGHT);
        //s1 = p.Result(s1, Action.TURN_RIGHT);
        //s1.setGoldTile(3,2);
        //s1 = p.Result(s1, Action.FORWARD);
        //s1 = p.Result(s1, Action.SHOOT);
        System.out.println("goldtile is : "+ s1.goldTile[0] +"，"+ s1.goldTile[1]);
        System.out.println("agentTile is : "+ s1.agentTile[0] + ", " + s1.agentTile[1]);
        //System.out.println("wumpsTile is : "+ s.wumpsTile[0] +"，"+ s.wumpsTile[1]);
        //
        System.out.println(s.equals(s1));
        //System.out.println(s.wumpsTile);
        //printDir(s1);
        //ArrayList<Action> actions = p.Actions(s);
        //for (int i = 0; i < actions.size(); i++) {
        //    System.out.println(actions.get(i));
        //}
         */


        LinkedList<Action> plan;
        plan = new LinkedList<Action>();
        //Node n = new Node();
        //Node n = bestFirstSearch(p);
        planIterator = plan.listIterator();
        //n.getAction();
        plan.add(Action.FORWARD);

        // Remove the code below //


        //for (int i = 0; i < 8; i++)
            //plan.add(Action.FORWARD);
        //plan.add(Action.TURN_LEFT);
        //plan.add(Action.TURN_LEFT);
        //for (int i = 10; i < 18; i++)
            //plan.add(Action.FORWARD);
        //plan.add(Action.CLIMB);


        // This must be the last instruction.
        planIterator = plan.listIterator();
    }

    //Best-First Search algorithm

    private LinkedList bestFirstSearch(Problem problem) {
        LinkedList<Action> plan_l;
        plan_l = new LinkedList<Action>();
        Node node = new Node(problem.INITIAL, null);
        PriorityQueue<Node> frontier = new PriorityQueue<>();
        frontier.add(node);
        //plan_l.add(node.getAction());
        HashMap<State, Node> reached = new HashMap<>();
        while (!frontier.isEmpty()) {
            Node currentNode = frontier.poll();
            plan_l.add(currentNode.getAction());
            if (problem.isGoal(currentNode.getState())) {
                //plan_l.add(currentNode.getAction());
                return currentNode;
            }
            for (Node child : expand(problem, currentNode)) {
                State state = child.getState();
                if (!reached.containsKey(state) || child.getPathCost() < reached.get(state).getPathCost()) {
                    reached.put(state, child);
                    frontier.add(child);
                }
            }
        }
        return plan_l;
    }

    private List<Node> expand(Problem problem, Node node) {
        ArrayList<Node> expandedNodes = new ArrayList<>();
        State state = node.getState();
        State nextState;
        int cost;
        for (Action action : problem.Actions(state)) {
            nextState = problem.Result(state, action);
            cost = node.getPathCost() + problem.Actioncost(action);
            expandedNodes.add(new Node(nextState, action, node, cost));
        }
        return expandedNodes;
    }


    @Override
    public Agent.Action getAction(boolean stench, boolean breeze, boolean glitter, boolean bump, boolean scream) {
        return planIterator.next();
    }

    public static void printDir(State s) {
        switch (s.agentDir) {
            case 0:
                System.out.println("AgentDir: Right");
                break;

            case 1:
                System.out.println("AgentDir: Down");
                break;

            case 2:
                System.out.println("AgentDir: Left");
                break;

            case 3:
                System.out.println("AgentDir: Up");
                break;

            default:
                System.out.println("AgentDir: Invalid");
        }
    }
}