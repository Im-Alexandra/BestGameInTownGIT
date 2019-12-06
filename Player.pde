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

  void display() {
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

  void jump() {
    //fySpeed=-6;
  }

  void fall() {
    //fySpeed+=0.2;
  }

  void checkColission() {
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

  void move() {
    // movement from here - http://studio.processingtogether.com/sp/pad/export/ro.91tcpPtI9LrXp
    final int r = pWidth/2;
    pxPos = constrain(pxPos + pVelocity*(int(isRight) - int(isLeft)), 0, width - pWidth);
    pyPos = constrain(pyPos + pVelocity*(int(isDown)  - int(isUp)), 52, height - (pHeight+52));

    if (isRight) {
      //println("moving right");
      currentFrameVertical = 0;
    } else if (isLeft) {
      //println("moving left");
      currentFrameVertical = 1;
    }
  }

  boolean setMove(final int k, final boolean b) {
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
