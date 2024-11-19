import processing.core.PImage;

public class Gem {
    int x, y, size, value;
    PImage image;

    public Gem(int x, int y, int s, PImage i, int v){
        this.x = x;
        this.y = y;
        image = i;
        size = s;
        value = v;
    }
}
