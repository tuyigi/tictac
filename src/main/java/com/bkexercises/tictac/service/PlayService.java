package com.bkexercises.tictac.service;

import org.springframework.http.ResponseEntity;

public interface PlayService {

    ResponseEntity<String> play(String board);
}
