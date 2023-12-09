package org.example;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void print(Monster x) {
        System.out.println("Eye color : " + x.getEyeColour());
        System.out.println("Hair color : " + x.getHairColour());
        System.out.println("Size : " + x.getSize());
        System.out.println("Agression level : " + x.getAgressionLevel());
        System.out.println("Color: " + x.getColour());
        System.out.println("Strength : " + x.getStrength());
        System.out.println("Weakness : " + x.getWeakness());
    }
    public static Monster createBaby(Monster p1, Monster p2) throws IllegalAccessException {

        Field[] fields = Monster.class.getDeclaredFields();
        Monster baby = new Monster();
        for(Field f : fields){
            f.setAccessible(true);
            Object x1=f.get(getRandom(p1,p2));
            f.set(baby,x1);
        }
        return baby;
    }

    public static Object getRandom(Monster p1, Monster p2){
        Random r = new Random();
        int x=r.nextInt(2);
        if(x==1)
            return p1;
        else
            return p2;
    }
    public static void main(String[] args) throws IllegalAccessException {
        ArrayList<Monster> monster = new ArrayList<>();
        ArrayList<Monster> babies = new ArrayList<>();
        int choice;
        Scanner sc = new Scanner(System.in);
        String value = null;
        do {
            System.out.println("Do you want to create a monster");
            System.out.println("1.Yes\n2.No\n");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    Monster m = new Monster();
                    System.out.println("Enter Wings:");
                    sc.next();
                    m.setWings(sc.nextLine());
                    System.out.println("Enter Eye color:");
                    m.setEyeColour(sc.nextLine());
                    System.out.println("Enter size:");
                    m.setSize(sc.nextLine());
                    System.out.println("Enter Hair color:");
                    m.setHairColour(sc.nextLine());
                    System.out.println("Enter agression level:");
                    m.setAgressionLevel(sc.nextLine());
                    System.out.println("Enter Color:");
                    m.setColour(sc.nextLine());
                    System.out.println("Enter Strength:");
                    m.setStrength(sc.nextLine());
                    System.out.println("Enter Weakness:");
                    m.setWeakness(sc.nextLine());
                    monster.add(m);

                    break;
                case 2:
                    System.out.println("Okay the monsters you created are:");
                    for(int i=0;i< monster.size();i++) {
                        System.out.println("Monster - "+(i+1));
                        print(monster.get(i));
                        System.out.println();
                    }
                    break;

            }
        } while (choice == 1);
        for (int i = 0; i < monster.size(); i++) {
            for (int j = i + 1; j < monster.size(); j++) {
                babies.add(createBaby(monster.get(i), monster.get(j)));
            }
        }
        if(monster.size()>1) {
            System.out.println("Okay the baby monsters that created are:");
            for(int i=0;i<babies.size();i++) {
                System.out.println("Baby monster - "+(i+1));
                print(babies.get(i));
                System.out.println();
            }
        }
    }
}