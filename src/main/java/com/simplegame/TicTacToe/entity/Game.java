package com.simplegame.TicTacToe.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private String status;
    private int playerTurn;
    private int grid;
    private String[][] board;
    private List<String> playerSign = new ArrayList<>();
    private int moveCount = 0;

    public Game(int grid){
        this.grid = grid;
        this.status = "Start";
        this.playerTurn = 1;
        this.board = new String[grid][grid];
        this.playerSign.add("O");
        this.playerSign.add("X");
    }

    public int getGrid(){
        return this.grid;
    }

    public void setGrid(int grid){
        this.grid = grid;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public int getPlayerTurn(){
        return this.playerTurn;
    }

    public void setPlayerTurn(int playerTurn){
        this.playerTurn = playerTurn;
    }

    public void addMovementBoard(int playerOrder, String squareBlocks){
        String[] blocksIndex = squareBlocks.split(",");
        int row = Integer.parseInt(blocksIndex[0]);
        int col = Integer.parseInt(blocksIndex[1]);
        String sign = this.playerSign.get(playerOrder-1);
        this.board[row][col] = sign;
        this.moveCount++;
    }

    public String[][] getBoard(){
        return this.board;
    }

    public Map<String, Object> checkBoard(){
        Map<String, Object> res = new HashMap<>();
        boolean horizontal;
        boolean vertical;
        boolean diagonal;
        boolean diagonalReverse;
        int diagonalCount = 0;
        int diagonalReverseCount = 0;
        int emptySquareCount = this.grid*this.grid;
        boolean win = false;
        boolean tie = false;
        String directionWin = "";
        int playerOrder = 0;
        int winLoc = 0;
        res.put("win", win);
        res.put("tie", tie);
        if(this.moveCount >= this.grid){
            for(int i = 0; i < this.grid; i++){
                horizontal = true;
                vertical = true;
                for(int j = 0; j < this.grid; j++){
                    //check horizontal
                    if(j > 0 && horizontal){
                        if(this.board[i][j] != this.board[i][j-1] || 
                        this.board[i][j] == null || this.board[i][j-1] == null){
                            horizontal = false;
                        }

                        if(j == (this.grid-1) && horizontal && this.board[i][j] == this.board[i][j-1]){
                            win = true;
                            directionWin = "horizontal";
                            winLoc = i;
                            playerOrder = (this.playerSign.indexOf(this.board[i][0])+1);
                            break;
                        }
                    }

                    if(j > 0 && vertical){
                        if(this.board[j][i] != this.board[j-1][i] || 
                        this.board[j][i] == null || this.board[j-1][i] == null){
                            vertical = false;
                        }
                        if(j == (this.grid-1) && vertical && this.board[j][i] == this.board[j-1][i]){
                            win = true;
                            directionWin = "vertical";
                            winLoc = j;
                            playerOrder = (this.playerSign.indexOf(this.board[0][i])+1);
                            break;
                        }
                    }

                    if(this.board[i][j] != null){
                        emptySquareCount--;
                    }

                    if(emptySquareCount == 0 && !win){
                        tie = true;
                        break;
                    }
                    
                }

                if(win || tie)
                    break;
                
            }

            if(!win && !tie){
                for (int i = 0; i < this.grid; i++) {
                    diagonal = true;
                    diagonalReverse = true;
                    if (i > 0 && diagonal) {
                        diagonalCount++;
                        if (this.board[i][i] != this.board[i-1][i-1] || this.board[i][i] == null
                                || this.board[i-1][i-1] == null) {
                            diagonal = false;
                            diagonalCount--;
                        }
                        if (i == (this.grid - 1) && diagonal && diagonalCount == (this.grid-1) && this.board[i][i] == this.board[i-1][i-1]) {
                            win = true;
                            directionWin = "diagonal";
                            winLoc = 0;
                            playerOrder = (this.playerSign.indexOf(this.board[0][0]) + 1);
                            break;
                        }
                    }

                    if (i > 0 && diagonalReverse) {
                        diagonalReverseCount++;
                        if (this.board[i][(this.grid-1)-i] != this.board[i-1][(this.grid-1)-i+1] || this.board[i][(this.grid-1)-i] == null
                                || this.board[i-1][(this.grid-1)-i+1] == null) {
                                    diagonalReverse = false;
                                    diagonalReverseCount--;
                        }
                        if (i == (this.grid-1) && diagonalReverse && diagonalReverseCount == (this.grid-1) && this.board[i][(this.grid-1)-i] == this.board[i-1][(this.grid-1)-i+1]) {
                            win = true;
                            directionWin = "diagonal";
                            winLoc = 0;
                            playerOrder = (this.playerSign.indexOf(this.board[0][(this.grid-1)]) + 1);
                            break;
                        }
                    }
                    

                    if(win)
                        break;
                }
            }

            if(win){
                res.put("win", win);
                res.put("statusGame", "win");
                res.put("directionWin", directionWin);
                res.put("winLoc", winLoc);
                res.put("playerWin", playerOrder);
            } else if(tie) {
                res.put("tie", tie);
                res.put("statusGame", "tie");
            }
        }
        
        return res;
        
    }
}
