# 🚢 Battleship: Java Edition

This program is an adaptation of the classic strategy board game, "Battleship" which allows a player to go against the program itself and attempt to destroy its fleet of ships before it does. It was a project done for the, "Intro to Computer Science" course CPT - using Java, of course.

## 📃 Instructions

Similar to the traditional game, the objective of this version is to successfully destroy the opponent's fleet of ships by iteratively guessing their coordinates. Who's your opponent you may ask? The computer, of course! As a result, it'll attempt to hit your boats as well, so be careful! You'll get the chance to choose the starting point and orientation (horizontal or vertical) for each of your boats. The types include:\n" +
- Carrier: Length of 5
- Battleship: Length of 4
- Submarine: Length of 3
- Destroyer: Length of 2

After each of your guesses, you'll be presented with a board that updates to indicate whether you hit or missed a coordinate you enter - with the characters "X" and "|" respectively.

## ▶️ How to Run

1. Simply FORK and clone the repository onto your local machine or download it as a standalone ZIP file
2. If you do not have the Java SDK already, download and install it through the "[Java Downloads](https://www.oracle.com/java/technologies/downloads)" website
3. Finally, run ```javac BattleshipJavaEdition.java``` and then ```java BattleshipJavaEdition``` in your terminal to launch the game
