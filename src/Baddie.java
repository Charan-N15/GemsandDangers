import processing.core.PImage;

public class Baddie {
    int x, y, size, damage, countX, countY;
    PImage image;

    public Baddie(int x, int y, int s, PImage i, int d){
        this.x = x;
        this.y = y;
        size = s;
        image = i;
        damage = d;

        //moving
        countX = (int)(Math.random() * 200);
        countY = (int)(Math.random() * 400);
    }

    public void move(){

        //movement
        if(countX < 100){
            countX++;
            this.x++;
        }else if(countY < 200){
            countY++;
            this.y++;
        }else if(countX < 200){
            countX++;
            this.x--;
        }else if(countY < 400){
            countY++;
            this.y--;
        }else{
            //reset
            countX = 0;
            countY = 0;
        }
    }
    public void reset(){

    }
}
