class Score{
  //variables
  int score = 0;
  float sxPos, syPos;
  
  //constructor
  Score(){
    
  }
  
  //methods
  void display(float xPos, float yPos){
    sxPos = xPos;
    syPos = yPos;
    textSize(20);
    fill(255);
    text("Score: " + score,sxPos,syPos);
  }


}