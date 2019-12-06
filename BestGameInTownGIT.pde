//BEST GAME IN TOWN EXERCISE
//Made by Katarina Kukavica and Alexandra Labusová

Level level = null;
int levelToLoad = 0; 
Player p;

void setup() { 
  size(720, 416);
  level = new Level(levelToLoad);
  p = new Player();
}

void draw() { 
  background(230, 219, 219,1); 
  level.display();
  p.display();
  level.move();
  p.move();
}

void keyPressed() {
  p.setMove(keyCode, true);
}

void keyReleased() {
  p.setMove(keyCode, false);
}
