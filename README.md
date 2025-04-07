# Programming of Functional Applications

This repository contains projects developed for the "Programming of Functional Applications" course during the fourth semester at my university. The assignments are organized into directories labeled `Lab_XX`, where `XX` represents the lab number.

## Projects Overview

Below is a brief overview of the projects:

- **Lab_01 to Lab_10**: Each folder contains a distinct project developed as part of the coursework. Initially, projects were implemented using Java Swing, and later transitioned to JavaFX for enhanced functionality and user interfaces.

## Technologies Used

- **Java**: Primary programming language for all projects.
- **Swing**: Utilized in early projects for building graphical user interfaces.
- **JavaFX**: Adopted in later projects to create more sophisticated and modern user interfaces.

## Getting Started

To explore or run any of the projects:

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/Frigzer/Programming-of-functional-applications.git
   ```
   
2. **Navigate to the Desired Lab Directory**:

   ```bash
   cd Programming-of-functional-applications/Lab_XX
   ```

3. **Build and Run**:

   - Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).
   - Ensure the necessary libraries (Swing or JavaFX) are configured.
   - Build and run the project as per the IDE's guidelines.

## Lab_01: Introduction to Java

**Description**:  
This initial lab focuses on familiarizing with Java basics. The task involves creating a simple console application where the user selects a geometric shape by entering a number. Depending on the chosen shape, the program prompts for the necessary dimensions (e.g., side lengths) and then calculates and displays the area and perimeter.

---

### 💡 Features

- Console-based user interface
- Shape selection via numeric input
- Area and perimeter calculation for:
  - Square
  - Rectangle
  - Triangle
  - Circle
  - Prism (3D object)
- Uses object-oriented principles (abstract class, interface)

---

### 📁 Project Structure

```
📦Lab_01/
 ┣ 📂.idea/
 ┃ ┣ 📜.gitignore
 ┃ ┣ 📜misc.xml
 ┃ ┣ 📜modules.xml
 ┃ ┗ 📜workspace.xml
 ┣ 📂out/
 ┃ ┗ 📂production/
 ┃   ┗ 📂Lab_01/
 ┃     ┣ 🧮Circle.class
 ┃     ┣ 🧮Figure.class
 ┃     ┣ 🧮Main.class
 ┃     ┣ 🧮Printable.class
 ┃     ┣ 🧮Prism.class
 ┃     ┣ 🧮Square.class
 ┃     ┗ 🧮Triangle.class
 ┣ 📂src/
 ┃ ┣ 📜Circle.java
 ┃ ┣ 📜Figure.java
 ┃ ┣ 📜Main.java
 ┃ ┣ 📜Printable.java
 ┃ ┣ 📜Prism.java
 ┃ ┣ 📜Square.java
 ┃ ┗ 📜Triangle.java
 ┣ 📜.gitignore
 ┣ 📜Lab_01.iml

   
```

---

### 📸 Screenshot

![Demo](assets/lab_01_demo.png)

---

## Lab_02: Car Showroom System (Console Application)

**Description**:  
In this lab, the goal was to create the foundation for a car showroom management system using Java.  
The application runs in the terminal and allows users to simulate adding, removing, searching, and managing vehicles within multiple car showrooms.

The lab introduces the use of collections, comparators, enums, and more advanced OOP concepts like containers and data management logic.

---

### 💡 Features

- Multiple car showrooms (`CarShowroomContainer`)
- Adding and removing vehicles
- Searching vehicles by exact or partial name
- Counting vehicles by condition (`NEW`, `USED`)
- Sorting by name or amount available
- Finding the most available vehicle
- Detecting and removing empty showrooms

---

### 📁 Project Structure

```
📦Lab_02/
 ┣ 📂.idea/
 ┃ ┣ 📜.gitignore
 ┃ ┣ 📜misc.xml
 ┃ ┣ 📜modules.xml
 ┃ ┗ 📜workspace.xml
 ┣ 📂out/
 ┃ ┗ 📂production/
 ┃   ┗ 📂Lab_02/
 ┃     ┣ 🧮CarShowroom$AmountComparator.class
 ┃     ┣ 🧮CarShowroom.class
 ┃     ┣ 🧮CarShowroomContainer.class
 ┃     ┣ 🧮ItemCondition.class
 ┃     ┣ 🧮Main.class
 ┃     ┗ 🧮Vehicle.class
 ┣ 📂src/
 ┃ ┣ 📜CarShowroom.java
 ┃ ┣ 📜CarShowroomContainer.java
 ┃ ┣ 📜ItemCondition.java
 ┃ ┣ 📜Main.java
 ┃ ┗ 📜Vehicle.java
 ┣ 📜.gitignore
 ┣ 📜Lab_02.iml
 ```

### 📸 Screenshot

![Showroom demo](assets/lab_02_demo.png)

### 📦 Class Overview

---

#### 🏷️ `ItemCondition` (enum)

Defines possible conditions for a vehicle.

**Values**:
- `NEW` – the vehicle is brand new
- `USED` – the vehicle has been previously owned
- `DAMAGED` – the vehicle is not in full working order

> This enum is used by `Vehicle` and referenced in filtering and counting operations throughout the system.

---

#### 🚗 `Vehicle`

Represents a single car model stored in a showroom.  
This class implements the `Comparable` interface and can be sorted by brand name.

**Fields**:
- `brand` – brand of the vehicle (e.g., BMW, Toyota)
- `model` – specific model (e.g., M8, Yaris)
- `condition` – vehicle condition, defined by `ItemCondition` enum (`NEW`, `USED`)
- `price` – price in local currency
- `yearOfProduction` – production year
- `mileage` – current mileage (in km)
- `engineCapacity` – engine displacement (in cm³)
- `quantity` – number of available units (default: 1)

**Methods**:
- `print()` – prints all vehicle details to the console
- `compareTo(Vehicle o)` – compares vehicles by brand (alphabetical order)
- `getCondition()` – returns the vehicle's condition
- `getAmount()` – returns the current quantity available

---

#### 🏢 `CarShowroom`

Represents a single car showroom that holds a collection of vehicles.  
Provides methods for managing, searching, and sorting vehicles. Handles capacity limits and vehicle quantities.

**Fields**:
- `showroomName` – the name of the showroom (e.g., "Auto Com")
- `cars` – list of vehicles stored in the showroom
- `maxCapacity` – maximum number of unique vehicle entries allowed

**Constructor**:
- `CarShowroom(String showroomName, int maxCapacity)` – initializes the showroom with a name and capacity

**Methods**:

- `addProduct(Vehicle vehicle)`  
  Adds a vehicle to the showroom. If the vehicle already exists, increments its quantity. Rejects addition if the showroom is full.

- `getProduct(Vehicle vehicle)`  
  Retrieves one unit of a vehicle (decrements quantity). Removes it if quantity reaches zero.

- `removeProduct(Vehicle vehicle)`  
  Removes the vehicle from the showroom regardless of quantity.

- `search(String name)`  
  Finds a vehicle by full brand and model name (exact match).

- `searchPartial(String name)`  
  Returns a list of vehicles whose brand or model contains the given substring.

- `countByCondition(ItemCondition condition)`  
  Counts how many vehicles in the showroom have a specific condition (`NEW` or `USED`).

- `summary()`  
  Prints a summary of all vehicles currently in the showroom.

- `sortByName()`  
  Sorts the vehicle list alphabetically by brand name.

- `sortByAmount()`  
  Sorts the vehicle list by descending quantity using a custom comparator.

- `max()`  
  Returns the vehicle with the highest quantity in the showroom.

- `isEmpty()`  
  Checks whether the showroom contains any vehicles.

- `getFillPercentage()`  
  Returns how full the showroom is, as a percentage of its maximum capacity.

**Inner Class**:
- `AmountComparator`  
  Custom comparator that compares two vehicles by quantity (descending).

---

#### 🗃️ `CarShowroomContainer`

Acts as a manager for multiple car showrooms.  
Internally uses a `HashMap` to map showroom names to `CarShowroom` objects.

**Fields**:
- `carShowrooms` – a map of all car showrooms, keyed by name

**Methods**:

- `addCenter(String name, int maxCapacity)`  
  Adds a new showroom with the given name and capacity.

- `removeCenter(String name)`  
  Removes a showroom by name.

- `findEmpty()`  
  Returns a list of all empty showrooms and prints their names to the console.

- `summary()`  
  Prints the name and fill percentage of every showroom in the system.

- `getShowroom(String name)`  
  Returns the showroom associated with the given name.

---

## Lab_03: Unit Testing and Sorting Algorithms

**Description**:  
This lab focuses on implementing and testing various classic sorting algorithms using the **Strategy design pattern**.  
The sorting strategies are encapsulated as separate classes, allowing them to be easily swapped during runtime.  
Each algorithm is tested against three data sets: pessimistic (reversed), optimistic (already sorted), and expected (random).

---

### 📂 Package: `sorting`

#### 🔄 `SortingStrategy` (interface) 
Defines a common interface for all sorting algorithms:
```java
void sort(int[] arr);
```
📥 `BubbleSort`, `InsertionSort`, `SelectionSort`, `QuickSort`, `MergeSort`

Each class implements `SortingStrategy` and provides its own algorithm.

- `BubbleSort`: Basic, inefficient, optimized with early exit
- `InsertionSort`: Shifts elements into position
- `SelectionSort`: Finds the smallest element each pass
- `QuickSort`: Divide-and-conquer using partitioning
- `MergeSort`: Recursive, stable merge-based sorting

⚙️ `SortingContext`

A context class for executing sorting strategies.
Measures execution time and allows dynamic switching of the algorithm via setStrategy().

```java
SortingContext context = new SortingContext(new BubbleSort());
context.executeStrategy(array);
```

Throws `EmptyArrayException` if the array is empty.

🧪 `SortingTest`

Test suite that runs all sorting strategies on:

- Pessimistic data (reverse sorted)
- Optimistic data (already sorted)
- Expected data (random)

Prints sorting time (in milliseconds) for each case.

---

### ✅ Key Concepts Used

- Strategy Pattern
- Performance testing via `System.nanoTime()`
- Copying arrays for fair comparisons
- Custom exceptions for validation

### 📁 Sorting Package Structure

```
📂sorting/
 ┣ 📜BubbleSort.java
 ┣ 📜InsertionSort.java
 ┣ 📜SelectionSort.java
 ┣ 📜QuickSort.java
 ┣ 📜MergeSort.java
 ┣ 📜SortingStrategy.java
 ┣ 📜SortingContext.java
 ┗ 📜SortingTest.java
```

---

### 📂 Package: `median`

This module implements an efficient solution for finding the **median of two sorted arrays**.  
It includes input validation, custom exception handling, and a test class demonstrating different scenarios.

#### 📊 `MedianCalculator`

Calculates the median of two sorted arrays using **binary search partitioning** (optimized O(log(min(n, m))) time complexity).

**Key method**:
- `double findMedian(int[] num1, int[] num2)`  
  - Validates that both arrays are sorted  
  - Uses partitioning to find the median in logarithmic time  
  - Throws `ArrayNotSortedException` if input arrays are unsorted

**Helper method**:
- `isSorted(int[])` – checks whether an array is sorted in ascending order

> Implements the **"Median of Two Sorted Arrays"** algorithm used in competitive programming and interviews.

---

#### 🧪 `MedianTest`

- Demonstrates the usage of `MedianCalculator` with example arrays  
- Prints results of the median operation  
- Handles and displays exceptions for unsorted input

Example output:

```bash
[1, 3, 6, 7] + [3, 4, 5] -> 4.0
[1, 2, 6] + [3, 4, 5, 9] -> 4.0
```

---

### 📁 Median Package Structure

```
📂median/
 ┣ 📜MedianCalculator.java
 ┣ 📜MedianTest.java
```

---

### 📂 Package: `matrix`

This module implements a transformation of a 2D matrix into a **spiral-ordered list**.  
It also includes input validation and test cases to showcase the spiral output for different matrix sizes.

#### 🌀 `SpiralMatrix`

Contains logic to traverse a matrix in **spiral order**, starting from the top-left corner and moving inward.

**Key method**:
- `List<Integer> makeSpiral(int[][] matrix)`  
  - Returns the matrix values in a spiral traversal order  
  - Throws `InvalidMatrixException` if the input is empty or malformed  
  - Supports rectangular and square matrices

> Traversal order is: left column → bottom row → right column (in reverse) → top row (in reverse), repeated.

---

#### 🧪 `MatrixTest`

Demonstrates spiral conversion of two example matrices:

- A tall 6×3 matrix  
- A square 4×4 matrix

Prints the original matrix and resulting spiral list.  
Catches and displays exceptions for invalid input.

---

### 📁 Matrix Package Structure

```
📂matrix/
 ┣ 📜SpiralMatrix.java
 ┣ 📜MatrixTest.java
```