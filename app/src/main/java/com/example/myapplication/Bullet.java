package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bullet {
    private float x;
    private float y;
    private float deltaY;
    private Context context;

    public Bullet(float x, float y, Context context) {
        this.x = x;
        this.y = y;
        this.context = context;
    }

    public void draw(Canvas c) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        c.drawCircle(x, y, 40, paint);
        y += deltaY;
    }

    public void setDeltaY(float deltaY) {
        this.deltaY = deltaY;
    }

    public float getY() {
        return y ;
    }

    public boolean collidesWith(Player player) {
        return player.getX() < x && x < player.getX() + player.getWidth() && player.getY() < y && y < player.getY() + player.getHeight();
    }
}
