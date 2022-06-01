package rhenez.hishab.hishab.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import rhenez.hishab.hishab.models.DiceScore;

@FeignClient(name = "LuckyDice", url = "http://developer-test.hishab.io",path = "/api/v1")
public interface ThrowDice {
    @GetMapping(value = "/roll-dice")
    DiceScore getDiceScore();
}
