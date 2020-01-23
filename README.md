# javaChess
DDSomething Project


* Ish UML

Vad ska abstrakta ChessPiece-klassen innehålla?
Color
Position

abstract:
movement(position){}


Subklasser som ärver av ChessPiece och definierar movement metodbody:
*Pawn
** If firstMove:
**special attack

*Knight
** Can move over pieces

*Bishop
*Rook 
*Queen
*King
** Can die => Loss



Checks:
Is tile occupied?
  Yes
    Is friend
      -> Invalid move
    No (is foe)
      Is move correct
        Yes
          Is path obstruced
            Yes
              -> Invalid Move
            No
              -> Kill & Replace
        No (move incorrect)
          -> Invalid move
  No
    Is move correct
    Yes
      Is path obstruced
        Yes
          -> Invalid Move
        No
          -> Kill & Replace
    No (move incorrect)
      -> Invalid move

     
Remember:
 * Knights ignore ubstructed paths
 * Pawns need an instance variable, boolean, for first move
 ** first move allows for different move pattern
 * Pawns have different attack moveset than movement
 
Win conditions
 
Chess icons

Tiles
          
          
 
  
