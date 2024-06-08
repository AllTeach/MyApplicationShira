package com.example.myapplication;

import static com.example.myapplication.AppConstant.canvasHeight;

import android.content.Context;
import android.graphics.Canvas;

public class AI {
    // Add fields for the AI's state here
    ComputerPlayer computerPlayer;

    Context context;


    Player player;

    private int counter = 0;

    private Bullet bullet;

    private boolean isShooting = false;

    public AI(ComputerPlayer computerPlayer, Context c,Player player) {
        // Initialize the AI's state here
        this.computerPlayer = computerPlayer;
        this.context = c;
        this.player = player;
    }

    public void update() {
        // Update the AI's state here
        counter++;
    }

    public void draw(Canvas c) {
        // Draw the AI's state here
      //  computerPlayer.draw(c);
        if(isShooting){
            shoot(c);
            return;
        }

        if(counter% 10 == 0 && !isShooting){
            bullet = new Bullet(player.getX()+ player.getWidth()/2, computerPlayer.getY()+computerPlayer.getHeight(), context);
            bullet.setDeltaY(20);

            isShooting= true;
            shoot(c);
        }
    }

    private void shoot(Canvas c) {
        if(bullet!=null) {

            // Add code to shoot a bullet here
            bullet.draw(c);

            if(bullet.collidesWith(player)){
                player.setLifeSum(player.getLifeSum()-100);
                isShooting = false;
            }

            if(bullet.getY() > player.getY()+player.getHeight()+20){
                isShooting = false;
            }

         //   isShooting = false;
        }
    }
}
