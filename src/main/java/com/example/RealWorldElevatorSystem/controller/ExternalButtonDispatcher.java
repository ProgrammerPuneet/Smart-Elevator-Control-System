package com.example.RealWorldElevatorSystem.controller;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.RealWorldElevatorSystem.model.Direction;

@Component
public class ExternalButtonDispatcher {
	List<ElevatorController> elevatorControllers;
	
    public ExternalButtonDispatcher(List<ElevatorController> elevatorControllers) {
        this.elevatorControllers = elevatorControllers;
    }
    
	public void submitExternalRequest(Integer floor,Direction direction) {
		Integer liftId=1;
		for(ElevatorController ec:elevatorControllers) {
			if(ec.getElevator().getLiftId().equals(liftId)) {
				ec.submitExternalRequest(floor, direction);
				break;
			}
		}
	}
}
