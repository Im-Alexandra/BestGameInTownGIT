//BEST GAME IN TOWN EXERCISE
//Made by Katarina Kukavica and Alexandra Labusová

Level level = null;
int levelToLoad = 1; 
Player p;
Score s;
Enemy[] e = new Enemy[10]; //we should do same as bullets here - push enemies to the empty array
Brick[][] bricks;
String gameState; //introScreeen -> playing -> betweenLevelsScreen -> playing -> endScreen

void setup() { 
  size(720, 456);
  level = new Level(levelToLoad);
  p = new Player(50, (height/2) - 32);
  s = new Score();
}

void draw() { 
  background(230, 219, 219,1); 
  level.display();
  p.display();
  p.displayLives(20, 27);
  s.display(150, 27);
  level.displayLevelText(320, 27);
  p.checkColission();
  level.move();
  p.move();
}

void keyPressed() {
  p.setMove(keyCode, true);
}

void keyReleased() {
  p.setMove(keyCode, false);
} 