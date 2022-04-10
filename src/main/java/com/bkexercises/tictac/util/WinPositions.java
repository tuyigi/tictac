package com.bkexercises.tictac.util;

import com.bkexercises.tictac.model.Position;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class WinPositions {

    List<List<Position>> positions=new ArrayList<>();

    public List<List<Position>> getWinPosition(){

        // rows

        List<Position> topRowPositions=new ArrayList<>();
        topRowPositions.add(new Position(0,0));
        topRowPositions.add(new Position(0,1));
        topRowPositions.add(new Position(0,2));

        positions.add(topRowPositions);

        List<Position> centerRowPositions=new ArrayList<>();
        centerRowPositions.add(new Position(1,0));
        centerRowPositions.add(new Position(1,1));
        centerRowPositions.add(new Position(1,2));

        positions.add(centerRowPositions);

        List<Position> bottomRowPositions=new ArrayList<>();
        bottomRowPositions.add(new Position(2,0));
        bottomRowPositions.add(new Position(2,1));
        bottomRowPositions.add(new Position(2,2));

        positions.add(bottomRowPositions);


        // columns


        List<Position> firstColPositions=new ArrayList<>();
        firstColPositions.add(new Position(0,0));
        firstColPositions.add(new Position(1,0));
        firstColPositions.add(new Position(2,0));

        positions.add(firstColPositions);


        List<Position> middleColPositions=new ArrayList<>();
        middleColPositions.add(new Position(0,1));
        middleColPositions.add(new Position(1,1));
        middleColPositions.add(new Position(2,1));

        positions.add(middleColPositions);



        List<Position> endColPositions=new ArrayList<>();
        endColPositions.add(new Position(0,2));
        endColPositions.add(new Position(1,2));
        endColPositions.add(new Position(2,2));

        positions.add(endColPositions);

        // diagonals

        List<Position> firstDiagonalPositions=new ArrayList<>();
        firstDiagonalPositions.add(new Position(0,0));
        firstDiagonalPositions.add(new Position(1,1));
        firstDiagonalPositions.add(new Position(2,2));

        positions.add(firstDiagonalPositions);

        List<Position> secondDiagonalPositions=new ArrayList<>();
        secondDiagonalPositions.add(new Position(0,2));
        secondDiagonalPositions.add(new Position(1,1));
        secondDiagonalPositions.add(new Position(2,0));

        positions.add(secondDiagonalPositions);



        return positions;
    }



    public int positionIntoNumber(Position position){

        int numberPosition=0;

        if(position.getRow()==0&& position.getCol()==0){
            numberPosition=1;
        }else if(position.getRow()==0&& position.getCol()==1){
            numberPosition=2;
        }else if(position.getRow()==0&& position.getCol()==2){
            numberPosition=3;
        } else if(position.getRow()==1&& position.getCol()==0){
            numberPosition=4;
        }else if(position.getRow()==1&& position.getCol()==1){
            numberPosition=5;
        }else if(position.getRow()==1&& position.getCol()==2){
            numberPosition=6;
        }else if(position.getRow()==2&& position.getCol()==0){
            numberPosition=7;
        }else if(position.getRow()==2&& position.getCol()==1){
            numberPosition=8;
        }else if(position.getRow()==2&& position.getCol()==2){
            numberPosition=9;
        }
        return numberPosition;
    }

}
