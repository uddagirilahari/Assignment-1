package org.example;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WayneEnterprise{
    private int orderDelivered=0;
    private int orderCancelled=0;
    private int shipMinCargo= 50;
    private int shipMaxCargo = 300;
    private int shipMaintainence = 5;
    private int earnings=0;
    private Ship[] ships;
    private Object orderLock = new Object();
    private Object shipLock = new Object();

    public WayneEnterprise() {
        this.ships = new Ship[]{new Ship("Gotham"), new Ship("Atlanta"),
                                new Ship("Gotham"), new Ship("Atlanta"),
                                new Ship("Gotham")};
    }
    public Order placeOrder(){
        Random r = new Random();
        int weight = r.nextInt(41) + 10;
        String dest = (r.nextBoolean())?"Gotham":"Atlanta";
        return new Order(weight, dest);
    }
    public boolean processOrder(Order order){
        synchronized (orderLock){
            if(order.cargoWeight + shipMinCargo > shipMaxCargo){
                return false; //cant process order
            }
            for(Ship s : ships){
                synchronized(shipLock){
                    if(!s.inMaintainence && s.location.equals(order.destination)) {
                        if (s.cargo + order.cargoWeight <= shipMaxCargo) {
                            s.cargo += order.cargoWeight;
                            s.trips+=1;
                            return true;//order success
                        }
                    }
                }
            }
            return false; // no ship available
        }
    }
    public void runConsumer(int cId){
        while(earnings<1000000){
            Order order = placeOrder();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (processOrder(order)) {
                synchronized (orderLock){
                    orderDelivered++;
                    earnings += 1000;
                    //System.out.println("Order Delivered : " + order.cargoWeight + " "+ order.destination);
                }
            }
            else{
                synchronized (orderLock){
                    orderCancelled++;
                    earnings -= 250;
                    //System.out.println("Order Cancelled : " + order.cargoWeight + " "+ order.destination);
                }
            }

        }
    }
    public void runShip(int sId){
        while(earnings<1000000){
            synchronized (shipLock){
                Ship s= ships[sId];
                if(s.inMaintainence){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    s.inMaintainence=false;
                    continue;
                }
                if(s.cargo >= shipMinCargo){
                    s.cargo -= shipMinCargo;
                    s.location = (s.location.equals("Gotham"))?"Atlanta":"Gotham";
                    s.trips+=1;
                }
                if(s.trips>shipMaintainence){
                    s.inMaintainence=true;
                    s.trips = 0;
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void stimulate(){
        ExecutorService executor = Executors.newFixedThreadPool(12);
        for(int i=0;i<7;i++){
            final int cId =i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    runConsumer(cId);
                }
            });
        }
        for(int i=0;i<5;i++){
            final int sId = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                        runShip(sId);
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("No of orders Delivered : " + orderDelivered);
        System.out.println("No of orders Cancelled : " + orderCancelled);
        System.out.println("Total Earnings : " + earnings);
        executor.shutdown();
    }
}
