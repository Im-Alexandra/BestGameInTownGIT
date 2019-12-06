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

  void display() { 
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

  void switchOn(int posX, int posY, int value) { 
    x=posX; 
    y=posY;
    bValue = value;
    isOn=true;
  }

  void switchOff() { 
    isOn=false;
  }
}
