# 🎯 Java Programming Fundamentals

> A comprehensive collection of learning materials and practical implementations for my Java programming journey.

## ⚙️ Development Environment

### Prerequisites
```bash
# Check Java version
java -version

# Check Java compiler version
javac -version
```

### Compilation & Execution Scripts
I've added convenient build scripts to make Java development easier:

#### Using compile.bat
```bash
# Compile a specific file
compile.bat YourFile.java

# Compile all Java files in the project
compile.bat
```

#### Using run.bat
```bash
# Run a specific class (finds and compiles if needed)
run.bat YourClassName

# Can also specify a Java file directly
run.bat path/to/YourFile.java
```

These scripts provide:
- Automatic compilation of Java files to a bin directory
- Smart class detection and execution
- Error handling and reporting
- Easy execution of any Java program in the repository

### Manual Compilation Commands

#### 1. Basic Compilation
```bash
javac <filename>.java
```

#### 2. Running Java Programs
```bash
java <classname>
```

#### 3. Creating JAR Files
```bash
jar cvf <jarname>.jar *.class
```

## 📚 Topics Covered

- 🌟 Core Java Concepts
  - Variables and Data Types
  - Control Structures
  - Object-Oriented Programming
  - Exception Handling

- 🔄 Data Structures
  - Arrays and Collections
  - Lists and Maps
  - Stacks and Queues

- ⚡ Algorithm Implementations
  - Pattern Programs
  - Sorting Algorithms
  - Search Techniques

- 🎯 Programming Exercises
  - Floyd's Triangle Variations
    - Binary Floyd's Triangle
      - [Efficient Implementation](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Q2_Floyd'sTriangle/binary/efficient.java)
      - [Main Program](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Q2_Floyd'sTriangle/binary/main.java)
    - Numeric Floyd's Triangle
      - [Efficient Implementation](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Q2_Floyd'sTriangle/num/efficient.java)
      - [Main Program](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Q2_Floyd'sTriangle/num/main.java)
  - Number Programs
    - [Prime Sum Calculator](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Q3_PrimeSum/main.java)
    - [GCD & LCM Calculator](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Q4_GCD_LCM/main.java)
    - [Decimal to Binary Converter](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Q5_DecimalToBinary/main.java)
    - [Perfect Number Finder](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Q6_PerfectNumList/main.java)
    - [Reverse Alphabets Pattern](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Q7_Rev_Alphabets/main.java)
  - Practice Programs
    - [Command Line Arguments](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Syllabus_Q/SumOFAnyNum.java) - Sum of integers from command line
    - Basic OOP Concepts
      - [StudentInfo](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Practice/StudentInfo.java) - Class and object demo
      - [VehicleInfo](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Practice/VehicleInfo.java) - Inheritance demo
      - [CarInfo](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Practice/CarInfo.java) - Constructor usage

## 🆕 Recent Additions (May 2025)

### ⚡ Practice Examples
I've recently added several practice programs to improve my understanding of Java fundamentals:

1. **Basic Java Concepts**
   - [Variables](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Practice/Variables.java) - Working with different variable types
   - [Operators](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Practice/Operators.java) - Arithmetic operators demo
   - [Functions](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Practice/Functions.java) - Method implementation examples
   - [InputDemo](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Practice/InputDemo.java) - Using Scanner for input
   - [Array Dynamic Input](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Syllabus_Q/ArrayDynamicInput.java) - Handling dynamic array inputs
   - [Factorial](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Syllabus_Q/Factorial.java) - Factorial calculation
   - [2D Array Length](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Syllabus_Q/TwoDArrayLength.java) - Demonstrating 2D array operations

2. **Object-Oriented Programming**
   - [Abstract Classes & Polymorphism](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Practice/ChatGPT/ShapeTest.java) - Shape area calculation
   - [Interfaces](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Practice/ChatGPT/DocumentTest.java) - Printer interface implementation

3. **Interactive Programs**
   - [Calculator](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Practice/ChatGPT/Calculator.java) - Simple arithmetic operations
   - [LargestNumber](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Practice/ChatGPT/LargestNumber.java) - Finding max with multiple input methods
   - [EvenOdd](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Practice/ChatGPT/EveOdd.java) - Number classification
   - [SumOfN](https://github.com/AriajSarkar/Java/blob/main/Lab_Q/Practice/ChatGPT/SumOFn.java) - Sum of first n numbers

4. **Advanced Java Programming**
   - [Area Calculation](https://github.com/AriajSarkar/Java/blob/main/20_Q/Q10_AreaTest.java) - Area calculations for different shapes
   - [Multiple Interface](https://github.com/AriajSarkar/Java/blob/main/20_Q/Q11_MultiInterface.java) - Implementation of multiple interfaces
   - [Default & Static Methods](https://github.com/AriajSarkar/Java/blob/main/20_Q/Q12_DefaultStatic.java) - Interface default and static methods
   - [Drawable Interface](https://github.com/AriajSarkar/Java/blob/main/20_Q/Q13_DrawableTest.java) - Drawing shapes with interfaces
   - [Triangle Validation](https://github.com/AriajSarkar/Java/blob/main/20_Q/Q16_TriangleCheck.java) - Check if three points form a triangle
   - [Custom Exceptions](https://github.com/AriajSarkar/Java/blob/main/20_Q/Q17_CustomExceptionTest.java) - Creating and handling custom exceptions

5. **Data Structure Implementations**
   - [Prime Number Listing](https://github.com/AriajSarkar/Java/blob/main/20_Q/Q1_PrimeInRange.java) - List prime numbers in a given range
   - [Decimal to Octal](https://github.com/AriajSarkar/Java/blob/main/20_Q/Q2_DecimalToOctal.java) - Converting decimal to octal numbers
   - [Array Manipulation](https://github.com/AriajSarkar/Java/blob/main/20_Q/Q3_ArrayFlipEvenOdd.java) - Flipping array elements
   - [Matrix Operations](https://github.com/AriajSarkar/Java/blob/main/20_Q/Q4_SymmetricMatrixCheck.java) - Check for symmetric matrices
   - [Circle Operations](https://github.com/AriajSarkar/Java/blob/main/20_Q/Q5_CircleTest.java) - Operations on circles
   - [Palindrome Check](https://github.com/AriajSarkar/Java/blob/main/20_Q/Q6_PalindromeCheck.java) - Optimized palindrome validation

### 🚀 Mini-Projects

I've started implementing small project-based learning examples:

- **[Student Marks Manager](https://github.com/AriajSarkar/Java/tree/main/Lab_Q/Practice/ChatGPT/Mini-Projects/Student-Marks-Manager)** - A console application that:
  - Takes input for 5 subject marks
  - Displays all marks
  - Calculates total and average marks
  - Finds the highest mark

- **[Java Repository Explorer](https://github.com/AriajSarkar/Java/blob/main/docs/index.html)** - A web-based UI for exploring this repository:
  - Improved styling and visual design
  - Enhanced file structure rendering
  - Better error handling
  - Intuitive navigation

### 🛠️ Code Improvements

- Fixed method signature in SumOfInt program (main → Main)
- Updated .gitignore to exclude personal practice files and bin directory
- Improved code formatting and spelling corrections
- Renamed class from Car to Cars for consistency and clarity
- Added compile.bat and run.bat scripts for Java project management:
  - Automatic compilation of Java files
  - Easy execution of Java programs
  - Smart class detection and path handling
  - Streamlined development workflow

## 🎯 Purpose

This repository serves as my learning playground to:
- Implement Java programming concepts
- Practice algorithmic problem-solving
- Document coding progress
- Build efficient and optimized solutions

## 🗂️ Project Structure

```
Java/
├── Lab_Q/                      # Lab Questions and Solutions
│   ├── Q1_SumOfInt/           # Integer Sum Problems
│   ├── Q2_Floyd'sTriangle/    # Pattern Programs
│   │   ├── binary/           # Binary Pattern Version
│   │   └── num/              # Numeric Pattern Version
│   ├── Q3_PrimeSum/          # Prime Number Sum
│   ├── Q4_GCD_LCM/          # GCD & LCM Calculator
│   ├── Q5_DecimalToBinary/   # Binary Conversion
│   ├── Q6_PerfectNumList/    # Perfect Numbers
│   ├── Q7_Rev_Alphabets/     # Alphabet Patterns
│   ├── Practice/             # Practice Programs
│   │   ├── ChatGPT/          # ChatGPT-assisted examples
│   │   │   └── Mini-Projects/ # Small project implementations
│   │   └── ...               # Basic language practice
│   └── Syllabus_Q/           # Syllabus-based exercises
├── Exercises/                 # Practice Problems
└── Notes/                    # Learning Materials
```

## 📈 Progress Tracking

- [x] Basic Java Syntax
- [x] Control Structures
- [x] Object-Oriented Concepts
- [x] Pattern Programs
- [x] Number Theory Problems
- [x] Basic OOP Implementation
- [x] Method Creation & Usage
- [ ] Advanced Data Structures
- [ ] File Handling
- [ ] Collections Framework

---
<div align="center">
Built with ☕ Java | Learning in Progress 🚀
</div>
