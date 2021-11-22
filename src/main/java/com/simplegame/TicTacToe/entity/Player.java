package com.simplegame.TicTacToe.entity;

import java.util.ArrayList;
import java.util.List;

public class Player{
    private String name;
    private int playerOrder;
    private List<String> movement;

    public Player(String name, int playerOrder){
        this.name = name;
        this.playerOrder = playerOrder;
        this.movement = new ArrayList<String>();
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setPlayerOrder(int playerOrder){
        this.playerOrder = playerOrder;
    }

    public int getPlayerOrder(){
        return this.playerOrder;
    }

    public void move(String squareBlocks){
        this.movement.add(squareBlocks);
    }

    public List<String> getMove(){
        return this.movement;
    }
}
