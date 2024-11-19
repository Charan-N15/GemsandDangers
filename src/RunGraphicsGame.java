import processing.core.PApplet;
import processing.core.PImage;
import java.util.ArrayList;

public class RunGraphicsGame extends PApplet {
    int level, levelHolder, counter, timer, number, requiredGems, gems;
    PImage player, gemImage, baddieImage, doorOpened, doorClosed;
    Player p1;
    ArrayList<Gem> gemList;
    ArrayList<Baddie> baddieList;

    public void settings(){
        size(600,600);
    }

    public void setup(){
        timer = 100;
        counter = 60;
        requiredGems = 1;
        levelHolder = 1;
        number = 1;
        level = 1;
        gemList = new ArrayList<>();
        baddieList = new ArrayList<>();
        gemImage = loadImage("gem.png");
        gemImage.resize(20,20);
        baddieImage = loadImage("baddie.png");
        baddieImage.resize(50,50);
        doorOpened = loadImage("dooropen2.png");
        doorClosed = loadImage("doorclose.png");
        doorOpened.resize(40,60);
        doorClosed.resize(40,60);
        player = loadImage("playericon.png");
        player.resize(30,30);
        p1 = new Player(10,570,20,player,10);

        for(int i = 0; i <= number; i++){
            gemList.add(new Gem((int)(Math.random() * 540 + 50), (int)(Math.random() * 540 + 50), 10, gemImage, 3));
            baddieList.add(new Baddie((int)(Math.random() * 500 + 100), (int)(Math.random() * 500), 30, baddieImage, 5));
        }
    }

    public void draw(){
        background(0);
        if(p1.lives > 0 ) {
            if (timer > 0) {

                frameRate(60);
                if (levelUp()) {
                    for (int i = 0; i <= number; i++) {
                        gemList.add(new Gem((int) (Math.random() * 540 + 50), (int) (Math.random() * 540 + 50), 10, gemImage, 3));
                        baddieList.add(new Baddie((int) (Math.random() * 500 + 100), (int) (Math.random() * 500), 30, baddieImage, 5));
                    }
                }

                //player
                image(player, p1.x, p1.y);

                //timer
                counter--;
                if (counter == 0) {
                    timer--;
                }
                if (counter == 0) {
                    counter = 60;
                }

                //text on screen
                fill(255);
                textSize(18);
                text("Gems: " + gems + " / " + requiredGems, 10, 20);
                text("Level: " + levelHolder, 10, 40);
                text("Lives: " + p1.lives, 10, 60);
                text("Timer: " + timer, 10, 80);

                //door
                image(doorClosed, 560, 0);
                if (p1.x > 560 && p1.y < 30) {
                    if (gems >= requiredGems) {
                        delay(500);
                        level++;
                        p1.reset();
                        p1.lives = 10;
                    }
                }
                if (p1.x > 540 && p1.y < 80 && requiredGems <= gems) {
                    image(doorOpened, 560, 0);
                }

                //spawn gems and baddies
                for (Gem g : gemList) {
                    image(g.image, g.x, g.y);
                    if (p1.collide(g.x, g.y, g.size)) {
                        p1.lives = p1.lives + g.value;
                        g.x = (int) (Math.random() * 520);
                        g.y = (int) (Math.random() * 520 + 60);
                        gems++;
                    }
                }
                for (Baddie b : baddieList) {
                    image(b.image, b.x, b.y);
                    if (p1.collide(b.x, b.y, b.size)) {
                        p1.reset();
                        p1.lives = p1.lives - b.damage;
                        if(gems != 0){
                            gems--;
                        }
                    }
                    b.move();
                }

                //player movement
                if (keyPressed) {
                    if (key == 'w') {
                        if (p1.y > 0) {
                            p1.y = p1.y - 2;
                        }
                    }
                    if (key == 'a') {
                        if (p1.x > 0) {
                            p1.x = p1.x - 2;
                        }
                    }
                    if (key == 's') {
                        if ((p1.y + p1.size) < height) {
                            p1.y = p1.y + 2;
                        }
                    }
                    if (key == 'd') {
                        if ((p1.x + p1.size) < width) {
                            p1.x = p1.x + 2;
                        }
                    }
                }
            }else{
                //end screen & play again
                resetAll();
                textSize(30);
                fill(255,0,0);
                text("You ran out of time!",145,375);
            }
        }else{
            //end screen & play again
            resetAll();
            textSize(30);
            fill(255,0,0);
            text("You ran out of lives!",150,375);

        }
    }

    public boolean levelUp(){
        if (level > levelHolder){
            if (number < 62) {//Cap For Baddies\\
                number = number + level;
                levelHolder = level;
            }else{
                number = 62;
            }
            //Maximum Requirements\\
            if (timer > 25 && level != 1){ //The least the timer will get to will be 25 seconds\\
                timer = 100;
                timer = timer - (5 * level);
            }
            if (requiredGems < 25){//The most the amount of gems required will be 25\\
                if(level != 1){
                    requiredGems = requiredGems + 3;
                }
            }else{
                requiredGems = 25;
            }
            gems = 0;

            return true;
        }
        return false;
    }

    public void resetAll(){
        //Died\\
        fill(color(255,0,0));
        textSize(75);
        text("GAME OVER", 85, 200);
        fill(255,0,0);
        rect(140,460,300,60);

        //Play Again\\
        if(mouseX > 160 && mouseX < 160 + 300){
            if(mouseY > 460 && mouseY < 460 + 60){
                //Restarting Level One\\
                fill(139,0,0);
                rect(140,460,300,60);
                if(mousePressed){
                    baddieList.clear();
                    gemList.clear();
                    p1.reset();
                    levelHolder = 0;
                    number = 0;
                    p1.lives = 10;
                    level = 1;
                    timer = 100;
                    requiredGems = 1;
                    gems = 0;
                }
            }
        }
        textSize(30);
        fill(0,0,128);
        text("Click to play again",160,500);
    }

    public static void main(String[] args) {
        PApplet.main(new String[] {"RunGraphicsGame"});
    }
}