# javaChess

TODO:

* Fix: Attempt to move to already selected tile results in end of turn
* Feat: Implement path obstruction
** Feat: Except knights. They can move through units
* Feat: Implement win conditions
* Feat: That one special move against pawns, whatever its name was


---------

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
          
          
 
  
