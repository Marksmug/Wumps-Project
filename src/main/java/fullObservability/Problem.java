package fullObservability;

import wumpus.Agent;

import java.util.ArrayList;

public class Problem extends Agent {
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
