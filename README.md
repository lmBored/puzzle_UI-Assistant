[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/_p0yNlNQ)

# Sujiko Puzzle Assistant

## Group Assignment 2IRR00 2024 Group 6

### Members
- Nguyen Duy Anh Quan (1704311)
- Mai Hoang Nam (1959190)
- Nguyen Thanh Vinh (1957104)
- Miquel Ibáñez Solbes (2118998)
- Isabel Cantero Corchero (2064413)
- Hoang Bao Khoi Nguyen (1979388)

### Description
Sujiko is a number puzzle game played on a 3x3 grid. The objective is to fill 
in the empty cells with numbers from 1 to 9. Each of the four central cells in 
the 2x2 subgrids must add up to the given target sum. 
Use logical reasoning to determine the correct placement of the remaining 
numbers, ensuring that all conditions are met.

**Rules:**
1. Fill in the blank cells with numbers from 1 to 9.
2. The sum of the numbers in the central cells of each 2x2 subgrid must equal the target value provided.
3. Each number from 1 to 9 must be used only once in the entire grid.

For more information read [this](https://en.wikipedia.org/wiki/Sujiko).

### File Format and Application Details:
The puzzle description is stored in a text file (.txt). Each file assigns the 
four numbers to the four central circles of the Sujiko puzzle, with each number 
appearing on a separate line. For example, a file sujiko1.txt might contain:
```
17
18
15
15
```

Or the file sujiko4.txt with the format:
```
17
18
15
15
a 0 4
a 1 7
a 2 8
b 0 5
c 0 6
```
In this file, `a, b, c` are the rows and `0, 1, 2` are the columns. So `a 0 4` means the cell at first row, first column have the value 4. This option is to save the state of the puzzle your are playing.

This file format ensures that the puzzle can be easily read and interpreted 
by the application.

In the application, the puzzle state is displayed graphically. 
On the left side of the panel, there is a visual representation of the puzzle.
On the right side of the panel, there is a text area that provides real-time 
updates on the puzzle's state, including the moves made and the mode.
This area helps users keep track of their progress.
When selecting and typing into the cells, the possible values are 
1 to 9 and also 0 to indicate an empty cell.

### Modes
The Sujiko Puzzle Assistant operates in three distinct modes:
- **VIEW Mode**: This is a read-only mode where the puzzle can be viewed, 
    but it is not possible to solve it or type any keys.
- **EDIT Mode**: In this mode, you can type to fill in the puzzle cells, 
    but the "Solve" button in the menu is disabled. You can also edit the
    number inside the circles, but you have to press enter every time you are
    done with editing one.
- **SOLVE Mode**: This mode allows full interaction; you can type to fill 
    in the puzzle cells and also press the "Solve" button in the menu 
    to find the solution.

## Use Case
To experience the Sujiko Puzzle Assistant, follow these steps:
1. Open the application.
2. Navigate to `File > Open` and select one of the Sujiko puzzle files (.txt) 
    from the `puzzles` folder. For this example, choose `sujiko1.txt`.
3. The puzzle will appear on the screen.
4. To solve the puzzle, simply click `Puzzle > Solve`. The backtracking 
    algorithm will find the solution and display it to the user.
5. To get hints, click `Puzzle > Apply Reasoning`. If `Stop at First Change` is enabled, the next cell in the puzzle will be filled in for you, else it will solve all the puzzle at once.
6. To create new puzzle, click `File > New`, this will first ask you to name the puzzle, then enter the number in the circles with the format `X,X,X,X`.

## Design Patterns
- The YPA project as a whole uses the MVC design pattern, with each packages representing each of the 3 layers. This structure not only separates the application's logic but also enhances maintainability and scalability. Below is a detailed explanation of the specific design patterns implemented or modified in different parts of the YPA project:

Model: The model package manages the data and business logic. It includes classes that represent the application's data and handle the rules for manipulating that data. For instance, in the Sujiko puzzle implementation, the `YPuzzle` class encapsulates the puzzle's state, rules, and logic for validating moves.
View: The view package handles the display and user interface. Classes here render the puzzle grid and provide visual feedback to the user. The `puzzlePanel` class is responsible for drawing the puzzle on the screen and updating it in response to user actions.
Controller: The controller package processes user input and interacts with the model and view. The `MainFrame` class listens for user interactions, such as clicks or keystrokes, updates the model accordingly, and refreshes the view.
