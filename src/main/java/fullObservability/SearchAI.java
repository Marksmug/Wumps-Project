package fullObservability;

import wumpus.Agent;
import wumpus.World;

import java.util.LinkedList;
import java.util.ListIterator;


public class SearchAI extends Agent {
    private ListIterator<Action> planIterator;

    public SearchAI(World.Tile[][] board) {

        /* The world is board[coloumn][row] with initial position (bottom left) being board[0][0] */


       LinkedList<Action> plan;

        // Remove the code below //
         plan = new LinkedList<Action>();
         for (int i = 0; i<8; i++)
             plan.add(Agent.Action.FORWARD);
        plan.add(Action.TURN_LEFT);
        plan.add(Action.TURN_LEFT);
        for (int i = 10; i<18; i++)
            plan.add(Action.FORWARD);
        plan.add(Action.CLIMB);





        // This must be the last instruction.
        planIterator = plan.listIterator();
    }


    @Override
    public Agent.Action getAction(boolean stench, boolean breeze, boolean glitter, boolean bump, boolean scream) {
        return planIterator.next();
    }

}
