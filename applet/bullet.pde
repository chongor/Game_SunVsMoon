class bullet {
  int bullettype, b = 255, h = 0, shooty, damage, bsize, lane;
  float speed, shootx, startx;
  boolean alive = true;
  boolean hlow = false;

  bullet(int bullettypein, int lanein, float xin,int damagein) {
    bullettype = bullettypein;
    lane = lanein;
    shootx = xin;
    startx = xin;
    damage = damagein;
    if(bullettypein == 1) {
      speed = 5;
      bsize = 30;
    }
    else if(bullettypein == 2) {
      speed = 10;
      bsize = 50;
    }
    else if(bullettypein ==3) {
      speed = 2;
    }
  }




  void display()
  {
    switch(lane)
    {
    case 1: 
      shooty = 90;  
      break;
    case 2: 
      shooty = 270; 
      break;
    case 3: 
      shooty = 450; 
      break;
    }
    if(alive == false) {
      shootx = 0; 
      return;
    }
    noStroke();
    if(bullettype == 1) {
      fill(255, b, 0);
      rectMode(CORNER);
      rect(startx - 50, shooty - bsize / 2, shootx, bsize); 
    }
    else if(bullettype == 2) {
      fill(255, b, 0); 
      ellipse(shootx, 80 * sin(shootx/70) + shooty, 10, 10);
      ellipse(shootx, -80 * sin(shootx/70) + shooty, 10, 10);
      ellipse(shootx, 80  * cos(shootx/70) + shooty, 10, 10);
      ellipse(shootx, -80 * cos(shootx/70) + shooty, 10, 10);
      ellipse(shootx, 10 * sin(shootx/50) + shooty - 30, 30, 30);
      ellipse(shootx, -10 * sin(shootx/50) + shooty + 30, 30, 30);
      if(mememode) {image(sprsoccer, shootx, shooty, 75, 75);}
      else{ellipse(shootx, shooty, 75, 75);}
      stroke(0);
    }
    else if(bullettype == 3) {
      colorMode(HSB, 100);
      fill(h,100,100);
      ellipse(shootx, 50 * sin(shootx/70) + shooty, 10, 10);
      ellipse(shootx, -50 * sin(shootx/70) + shooty, 10, 10);
      ellipse(shootx, 50 * cos(shootx/70) + shooty, 10, 10);
      ellipse(shootx, -50 * cos(shootx/70) + shooty, 10, 10);
      ellipse(shootx, shooty, 10, 10);
      ellipse(shootx, shooty - 50, 10, 10);
      ellipse(shootx, shooty + 50, 10 ,10);
      colorMode(RGB, 255);
    }
    else if(bullettype == 4) {
      
      fill(255,b);
      rectMode(CORNER);
      rect(0, 0, width, height);
      if(b == 0) {wtfboom.pause();wtfboom.rewind();}
    }
    stroke(0);
    shootx += speed;
    b-=1;
    if(h == 0) {hlow = false;}
    if(h == 100) {hlow = true;}
    if(hlow == false) {h++;}
    else{h--;}
  }
}


