# Escape Room Game

## Overview

This is **my final project after a 6-week Java course**. I wrote it within one week. We needed to show all of the learned topics like OOP, Enum, Interfaces, Lambda, file-system/database etc. And all of that in clean code and according to Java naming and documentation conventions.

The **Escape Room Game** is an interactive text-based game where players must solve multiple puzzles to escape from a virtual escape room. This Java console-based project offers a series of challenging brain teasers that need to be solved in a certain timeframe.

The game keeps track of player progress and saves it at the end, allowing players to compare their results. A leaderboard of the top 10 players is displayed at the end, showing the highest scores achieved.

## Features

* **Variety of Puzzles**: The game features a wide range of puzzles, including number sequences, maze, word search, and a Mastermind-style encoding puzzle.
* **Time Tracking and Failed Attempts**: For each puzzle, the time taken is recorded, and failed attempts are tracked. These factors influence the player's final score.
* **Scoring System**: Players earn points based on their performance in each puzzle. The total score is calculated after all puzzles are completed.
* **Save Progress**: Game progress is automatically saved at the end of the game, and players can choose to view the top 10 leaderboard.
* **Gimmicks**: The game features visually engaging ASCII art and countdown timers for important game moments.
* **Useful**: Writes names with capital first letters, test-mode doesn't save gamescore, visually appealing design

## Game Flow

1. **Welcome Message**: The game starts with a welcome message and allows the player to enter their name.
2. **Puzzles**:
   * **Number Sequence Puzzle**: Continue a given number sequence.
   * **Labyrinth Puzzle**: Find the exit of the maze by navigating with keyboard inputs.
   * **Number Guessing Game**: Guess a randomly generated number between 1 and 1000.
   * **Word Search Puzzle**: Find hidden words in a letter matrix and form a meaningful sentence.
   * **Mastermind Puzzle**: Guess the correct color combination in a limited number of attempts.
3. **End Message**: Once all puzzles are completed, a summary of the player's performance is displayed, including points and time taken for each puzzle. The player can choose to view the top 10 leaderboard.

## How to Run the Game

1. **Prerequisites**: 
   * Java installed on your machine.
   * A code editor or IDE such as IntelliJ IDEA or Eclipse.

2. **Steps**:
   * Clone or download the repository.
   * Open the project in your preferred Java IDE.
   * Compile and run the `EscaperoomMain` class to start the game.

## Code Structure

* **`accompanyingclasses` package**: Contains classes that control the game flow, handle file access, display end messages, and manage player stats.
* **`riddles` package**: Contains the logic for each puzzle type.
* **`enums` package**: Defines game statuses (e.g., IN_PROGRESS, COMPLETED, NOT_COMPLETED).
* **`interfaces` package**: Provides utility methods like time measurement and user interaction.

## Future Enhancements

* **Additional Puzzles**: Implement more puzzles to add variety.
* **Story Development**: Add a more detailed story to enhance the escape room experience.
* **Improved Save Feature**: Save progress right after completion (to skip the end screen and not lose the achieved score).
