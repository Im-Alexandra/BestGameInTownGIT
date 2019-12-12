//Level class
class Level {

  int id = -1; 
  int cols = 60; //these should be same number for every level
  int rows = 8; //these should be same number for every level
  int levelPlaying;
  float levelXPos, levelYPos;
  String[] levelString;
  Brick[][] bricks = new Brick[rows][cols];

  Level(int levelID) {
    
    levelString = loadStrings("level" + levelID + ".txt");
    levelPlaying = levelID;
    cols = levelString[0].length();
    rows = levelString.length;
    //println("rows: "+levelString.length+" cols: "+levelString[0].length());
    for (int i=0; i < levelString.length; i++) { 
      for (int j=0; j < levelString[i].length(); j++) {
        bricks[i][j] = new Brick();
      }
    }

    //CONSOLE
    println("there are " + levelString.length + " levelString");
    for (int i = 0; i < levelString.length; i++) { 
      println(levelString[i]);
    }

    for (int i = 0; i < levelString.length; i++) { //goes through levelString
      for (int j = 0; j < levelString[i].length(); j++) { //goes through rows 
        if (levelString[i].charAt(j) != '0') {
          bricks[i][j].switchOn(j*52, i*52, levelString[i].charAt(j)); 
          //println(currentBrick);
        }
      }
    }
  }

  void display() { 
    for (int i = 0; i < rows; i++) { 
      for (int j=0; j < cols; j++) {
        bricks[i][j].display();
      }
    }
  }

  void displayLevelText(float x, float y) {
    levelXPos = x;
    levelYPos = y;
    textSize(20);
    fill(255);
    text("Level: " + levelPlaying, levelXPos, levelYPos);
  }

  void move() {
    for (int i = 0; i < rows; i++) { //goes through levelString
      for (int j = 0; j < cols; j++) { //goes through rows 
        bricks[i][j].x -= 2;
      }
    }
  }
}
