package com.bkexercises.tictac.util;


import org.springframework.stereotype.Component;

@Component
public class Formatting {


    public char[][] intoArray(String board){
        char[][] boardArray=new char[3][3];
        int y=0;
        for(int i=0;i<3;i++){
            for(int x=0;x<3;x++){
                if(board.charAt(y)=='+'){
                    boardArray[i][x]=' ';
                }else{
                    boardArray[i][x]=board.charAt(y);
                }
                y++;
            }
        }
        return boardArray;
    }


    public String intoString(char[][] boardArray){
        String stringBoard="";
        for(int i=0;i<3;i++){
            for(int x=0;x<3;x++){
                stringBoard=stringBoard.concat(String.valueOf(boardArray[i][x]));
            }
        }
        return stringBoard;
    }

}
