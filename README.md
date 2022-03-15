# Android-Battleship

For this project, you are going to create a version of the game Battleship where you play against the computer. 

- In the game screen, you will need to show the players board, as well as provide a board for the player to use to attack the opponent. 
- The traditional board size is 10x10 but we are going to use a smaller 8x8 size since a phone screen is typically smaller. 
- The screen itself is programmed similar to how the TicTacToe game was done, writing the interface in Java. 
- The top 8 x 8 area is the player’s board. The numbers indicate the randomly placed ships with different numbers indicating 
  different ships. ‘1’ is for the size 5 ship, 2 is for size 4, 3 and 4 are the two size 3 ships, and 5 is for the single size 2 ship. 
  This board will be different every run since you will use the java random number generator. 
- The ‘Choose a square’ is just txt and then I have an 8x8 button array for the player to attack the computer 
  (which also has a randomly generated board).
- The player goes first, choosing a location to ‘attack’. After every attack, the computer randomly generates a location that it hasn’t 
  tried yet and the top board is updated as a result.  
  On the bottom board, spots already chosen no longer have ‘x’ markers. The red location indicates a hit. On the top board, you can see the 
  computer’s moves that missed (yellow) and the ones that hit (red). 
- The game ends when either the player or the computer finds all of their opponent’s ships. I can win my game every time. 
- Be sure to error check. There is a mandatory 10 point deduction for apps that crash. You will want this to look ok on both emulators 
  and a real device. It is better to have some extra room.
