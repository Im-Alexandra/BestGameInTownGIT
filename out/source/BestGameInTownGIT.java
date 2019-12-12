import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class BestGameInTownGIT extends PApplet {

//BEST GAME IN TOWN EXERCISE
//Made by Katarina Kukavica and Alexandra Labusová

Level level = null;
int levelToLoad = 1; 
Player p;
Score s;
Enemy[] e = new Enemy[10]; //we should do same as bullets here - push enemies to the empty array
Brick[][] bricks;
String gameState; //introScreeen -> playing -> betweenLevelsScreen -> playing -> endScreen

public void setup() { 
  
  level = new Level(levelToLoad);
  p = new Player(50, (height/2) - 32);
  s = new Score();
}

public void draw() { 
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

public void keyPressed() {
  p.setMove(keyCode, true);
}

public void keyReleased() {
  p.setMove(keyCode, false);
} 
//Brick class
class Brick {

  int x = 0; 
  int y = 0; 
  int w = 52; 
  int h = 52; 
  boolean isOn = false;
  int bValue;
  PImage spritesheet;
  PImage currentImage;

  Brick() {
  }

  public void display() { 
    if (isOn) {
      if(bValue == '1'){
        //fill(255);
        //rect(x, y, w, h);
        spritesheet = loadImage("tile.png");
        currentImage = spritesheet.get(0 + (0 * 52), 0, 52, 52);
        image(currentImage, x, y+40, w, h); //+40 to have space for top menu
      }else if(bValue == 'E'){ 
       for (int i = 0; i<3; i++) {
        e[i]=new Enemy(x,y+40);
        e[i].display();
        
       }
       //KATKA HERE YOU MAKE NEW ENEMIES + IN THE TEXT FILE
       
      }
    }
  }
  
  public boolean isColliding(float px, float py, int playerWidth, int playerHeight){
    if((x < px+playerWidth) && (x+w > px) && (y+h > py) && (y < py+playerHeight)){
    return true;
    }
    return false;
  }

  public void switchOn(int posX, int posY, int value) { 
    x=posX; 
    y=posY;
    bValue = value;
    isOn=true;
  }

  public void switchOff() { 
    isOn=false;
  }
}
//Enemy class
class Enemy {

  //variables
  float exPos, eyPos;
  int eWidth = 100, eHeight = 54;
  PImage spritesheet;
  PImage currentImage;
  int currentFrameHorizontal = 0;
  int lastFrameHorizontal = 9;
  int iTicks = millis();
  int durationOneFrame = 100; //in milliseconds

  //constructor
  Enemy(float x, float y) {
    exPos = x;
    eyPos = y;
  }

  public void display() {
    spritesheet = loadImage("enemy.png");
    currentImage = spritesheet.get(0 + (currentFrameHorizontal * eWidth), 0 , eWidth, eHeight);
    image(currentImage, exPos, eyPos, eWidth, eHeight);
  }
}
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

  public void display() { 
    for (int i = 0; i < rows; i++) { 
      for (int j=0; j < cols; j++) {
        bricks[i][j].display();
      }
    }
  }

  public void displayLevelText(float x, float y) {
    levelXPos = x;
    levelYPos = y;
    textSize(20);
    fill(255);
    text("Level: " + levelPlaying, levelXPos, levelYPos);
  }

  public void move() {
    for (int i = 0; i < rows; i++) { //goes through levelString
      for (int j = 0; j < cols; j++) { //goes through rows 
        bricks[i][j].x -= 2;
      }
    }
  }
}
//Player class
class Player {

  //variables
  float pxPos, pyPos;
  float pVelocity = 3;
  boolean isLeft, isRight, isUp, isDown;
  int pWidth = 100, pHeight = 65;
  PImage spritesheet;
  PImage currentImage;
  int currentFrameHorizontal = 0;
  int lastFrameHorizontal = 9;
  int currentFrameVertical = 0; //0 - right, 1 - left
  int iTicks = millis();
  int iTicks1 = millis();
  int durationOneFrame = 100; //in milliseconds
  int lives = 3;
  int livesXPos, livesYPos;
  boolean check = false;

  //constructor
  Player(float x, float y) {
    pxPos = x;
    pyPos = y;
  }

  //methods
  public void display() {
    spritesheet = loadImage("player.png");
    currentImage = spritesheet.get(0 + (currentFrameHorizontal * pWidth), 0 + (currentFrameVertical * pHeight), pWidth, pHeight);
    image(currentImage, pxPos, pyPos, pWidth, pHeight);

    int delta = millis() - iTicks;
    if (delta >= durationOneFrame) {
      currentFrameHorizontal++;

      if (currentFrameHorizontal >= lastFrameHorizontal) {
        currentFrameHorizontal = 0;
      }

      iTicks += delta;
    }
  }

  public void displayLives(int x, int y) {
    livesXPos = x;
    livesYPos = y;
    textSize(20);
    fill(255);
    text("Lives: " + lives, livesXPos, livesYPos);
  }

  public void move() {
    // movement from here - http://studio.processingtogether.com/sp/pad/export/ro.91tcpPtI9LrXp
    final int r = pWidth/2;
    pxPos = constrain(pxPos + pVelocity*(PApplet.parseInt(isRight) - PApplet.parseInt(isLeft)), 0, width - pWidth);
    pyPos = constrain(pyPos + pVelocity*(PApplet.parseInt(isDown)  - PApplet.parseInt(isUp)), 52+40, height - (pHeight+52));

    if (isRight) {
      //println("moving right");
      currentFrameVertical = 0;
    } else if (isLeft) {
      //println("moving left");
      currentFrameVertical = 1;
    }
  }

  public boolean setMove(final int k, final boolean b) {
    switch (k) {
    case +'W':
    case UP:
      return isUp = b;

    case +'S':
    case DOWN:
      return isDown = b;

    case +'A':
    case LEFT:
      return isLeft = b;

    case +'D':
    case RIGHT:
      return isRight = b;

    default:
      return b;
    }
  }

  public void checkColission() {
    int headingHeight = 40; //has to be added to brick.y because I pushes all bricks lower

    for (int i=0; i < level.rows; i++) { 
      for (int j=0; j < level.cols; j++) {

        if (level.bricks[i][j].bValue == 'E') {
          if (pxPos + pWidth > level.bricks[i][j].x &&
            pxPos < level.bricks[i][j].x + 100 &&
            pyPos + pHeight > level.bricks[i][j].y + headingHeight &&
            pyPos < level.bricks[i][j].y + 54 + headingHeight && check == false) {

            println("death by - ENEMY");
            level.bricks[i][j].bValue = '0';
            lives --;
          }
        } else if (level.bricks[i][j].bValue == '1') {
          if (pxPos + pWidth > level.bricks[i][j].x &&
            pxPos < level.bricks[i][j].x + 100 &&
            pyPos + pHeight > level.bricks[i][j].y + headingHeight &&
            pyPos < level.bricks[i][j].y + 54 + headingHeight) {

            println("death by - BRICK");
            level.bricks[i][j].bValue = '0';
            lives --;
          }
        }
      }
    }
  }
}
class Score{
  //variables
  int score = 0;
  float sxPos, syPos;
  
  //constructor
  Score(){
    
  }
  
  //methods
  public void display(float xPos, float yPos){
    sxPos = xPos;
    syPos = yPos;
    textSize(20);
    fill(255);
    text("Score: " + score,sxPos,syPos);
  }


}
  public void settings() {  size(720, 456); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "BestGameInTownGIT" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
