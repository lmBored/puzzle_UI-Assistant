[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/_p0yNlNQ)

# Sujiko Puzzle Assistant

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
    number inside the circles. However, if the puzzle that you edited doesn't
    have a solution, you won't be able to switch to SOLVE Mode to solve the puzzle.
- **SOLVE Mode**: This mode allows full interaction; you can type to fill 
    in the puzzle cells and also press the "Solve" button in the menu 
    to find the solution.

## Use Cases

### Main Use Case: Solve a Sujiko Puzzle
To get a complete solution for a Sujiko puzzle, follow these steps:
1. Open the application.
2. Load a Sujiko Puzzle:
    - Navigate to `File > Open`.
    - Select a Sujiko puzzle file (.txt) from the `puzzles` folder. For this example, choose `sujiko1.txt`.
3. The puzzle will appear on the screen. While solving, if any of the cells violated Sujiko puzzle rules, they will be highlighted red.
4. To get the Sujiko puzzle solved automatically by the Sujiko Puzzle Assistant, simply click `Puzzle > Solve`. The backtracking algorithm will find the solution and display it to the user.

### Alternative Use Case 1: Get Hints
If you need help solving the puzzle, you can get hints:

1. (a) To get hints in the next cell, click `Puzzle > Apply Reasoning` (or `Ctrl + r`). If `Puzzle > Stop at First Change` is enabled, the next cell in the puzzle will be filled in for you, else it will solve all the puzzles at once.

### Alternative Use Case 2: Create a New Puzzle
2. (a) To create a new puzzle, click `File > New` (or `Ctrl + n`).
2. (a1) Enter a name for your puzzle.
2. (a2) After that, enter 4 numbers for the circles with the following format: `X,X,X,X`. As an example, you could write `22,18,15,17`.
> Be aware that your 4 input numbers must form a solvable Sujiko puzzle. Otherwise, the Sujiko Puzzle Assistant may not be able to help you.

### Alternative Use Case 3: Edit your current puzzle

3. (a) To edit your current puzzle, click `Edit > Edit Mode`.
3. (a1) Click on one of the circle, a window will pop up asking you to type a number.
> Your 4 numbers must form a Sujiko puzzle as mentioned in `Use Case 2`

### Alternative Use Case 4: Undo - Redo

If you want to undo or redo the last command, you can use these functions:

4. (a) If you want to undo, click `Edit > Undo` (or press `Ctrl + z`)
4. (a1) If you want to redo, click `Edit > Redo` (or press `Ctrl + y`)
4. (a2) If you want to undo all, click `Edit > Undo All` (or press `Ctrl + Alt + z`)
4. (a3) If you want to redo all, click `Edit > Redo All` (or press `Ctrl + Alt + y`)

### Alternative Use Case 5: Save current state of the puzzle

5. (a) You can save the current state of your puzzle by pressing `File > Save As` (or `Ctrl + s`). This will save the puzzle in the format mentioned above, and you can continue on your puzzle anytime after saving just by reopening it.

## Design Patterns
- The YPA project as a whole uses the MVC design pattern, with each packages representing each of the 3 layers. This structure not only separates the application's logic but also enhances maintainability and scalability. Below is a detailed explanation of the specific design patterns implemented or modified in different parts of the YPA project:

    - Model: The model package manages the data and business logic. It includes classes that represent the application's data and handle the rules for manipulating that data. For instance, in the Sujiko puzzle implementation, the `YPuzzle` class encapsulates the puzzle's state, rules, and logic for validating moves.
    - View: The view package handles the display and user interface. Classes here render the puzzle grid and provide visual feedback to the user. The `puzzlePanel` class is responsible for drawing the puzzle on the screen and updating it in response to user actions.
    - Controller: The controller package processes user input and interacts with the model and view. The `MainFrame` class listens for user interactions, such as clicks or keystrokes, updates the model accordingly, and refreshes the view.
