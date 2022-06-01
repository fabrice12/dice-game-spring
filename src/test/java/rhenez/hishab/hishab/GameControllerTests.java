package rhenez.hishab.hishab;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rhenez.hishab.hishab.controllers.GameController;
import rhenez.hishab.hishab.dto.PlayerDto;
import rhenez.hishab.hishab.models.Player;
import rhenez.hishab.hishab.models.RegisterResponse;
import rhenez.hishab.hishab.services.GameService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)
@AutoConfigureMockMvc
public class GameControllerTests {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
//    @MockBean
//    GameService gameService;
    @MockBean
    GameController controller;

    Player player2 = new Player("rene", 11);
    Player player3 = new Player("dushime", 30);

    @Test
    public void createPlayerTest() throws Exception {
        PlayerDto player1 = new PlayerDto("Fab", 12);
        ResponseEntity<RegisterResponse> ok = ResponseEntity.ok(new RegisterResponse("You have registered into dice game", 1, 1));
        Mockito.when(controller.registerPlayer(player1)).thenReturn(ok);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/player-registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(player1));
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value(1))
        ;
    }
    @Test
    public void createPlayerwithNullNameTest() throws Exception{
        PlayerDto player1 = new PlayerDto(null, 12);
        ResponseEntity<RegisterResponse> ok = ResponseEntity.ok(new RegisterResponse("You have registered into dice game", 1, 1));
        Mockito.when(controller.registerPlayer(player1)).thenReturn(ok);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/player-registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(player1));
        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value(1))
        ;
    }


}
