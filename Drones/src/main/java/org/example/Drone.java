package org.example;

public class Drone{
    String name;
    int x;
    int y;

    public Drone(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public void moveTowards(int targetX, int targetY){
        //moving coordinates to one position so that it can reach target
        if(this.x< targetX) {
            this.x++;
        }
        else if(x>targetX) {
            this.x--;
        }
        if(this.y< targetY) {
            this.y++;
        }
        else if(y>targetY){
            this.y--;
        }
    }
}
