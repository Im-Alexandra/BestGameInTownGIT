//Level class

class Level {

  int id = -1; 
  int cols = 500;
  int rows = 8;
  Brick[][] bricks = new Brick[cols][rows];

  Level(int levelID) {
    String[] levelString = loadStrings("level" + levelID + ".txt");

    for (int i=0; i < cols; i++) { 
      for(int j=0; j < rows; j++){
         bricks[i][j] = new Brick();
      }
    }
    
    //CONSOLE
    println("there are " + levelString.length + " levelString");
    for (int i = 0; i < levelString.length; i++) { 
      println(levelString[i]);
    }

    int currentBrick=0; 
    for (int i = 0; i < levelString.length; i++) { //goes through levelString
      println("i is "+i);
      for (int j = 0; j < levelString[i].length(); j++) { //goes through rows 
        if (levelString[i].charAt(j) != '0') {
          bricks[currentBrick][i].switchOn(j*52, i*52, levelString[i].charAt(j)); 
          println(currentBrick);
          currentBrick++;
        }
      }
    }
  }


    void display() { 
      for (int i = 0; i < cols; i++) { 
        for(int j=0; j < rows; j++){
        bricks[i][j].display();
        }
      }
    }
    
    void move() {
      for (int i = 0; i < cols; i++) { //goes through levelString
      for(int j = 0; j < rows; j++) { //goes through rows 
        bricks[i][j].x -= 2;
      }
    }
    
    }
 }
