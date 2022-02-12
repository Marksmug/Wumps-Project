package fullObservability;

import wumpus.Agent;

public class Node implements Comparable<Node> {
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
