package org.example;
import java.util.*;
// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void print(Drone[] drones, int[] target){
        //Display paths of drowns
        for(Drone d : drones){
            System.out.println(d.name +"path is :");
            //int i=0
            for(int i=0;d.x!=target[0] || d.y!=target[1];i++){
                d.moveTowards(target[0],target[1]);
                System.out.print(" ("+d.x+" , "+d.y+") ");
            }
            System.out.println("");
        }
    }
    public static void main(String[] args) {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the rows : ");
        int rows = sc.nextInt();
        System.out.println("Enter the columns : ");
        int columns = sc.nextInt();
        Drone[] drones={
                new Drone("D1",1,3),
                new Drone("D2",7,8),
                new Drone("D3",1,3),
                new Drone("D4",5,6)
        };
        for(Drone d : drones){
            d.x=Math.max(0,Math.min(d.x,rows-1));
            d.y=Math.max(0,Math.min(d.y,columns-1));
        }
        int[] target = {20,20};
        print(drones,target);
    }
}