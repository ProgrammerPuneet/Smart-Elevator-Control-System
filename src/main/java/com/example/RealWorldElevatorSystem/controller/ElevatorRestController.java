package com.example.RealWorldElevatorSystem.controller;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.RealWorldElevatorSystem.model.Direction;

@RestController
@RequestMapping("/elevator")
public class ElevatorRestController {
	private final ThreadPoolExecutor executor;

    ExternalButtonDispatcher externalButtonDispatcher;
    InternalButtonDispatcher internalButtonDispatcher;

    public ElevatorRestController(ThreadPoolExecutor executor,
            ExternalButtonDispatcher externalButtonDispatcher,
            InternalButtonDispatcher internalButtonDispatcher) {
		this.executor = executor;
		this.externalButtonDispatcher = externalButtonDispatcher;
		this.internalButtonDispatcher = internalButtonDispatcher;
	}

    // Internal button press (inside lift)
    @GetMapping("/internal")
    public ResponseEntity<String> internal(@RequestParam int floor) {
    	
        executor.submit(() -> internalButtonDispatcher.submitInternalRequest(1,floor));
        return ResponseEntity.accepted().body("Internal request: move to floor " + floor);
    }

    // External button press (outside lift)
    @GetMapping("/external")
    public ResponseEntity<String> external(@RequestParam int floor, @RequestParam Direction direction) {
        executor.submit(() -> externalButtonDispatcher.submitExternalRequest(floor, direction));
        return ResponseEntity.accepted()
                             .body("External request: floor " + floor + " going " + direction);
    }
}