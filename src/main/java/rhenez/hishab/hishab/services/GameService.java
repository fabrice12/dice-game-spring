package rhenez.hishab.hishab.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import rhenez.hishab.hishab.models.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Scope("singleton")
public class GameService {

    List<Player> players = new ArrayList<>();
    Integer maxPlayers = 4;
    Integer minPlayers = 2;
    Integer allowedPlayerNumber = 1;
//    get final score from configuration file and set 25 as default
    @Value("${game.score:25}")
    Integer finalScore;
    boolean gameEnded=false;
    boolean isGameStarted = false;

    HashMap<String, Integer> ScoreBoard = new HashMap<String, Integer>();

    public Integer getFinalScore() {
        return finalScore;
    }
    public void gameOver(){
        players.clear();
        isGameStarted=false;
        allowedPlayerNumber=1;
        setGameEnded(true);

    }
    public void increaseScore(Player player,Integer scoreToIncrease){
        player.setScore(scoreToIncrease);
        if (player.getScore()>=getFinalScore()){
            gameOver();
        }
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }

    public void setAllowedPlayerNumber(Integer allowedPlayerNumber) {
        if (allowedPlayerNumber > players.size()) {
            this.allowedPlayerNumber = 1;
        } else {
            this.allowedPlayerNumber = allowedPlayerNumber;
        }

    }

    public Integer getAllowedPlayerNumber() {
        return allowedPlayerNumber;
    }

    public void updateScoreBoard(String name, Integer score) {
        ScoreBoard.put(name, score);
    }

    public void registerPlayer(Player player) {
        players.add(player);
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public Integer getMinPlayers() {
        return minPlayers;
    }

    public boolean isGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }

    public List<Player> getPlayers() {
        return players;
    }


}
