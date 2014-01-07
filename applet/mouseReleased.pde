

void mouseReleased(){

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










