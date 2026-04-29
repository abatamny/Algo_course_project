# Clinic Manager System

A university course project in **Data Structures and Algorithms** that implements an efficient clinic management system using advanced tree-based data structures.

## 🎓 Project Grade: **100/100** ✅

This project successfully passed all test cases and received a perfect score!

## Project Overview

This project demonstrates the practical application of **2-3 Trees** (balanced search trees) to manage a clinic's operations, including:
- Doctor registration and departure
- Patient queue management
- Workload distribution tracking
- Efficient range queries on doctor loads

## Key Features

- **Doctor Management**: Register/remove doctors from the clinic
- **Patient Queue Management**: Manage patient waiting queues with O(log n) operations
- **Dynamic Workload Tracking**: Monitor doctor workloads in real-time
- **Range Queries**: Efficiently find statistics about doctors within a load range
- **Early Departure Handling**: Allow patients to leave early from queues

## Architecture

### Core Components

| Class | Purpose |
|-------|---------|
| `ClinicManager` | Main manager class orchestrating all operations |
| `TwoThreeTree<T>` | Balanced 2-3 tree implementation for efficient searching and insertion |
| `Doctor` | Represents a doctor with a patient queue |
| `Patient` | Represents a patient assigned to a doctor |
| `Queue<T>` | Custom doubly-linked queue implementation |
| `InNode` | Internal nodes of the 2-3 tree |
| `Leaf<T>` | Leaf nodes containing data objects |

### Data Structures Used

- **2-3 Tree**: Self-balancing tree with O(log n) operations for insert, delete, and search
- **Doubly-Linked Queue**: For managing patient waiting lines with O(1) removal from arbitrary positions
- **Nodeable Interface**: Generic interface for tree-storable objects

## Time Complexity

| Operation | Complexity |
|-----------|-----------|
| Doctor Enter/Leave | O(log D) |
| Patient Enter/Leave | O(log D + log P) |
| Get Number of Patients | O(log D) |
| Range Query (Doctors in Load Range) | O(log D) |
| Average Load in Range | O(log D) |

Where **D** = number of doctors, **P** = number of patients

## API Methods

### ClinicManager

```java
// Doctor operations
void doctorEnter(String doctorId)
void doctorLeave(String doctorId)

// Patient operations
void patientEnter(String doctorId, String patientId)
String nextPatientLeave(String doctorId)
void patientLeaveEarly(String patientId)

// Query operations
int numPatients(String doctorId)
String nextPatient(String doctorId)
String waitingForDoctor(String patientId)
int numDoctorsWithLoadInRange(int low, int high)
int averageLoadWithinRange(int low, int high)
```

## Usage Example

```java
ClinicManager clinic = new ClinicManager();

// Register doctors
clinic.doctorEnter("D1");
clinic.doctorEnter("D2");

// Patients enter the clinic
clinic.patientEnter("D1", "P1");
clinic.patientEnter("D1", "P2");
clinic.patientEnter("D2", "P3");

// Check workload
System.out.println(clinic.numPatients("D1")); // Output: 2

// Query doctors by load
int doctorsWithLoad = clinic.numDoctorsWithLoadInRange(1, 2); // All doctors
int avgLoad = clinic.averageLoadWithinRange(0, 10);

// Patient sees doctor
String nextPatient = clinic.nextPatient("D1"); // "P1"
clinic.nextPatientLeave("D1");
```

## Project Structure

```
src/
├── ClinicManager.java      # Main manager class
├── TwoThreeTree.java       # 2-3 tree implementation
├── Doctor.java             # Doctor entity
├── Patient.java            # Patient entity
├── Queue.java              # Custom queue implementation
├── Node.java               # Abstract node class
├── InNode.java             # Internal tree node
├── Leaf.java               # Leaf tree node
├── Nodeable.java           # Interface for tree objects
├── NodeableInteger.java    # Integer wrapper for 2-3 tree
└── Main.java               # Test cases and demonstrations
```

## Testing

The project includes comprehensive test cases demonstrating:
- Basic operations (doctor/patient entry and departure)
- Workload queries and range operations
- Edge cases and error handling
- Performance under various load scenarios

All test cases passed successfully! Run the test cases via `Main.java` to verify all functionality.

## Learning Objectives

This project reinforces key concepts:
- **Balanced Trees**: 2-3 tree rotations and balancing
- **Generic Programming**: Java generics and interfaces
- **Algorithm Analysis**: Time complexity evaluation
- **Data Structure Design**: Choosing appropriate structures for specific problems
- **Queue Management**: FIFO operations with removal from arbitrary positions

## Requirements

- Java 9 or higher
- No external dependencies

## Compilation & Execution

```bash
javac src/*.java
java -cp src Main
```

## Author

**Course**: Data Structures and Algorithms  
**Grade**: 100/100 ✅  
**Status**: All Tests Passed ✅

---

*This project demonstrates practical application of advanced data structures in real-world scenarios like clinic management systems.*