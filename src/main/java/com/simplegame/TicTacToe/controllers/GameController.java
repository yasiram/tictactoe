package com.simplegame.TicTacToe.controllers;

import java.util.HashMap;
import java.util.Map;

import com.simplegame.TicTacToe.entity.Game;
import com.simplegame.TicTacToe.entity.Player;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("game")
public class GameController {
    Game game;
    Player playerOne;
    Player playerTwo;
    @GetMapping("/create")
	public Map<String, Object> createGame(@RequestParam(name="grid", required=true, defaultValue="3") int grid,
    @RequestParam(name="oneName", required=true, defaultValue="") String oneName,
    @RequestParam(name="twoName", required=true, defaultValue="") String twoName){
        Map<String, Object> res = new HashMap<>();
        if (game == null) {
            game = new Game(grid);
            playerOne = new Player(oneName, 1);
            playerTwo = new Player(twoName, 2);
            res.put("status", "S");
            res.put("grid", game.getGrid());
            res.put("statusGame", game.getStatus());
            res.put("playerTurn", game.getPlayerTurn());
            res.put("playerOneName", playerOne.getName());
            res.put("playerTwoName", playerTwo.getName());
        } else {
            res.put("status", "E");
            res.put("message", "Game has been created");
            game = null;
            playerOne = null;
            playerTwo = null;
        }
		return res;
	}

	@GetMapping("/restart")
	public Map<String, Object> restartGame(){
        Map<String, Object> res = new HashMap<>();
        if (game != null){
            game.setStatus("Restart");
            game.setPlayerTurn(1);
            res.put("status", "S");
            res.put("grid", game.getGrid());
            res.put("status", game.getStatus());
            res.put("playerTurn", game.getPlayerTurn());
        }
        else {
            res.put("status", "E");
            res.put("message", "Please create game first");
        }
		return res;
	}

    @PostMapping("/move")
    @ResponseBody
    public Map<String, Object> move(@RequestBody Map<String, Object> param){
        Map<String, Object> res = new HashMap<>();
        if (param.get("playerOrder") != null) {
            int playerOrder = Integer.parseInt(param.get("playerOrder").toString());
            if (game != null) {
                game.addMovementBoard(playerOrder, param.get("squareBlocks").toString());
                Player player;
                if (playerOrder == 1) {
                    game.setPlayerTurn(2);
                    playerOne.move(param.get("squareBlocks").toString());
                    player = playerOne;
                } else {
                    game.setPlayerTurn(1);
                    playerTwo.move(param.get("squareBlocks").toString());
                    player = playerTwo;
                }
                Map<String, Object> resCheck = game.checkBoard();
                if ((boolean) resCheck.get("win") || (boolean) resCheck.get("tie")) {
                    res = resCheck;
                    res.put("status", "S");
                    res.put("board", game.getBoard());
                    game = null;
                    playerOne = null;
                    playerTwo = null;
                } else {
                    res.put("status", "S");
                    res.put("board", game.getBoard());
                    res.put("playerTurn", game.getPlayerTurn());
                    res.put("movement", player.getMove());
                }
            } else {
                res.put("status", "E");
                res.put("message", "Please create game first");
            }
        } else {
            res.put("status", "E");
            res.put("message", "Please create game first");
        }
		return res;
	}
}
