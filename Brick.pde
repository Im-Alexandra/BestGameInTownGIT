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
  
  boolean isColliding(float px, float py, int playerWidth, int playerHeight){
    if((x < px+playerWidth) && (x+w > px) && (y+h > py) && (y < py+playerHeight)){
    return true;
    }
    return false;
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