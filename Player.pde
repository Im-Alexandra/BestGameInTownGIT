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

  void displayLives(int x, int y) {
    livesXPos = x;
    livesYPos = y;
    textSize(20);
    fill(255);
    text("Lives: " + lives, livesXPos, livesYPos);
  }

  void move() {
    // movement from here - http://studio.processingtogether.com/sp/pad/export/ro.91tcpPtI9LrXp
    final int r = pWidth/2;
    pxPos = constrain(pxPos + pVelocity*(int(isRight) - int(isLeft)), 0, width - pWidth);
    pyPos = constrain(pyPos + pVelocity*(int(isDown)  - int(isUp)), 52+40, height - (pHeight+52));

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

  void checkColission() {
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
