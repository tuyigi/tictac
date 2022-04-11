package com.bkexercises.tictac.controller;


import com.bkexercises.tictac.service.PlayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Game PLay Controller", description = "REST API for playing")
@RestController
@RequestMapping("/game/v1")
public class GameController {

    @Autowired
    PlayService playService;
    @ApiOperation(value = "Play", response = Iterable.class, tags = "Game")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad request")})
    @GetMapping("/play")
    public ResponseEntity<String> play(@RequestParam(name = "board",required = true) String board){
        return playService.play(board);
    }
}
