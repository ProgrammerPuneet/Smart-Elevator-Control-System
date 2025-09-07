package com.example.RealWorldElevatorSystem.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Elevator {
    Integer liftId;
    Display display;
    int currentFloor;
    Direction direction;
    Status status;
    Door door;
    
    public Elevator(int liftId, int startFloor, Direction startDirection) {
        this.liftId = liftId;
        this.currentFloor = startFloor;
        this.direction = startDirection;
    }
    
    public void move(int destination, Direction moveDirection) {
        if (destination == currentFloor) {
            System.out.println("Lift " + liftId + " already at floor " + destination);
            return;
        }
        
        System.out.println("Lift " + liftId + " moving " + moveDirection + " from floor " + currentFloor + " to " + destination);
        
        if (moveDirection == Direction.UP) {
            for (int i = currentFloor + 1; i <= destination; i++) {
                currentFloor = i;
                System.out.println("Lift " + liftId + " at floor " + currentFloor);
                try { 
                    Thread.sleep(1000); 
                } catch (InterruptedException e) { 
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        } else {
            for (int i = currentFloor - 1; i >= destination; i--) {
                currentFloor = i;
                System.out.println("Lift " + liftId + " at floor " + currentFloor);
                try { 
                    Thread.sleep(1000); 
                } catch (InterruptedException e) { 
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
        
        System.out.println("Lift " + liftId + " reached floor " + destination);
        System.out.println("--- DOORS OPENING ---");
        try { 
            Thread.sleep(2000); // Door open time
        } catch (InterruptedException e) { 
            Thread.currentThread().interrupt();
        }
        System.out.println("--- DOORS CLOSING ---");
        System.out.println();
    }
}