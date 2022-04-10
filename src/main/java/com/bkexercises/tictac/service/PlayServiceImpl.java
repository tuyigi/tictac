package com.bkexercises.tictac.service;

import com.bkexercises.tictac.config.Config;
import com.bkexercises.tictac.model.Position;
import com.bkexercises.tictac.util.Formatting;
import com.bkexercises.tictac.util.WinPositions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class PlayServiceImpl implements PlayService {
    @Autowired
    Formatting formatting;

    @Autowired
    WinPositions winPositions;

    @Autowired
    Config config;

    // lucky positions

    int[] pos={1,3,5,7,9};

    @Override
    public ResponseEntity<String> play(String board) {

        try {

        // convert to uppercase for avoiding misatkes
        board = board.toUpperCase();

        // remove spaces and check if it has corespodning character
        if (board.length() != 9) {
            return new ResponseEntity("Incorrect board", HttpStatus.BAD_REQUEST);
        }

        // format string into char array
        char[][] boardArray = formatting.intoArray(board);

        // if it is our server to play first , we play in the center or in the one of the corners
        if (!board.contains("X")) {

            int randomPos = getLuckyPOsition();

            boardArray = playLuckyPosition(boardArray, randomPos);

            return new ResponseEntity(formatting.intoString(boardArray), HttpStatus.OK);
        }

        // if the user calling API has already played we play against him or her

        // count how many x we have

        long totalX = board.chars().filter(ch -> ch == config.getOpponent()).count();

        // check if it is the first round for the player and get where he/she played , so we have one X in the board

        if (totalX == 1) {

            System.out.println("only one X");
            // get the position of the player if we have only one X
            int position = findPosition(boardArray);

            // check if it is in lucky position (top corner,right corner , center, right bottom corner,left bottom corner)
            if (findLuckyPosition(position)) {
                // if yes we play in other lucky position
                int randomLuckyPosition = getLuckyPOsition();

                while (pos[randomLuckyPosition] == position) randomLuckyPosition = getLuckyPOsition();


                System.out.println("randomLuckyPosition == " + randomLuckyPosition);

                // then we play
                boardArray = playLuckyPosition(boardArray, randomLuckyPosition);

                return new ResponseEntity(formatting.intoString(boardArray), HttpStatus.OK);

            }else{

                // get player human positions
                List<Position> XPositions = findAllXOPositions(boardArray,config.getOpponent());

                for(Position p:XPositions){

                    System.out.println(p.getRow()+","+p.getCol());

                    // get number position
                    int numberPos=winPositions.positionIntoNumber(p);

                    // check if in the center is already filled or not
                    if(boardArray[1][1]==' '){
                        boardArray[1][1]=config.getServer();
                        return new ResponseEntity(formatting.intoString(boardArray),HttpStatus.OK);
                    }
                    // check if we have positions like 2,4,6,8
                    if(numberPos==2){
                       if(boardArray[0][0]==' '){
                           boardArray[0][0]=config.getServer();
                           return new ResponseEntity(formatting.intoString(boardArray),HttpStatus.OK);
                       }else if(boardArray[0][2]==' '){
                           boardArray[0][2]=config.getServer();
                           return new ResponseEntity(formatting.intoString(boardArray),HttpStatus.OK);
                       }
                    }else if(numberPos==4){
                        if(boardArray[0][0]==' '){
                            boardArray[0][0]=config.getServer();
                            return new ResponseEntity(formatting.intoString(boardArray),HttpStatus.OK);
                        }else if(boardArray[2][0]==' '){
                            boardArray[0][0]=config.getServer();
                            return new ResponseEntity(formatting.intoString(boardArray),HttpStatus.OK);
                        }
                    }else if(numberPos==6){
                        if(boardArray[0][2]==' '){
                            boardArray[0][2]=config.getServer();
                            return new ResponseEntity(formatting.intoString(boardArray),HttpStatus.OK);
                        }else if(boardArray[2][2]==' '){
                            boardArray[2][2]=config.getServer();
                            return new ResponseEntity(formatting.intoString(boardArray),HttpStatus.OK);
                        }
                    }else if(numberPos==8){
                        if(boardArray[2][0]==' '){
                            boardArray[2][0]=config.getServer();
                            return new ResponseEntity(formatting.intoString(boardArray),HttpStatus.OK);
                        }else if(boardArray[2][2]==' '){
                            boardArray[2][2]=config.getServer();
                            return new ResponseEntity(formatting.intoString(boardArray),HttpStatus.OK);
                        }
                    }
                }




            }

        } else {

            // check if we have winner


            // get server positions
            List<Position> OPositions=findAllXOPositions(boardArray,config.getServer());

            // get player human positions
            List<Position> XPositions = findAllXOPositions(boardArray,config.getOpponent());



            // check we have more O that need only one O the we win

            if(OPositions.size()==2){
                System.out.println("two");
                Position winPositonO=findPositionBlock(OPositions,boardArray);
                if(winPositonO!=null && boardArray[winPositonO.getRow()][winPositonO.getCol()]==' '){
                    boardArray[winPositonO.getRow()][winPositonO.getCol()]=config.getServer();
                    // for testing the output for our win !
                    System.out.println( "win position ==> "+winPositonO.getRow() + "," + winPositonO.getCol());

                    if(findWinner(boardArray,config.getServer())){
                        return new ResponseEntity("Computer won! with the board :: " +formatting.intoString(boardArray),HttpStatus.OK);
                    }

                    if(findWinner(boardArray,config.getOpponent())){
                        return new ResponseEntity("you won! :: "+formatting.intoString(boardArray),HttpStatus.OK);
                    }

                    return new ResponseEntity(formatting.intoString(boardArray),HttpStatus.OK);
                }
            }





            // if we have more X of the user who is playing, find all positions have X value



            Position  blockPosition=findPositionBlock(XPositions,boardArray);

            if(blockPosition!=null && boardArray[blockPosition.getRow()][blockPosition.getCol()]==' '){
                boardArray[blockPosition.getRow()][blockPosition.getCol()]=config.getServer();

//                for testing the output

                System.out.println(blockPosition.getRow() + "," + blockPosition.getCol());
                if(findWinner(boardArray,config.getServer())){
                    return new ResponseEntity("Computer won! with the board :: " +formatting.intoString(boardArray),HttpStatus.OK);
                }

                if(findWinner(boardArray,config.getOpponent())){
                    return new ResponseEntity("you won! :: "+formatting.intoString(boardArray),HttpStatus.OK);
                }

                return new ResponseEntity(formatting.intoString(boardArray),HttpStatus.OK);
            }


            // if we don't have two win positions for both server O and player X , let implement other logics to win

            // by search where we have one position by have 2 position possible to win

            // start by O (server) positions
            for(Position position:OPositions){
                List<Position> OGapPosition=findGapPosition(boardArray,config.getServer(),position);
                // random from the result and take if it is more than one
                if(OGapPosition.size()>1){
                    Position p=OGapPosition.get(new Random().nextInt(OGapPosition.size()));
                    boardArray[p.getRow()][p.getCol()]='O';


                    if(findWinner(boardArray,config.getServer())){
                        return new ResponseEntity("Computer won! with the board :: " +formatting.intoString(boardArray),HttpStatus.OK);
                    }

                    if(findWinner(boardArray,config.getOpponent())){
                        return new ResponseEntity("you won! :: "+formatting.intoString(boardArray),HttpStatus.OK);
                    }

                    return new ResponseEntity(formatting.intoString(boardArray),HttpStatus.OK);
                }
            }





            // check if player or opponet has two gaps positions he can possibly use

            // start by X (player) positions
            for(Position position:XPositions){
                List<Position> XGapPosition=findGapPosition(boardArray,config.getOpponent(),position);
                // random from the result and take if it is more than one
                if(XGapPosition.size()>1){
                    Position p=XGapPosition.get(new Random().nextInt(XGapPosition.size()));
                    boardArray[p.getRow()][p.getCol()]=config.getServer();

                    if(findWinner(boardArray,config.getServer())){
                        return new ResponseEntity("Computer won! with the board :: " +formatting.intoString(boardArray),HttpStatus.OK);
                    }

                    if(findWinner(boardArray,config.getOpponent())){
                        return new ResponseEntity("you won! :: "+formatting.intoString(boardArray),HttpStatus.OK);
                    }

                    return new ResponseEntity(formatting.intoString(boardArray),HttpStatus.OK);
                }
            }

        }

        
    } catch (Exception e){
            System.out.println(e.getStackTrace());
           
        }
        return null;
    
    }




//    function to find position if we have only one X in the board

    private int findPosition(char[][]boardArray ){
        int position=0;

        for(int i=0;i<3;i++){
            for(int y=0;y<3;y++){
                if(boardArray[i][y]==config.getOpponent()){
                    Position p=new Position(i,y);
                    position=winPositions.positionIntoNumber(p);
                }
            }
        }
        return position;
    }


    // find if the positon is in the lucky position

    private boolean findLuckyPosition(int position){
        for(int i:pos){
            if(i==position) return true;
        }
        return false;
    }


//   function to get   random lucky position

    private int getLuckyPOsition(){
        return  new Random().nextInt(pos.length);
    }

    // function to play lucky position

    private char[][] playLuckyPosition(char[][] boardArray,int position){
        switch (position){
            case 0:
                boardArray[0][0]=config.getServer();
                break;
            case 1:
                boardArray[0][2]=config.getServer();
                break;
            case 2:
                boardArray[1][1]=config.getServer();
                break;
            case 3:
                boardArray[2][0]=config.getServer();
                break;
            case 4:
                boardArray[2][2]=config.getServer();
                break;
            default:
                break;
        }

        return boardArray;
    }


    // function to find all X positions in our board

    private List<Position> findAllXOPositions(char[][] boardArray,char c){
        List<Position> positions=new ArrayList<>();
        Position position;
       for(int i=0;i<3;i++){
           for(int y=0;y<3;y++){
               if(boardArray[i][y]==c){
                   System.out.println("XXXXX found===>"+i+","+y);
                   position=new Position(i,y);
                   positions.add(position);
               }
           }
       }

       return positions;
    }


    // function to find which position to block when we have more than one X in then board

    private Position findPositionBlock(List<Position> XPositions,char[][] boardArray){

        // we find if we have two x that need only one X to win and then we block them


        // we first get all available possible wins positions
        List<List<Position>> allWinPositions = winPositions.getWinPosition();

        int count = 0;

        for (List<Position> ps : allWinPositions) {

            // check if we have atleast two positons of wins



            Position pp=null;

            count=0;

            for(int i=0;i<ps.size();i++){

                if(containsPosition(XPositions,ps.get(i))){
                    System.out.println(ps.get(i).getRow()+"========== found======="+ps.get(i).getCol());
                    count++;
                }else{
                    System.out.println(ps.get(i).getRow()+"========== not found ======="+ps.get(i).getCol());
                    pp=ps.get(i);
                }
            }

            if(count==2){
                System.out.println(pp.getRow()+"=========="+pp.getCol());
                if(boardArray[pp.getRow()][pp.getCol()]==' '){
                    return pp;
                }

            }



        }
        return null;
    }


    // search the object of posiiotn in the list

    private boolean containsPosition(List<Position> positions,Position position){
        for(Position pp:positions){
            if(pp.getRow()==position.getRow()&&pp.getCol()==position.getCol()){
                return true;
            }
        }
        return false;
    }


    // check if i get two position with alongside with one O or X for winning

    private List<Position> findGapPosition(char[][] boardArray,char c,Position position){

        List<Position> gapPosition=new ArrayList<>();

        List<List<Position>> allWinPositions = winPositions.getWinPosition();

        for(List<Position> positions:allWinPositions){

            // where our position is in the winning positions

            if(containsPosition(positions,position) && boardArray[position.getRow()][position.getCol()]==c){

                // check if we have two positions and which have empty value ' ' that is chance for use
                int countEmpty=0;
                for(int i=0;i<positions.size();i++){
                    if(boardArray[positions.get(i).getRow()][positions.get(i).getCol()]==' '){
                        gapPosition.add(positions.get(i));
                        countEmpty++;
                    }
                }
                if(countEmpty==2){
                    return gapPosition;
                }else{
                    gapPosition.clear();
                }
            }

        }

        return gapPosition;
    }


    // function to find who win the game

    private boolean findWinner(char[][] boardArray,char c){



        List<Position> positions=findAllXOPositions(boardArray,c);

//        if(c==config.getServer()){
//            positions=findAllXOPositions(boardArray,c);
//        }else if(c==config.getOpponent()){
//            positions=findAllXOPositions(boardArray,c);
//        }

        // we loop through the win positions to see if we have winner

        List<List<Position>> allWinPositions= winPositions.getWinPosition();
        int count = 0;

        for (List<Position> ps : allWinPositions) {

            count=0;
            for(int i=0;i<ps.size();i++){

                if(containsPosition(positions,ps.get(i))){
                    System.out.println(ps.get(i).getRow()+"========== found======="+ps.get(i).getCol());
                    count++;
                }
            }

            // if we count three positions in the winning positions, the we have winner
            if(count==3){
                return true;

            }

        }

       return false;

    }


}
