package com.example.RealWorldElevatorSystem.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.RealWorldElevatorSystem.controller.ElevatorController;
import com.example.RealWorldElevatorSystem.controller.ExternalButtonDispatcher;
import com.example.RealWorldElevatorSystem.controller.InternalButtonDispatcher;
import com.example.RealWorldElevatorSystem.model.Elevator;

@Configuration
public class ElevatorConfig {

    @Bean
    public ThreadFactory customThreadFactory() {
        return new ThreadFactory() {
            private final AtomicInteger count = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread th = new Thread(r);
                th.setName("Elevator-Worker-" + count.getAndIncrement());
                th.setPriority(Thread.NORM_PRIORITY);
                th.setDaemon(false);
                return th;
            }
        };
    }

    @Bean
    public RejectedExecutionHandler customRejectedHandler() {
        return (r, executor) ->
                System.err.println("Task rejected: " + r.toString() + 
                                   " | Queue size: " + executor.getQueue().size());
    }

    @Bean(destroyMethod = "shutdown")
    public ThreadPoolExecutor elevatorExecutor(ThreadFactory customThreadFactory,
                                               RejectedExecutionHandler customRejectedHandler) {
        return new ThreadPoolExecutor(
                2,                       // core pool size
                4,                       // max pool size
                1, TimeUnit.HOURS,       // idle thread keep-alive
                new ArrayBlockingQueue<>(10), // bounded queue
                customThreadFactory,
                customRejectedHandler
        );
    }

    // Elevator controller bean
    @Bean
    public List<ElevatorController> elevatorControllers() {
        Elevator elevator = new Elevator(1, 0, null);
        ElevatorController controller = new ElevatorController(elevator);
        
        // Start elevator thread
        new Thread(controller, "Elevator-Controller-1").start();
        
        // Wrap the single controller in a list
        List<ElevatorController> controllers = new ArrayList<>();
        controllers.add(controller);
        return controllers;
    }
    
    @Bean
    public InternalButtonDispatcher internalDispatcher(List<ElevatorController> elevatorControllers) {
        return new InternalButtonDispatcher(elevatorControllers);
    }

    @Bean
    public ExternalButtonDispatcher externalDispatcher(List<ElevatorController> elevatorControllers) {
        return new ExternalButtonDispatcher(elevatorControllers);
    }
    
}
