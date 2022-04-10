# Tic Tac Toe API (Exercise)

## URL

[Click here to start playing](https://tictacapi.herokuapp.com/tictac/game/v1/play?board=+++++++++)

## My Approach on solving this exercise

- **Identify all possible winning combination of three positions**:
  Here I store all winning positions in form of row and colmun index in a list,this helped me to know which combination of positions human is going to use to defeat me.
- **Identify lucky positions to win**: According to what I read from the document of how to play tic tac and win , we have some combination positions that are very likely to win for instance **position 1(0,0)**;**position 3(0,2)**;**position 5(1,1)**;**position 7(2,0)** and **position 9(2,2)** . When human or opponet played one of these positions I also play one of these position randomly to prevent him/her win easily.
- **Identify other combination of positions that are less likely to trick computer**: Those positions are **position 2(0,1)**;**position 4(1,0)**;**position 6(1,2)** and **position 8(2,1)**.
- **Check availability of two combination of winning position on both side (humand and computer)**:First, we  check if computer has two combination that need only one position to win then we play on that position. When human has two combination of winning position , then we block him from winning.
- **Check availability of one combination of winning position**: Then we play in a random position from the lucky position.
 
