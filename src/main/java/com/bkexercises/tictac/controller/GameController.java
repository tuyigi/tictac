package com.bkexercises.tictac.controller;


import com.bkexercises.tictac.service.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game/v1")
public class GameController {

    @Autowired
    PlayService playService;

    @GetMapping("/play")
    public ResponseEntity<String> play(@RequestParam(name = "board",required = true) String board){
        return playService.play(board);
    }
}
