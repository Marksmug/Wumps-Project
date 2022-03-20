package partialObservability;
import org.tweetyproject.commons.BeliefSet;
import org.tweetyproject.commons.Parser;
import org.tweetyproject.logics.pl.reasoner.AbstractPlReasoner;
import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.sat.Sat4jSolver;
import org.tweetyproject.logics.pl.sat.SatSolver;
import org.tweetyproject.logics.pl.syntax.*;
import wumpus.Agent;
import wumpus.World;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
//import org.tweetyproject.logics.pl;

//map class
class MAP {
	public class Tile
	{
		boolean pit    = false;
		public boolean getPit() {return this.pit;}

	}

	private int			colDimension;	// The number of columns the game board has
	private int			rowDimension;	// The number of rows the game board has
	public Tile[][]	board;

	MAP(int x, int y){
		colDimension = x;
		rowDimension = y;
		board = new Tile[x+1][y+1];
	}

	public void setPit(int x, int y){
		board[x][y].pit = true;
	}
	public  void setBoard(){
		for (int i = 0; i <= colDimension; i++) {
			for (int j = 0; j <= rowDimension; j++) {
				board[i][j] = new Tile();
			}
		}
	}

}

public class MyAI extends Agent
{
	int currentX = 0;
	int currentY = 0;
	int currentDir = 0;
	int maxX = 0;
	int maxY = 0;
	Queue<Action> plan = new LinkedList<Action>();

	AbstractPlReasoner reasoner = new SatReasoner();
	PlBeliefSet kb = new PlBeliefSet();

	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}

	//function 1 to add unvisit
	public void addUnvisted(){
		if(currentY == 0){
			int[] tileUp = new int[]{currentX, currentY + 1};
			int[] tileLeft = new int[]{currentX-1, currentY};
			int[] tileRight = new int[]{currentX+1, currentY};

			Proposition unvisited1 = new Proposition("unvisited" + toString(tileUp[0]) + "_" + toString(tileUp[1]));
			Negation negUnvisited1 = new Negation(unvisited1);
			Proposition unvisited2 = new Proposition("unvisited" + toString(tileLeft[0]) + "_" + toString(tileLeft[1]));
			Negation negUnvisited2 = new Negation(unvisited2);
			Proposition unvisited3 = new Proposition("unvisited" + toString(tileRight[0]) + "_" + toString(tileRight[1]));
			Negation negUnvisited3 = new Negation(unvisited3);

			if(!kb.contains(unvisited1) && !kb.contains(negUnvisited1)){
				kb.add(unvisited1);
			}
			if(!kb.contains(unvisited2) && !kb.contains(negUnvisited2) && tileLeft[0] > -1 && tileLeft[1] > -1 ){
				kb.add(unvisited2);
			}
			//if there is no unvisited3 in kb and X of unvisited is smaller than maxX
			if(!kb.contains(unvisited3) && !kb.contains(negUnvisited3) && (maxX == 0 || tileRight[0] < maxX)){
				kb.add(unvisited3);
			}
			return;
		}
		else if (currentX == 0){
			int[] tileUp = new int[]{currentX, currentY + 1};
			int[] tileDowm = new int[]{currentX, currentY - 1};
			int[] tileRight = new int[]{currentX+1, currentY};

			Proposition unvisited1 = new Proposition("unvisited" + toString(tileUp[0]) + "_" + toString(tileUp[1]));
			Negation negUnvisited1 = new Negation(unvisited1);
			Proposition unvisited2 = new Proposition("unvisited" + toString(tileDowm[0]) + "_" + toString(tileDowm[1]));
			Negation negUnvisited2 = new Negation(unvisited2);
			Proposition unvisited3 = new Proposition("unvisited" + toString(tileRight[0]) + "_" + toString(tileRight[1]));
			Negation negUnvisited3 = new Negation(unvisited3);

			if(!kb.contains(unvisited1) && !kb.contains(negUnvisited1) && (maxY == 0 || tileUp[1] < maxY)){
				kb.add(unvisited1);
			}
			if(!kb.contains(unvisited2) && !kb.contains(negUnvisited2)){
				kb.add(unvisited2);
			}
			//if there is no unvisited3 in kb and X of unvisited is smaller than maxX
			if(!kb.contains(unvisited3) && !kb.contains(negUnvisited3)){
				kb.add(unvisited3);
			}
			return;
		}
		else {
			int[] tileUp = new int[]{currentX, currentY + 1};
			int[] tileDowm = new int[]{currentX, currentY - 1};
			int[] tileRight = new int[]{currentX+1, currentY};
			int[] tileLeft = new int[]{currentX-1, currentY};

			Proposition unvisited1 = new Proposition("unvisited" + toString(tileUp[0]) + "_" + toString(tileUp[1]));
			Negation negUnvisited1 = new Negation(unvisited1);
			Proposition unvisited2 = new Proposition("unvisited" + toString(tileDowm[0]) + "_" + toString(tileDowm[1]));
			Negation negUnvisited2 = new Negation(unvisited2);
			Proposition unvisited3 = new Proposition("unvisited" + toString(tileRight[0]) + "_" + toString(tileRight[1]));
			Negation negUnvisited3 = new Negation(unvisited3);
			Proposition unvisited4 = new Proposition("unvisited" + toString(tileLeft[0]) + "_" + toString(tileLeft[1]));
			Negation negUnvisited4 = new Negation(unvisited2);

			if(!kb.contains(unvisited1) && !kb.contains(negUnvisited1)){
				kb.add(unvisited1);
			}
			if(!kb.contains(unvisited2) && !kb.contains(negUnvisited2)){
				kb.add(unvisited2);
			}
			//if there is no unvisited3 in kb and X of unvisited is smaller than maxX
			if(!kb.contains(unvisited3) && !kb.contains(negUnvisited3)){
				kb.add(unvisited3);
			}
			if(!kb.contains(unvisited4) && !kb.contains(negUnvisited4)){
				kb.add(unvisited3);
			}
			return;
		}


	}
	//function 2 to add unvisit
	public void unvisit(boolean bump){
		//if perceive bump and direction is right, set maxX and remove beyond unvisited
		if (maxX == 0 && bump && currentDir == 0){
			currentX--;               //agent does not move forward, currentX-1
			maxX = currentX;
			Proposition beyond = new Proposition("unvisited" + toString(maxX+1) + "_" + currentY);
			kb.remove(beyond);
		}
		//if perceive bump and direction is up, set maxY and remove beyond unvisited
		if (maxY == 0 && bump && currentDir == 3){
			currentY--;               //agent does not move forward, currentX-1
			maxY = currentY;
			Proposition beyond = new Proposition("unvisited" + currentX + "_" + toString(maxY+1));
			kb.remove(beyond);
		}
		//if doesn't find maxX and maxY or current x is smaller than maxX and current y is smaller than maxY
		if((maxX == 0 && maxY == 0) || (currentX < maxX && currentY < maxY)) {
			int[] currentTile = new int[]{currentX, currentY};
			Proposition current = new Proposition("unvisited" + toString(currentTile[0]) + "_" + toString(currentTile[1]));
			Negation negCurrent = new Negation(current);
			if (kb.contains(current)) {
				kb.remove(current);
			}
			if (!kb.contains(negCurrent)) {
				kb.add(negCurrent);
			}
			addUnvisted();
		}
	}


	//tell function
	public void tell(boolean stench, boolean breeze, boolean gliter, boolean scream){

	}

	private String toString(int i) {
		String s = Integer.toString(i);
		return  s;
	}

	public MyAI ( )
	{
	}

	public Agent.Action getAction
	(
		boolean stench,
		boolean breeze,
		boolean glitter,
		boolean bump,
		boolean scream
	)
	{


		//testing move
		if(currentX == 0 && currentY == 0 && currentDir == 0){
			plan.add(Action.TURN_LEFT);
			plan.add(Action.FORWARD);
			plan.add(Action.TURN_RIGHT);
			plan.add(Action.FORWARD);
			plan.add(Action.FORWARD);
			plan.add(Action.FORWARD);
			plan.add(Action.FORWARD);
			plan.add(Action.FORWARD);

		}
		SatSolver.setDefaultSolver(new Sat4jSolver());


		//adding unvisit square into knowledge base;
		unvisit(bump);
		//update the knowledge base by perceptions;  * need a tell function from knowledge base
		//tell(stench, breeze, glitter, scream);
		Proposition Glitter = new Proposition("glitter" + currentX + "_" + currentY);
		Proposition haveArrow = new Proposition("haveArrow");
		//Proposition OK = new Proposition(("ok" + currentX + "_" + currentY));



		//if perceive a glitter in current square, find a path from current square to starting square
		if(reasoner.query(kb, Glitter)){
			plan.add(Action.GRAB);
			int[] current = {currentX, currentY};
			int[] starting = {0, 0};
			MAP map = generateMap();
 			LinkedList<Action> route = plan_route(current, currentDir, starting, map);
			for (int i = 0; i < route.size(); i++) {
				plan.add(route.get(i));
			}
			plan.add(Action.CLIMB);
		}

		//if plan is empty, find a path to an unvisited and safe square
		else if(plan.isEmpty()){
			int[] current = {currentX, currentY};
			int[] goal = {0,0};

			//finding an unvisited and safe square
			for (int i = 0; i < currentX+1; i++) {
				for (int j = 0; j < currentY+1; j++) {
					Proposition unvisited = new Proposition("unvisited" + i + "_" + j);
					Proposition OK = new Proposition("ok" + i + "_" + j);
					if (reasoner.query(kb, unvisited) && reasoner.query(kb, OK)){
						int[] square = {i,j};
						goal = square;
						break;
					}
				}
			}
			MAP map = generateMap();
			LinkedList<Action> route = plan_route(current, currentDir, goal, map);
			for (int i = 0; i < route.size(); i++) {
				plan.add(route.get(i));
			}
		}






		//kb.add(pAq);

		//boolean answer = reasoner.query(kb, r);
		//Action move = plan.poll();
		System.out.println(scream);

		// Replace this with something more useful...


		//update the state of the agent according to the action we made
		if(!plan.isEmpty()){
			if(plan.peek() == Action.TURN_LEFT) {
				currentDir = (currentDir+ 3)%4;
			}
			else if(plan.peek() == Action.TURN_RIGHT){
				currentDir = (currentDir + 1)%4;
			}
			if(plan.peek() == Action.FORWARD){
				if(currentDir == 0 && (maxX == 0 ||currentX < maxX)) {
					currentX++;
				}
				else if(currentDir == 3 && (maxY == 0 || currentY < maxY)){
					currentY++;
				}
				else if(currentDir == 1 && (currentY - 1) >= 0){
					currentY--;
				}
				else if(currentDir == 2 && (currentX -1 ) >= 0){
					currentX--;
				}

			}
			return plan.poll();
		}

	return plan.poll();
	}

	//plan_route
	private LinkedList<Action> plan_route(int[] current, int direction , int[] starting, MAP map) {
		LinkedList<Action> route = searchAI(current, direction, starting, map);
		return route;
	}

	//generate map
	private MAP generateMap(){

		int x = currentX + 1;      //map col is current x + 1
		int y = currentY + 1;      //map row is current y + 1
		int mapX;
		int mapY;

		//if current x + 1 is greater than maxX, map col is maxX
		if(maxX != 0 && x > maxX){
			mapX = maxX;
		}else{
			mapX = x;
		}

		//if current y + 1 is greater than maxY , map row is maxY
		if (maxY != 0 && y > maxY){
			mapY = maxY;
		}else{
			mapY = y;
		}
		MAP map = new MAP(mapX,mapY);
		map.setBoard();

		//set all square in the map
		for (int i = 0; i < mapX; i++) {
			for (int j = 0; j < mapY; j++) {
				Proposition OK = new Proposition("ok" + i +"_" + j);
				if(!reasoner.query(kb, OK)){
					map.setPit(i,j);
				}
			}
		}
		return  map;

	}

}