package partialObservability;


// DESCRIPTION: This file contains the random agent class, which
//              implements the agent interface. The RandomAI will return
//              a random move at every turn of the game, with only one
//              exception. If the agent perceives glitter, it will grab
//              the gold.
//
// NOTES:       - Don't make changes to this file.
// ======================================================================

import wumpus.Agent;

import java.util.Random;

public class RandomAI extends Agent
{
	
	public Agent.Action getAction
	(
		boolean stench,
		boolean breeze,
		boolean glitter,
		boolean bump,
		boolean scream
	)
	{
		if ( glitter )
			return Agent.Action.GRAB;
		
		return actions [ rand.nextInt ( actions.length ) ];
	}
	
	private final Agent.Action[] actions =
	{
		Agent.Action.TURN_LEFT,
		Agent.Action.TURN_RIGHT,
		Agent.Action.FORWARD,
		Agent.Action.SHOOT,
		Agent.Action.GRAB,
		Agent.Action.CLIMB
	};
	
	private Random rand = new Random();
	
}