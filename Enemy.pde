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

  void display() {
    spritesheet = loadImage("enemy.png");
    currentImage = spritesheet.get(0 + (currentFrameHorizontal * eWidth), 0 , eWidth, eHeight);
    image(currentImage, exPos, eyPos, eWidth, eHeight);
  }
}