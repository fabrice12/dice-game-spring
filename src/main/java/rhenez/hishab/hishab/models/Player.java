package rhenez.hishab.hishab.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Player  {
    @NotBlank(message = "Name is required")
    String name;
    @NotNull(message = "Age parameter is required")
    @Min(1)
    Integer age;

    Integer score=-1;

    Integer playerNumber;
    @JsonIgnore

    boolean canPlay=false;

    boolean gameStarted=false;
//    to hold last score from a dice throw
    Integer lastScore;

    boolean playerScoringStopped=false;

    public Player() {
    }


    public boolean isPlayerScoringStopped() {
        return playerScoringStopped;
    }

    public void setPlayerScoringStopped(boolean playerScoringStopped) {
        this.playerScoringStopped = playerScoringStopped;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public Integer getLastScore() {
        return lastScore;
    }

    public void setLastScore(Integer lastScore) {
        this.lastScore = lastScore;
    }

    public Player(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Player(String name, Integer age, Integer score, boolean canPlay) {
        this.name = name;
        this.age = age;
        this.score = score;
        this.canPlay = canPlay;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public boolean isCanPlay() {
        return canPlay;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }
}
