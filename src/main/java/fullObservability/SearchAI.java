package fullObservability;

import wumpus.Agent;
import wumpus.World;

import java.util.LinkedList;
import java.util.ListIterator;


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
        s.setAgentTile(0,0);
        //agent always faces right at beginning
        s.setAgentDir(0);
        //extract goldTile, wumpsTile, pitTiles from the board
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j].getGold() == true){
                    s.setGoldTile(i, j);
                }
                if (board[i][j].getWumpus() == true){
                    s.setWumpsTile(i, j);
                }
                if (board[i][j].getPit() == true){
                    pits[pitNumber][0] = i;
                    pits[pitNumber][1] = j;
                    pitNumber++;
                }
            }
        }
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

        // Remove the code below //
        plan = new LinkedList<Action>();
        for (int i = 0; i<8; i++)
            plan.add(Action.FORWARD);
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

    public static void printDir(State s){
        switch (s.agentDir)
        {
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
