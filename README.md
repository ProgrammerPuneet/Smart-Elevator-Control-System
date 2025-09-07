# 🏢 Smart Elevator Control System

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0+-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/Build-Passing-success.svg)]()

A sophisticated, real-world elevator control system built with **Spring Boot** that implements the **SCAN algorithm** for optimal elevator scheduling. This system simulates realistic elevator behavior with proper request handling, intelligent movement optimization, and comprehensive REST API integration.

## ✨ Key Features

### 🎯 **Intelligent Scheduling Algorithm**
- **SCAN Algorithm Implementation**: Optimizes elevator movement by serving requests in the current direction before changing direction
- **Dual Request Queues**: Separate TreeSet-based queues for UP and DOWN requests with automatic sorting
- **Smart Direction Selection**: Automatically chooses the most efficient initial direction based on request proximity

### 🔄 **Realistic Elevator Simulation**
- **Floor-by-Floor Movement**: Authentic elevator travel simulation with configurable delays
- **Door Operations**: Simulated door opening/closing with realistic timing
- **Real-time Status Updates**: Live console output showing elevator position and operations

### 🌐 **RESTful API Interface**
- **Internal Requests**: `/elevator/internal?floor=5` - Simulate button presses inside the elevator
- **External Requests**: `/elevator/external?floor=3&direction=UP` - Simulate hall call buttons
- **Asynchronous Processing**: All requests handled via thread pool for concurrent operation

### 🏗️ **Enterprise-Grade Architecture**
- **Clean Separation of Concerns**: MVC pattern with dedicated controllers, services, and models
- **Thread-Safe Operations**: Synchronized request handling with proper concurrency control
- **Configurable Thread Pool**: Custom thread factory with rejection handling
- **Spring Dependency Injection**: Professional Spring Boot configuration

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Spring Boot 3.0+

### Installation & Running
```bash
# Clone the repository
git clone https://github.com/yourusername/smart-elevator-system.git
cd smart-elevator-system

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📡 API Usage

### Making Requests

**Internal Elevator Requests** (from inside the elevator):
```bash
curl "http://localhost:8080/elevator/internal?floor=5"
curl "http://localhost:8080/elevator/internal?floor=2"
curl "http://localhost:8080/elevator/internal?floor=8"
```

**External Hall Calls** (from building floors):
```bash
curl "http://localhost:8080/elevator/external?floor=3&direction=UP"
curl "http://localhost:8080/elevator/external?floor=10&direction=DOWN"
```

### Example Interaction Sequence
```bash
# Person on floor 2 wants to go up
GET /elevator/external?floor=2&direction=UP

# Inside elevator, someone presses floor 5
GET /elevator/internal?floor=5

# Person on floor 10 wants to go down  
GET /elevator/external?floor=10&direction=DOWN
```

## 🎬 Live Demo Output

```console
Request added: Floor 2 Direction UP
Current UP requests: [2]
Current DOWN requests: []
Lift 1 moving UP from floor 0 to 2
Lift 1 at floor 1
Lift 1 at floor 2
Lift 1 reached floor 2
--- DOORS OPENING ---
--- DOORS CLOSING ---

Request added: Floor 5 Direction UP
Current UP requests: [5]
Current DOWN requests: []
Lift 1 moving UP from floor 2 to 5
Lift 1 at floor 3
Lift 1 at floor 4
Lift 1 at floor 5
Lift 1 reached floor 5
--- DOORS OPENING ---
--- DOORS CLOSING ---
```

## 🏗️ System Architecture

### Core Components

**🎛️ ElevatorController**
- Implements SCAN algorithm for request optimization
- Manages dual-priority queues (UP/DOWN requests)
- Handles real-time elevator movement and status

**🌐 REST Controllers**
- `ElevatorRestController`: HTTP API endpoints
- Asynchronous request processing with thread pools

**📨 Request Dispatchers**
- `InternalButtonDispatcher`: Handles in-elevator requests
- `ExternalButtonDispatcher`: Manages hall call buttons

**🏢 Core Models**
- `Elevator`: Complete elevator state and movement logic
- `Direction`: UP/DOWN enumeration
- `Status`: IDLE/MOVING state management

### SCAN Algorithm Benefits
- **Efficiency**: Minimizes elevator direction changes
- **Fairness**: Serves requests in optimal order
- **Scalability**: Handles multiple concurrent requests
- **Real-world Accuracy**: Mirrors actual elevator control systems

## 🔧 Configuration

The system uses Spring Boot's configuration management:

```java
@Configuration
public class ElevatorConfig {
    // Custom thread pool for elevator operations
    // Configurable elevator instances
    // Request dispatcher setup
}
```

**Configurable Parameters:**
- Thread pool size (core: 2, max: 4)
- Request queue capacity (10 requests)
- Elevator movement delays
- Door operation timing

## 🎯 Technical Highlights

### Concurrency & Thread Safety
- **Synchronized Operations**: All request additions are thread-safe
- **Producer-Consumer Pattern**: Wait/notify mechanism for efficient request processing
- **Custom Thread Factory**: Named threads for better debugging
- **Graceful Shutdown**: Proper resource cleanup on application termination

### Data Structures & Algorithms
- **TreeSet with Custom Comparator**: DOWN requests sorted in reverse order for efficiency
- **SCAN Algorithm**: Industry-standard elevator scheduling
- **Ceiling Operations**: Fast lookup for next target floor

### Spring Boot Integration
- **Dependency Injection**: Clean component wiring
- **Bean Lifecycle Management**: Automatic thread pool shutdown
- **RESTful Architecture**: Standard HTTP status codes and responses

## 🔮 Future Enhancements

- 🏢 **Multi-Elevator Support**: Complete building management system
- 📊 **Performance Analytics**: Request handling metrics and optimization
- 🎨 **Web Dashboard**: Real-time elevator monitoring interface
- 🔧 **Configuration API**: Dynamic system parameter adjustment
- 🚨 **Emergency Handling**: Priority override and safety features
- 📱 **Mobile Integration**: Smartphone elevator calling

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙋‍♂️ Author

Built with ❤️ by [Puneet Tiwari](https://github.com/ProgrammerPuneet)

---

⭐ **Star this repository if you found it helpful!** ⭐
