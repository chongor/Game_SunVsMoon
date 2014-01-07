
class minion {
  int health, damage, sprite, lane, x = 1000, y, loot, r = 150, eatrate;
  float speed;
  boolean eating = false;
  boolean slow = false;

  minion(int lanein, int healthin, int damagein, int eatratein, int speedin, int spritein, int lootin ) {
    health = healthin;
    speed = speedin;
    damage = damagein;
    sprite = spritein;
    lane = lanein;
    eatrate = eatratein;
    loot = lootin;
  }






  void display(PFont fontin)
  {
    PFont font;
    font = fontin;
    textFont(font);
    fill(255);
    rectMode(CENTER);

    switch(lane) {
    case 1:  
      y = 90;
      break;
    case 2: 
      y = 270;
      break;
    case 3: 
      y = 450;
      break;
    default: 
      return;
    }
    if(slow == true) {tint(0,0,255);}
    switch(loot)
    {
      case 15:
      image(sprmoonrock, x, y, r, r); break;
      case 35:
      image(sprcomet, x, y, r + 100, r + 10); break;
      case 80:
      image(sprasteroid, x, y, r + 20, r+ 20); break;
      default: return;
    }
    
    noTint();
    if(eating == false) {
      x -= speed;
    }
    else if((frameCount % eatrate == 0) && (eating == true))
    {
      fill(255);
      textFont(font2);
      text("NOM", x - r, y);
      textFont(font);
      fill(0);
    }

  }
}

