package partialObservability;
import org.tweetyproject.commons.BeliefSet;
import org.tweetyproject.commons.Parser;
import org.tweetyproject.logics.pl.parser.PlParser;
import org.tweetyproject.commons.ParserException;
import org.tweetyproject.logics.pl.reasoner.AbstractPlReasoner;
import org.tweetyproject.logics.pl.reasoner.SatReasoner;
import org.tweetyproject.logics.pl.sat.Sat4jSolver;
import org.tweetyproject.logics.pl.sat.SatSolver;
import org.tweetyproject.logics.pl.syntax.*;
import wumpus.Agent;
import wumpus.World;
import java.io.IOException;
import java.util.List;

import java.util.*;

import static wumpus.Agent.Action.TURN_LEFT;
import static wumpus.Agent.Action.TURN_RIGHT;
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
	int currentX;
	int currentY;
	int currentDir;
	int maxX;
	int maxY;
	boolean findMaxX;
	boolean findMaxY;
	Queue<Action> plan = new LinkedList<Action>();

	AbstractPlReasoner reasoner = new SatReasoner();
	PlBeliefSet kb = new PlBeliefSet();
	PlParser tweetyParser = new PlParser();


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
			if(!kb.contains(unvisited3) && !kb.contains(negUnvisited3) && (tileRight[0] <= maxX)){
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

			if(!kb.contains(unvisited1) && !kb.contains(negUnvisited1) && tileUp[1] <= maxY){
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
			Negation negUnvisited4 = new Negation(unvisited4);

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
		if(currentX + 1 > maxX && !findMaxX){
			maxX = currentX + 1;
		}
		if(currentY + 1 > maxY && !findMaxY){
			maxY = currentY + 1;
		}
		//if does not find maxX, perceive bump and direction is right, set maxX and remove beyond unvisited
		if (!findMaxX && bump && currentDir == 0){
			currentX--;               //agent does not move forward, currentX-1
			maxX = currentX;
			findMaxX = true;
			Proposition beyond = new Proposition("unvisited" + toString(maxX+1) + "_" + currentY);
			kb.remove(beyond);
		}
		//if perceive bump and direction is up, set maxY and remove beyond unvisited
		if (!findMaxY && bump && currentDir == 3){
			currentY--;               //agent does not move forward, currentX-1
			maxY = currentY;
			findMaxY = true;
			Proposition beyond = new Proposition("unvisited" + currentX + "_" + toString(maxY+1));
			kb.remove(beyond);
		}
		//if doesn't find maxX and maxY or current x is smaller than maxX and current y is smaller than maxY
		if(currentX <= maxX && currentY <= maxY) {
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
	public void tell(int i, int j, boolean stench, boolean breeze, boolean glitter, boolean bump, boolean scream)throws IOException{


		String cB = 'B' + toString(i) +'_'+ toString(j);
		String cS = 'S' + toString(i) +'_'+ toString(j);
		String cW = 'W' +  toString(i) +'_'+ toString(j);
		String cP = 'P' + toString(i) +'_'+ toString(j);


		//Propositions for adjacent Breeze Squares
		String tB = 'B' + toString(i) +'_'+ toString(j+1);
		String bB = 'B' + toString(i) +'_'+ toString(j-1);
		String lB = 'B' + toString(i-1) +'_'+ toString(j);
		String rB = 'B' + toString(i+1) +'_'+ toString(j);



		//Propositions variables for adjacent Wumpus Squares

		String tW = 'W' + toString(i) +'_'+ toString(j+1);
		String bW = 'W' + toString(i) +'_'+ toString(j-1);
		String lW = 'W' + toString(i-1) +'_'+ toString(j);
		String rW = 'W' + toString(i+1) +'_'+ toString(j);

		//OK squares
		String tO = "ok" + toString(i) +'_'+ toString(j+1);
		String bO = "ok" + toString(i) +'_'+ toString(j-1);
		String lO = "ok" + toString(i-1) +'_'+ toString(j);
		String rO = "ok" + toString(i+1) +'_'+ toString(j);


		//Propositions variables for new Pitt Squares

		String tP = 'P' + toString(i) +'_'+ toString(j+1);
		String bP = 'P' + toString(i) +'_'+ toString(j-1);
		String lP = 'P' + toString(i-1) +'_'+ toString(j);
		String rP = 'P' + toString(i+1) +'_'+ toString(j);


		//Update KB on new position so No Wumpus and No Pit at current location
		Proposition f1 = new Proposition(cP);
		Proposition f2 = new Proposition(cW);
		Negation nf1 = new Negation(f1);
		Negation nf2 = new Negation(f2);

		Proposition s1 = new Proposition(cS);

		kb.add(nf1,nf2);

		//Glitter update:
		if (glitter == true){
			kb.add(tweetyParser.parseFormula("G" ));
		}

		if (scream == true){
			kb.remove(tweetyParser.parseFormula("!S"));
			kb.add(tweetyParser.parseFormula("S"));
		}


		//Stench sensor update:
		if(i == 0 & j == 0){
			if (!stench){

				Negation ns1 = new Negation(s1);
				kb.add(ns1);
				kb.add(tweetyParser.parseFormula("!" + tW +  " && " + "!" + rW ));


			}
			else{
					kb.add(s1);

					kb.add(tweetyParser.parseFormula( "S || "+ tW + " || "  + rW ));


					kb.add(tweetyParser.parseFormula("!" + tW + " || " + "!" + rW ));

					kb.add(tweetyParser.parseFormula( "!S || (!" + tW + " && " + "!" + rW + ")"));


			}
			Proposition b1 = new Proposition(cB);

			if (!breeze){
				Negation nb1 = new Negation(b1);
				kb.add(nb1);

				kb.add(tweetyParser.parseFormula("!" + tP + " && " +  "!" + rP ));

				//adds the OK statements

			}
			else{
				kb.add(b1);
				kb.add(tweetyParser.parseFormula( tP + " || " +  rP ));
			}
			kb.add(tweetyParser.parseFormula("(!" + tP + " && " + "!" +tW + ") => " + tO ));
			kb.add(tweetyParser.parseFormula("(!" + rP + " && " + "!" +rW + ") => " + rO ));
		}


		//Middle of World Game
		if(i>0 & j>0){

			if (!stench){

				Negation ns1 = new Negation(s1);
				kb.add(ns1);
				kb.add(tweetyParser.parseFormula("!" + tW + " && " + "!" +bW + " && " + "!" + lW + " && " + "!" + rW ));


			}
			else{
					kb.add(s1);
					kb.add(tweetyParser.parseFormula( "S || "+ tW + " || " + bW + " || " + lW + " || " + rW )); //!S => (tw || bw || lw || rw)
					kb.add(tweetyParser.parseFormula("!" + tW + " || " + "!" +bW));
					kb.add(tweetyParser.parseFormula("!" +bW + " || " + "!" + lW));
					kb.add(tweetyParser.parseFormula("!" +bW + " || " + "!" + rW));
					kb.add(tweetyParser.parseFormula("!" + lW + " || " + "!" + rW));
					kb.add(tweetyParser.parseFormula("!" + tW + " || " + "!" + rW ));
					kb.add(tweetyParser.parseFormula("!" + tW + " || " + "!" + lW));
					kb.add(tweetyParser.parseFormula( "!S || (!" + tW + " && " + "!" +bW + " && " + "!" + lW + " && " + "!" + rW + ")"));//S => (!tw && !bw && !lw && rw)


			}
			Proposition b1 = new Proposition(cB);

			if (!breeze){
				Negation nb1 = new Negation(b1);
				kb.add(nb1);
				kb.add(tweetyParser.parseFormula("!" + tP + " && " + "!" +bP + " && " + "!" + lP + " && " + "!" + rP ));

				//adds the OK statements


			}
			else{
				kb.add(b1);
				kb.add(tweetyParser.parseFormula( tP + " || " + bP + " || " + lP + " || " + rP ));
			}
			kb.add(tweetyParser.parseFormula("(!" + tP + " && " + "!" +tW + ") => " + tO ));
			kb.add(tweetyParser.parseFormula("(!" + bP + " && " + "!" +bW + ") => " + bO ));
			kb.add(tweetyParser.parseFormula("(!" + lP + " && " + "!" +lW + ") => " + lO ));
			kb.add(tweetyParser.parseFormula("(!" + rP + " && " + "!" +rW + ") => " + rO ));
			//Update KB When we're in the bottom edge of world
		} else if (i == 0 & j>0){

			if (!stench){

				Negation ns1 = new Negation(s1);
				kb.add(ns1);
				kb.add(tweetyParser.parseFormula("!" + tW + " && " + "!" + lW + " && " + "!" + rW ));

			}
			else{
				kb.add(s1);

					kb.add(tweetyParser.parseFormula("S || " + tW + " || " + lW + " || " + rW ));
					//kb.add(tweetyParser.parseFormula(tW + " || " + lW + " || " + rW));
					kb.add(tweetyParser.parseFormula("!" + lW + " || " + "!" + rW));
					kb.add(tweetyParser.parseFormula("!" + tW + " || " + "!" + rW));
					kb.add(tweetyParser.parseFormula("!" + lW + " || " + "!" + tW));
					kb.add(tweetyParser.parseFormula( "!S || (!" + tW + " && " + "!" + lW + " && " + "!" + rW + ")"));



			}


			Proposition b1 = new Proposition(cB);

			if (!breeze){
				Negation nb1 = new Negation(b1);
				kb.add(nb1);
				kb.add(tweetyParser.parseFormula("!" + tP  + " && " + "!" + lP + " && " + "!" + rP ));
				if (!stench){
					kb.add(tweetyParser.parseFormula(tO + " && " +  lO + " && " + rO ));
				}

			}
			else{
				kb.add(b1);
				kb.add(tweetyParser.parseFormula( tP + " || " + lP + " || " + rP ));
			}

			kb.add(tweetyParser.parseFormula("(!" + tP + " && " + "!" +tW + ") => " + tO ));
			kb.add(tweetyParser.parseFormula("(!" + lP + " && " + "!" +lW + ") => " + lO ));
			kb.add(tweetyParser.parseFormula("(!" + rP + " && " + "!" +rW + ") => " + rO ));

			//Update KB when we're at the left edge of Wumpus World
		} else if(i>0 & j==0){

			if (!stench){

				Negation ns1 = new Negation(s1);
				kb.add(ns1);
				kb.add(tweetyParser.parseFormula("!" + tW + " && " + "!" +bW  + " && " + "!" + rW ));

			}
			else{
				kb.add(s1);
					//at least one Wumpu
					kb.add(tweetyParser.parseFormula("S || " + tW + " || " + bW + " || " + rW ));
					//only one Wumpus
					kb.add(tweetyParser.parseFormula("!" + tW + " || " + "!" + rW));
					kb.add(tweetyParser.parseFormula("!" + tW + " || " + "!" + bW));
					kb.add(tweetyParser.parseFormula("!" + bW + " || " + "!" + rW));
					kb.add(tweetyParser.parseFormula("!S || (!" + tW + " && " + "!" + bW + " && " + "!" + rW + ")"));





			}


			Proposition b1 = new Proposition(cB);

			if (!breeze){
				Negation nb1 = new Negation(b1);
				kb.add(nb1);
				kb.add(tweetyParser.parseFormula("!" + tP + " && " + "!" +bP + " && " + "!" + rP ));


			}
			else{
				kb.add(b1);
				kb.add(tweetyParser.parseFormula( tP + " || " + bP  + " || " + rP ));
			}
			//kb.add(tweetyParser.parseFormula( tP + " || " + bP  + " || " + rP ));
			kb.add(tweetyParser.parseFormula(tP + " || "   +tW + " || " + tO ));
			kb.add(tweetyParser.parseFormula("(!" + bP + " && " + "!" +bW + ") => " + bO ));
			kb.add(tweetyParser.parseFormula("(!" + rP + " && " + "!" +rW + ") => " + rO ));

		}
	}





	private String toString(int i) {
		String s = Integer.toString(i);
		return  s;
	}

	public MyAI ( )
	{
		currentX = 0;
		currentY = 0;
		currentDir = 0;
		maxX = 0;
		maxY = 0;
		findMaxX = false;
		findMaxY =false;
		Proposition W0_0 = new Proposition("W0_0");
		Proposition P0_0 = new Proposition("P0_0");
		Proposition Scream = new Proposition("S");
		Proposition haveArrow = new Proposition("haveArrow");
		Proposition Ok0_0 = new Proposition("ok0_0");
		Proposition Ok1_0 = new Proposition("ok1_0");
		Proposition Ok0_1 = new Proposition("ok0_1");
		Negation noW0_0 = new Negation(W0_0);
		Negation noP0_0 = new Negation(P0_0);
		Negation noScream = new Negation(Scream);

		//initialize the knowledge base
		kb.add(noScream);
		kb.add(noW0_0);
		kb.add(noP0_0);
		kb.add(haveArrow);
		kb.add(Ok0_0);

	}

	public Agent.Action getAction
	(
		boolean stench,
		boolean breeze,
		boolean glitter,
		boolean bump,
		boolean scream
	) {



		SatSolver.setDefaultSolver(new Sat4jSolver());

		//&&

		//adding unvisit square into knowledge base;
		unvisit(bump);
		//update the knowledge base by perceptions;  * need a tell function from knowledge base
		try {
			tell(currentX, currentY, stench, breeze, glitter, bump, scream);
		}catch(IOException e){

		}
		Proposition Glitter = new Proposition("G");
		Negation noG = new Negation(Glitter);
		Proposition haveArrow  = new Proposition("haveArrow");

		//if perceive a glitter in current square, find a path from current square to starting square
		if(reasoner.query(kb, Glitter)){



			System.out.println("Gliter is true");
			plan.add(Action.GRAB);
			int[] current = {currentX, currentY};
			int[] starting = {0, 0};
			MAP map = generateMap();
 			LinkedList<Action> route = plan_route(current, currentDir, starting, map);
			for (int i = 0; i < route.size(); i++) {
				plan.add(route.get(i));
			}
			plan.add(Action.CLIMB);
			kb.remove(Glitter);
		}

		//if plan is empty, find a path to an unvisited and safe square
		else if(plan.isEmpty()){
			System.out.println("plan is empty");
			int[] current = {currentX, currentY};
			int[] goal = null;   // * change
			//finding an unvisited and safe square
			for (int i = 0; i <= maxX; i++) { //* changed
				for (int j = 0; j <= maxY; j++) { //* changed
					Proposition unvisited = new Proposition("unvisited" + i + "_" + j);
					Proposition OK = new Proposition("ok" + i + "_" + j);
					if (reasoner.query(kb, unvisited) && reasoner.query(kb, OK)){
						int[] square = {i,j};
						goal = square;
						break;
					}
				}
			}

			if (goal != null) {
				System.out.println("goal is "+goal[0] + goal[1]);//* change
				MAP map = generateMap();
				LinkedList<Action> route = plan_route(current, currentDir, goal, map);
				for (int i = 0; i < route.size(); i++) {
					plan.add(route.get(i));
				}
				System.out.println("the plan is "+ plan.toString());
			}
		}


		if (plan.isEmpty() && reasoner.query(kb, haveArrow)) {
			int[] current = {currentX, currentY};
			int closestX = currentX;
			int closestY = currentY;
			boolean wumpsFound = false;
			for (int i = 0; i <= maxX; i++) {
				for (int j = 0; j <= maxY; j++) {
					Proposition Wumpus = new Proposition("W" + i + "_" + j);
					Proposition noWumpus = new Proposition("!W" + i + "_" + j);
					if (reasoner.query(kb, Wumpus)) {
						if (Math.abs(i - currentX) < Math.abs(closestX - currentX)) {
							closestX = i;
						}
						if (Math.abs(j - currentY) < Math.abs(closestY - currentY)) {
							closestY = j;
						}
						wumpsFound = true;
						break;
					}
					if (!reasoner.query(kb, noWumpus)) {
						if (Math.abs(i - currentX) < Math.abs(closestX - currentX)) {
							closestX = i;
						}
						if (Math.abs(j - currentY) < Math.abs(closestY - currentY)) {
							closestY = j;
						}
					}
				}
				if(wumpsFound){
					break;
				}
			}
			//The direction the agent is facing: 0 - right, 1 - down, 2 - left, 3 - up
			int wumpusDir;
			int[] goal;
			if (Math.abs(closestY - currentY) > Math.abs(closestX - currentX)) {
				goal = new int[]{closestX, currentY};
				if(currentX > closestX){
					wumpusDir = 2;
				} else {
					wumpusDir = 0;
				}
			} else {
				goal = new int[]{currentX, closestY};
				if(currentY > closestY){
					wumpusDir = 1;
				} else {
					wumpusDir = 3;
				}
			}


			if (!(goal[0] == currentX && goal[1] == currentY)) {
				MAP map = generateMap();
				LinkedList<Action> route = plan_route(current, currentDir, goal, map);
				for (int i = 0; i < route.size(); i++) {
					plan.add(route.get(i));
				}
			}

			List<Action> actions = turnToWumpusSquare(wumpusDir);
			plan.addAll(actions);
			plan.add(Action.SHOOT);
			kb.remove(haveArrow);

		}

		if (plan.isEmpty()) {

			int[] current = {currentX, currentY};
			int[] goal = null;   // * change

			//finding an unvisited and not safe square
			for (int i = 0; i <= maxX; i++) { //* changed
				for (int j = 0; j <= maxY; j++) { //* changed
					Proposition unvisited = new Proposition("unvisited" + i + "_" + j);
					Proposition OK = new Proposition("!ok" + i + "_" + j);
					if (reasoner.query(kb, unvisited) && !reasoner.query(kb, OK)) {
						int[] square = {i, j};
						goal = square;
						break;
					}
				}
			}
			if (goal != null) {       //* change
				MAP map = generateMap();
				map.board[goal[0]][goal[1]].pit = false;       //set the goal square a safe square
				LinkedList<Action> route = plan_route(current, currentDir, goal, map);
				for (int i = 0; i < route.size(); i++) {
					plan.add(route.get(i));
				}
			}


		}

		if (plan.isEmpty()) {
			int[] current = {currentX, currentY};
			int[] starting = {0, 0};
			MAP map = generateMap();
			LinkedList<Action> route = plan_route(current, currentDir, starting, map);
			for (int i = 0; i < route.size(); i++) {
				plan.add(route.get(i));
			}
			plan.add(Action.CLIMB);
		}




		//update the state of the agent according to the action we made
		if(!plan.isEmpty()){
			if(plan.peek() == TURN_LEFT) {
				currentDir = (currentDir+ 3)%4;
			}
			else if(plan.peek() == TURN_RIGHT){
				currentDir = (currentDir + 1)%4;
			}
			if(plan.peek() == Action.FORWARD){
				if(currentDir == 0 && (currentX != maxX)) {
					currentX++;
				}
				else if(currentDir == 3 && (currentY != maxY)){
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


	private List<Action> turnToWumpusSquare(int wumpusDir) {
		LinkedList<Action> actions = new LinkedList<>();
		int value = currentDir - wumpusDir;
		if (value == 0) {
			return actions;
		}
		if (value == 1 || value == -3) {
			actions.add(TURN_LEFT);
			return actions;
		}
		if (value == -1 || value == 3) {
			actions.add(TURN_RIGHT);
			return actions;
		}
		if (value == -2 || value == 2) {
			actions.add(TURN_LEFT);
			actions.add(TURN_LEFT);
			return actions;
		}
		return actions;
	}
	//plan_route
	private LinkedList<Action> plan_route(int[] current, int direction , int[] starting, MAP map) {
		SearchAI AI = new SearchAI(current, direction, starting, map.board);    //* change
		LinkedList<Action> route = AI.plan;    //* change
		return route;
	}

	//generate map
	private MAP generateMap(){

		//int x = maxX;      //map col is current x + 1
		//int y = maxY;      //map row is current y + 1
		int mapX = maxX;
		int mapY = maxY;

		//if current x + 1 is greater than maxX, map col is maxX
		/*if(maxX != 0 && x > maxX){
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

		 */
		MAP map = new MAP(mapX,mapY);
		map.setBoard();

		//set all square in the map
		for (int i = 0; i <= mapX; i++) {   //* changed
			for (int j = 0; j <= mapY; j++) { //* changed
				Proposition OK = new Proposition("ok" + i +"_" + j);
				if(!reasoner.query(kb, OK)){
					map.setPit(i,j);
				}
			}
		}
		return  map;

	}

}



