import processing.core.PImage;

public class Player {
    int x, y, size, lives;
    int originalX, originalY;
    PImage image;

    public Player(int x, int y, int s, PImage i, int l){
        this.x = x;
        this.y = y;
        size = s;
        image = i;
        lives = l;
        originalX = x;
        originalY = y;
    }

    public void reset(){
        x = originalX;
        y = originalY;
    }

    public boolean collide(int otherX, int otherY, int otherSize){
        if(x + size > otherX && x < otherX + otherSize){
            if(y < otherY + otherSize && y + size > otherY){
                return true;
            }
        }
        return false;
    }

}
