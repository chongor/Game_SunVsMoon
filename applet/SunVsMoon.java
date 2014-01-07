import processing.core.*; 
import processing.xml.*; 

import ddf.minim.*; 
import ddf.minim.signals.*; 
import ddf.minim.analysis.*; 
import ddf.minim.effects.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class SunVsMoon extends PApplet {




// Import useful things




Minim minim;
AudioPlayer bmusic;
AudioPlayer wtfboom;
AudioPlayer loseclip;

ArrayList pew;
ArrayList sunlight;
ArrayList moonrock;
PFont font;
PFont font2;
PFont font3;
PImage bground;
PImage sprsunlight;
PImage sprflare;
PImage sprwind;
PImage sprboom;
PImage sprsell;
PImage sprmoonrock;
PImage sprcomet;
PImage sprasteroid;
PImage sprsoccer;

//gameover boolean
boolean gameover = false;
boolean mememode = false;
boolean wavefreeze = false;
boolean debugmode = false;

// menu boolean and integer
boolean menu = true;
int numMenu = 0;

//game boolean
boolean game = false;


// columns and lanes of field
int numLanes = 3;
int numColumns = 6;

int spawnrate = 120;

//field points in boolean array [columns][lanes]
boolean[][] field = new boolean[numLanes][numColumns];

//buttons in the ui for towers
//numTowers = how many tower boxes, the 7th one is sell
int numButton = 7;
boolean [] button = new boolean[numButton];

//placeMode, a mode to place towers, includes sell button
boolean[] placeMode = new boolean [numButton];

//size of the battlefield
int fieldHeight = 540;
int fieldWidth = 1080;

//size of the User Interface (ui) box
int uiHeight = 180;
int tDisplayWidth = 630;

// money $$
int money = 200;

//level and wave #
int wave = 1;
int userhealth = 10;
int killcount = 0;


public void setup()
{
  menu = true;

  size(fieldWidth,fieldHeight + uiHeight);
  smooth();

  //start with no towers, space is false
  for (int i = 0; i < numLanes; i++)
  {
    for (int j = 0; j < numColumns; j++) {
      field[i][j] =  false;
    }
  }

  //tower boxes are false until clicked

  for (int i = 0; i < numButton; i++)
  {
    button[i] = false;
  }

  for (int i = 0; i < (numButton); i++)
  {
    placeMode[i] =  false;
  }  


  font = loadFont( "Arial-Black-16.vlw");
  textFont(font);

  font2 = loadFont("Arial-Black-48.vlw");
  font3 = loadFont("Arial-Black-100.vlw");
  //textFont(font2);

  minim = new Minim(this);


  bground = loadImage("Field Large.png");
  if(mememode == true)
  {
    bmusic = minim.loadFile("Scorponok REMIXED.mp3");
    bmusic.play();
    wtfboom = minim.loadFile("ORIGINAL WTF BOOM.mp3");
    wtfboom.loop();
    wtfboom.pause();

    loseclip = minim.loadFile("Hamster Look.mp3");
    sprsunlight = loadImage("pacman.png");
    sprsell = loadImage("recycle_symbol_icon.png");
    sprflare = loadImage("Sarah Tirith.png");
    sprwind = loadImage("pedobear.png");
    sprboom = loadImage("shoop.png");
    sprmoonrock = loadImage("ReHaoMoonRock copy.png");
    sprcomet = loadImage("SarahComet copy.png");
    sprasteroid = loadImage("JosueAsteroid copy.png");
    sprsoccer = loadImage("SoccerBall.png");
  }
  else
  {
    bmusic = minim.loadFile("Scorponok REMIXED.mp3");
    bmusic.play();
    wtfboom = minim.loadFile("ORIGINAL WTF BOOM.mp3");
    wtfboom.loop();
    wtfboom.pause();

    loseclip = minim.loadFile("Hamster Look.mp3");
    sprsunlight = loadImage("SunLight.png");
    sprsell = loadImage("recycle_symbol_icon_3.jpg");
    sprflare = loadImage("SolarFlare.png");
    sprwind = loadImage("SolarWind.png");
    sprboom = loadImage("SuperNova.png");
    sprmoonrock = loadImage("MoonRock copy.png");
    sprcomet = loadImage("Comet copy.png");
    sprasteroid = loadImage("Asteroid.png");
  }

  imageMode(CENTER);

  pew = new ArrayList();
  sunlight = new ArrayList();
  moonrock = new ArrayList();

}

public void draw()
{

  if(wavefreeze == true) {
    while(keyPressed == false) {
      noLoop();
    }
    wavefreeze = false;
    loop();
  }

  if (keyPressed)
  {
    if (key == ' ')
    {
      menu = false;

    }
  }

  if (menu == true)
  {
    background (0);
    text("Menu" , 180, 180);
    text("Press Space to Play" , 360, 360);
  }
  if(menu == false)
  {



    for(int i = 0; i < moonrock.size(); i++)
    {
      minion m = (minion)moonrock.get(i);
      if(m.x <= 0)
      {
        userhealth -= 1;
        if(userhealth == 0) {
          gameover = true;
        }
        break;
      }
    }
    if(gameover == true)
    {
      loseclip.play();
      bmusic.pause();
      background(255);
      fill(0);
      noLoop();
      textFont(font2);
      text("FFFF    A      IIIIIII  L" , width/6, height/4 +48);
      text("F         AA        I      L" , width/6, height/4 +48*2);
      text("FFFF  AAA       I      L" , width/6, height/4 +48*3);
      text("F       A     A     I      L" , width/6, height/4 +48*4);
      text("F      A       A IIIIIII  LLLLLL" , width/6, height/4 +48*5);
      //font = loadFont("Arial-Black-16.vlw");
      //textFont(font);
      textFont(font);
      text("You failed to Nobulate (NOBNI) back to the manufacturing media (A:)", width/8, height/4 + 48*6);
      return;
    }



    /*fill(255,30);
     rectMode(CORNER);
     noStroke();
     rect(0,0,width,height);
     stroke(0);*/

    imageMode(CORNER);
    tint(255,30);
    image(bground, 0, 0);
    imageMode(CENTER);
    noTint();


    //background(255);


    //if statement for whether a box is true or false/position of text
    if(debugmode == true) { 
      for (int i = 0; i < numLanes; i++)
      {
        for (int j = 0; j < numColumns; j++) {
          fill(255);
          if(field[i][j] == true) {
            text("TRUE" , fieldWidth/numColumns * j + 5, fieldHeight/numLanes * i + 20);
          }
          if(field[i][j] == false) {
            text("FALSE" , fieldWidth/numColumns * j + 5, fieldHeight/numLanes * i + 20);
          }
        }
      }
    }

    //grid of field



    for(int i = fieldWidth/numColumns; i <= fieldWidth; i += fieldWidth/numColumns)
    {
      stroke(255);
      line(i, 0, i, fieldHeight);
    }
    for(int i = fieldHeight/numLanes; i <= fieldHeight; i += fieldHeight/numLanes)
    {
      stroke(255);
      line(0, i, fieldWidth, i);
    }

    //if statement for whether a tower button is true or false/position of text
    // lines of text for false

    image(sprsunlight, 45, 630, 70,60);
    image(sprflare, 135, 630, 80,80);
    image(sprwind, 225, 630, 80, 80);
    image(sprboom,  315, 630, 80, 80);
    fill(0);
    text("50", 35, 680);
    text("100", 115, 680);
    text("200", 200, 680);
    text("1000", 290, 680);
    fill(255);

    for(int i = 0; i < numButton; i++)
    {
      fill(0);
      if(debugmode == true)
      {

        if(button[i] == false) {
          text("FALSE" , i * tDisplayWidth/numButton + 5, fieldHeight + 16);
        }
        if(button[i] == true) {
          text("TRUE" , i * tDisplayWidth/numButton + 5, fieldHeight + 16);
        }
      }
      if(button[6] == false) {
        text("Sell", 570, 630);
      }
      if(button[6] == true) {
        fill(255,0,0);
        text("Sell", 570,630);
      }
    }

    //tower button grid
    //variable for incrementing lines
    int tDisplayLines = 1;

    for(int i = tDisplayWidth/numButton * tDisplayLines; i <= tDisplayWidth ; i += (tDisplayWidth)/numButton)
    {
      line (i,fieldHeight,i,uiHeight+fieldHeight);
    }
    tDisplayLines++;
    //line to seperate field from player's interface
    line(0,540,1080,540);

    // sell button

    if(button[6] == true)
    {
      if(mousePressed = true)
      {
        image(sprsell, mouseX, mouseY, 80,80);
      }
    }

    // to select a tower to build on field, tower is a rect
    // does not include the sell button\
    tint(255, 128);

    if(button[0] == true)
    {

      image(sprsunlight,mouseX,mouseY,50,50);
    }
    else if(button[1] == true)
    {
      image(sprflare, mouseX, mouseY, 80,80);
    }
    else if(button[2] == true)
    {
      image(sprwind, mouseX, mouseY, 80, 80);
    }
    else if(button[3] == true)
    {
      image(sprboom, mouseX, mouseY, 80, 80);
    }
    noTint();

    // display system for moneyz
    fill(255);
    line(900, 540, 900, 720);
    text("Hydrogen \n\n" + "      " + money, 760, 580);

    //health
    line(720,540,720,720);
    text("Health\n" + userhealth, 645, 580);

    //display system
    //text("Level", 930, 560);
    text("Wave " + wave, 930, 615);
    fill(0);
    text("Kills: " + killcount, 930, 670);
    fill(255);






    //draw crosshair (Why? I don't know)


    /*fill(255);
     stroke(0);
     strokeWeight(4);
     ellipse(mouseX,mouseY,80,80);
     line(mouseX-50, mouseY, mouseX + 50, mouseY);
     line(mouseX, mouseY - 50, mouseX, mouseY +50);*/



    //Bullets hurt minions

    for(int i = 0; i < pew.size(); i++)
    {
      bullet b = (bullet)pew.get(i);


      for(int j =  0; j < moonrock.size(); j++)
      {
        if(b.shootx >= width) {
          pew.remove(i);
          break;
        }
        minion m = (minion)moonrock.get(j);
        if(m.health <= 0)
        {
          money += m.loot;
          moonrock.remove(j);
          killcount++;
        }
        if((b.lane == m.lane) && (b.shootx >= m.x))
        {
          if(b.bullettype == 3) {
            m.speed = 0.5f; 
            m.eatrate = 70; 
            m.slow = true;
          }
          b.alive = false;
          m.health -= b.damage;
        }



      }

      b.display();
    }

    //Towers shoot bullets if minion is in lane
    for(int i = 0; i < sunlight.size(); i++)
    {
      boolean shoot = false;
      tower t = (tower)sunlight.get(i);
      if(t.alive == true) {
        t.display();
      }
      for(int j = 0; j < moonrock.size(); j++)
      {
        minion m = (minion)moonrock.get(j);
        if(m.lane == t.lane) {
          shoot = true; 
          break;
        }
      }
      if(t.cost == 1000) {
        pew.add(new bullet(4, t.lane, t.barrelx, t.damage));
        field[t.lane - 1][t.column -1] = false;
        sunlight.remove(i);
      }
      if(shoot == true)
      {

        if(frameCount % t.rate == 0)
        {
          if(t.cost == 50) {
            pew.add(new bullet(1, t.lane, t.barrelx,t.damage));
          }
          else if(t.cost == 100) {
            pew.add(new bullet(2, t.lane, t.barrelx, t.damage));
          }
          else if(t.cost == 200) {
            pew.add(new bullet(3, t.lane, t.barrelx, t.damage));
          }

        }
      }
    }



    //Minions eating towers

    for(int i = 0; i < moonrock.size(); i++)
    {
      minion m = (minion)moonrock.get(i);
      m.eating = false;
      if(m.x <= 0)
      {
        gameover = true;
        break;
      }

      for(int j = 0; j < sunlight.size(); j++)
      {
        tower t = (tower)sunlight.get(j);
        if((t.lane == m.lane) && (t.alive == false))
        {
          m.eating = false;
          field[t.lane - 1][t.column - 1] = false;
          sunlight.remove(j);
          if(m.eating == false) {
            println("I'M NOT EATING");
          }

        }
        else if((t.lane == m.lane) && (m.x <= t.barrelx))
        {
          m.eating = true;
          if(frameCount % m.eatrate == 0)
          {
            t.health -= m.damage;
          }
        }

      }
    }







    //Draw the minions

    for(int i = 0; i < moonrock.size(); i++)
    {
      minion m = (minion)moonrock.get(i);
      m.display(font);
    }

    if((millis() % 15000 >= 0) && (millis() % 15000 <= 10)) {
      fill(255);
      textFont(font3); 
      println("NEXT WAVE");

      if(wave < 6) {
        spawnrate -= 20; 
        wave++;
        text("WAVE " + wave, width/2 - 400, fieldHeight/2); 
        textFont(font2);
        text("Press any key to continue", width/2 - 400, fieldHeight/2 + 100);
        wavefreeze = true;
      }
      textFont(font);
    }

    else if(frameCount % spawnrate == 0) {
      moonrock.add(new minion(round(random(0.5f,3.5f)),50,10 ,40 , 2, 1, 15));
    }
    if(frameCount % round(spawnrate * 1.5f) == 0) {
      if(wave >= 3) {
        moonrock.add(new minion(round(random(0.5f,3.5f)),50,5 ,30 , 5, 1, 35));
      }
    }
    if(frameCount % round(spawnrate * 2.5f) == 0) {
      if(wave >= 5) {
        moonrock.add(new minion(round(random(0.5f,3.5f)),100, 30 ,30 , 1, 1, 80));
      }
    }



    //delay(100);
  }
}

/*void keyPressed()
{
  if(key == 'q') {
    money += 1000;
  }
}*/

/*void branche(float x, float y, float s, float a){
 strokeWeight(s*0.04);
 a+=radians(random(-7,7));
 float newx = x+cos(a)*s;
 float newy = y+sin(a)*s;
 line(x,y,newx,newy);
 if(s>3){
 branche(newx, newy, s*(random(0.55,0.70)), a-radians(random(17,12)));
 branche(newx, newy, s*(random(0.55,0.70)), a+radians(random(17,12)));
 }
 }
 
 void mouseDragged(){
 stroke(random(130,170),random(220,255),random(130));
 branche(mouseX, height, height-mouseY, radians(270));
 }
 */











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




  public void display()
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






  public void display(PFont fontin)
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
      fill(0);
    }

  }
}



public void mouseReleased(){

  // makes buttons false after a tower is created
  for (int n = 0; n < 6; n++)
  {
    if(placeMode[n] == true)
    {
      // allow mouse to click to make space true
      for (int i = 0; i < numLanes; i++)
      {

        for (int j = 0; j < numColumns; j++)
        {
          if((mouseX < fieldWidth/numColumns * (j + 1)) && 
            (mouseY <  fieldHeight/numLanes * (i +1)) &&
            (mouseX > fieldWidth/numColumns * j ) &&
            (mouseY > fieldHeight/numLanes * i))
          {            

            if(field[i][j] == false)
            {
              if (button[0] == true){
                if (money < 50)
                {
                  break;
                }

                sunlight.add(new tower(i+1, j+1, 30, 10, 35,  1, 50, 25));
                money -= 50;

              }
              else if(button[1] == true)
              {
                if (money < 100)
                {
                  break;
                }
                sunlight.add(new tower(i+1, j+1, 50, 25, 100, 1, 100, 50));
                money -= 100;
              }
              else if(button[2] == true)
              {
                if(money < 200)
                {
                  break;
                }
                sunlight.add(new tower(i+1, j+1, 150, 5, 70, 1, 200, 100));
                money -= 200;
              }
              else if(button[3] == true)
              {
                if (money < 1000)
                {
                  break;
                }
                for(int k = 0; k < moonrock.size(); k++)
                {
                  minion m = (minion)moonrock.get(k);
                  money += m.loot;
                  m.health = 0;
                }
                sunlight.add(new tower(i+1, j+1, 1, 100, 5, 1, 1000, 500));
                money -= 1000;
                if(mememode == true) {wtfboom.play();}
              }
            }
            field[i][j] = true;

          }
        }
      }
      // reset buttons to false and placeMode
      placeMode[n] = false;
      for(int i = 0; i < 6; i++)
      {
        button[i] = false;
      }
    }
  }


  // allow mouse to click to make Tower Button space true
  for (int i = 0; i < numButton; i++)
  {
    {
      if((mouseX < tDisplayWidth/numButton * (i+1)) && 
        (mouseY < fieldHeight+uiHeight) &&
        (mouseX > tDisplayWidth/numButton * i) &&
        (mouseY > fieldHeight))
      {
        button[i] = true;
        placeMode[i] = true;
      }
    }
  }







  // create a sell button
  if((mouseX < 630) &&
    (mouseY < 720) &&
    (mouseX > 540) &&
    (mouseY > 540))
  {
    button[6] = true;
    placeMode[6] = true;
  }

  // still don't get why this works, but it allows sell button to return to false
  if(placeMode[6] == true)
  {
    // allow mouse to click to make field space false
    for (int i = 0; i < numLanes; i++)
    {
      for (int j = 0; j < numColumns; j++)
      {
        if((mouseX < fieldWidth/numColumns * (j + 1)) && 
          (mouseY <  fieldHeight/numLanes * (i +1)) &&
          (mouseX > fieldWidth/numColumns * j ) &&
          (mouseY > fieldHeight/numLanes * i))
        {
          // reset sell button to false
          if (field[i][j] == true)
          {
            field[i][j] = false;
            for(int a = 0; a < sunlight.size(); a++)
            {
              tower t = (tower)sunlight.get(a);
              money += t.sell;
              if((t.lane - 1 == i) && (t.column - 1 == j)) {
                sunlight.remove(a);
              }
            }
            button[6] = false;
            placeMode[6] = false;
            println("BAM!");
          }
          else if (field[i][j] == false)
          {
            button[6] = false;
            placeMode[6] = false;
          }
        }
      }
    }

    for (int i = 0; i < numButton; i++)
    {
      {
        if((mouseX < tDisplayWidth/numButton * (i+1)) && 
          (mouseY < fieldHeight+uiHeight) &&
          (mouseX > tDisplayWidth/numButton * i) &&
          (mouseY > fieldHeight))
        {
          button[i] = true;
          placeMode[i] = true;
        }
        if (button [0] == true ||
          button[1] == true ||
          button[2] == true ||
          button[3] == true ||
          button[4] == true ||
          button[5] == true)
        {
          button[6] = false;
          placeMode[6] = false;
        }
      }
    }
  }
}










class tower {
  int lane, column, health, damage, range, rate, sprite, cost, sell, barrelx, barrely;
  boolean alive = true;
  tower(int lanein, int columnin, int healthin, int damagein, int ratein, int spritein, int costin, int sellin)
  {
    health = healthin;
    rate = ratein;
    damage = damagein;
    sprite = spritein;
    lane = lanein;
    column = columnin;
    cost = costin;
    sell = sellin;

  }




  public void display ()
  {
    if(health <= 0) {
      alive = false;
    }
    fill(255);
    switch(lane) {
    case 1: 
      barrely = 90; 
      break;
    case 2:
      barrely = 270;
      break;
    case 3:
      barrely = 450;
      break;
    default: 
      break;
    }
    switch(column) {
    case 1: 
      barrelx = 135; 
      break;
    case 2: 
      barrelx = 135 + 180; 
      break;
    case 3: 
      barrelx = 135 + 180 * 2; 
      break;
    case 4: 
      barrelx = 135 + 180 * 3; 
      break;
    case 5: 
      barrelx = 135 + 180 * 4; 
      break;
    case 6: 
      barrelx = 135 + 180 * 5; 
      break;
    }
    noTint();
    switch(cost)
    {
      case 50:
      if(mememode == true) {image(sprsunlight, 90 + 180 * (column - 1), 90 + 180 * (lane - 1), 100 ,100);}
      else {image(sprsunlight, 90 + 180 * (column - 1), 100 + 180 * (lane - 1), 180 ,149);} break;
      case 100:
      image(sprflare, 90 + 180 * (column - 1), 90 + 180 * (lane - 1), 150 ,150); break;
      case 200:
      image(sprwind, 90 + 180 * (column - 1), 90 + 180 * (lane - 1), 150 ,150); break;
      case 1000:
      image(sprboom, 90 + 180 * (column - 1), 90 + 180 * (lane - 1), 381 ,575); break;
      default:
      return;
    }

    /*
    ellipse(90 + 180 * (column - 1),90 + 180 * (lane - 1),50,50);
     rectMode(CENTER);
     rect(barrelx-20,barrely,40,10);
     
     triangle(90 + 180 * (column - 1),115 + 180 * (lane - 1),60 + 180 * (column - 1),140 + 180 * (lane - 1),120 + 180 * (column - 1),140 + 180 * (lane - 1));
     */
  }
}





  static public void main(String args[]) {
    PApplet.main(new String[] { "--bgcolor=#F0F0F0", "SunVsMoon" });
  }
}
