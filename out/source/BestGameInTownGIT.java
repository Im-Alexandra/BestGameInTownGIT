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
int levelToLoad = 0; 
Player p;

public void setup() { 
  
  level = new Level(levelToLoad);
  p = new Player();
}

public void draw() { 
  background(230, 219, 219,1); 
  level.display();
  p.display();
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
        image(currentImage, x, y, w, h);
      }else if(bValue == '2'){
        fill(255, 0, 0);
        rect(x, y, w, h);
      }
    }
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


    public void display() { 
      for (int i = 0; i < cols; i++) { 
        for(int j=0; j < rows; j++){
        bricks[i][j].display();
        }
      }
    }
    
    public void move() {
      for (int i = 0; i < cols; i++) { //goes through levelString
      for(int j = 0; j < rows; j++) { //goes through rows 
        bricks[i][j].x -= 2;
      }
    }
    
    }
 }
//Player class

class Player {

  //variables
  float pxPos = 50;
  float pyPos = height/2 - 10;
  float pVelocity = 3;
  boolean isLeft, isRight, isUp, isDown;
  int pWidth = 100, pHeight = 65;
  PImage spritesheet;
  PImage currentImage;
  int currentFrameHorizontal = 0;
  int lastFrameHorizontal = 9;
  int currentFrameVertical = 0; //0 - right, 1 - left
  int iTicks = millis();
  int durationOneFrame = 100; //in milliseconds

  //constructor
  Player() {
  }

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

  public void jump() {
    //fySpeed=-6;
  }

  public void fall() {
    //fySpeed+=0.2;
  }

  public void checkColission() {
    //with the floor
    /*if(fyPos>height-24){
     playing = false;
     dead = true;
     tint(204, 0, 0);
     println("you dead :P");
     }
     
     //with the walls
     for(int i = 0; i<3; i++){
     if((fxPos+20 < w[i].wxPos+20 
     && fxPos+20 > w[i].wxPos-20)
     && (fyPos < w[i].openingY-w[i].openingHeight-20
     || fyPos > w[i].openingY+w[i].openingHeight+20 )){
     
     tint(204, 0, 0);
     e.display(fxPos, fyPos);
     playing=false; 
     dead = true;
     println("you dead :P");
     }
     }*/
  }

  public void move() {
    // movement from here - http://studio.processingtogether.com/sp/pad/export/ro.91tcpPtI9LrXp
    final int r = pWidth/2;
    pxPos = constrain(pxPos + pVelocity*(PApplet.parseInt(isRight) - PApplet.parseInt(isLeft)), 0, width - pWidth);
    pyPos = constrain(pyPos + pVelocity*(PApplet.parseInt(isDown)  - PApplet.parseInt(isUp)), 52, height - (pHeight+52));

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
}
  public void settings() {  size(720, 416); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "BestGameInTownGIT" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
