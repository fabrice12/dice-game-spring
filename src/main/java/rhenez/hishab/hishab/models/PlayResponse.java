package rhenez.hishab.hishab.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayResponse {
    String message;
    Integer responseCode;
    Integer diceValue;
    Integer nextPlayer;
    Integer totalScore;


    public PlayResponse(String message, Integer responseCode) {
        this.message = message;
        this.responseCode = responseCode;
    }
    public PlayResponse(String message, Integer responseCode,Integer nextPlayer) {
        this.message = message;
        this.responseCode = responseCode;
        this.nextPlayer=nextPlayer;
    }
}
