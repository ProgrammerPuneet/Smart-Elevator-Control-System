package com.example.RealWorldElevatorSystem.controller;

import java.util.Comparator;
import java.util.TreeSet;

import com.example.RealWorldElevatorSystem.model.Direction;
import com.example.RealWorldElevatorSystem.model.Elevator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElevatorController implements Runnable {
    Elevator elevator;
    TreeSet<Integer> upRequests;     // sorted ascending
    TreeSet<Integer> downRequests;   // sorted descending

    private volatile boolean running = true;

    public ElevatorController(Elevator elevator) {
        this.elevator = elevator;
        this.upRequests = new TreeSet<>();
        this.downRequests = new TreeSet<>(Comparator.reverseOrder());
    }

    public void submitInternalRequest(int floor, Direction direction) {
        addRequest(floor, direction);	
    }

    public void submitExternalRequest(int floor, Direction direction) {
        addRequest(floor, direction);
    }

    private synchronized void addRequest(int floor, Direction direction) {
        if (direction == Direction.UP) {
            upRequests.add(floor);
        } else {
            downRequests.add(floor);
        }
        System.out.println("Request added: Floor " + floor + " Direction " + direction);
        System.out.println("Current UP requests: " + upRequests);
        System.out.println("Current DOWN requests: " + downRequests);
        notifyAll();
    }

    @Override
    public void run() {
        while (running) {
            try {
                processRequests();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    private synchronized void processRequests() throws InterruptedException {
        while (upRequests.isEmpty() && downRequests.isEmpty()) {
            wait();
        }

        // SCAN Algorithm Implementation
        if (elevator.getDirection() == null) {
            // Choose initial direction based on closest request
            if (!upRequests.isEmpty() && !downRequests.isEmpty()) {
                int closestUp = findClosestRequest(upRequests);
                int closestDown = findClosestRequest(downRequests);
                
                int upDistance = Math.abs(closestUp - elevator.getCurrentFloor());
                int downDistance = Math.abs(closestDown - elevator.getCurrentFloor());
                
                elevator.setDirection(upDistance <= downDistance ? Direction.UP : Direction.DOWN);
            } else if (!upRequests.isEmpty()) {
                elevator.setDirection(Direction.UP);
            } else {
                elevator.setDirection(Direction.DOWN);
            }
        }

        // Process requests in current direction first (SCAN algorithm)
        if (elevator.getDirection() == Direction.UP) {
            processUpRequests();
            if (!downRequests.isEmpty()) {
                elevator.setDirection(Direction.DOWN);
                processDownRequests();
            } else {
                elevator.setDirection(null);
            }
        } else if (elevator.getDirection() == Direction.DOWN) {
            processDownRequests();
            if (!upRequests.isEmpty()) {
                elevator.setDirection(Direction.UP);
                processUpRequests();
            } else {
                elevator.setDirection(null);
            }
        }
    }

    private void processUpRequests() {
        while (!upRequests.isEmpty()) {
            // Find next floor at or above current floor
            Integer target = upRequests.ceiling(elevator.getCurrentFloor());
            if (target == null) {
                // No more floors above, get the lowest remaining floor
                target = upRequests.first();
            }
            
            elevator.move(target, target > elevator.getCurrentFloor() ? Direction.UP : Direction.DOWN);
            upRequests.remove(target);
        }
    }

    private void processDownRequests() {
        while (!downRequests.isEmpty()) {
            // Find next floor at or below current floor
            Integer target = downRequests.ceiling(elevator.getCurrentFloor());
            if (target == null) {
                // No more floors below, get the highest remaining floor
                target = downRequests.first();
            }
            
            elevator.move(target, target < elevator.getCurrentFloor() ? Direction.DOWN : Direction.UP);
            downRequests.remove(target);
        }
    }

    private int findClosestRequest(TreeSet<Integer> requests) {
        int closest = requests.first();
        int minDistance = Math.abs(closest - elevator.getCurrentFloor());
        
        for (Integer floor : requests) {
            int distance = Math.abs(floor - elevator.getCurrentFloor());
            if (distance < minDistance) {
                minDistance = distance;
                closest = floor;
            }
        }
        return closest;
    }
}