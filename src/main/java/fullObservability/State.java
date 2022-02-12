package fullObservability;

import com.android.internal.util.Objects;

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
        this.wumpsTile = null;
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
        int result = Objects.hashCode(agentDir, pitNumber, colDimension, rowDimension);
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
