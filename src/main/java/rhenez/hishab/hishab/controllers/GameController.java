package rhenez.hishab.hishab.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rhenez.hishab.hishab.api.ThrowDice;
import rhenez.hishab.hishab.dto.PlayDto;
import rhenez.hishab.hishab.dto.PlayerDto;
import rhenez.hishab.hishab.models.BaseResponse;
import rhenez.hishab.hishab.models.PlayResponse;
import rhenez.hishab.hishab.models.Player;
import rhenez.hishab.hishab.models.RegisterResponse;
import rhenez.hishab.hishab.services.GameService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class GameController {
    @Autowired
    GameService gameService;

    final ThrowDice throwDice;
    @Autowired
    private ModelMapper modelMapper;

    public GameController(ThrowDice throwDice) {
        this.throwDice = throwDice;
    }

    @Operation(summary = "Api used for player registration")
    @PostMapping("/player-registration")
    public ResponseEntity<RegisterResponse> registerPlayer(@Valid @RequestBody PlayerDto playerDto) {

//        Player newPlayer = new Player(pl.getName(), player.getAge());
        Player newPlayer = modelMapper.map(playerDto,Player.class);
        int playersNumber = gameService.getPlayers().size();
        if (!gameService.isGameStarted()){
            if (playersNumber < gameService.getMaxPlayers()) {

                newPlayer.setPlayerNumber(playersNumber + 1);
                gameService.registerPlayer(newPlayer);
                return ResponseEntity.ok( new RegisterResponse("You have registered into dice game",1,newPlayer.getPlayerNumber()));
            } else {
                return new ResponseEntity<>(new RegisterResponse("Game is full",0), HttpStatus.BAD_REQUEST);
            }
        }else {
            return new ResponseEntity<>(new RegisterResponse("Game already started!! you can't register",0),HttpStatus.BAD_REQUEST);
        }


    }
    @Operation(summary = "Api used to start the game")
    @GetMapping("/start-game")
    public ResponseEntity<BaseResponse> startGame() {
        if (gameService.getPlayers().size() < gameService.getMinPlayers()) {
            return new ResponseEntity<>(new BaseResponse("Game can't be started at least " + gameService.getMinPlayers() + " players must be present",1),HttpStatus.BAD_REQUEST) ;
        } else {
            gameService.setGameStarted(true);
//        start the game with player number 1
            gameService.setAllowedPlayerNumber(1);

            return new ResponseEntity<>(new BaseResponse("Game started",1),HttpStatus.OK);
        }

    }

    @Operation(summary = "Api used to actually play (throw a dice)")
    @PostMapping("/play")
    public PlayResponse play(@RequestBody PlayDto playDto) {
        List<Player> players = gameService.getPlayers();

        if (gameService.isGameStarted()) {
            for (Player player : players) {
                //search for current player
                if (player.getPlayerNumber().equals(playDto.getPlayerNumber())) {

                    if (player.getPlayerNumber().equals(gameService.getAllowedPlayerNumber())) {
                        Integer existingScore = player.getScore();
                        int newScore = throwDice.getDiceScore().getScore();
                        player.setLastScore(newScore);
                        if (player.isGameStarted()) {
                            if (!player.isPlayerScoringStopped()) {
                                switch (newScore) {
                                    case 6:
                                        gameService.increaseScore(player, existingScore + newScore);
                                        gameService.setAllowedPlayerNumber(player.getPlayerNumber());
                                        break;
                                    case 4:
                                        if (player.getLastScore() == 6) {
                                            player.setScore(existingScore);
                                            player.setPlayerScoringStopped(true);
                                        } else {
                                            player.setScore(existingScore - 4);
                                        }

                                        gameService.setAllowedPlayerNumber(gameService.getAllowedPlayerNumber() + 1);
                                        break;
                                    default:
                                        gameService.increaseScore(player, existingScore + newScore);
                                        gameService.setAllowedPlayerNumber(gameService.getAllowedPlayerNumber() + 1);
                                        break;

                                }
                            } else {
                                switch (newScore) {
                                    case 6:
                                        player.setPlayerScoringStopped(false);
                                        gameService.setAllowedPlayerNumber(player.getPlayerNumber());
                                        break;
                                    default:
                                        player.setPlayerScoringStopped(true);
                                        gameService.setAllowedPlayerNumber(gameService.getAllowedPlayerNumber() + 1);
                                        break;
                                }

                            }

                        } else {
                            switch (newScore) {
                                case 6:
                                    player.setGameStarted(true);
                                    player.setScore(0);
                                    gameService.setAllowedPlayerNumber(player.getPlayerNumber());
                                    break;
                                default:
                                    gameService.setAllowedPlayerNumber(gameService.getAllowedPlayerNumber() + 1);
                                    break;
                            }
                        }
                        log.info("Player name: " + player.getName()+",Total score: "+player.getScore()+", Current Value of dice: "+newScore);

                        return new PlayResponse("Dice thrown successfully", 1, newScore, gameService.getAllowedPlayerNumber(), player.getScore());
                    } else {
                        return new PlayResponse("You are not allowed to play", 0, gameService.getAllowedPlayerNumber());
                    }
                }
            }
            return new PlayResponse("Dice thrown successfully2", 1);
        } else {
            return new PlayResponse("Please wait for the game to start", 0);
        }


    }

    @Operation(summary = "Api used to get all players points and status")
    @GetMapping("/all-players")
    public List<Player> getPlayers() {
        return gameService.getPlayers();
    }
}
