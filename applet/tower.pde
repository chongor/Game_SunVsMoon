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




  void display ()
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




