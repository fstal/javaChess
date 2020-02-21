# javaChess

TODO:

* Fix: Attempt to move to already selected tile results in end of turn
* Feat: Implement path obstruction
** Feat: Except knights. They can move through units
* Feat: Implement win conditions
* Feat: That one special move against pawns, whatever its name was


---------

Projektförslag 4: Intelligent schackbräde

Programmet för betyg A ska fungera så här (för C och E implementeras delmängder)

1. ~Visa ett schackbräde (rutigt 8x8) med bilder av schackpjäser~
2. ~Hålla reda på om svart eller vit står på tur att flytta~
3. ~Utföra drag som begärs av användaren~
4. ~Användaren klickar på startruta och slutruta. Om det är ett korrekt (*) drag så utförs det,~ annars ges felmeddelande.
5. ~Kunna reglerna för pjäsernas förflyttningar (men inte föreslå drag, dvs inte spela) och endast utföra korrekta (*) schackdrag.~
6. ~Ta bort slagna pjäser från brädet~
7. När användaren valt en pjäs att flytta, visa vilka rutor det är möjligt att flytta till, t.ex. genom att ge dem annan färg.
8. När en bonde når motsatt ände av brädet (motståndarens startände) ska bonden promoveras. Det går bra att bonden alltid blir en dam.
9. Identifiera när ett drag medför att andra spelarens kung är hotad (schack) och meddela det.

Två personer spelar mot varandra med programmet som bräde. Brädet utför endast korrekta drag.

(*) Reglerna för schack finns att läsa här: https://sv.wikipedia.org/wiki/Schackregler (Länkar till en externa sida.). Implementera pjäsernas rörelse enligt Wikipeida-sidan med följande undantag. Regeln att kungen inte får ställas "i slag" (så att det egna draget gör att kungen hotas av andra spelaren) behöver inte implementeras. Specialdragen en passant och rockad behöver inte finnas med i programmet för någon betygsnivå. Specialdraget promovering ska finnas med för betyg A.

Betyg E: Utför punkterna 1-6 ovan fast med förenklade schackregler: Bönderna flyttar som bönder. Alla andra pjäser får flytta som godtycklig annan pjäs, t.ex. som kung (allra enklast) eller springare (näst enklast). Andra pjäser kräver att man kollar fri väg mellan pjäserna.

Betyg C: Utför punkterna 1-6 med vanliga schackregler enligt (*). Promovering (punkt 8) krävs inte.

Betyg A: Utför punkterna 1-9 ovan. 


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
          
          
 
  
