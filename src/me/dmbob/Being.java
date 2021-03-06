package me.dmbob;


import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bobby
 */
public class Being {
    public static int steps = 0;
    private int width, height, actCount;
    private String gender;
    private WorldGrid world;
    private GridTile curTile, adjacentTile;
    private boolean acted= false, personFound = false, playerKilled = false, 
            spawnBaby = false, isBaby = false, hadBaby = false;
    private ArrayList<Action> actions;
    private int pick = 0;
    private String name;
    private Action prev;
    private Being killedEnt;
    private Being foundPerson;
    
    public Being(int width, int height, String gender, WorldGrid world, GridTile tile) {
        this.width = width;
        this.height = height;
        this.gender = gender;
        this.world = world;
        this.curTile = tile;
        this.curTile.setPerson(this);
        actions = new ArrayList<Action>();
        for(int a = 0; a < Action.values().length; a++) {
           actions.add(Action.values()[a]);
       }
    }
 
    public void draw(Graphics g) {
            if(gender.equalsIgnoreCase("m")) {
                g.setColor(Color.blue);
            }else if(gender.equalsIgnoreCase("f")) {
                g.setColor(Color.red);
            }else if(gender.equalsIgnoreCase("b")) {
                isBaby = true;
            }
            g.fillRect(curTile.getWidth()/2 - this.width/2, curTile.getHeight()/2 - this.height/2, width, height);
    }
    
    public int getPick() {
        return pick;
    }
    
    public String getLastAction() {
        return prev.toString();
    }
    
    public ArrayList<Action> getActions() {
        return actions;
    }

    public void move(Action a) {
        adjacentTile = getAdjacentTile(a);
        System.out.println(adjacentTile);
        if(curTile == null || adjacentTile == null) { return; }
        curTile.setPerson(null);
        curTile = adjacentTile.setPerson(this);
    }
    
    public void actRandomly() {
        prev = actions.get((int) (Math.random()*actions.size()));
        this.act(prev);
        actions.add(prev);
        System.out.println(prev);
        System.out.println(actions.size());
    }
    
    public GridTile getAdjacentTile(Action a) {
        GridTile newTile = null;
        try {
            if(a.equals(Action.UP)) {
                newTile = world.getTiles().get(getX()).get(getY()-1);
            }
            if(a.equals(Action.DOWN)) {
                newTile = world.getTiles().get(getX()).get(getY()+1);
            }
            if(a.equals(Action.LEFT)) {
                newTile = world.getTiles().get(getX()-1).get(getY());
            }
            if(a.equals(Action.RIGHT)) {
                newTile = world.getTiles().get(getX()+1).get(getY());
            }
        }catch(Exception ex) {
            newTile = curTile;
        }
        return newTile;
    }
    
    public boolean acted() {
        return acted;
    }
    
    public boolean makeBaby() {
        return spawnBaby;
    }
    
    public boolean playerKilled() {
        return playerKilled;
    }
    
    public void setHasKilled(boolean killed) {
        playerKilled = killed;
    }
    
    public Being getKilled() {
        return killedEnt;
    }
    
    public void act(Action a) {
        acted = true;
        actCount++;
        steps++;
        if(actCount > 18 && gender.equals("b")) {
            width = 16;
            height = 16;
            gender = "m";
            isBaby = false; 
            ConsoleDisplay.append("A baby has grown up");
        }
        
        if(a.equals(Action.UP) || a.equals(Action.DOWN) || a.equals(Action.LEFT) || a.equals(Action.RIGHT)) {
            move(a);
            return;
        }
        
        for (Action dir : MainGame.MOVE_ACTIONS) {
            if(getAdjacentTile(dir) != null) {
                foundPerson = getAdjacentTile(dir).getPerson();
                if(foundPerson != null && foundPerson != this) {
                    System.out.println("Hello Person " + name + ", How are you?"); 
                    personFound = true;
                }
                if(personFound && a.equals(Action.KILL) && foundPerson != this) {
                    try {
                        killedEnt = foundPerson;
                        foundPerson.getTile().setPerson(null);
                        playerKilled = true;
                    } catch (NullPointerException ex) { }
                }
                if(personFound && a.equals(Action.MATE) && !isBaby && !hadBaby) {
                    spawnBaby = true;  
                }else {
                    spawnBaby = false;
                } 
            }
        }     
    }
    
    public Being personFound() {
        if(foundPerson!=null) {
            return foundPerson;
        }else {
            return null;
        }
    }
    
    public void setMakeBaby(boolean bool) {
        spawnBaby = bool;
    }
    
    public int getRealX() {
        return curTile.getX()*32;
    }
    
    public int getRealY() {
        return curTile.getY()*32;
    }
    
    public int getX() {
        return curTile.getX();
    }
    
    public int getY() {
        return curTile.getY();
    }
    
    public GridTile getTile() {
        return curTile;
    }
    
    public String toString() {
        return " | name: " + this.name + ", " + getRealX() + ", " + getRealY() + " | ";
    }
}