package rhenez.hishab.hishab.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse extends BaseResponse {

    Integer playerNumber;

    public RegisterResponse(String message, Integer responseCode, Integer playerNumber) {
        super(message, responseCode);
        this.playerNumber = playerNumber;
    }

    public RegisterResponse(String message, Integer responseCode) {
        super(message, responseCode);
    }
}
