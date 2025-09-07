package com.example.RealWorldElevatorSystem.controller;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.RealWorldElevatorSystem.model.Direction;

@Component
public class InternalButtonDispatcher {
	List<ElevatorController> elevatorControllers;

    public InternalButtonDispatcher(List<ElevatorController> elevatorControllers) {
        this.elevatorControllers = elevatorControllers;
    }
    
	public void submitInternalRequest(Integer liftId, Integer floor) {
		
		for(ElevatorController ec:elevatorControllers) {
			if(ec.getElevator().getLiftId().equals(liftId)) {
				Direction direction;
				if (ec.getElevator().getCurrentFloor()>floor) {
					direction=Direction.DOWN;
				}else {
					direction=Direction.UP;
				}
				ec.submitInternalRequest(floor, direction);
				break;
			}
		}
	}
}
