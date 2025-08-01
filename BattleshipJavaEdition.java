import java.util.Scanner; // Imports the Scanner class
import java.util.Random; // Imports the Random class

public class BattleshipJavaEdition
{
    /* displayMenu
       This function takes in an array with the program's menu
       options as values within it, iterates through it, and returns
       each of its index values as strings

       Parameters: String array
       Returns: String
    */
    public static String displayMenu(String[] arrayForMenu)
    {
        // Initializes a string that acts as if the program were
        // to manually use System.out.println()
        String menuOutput = "";

        // Combines each element of the array with a new line
        // to the menuOutput string
        for (int i = 0; i < arrayForMenu.length; i++) { menuOutput += arrayForMenu[i] + "\n"; }

        // Displays the text to prompt the user to choose an
        // option from the program's menu on a new line
        menuOutput += "\nSelect a Menu Option: ";

        // Returns the concatenated string to be presented to the user
        // as the program's menu
        return menuOutput;
    }

    /* createGrid
       This function helps properly initialize grids in the game to
       be used by the computer and the player as well
       as to be outputted to the screen

       Parameters: final int, final char
       Returns: 2-D char array
    */
    public static char[][] createGrid(final int GRID_SIZE, final char VACANT_SPOT_CHAR)
    {
        // Defines a 2-D character array to be filled with vacant
        // spots and returned
        char[][] newGrid = new char[GRID_SIZE][GRID_SIZE];

        // Two for-loops are required to Iterates through each individual position
        // in the array - since it is 2-dimensional
        for (int i = 0; i < GRID_SIZE; i++)
        {
            // Assigns the ith jth index of the array with a
            // character representing an empty spot
            for (int j = 0; j < newGrid[i].length; j++) { newGrid[i][j] = VACANT_SPOT_CHAR; }
        }

        return newGrid; // Returns the newly-generated grid
    }

    /* outputGrid
       This method enables the program to print a specified game
       grid to the screen with X and Y value headers
       for reference and proper formatting

       Parameters: 2-D char array
       Returns: Nothing
    */
    public static void outputGrid(char[][] gridToOutput)
    {
        // Displays the board's header, showing its valid X values
        System.out.println("   0   1   2   3   4   5   6   7   8   9");

        for (int i = 0; i < gridToOutput.length; i++)
        {
            // Outputs each of the board's row numbers, showing valid Y
            // values
            System.out.print(i + "  ");

            // Prints the value of the ith jth index in the
            // corresponding array as well as a couple spaces for separation
            for (int j = 0; j < gridToOutput[i].length; j++) { System.out.print(gridToOutput[i][j] + "   "); }

            System.out.println("\n"); // Adds two new lines
        }
    }

    /* enterToAdvance
       This procedure enables the gameplay to indicate that the player
       must press their enter key to continue with an action,
       especially during points with a considerable amount of output

       Parameters: final String, Scanner
       Returns: Nothing
    */
    public static void enterToAdvance(final String MESSAGE_SUFFIX, Scanner inputObject)
    {
        // Outputs the action to be taken on a new line
        System.out.print("\nPress ENTER to " + MESSAGE_SUFFIX + ": ");
        inputObject.nextLine();
    }

    /* greetPlayer
       This procedure welcomes the user to the game, describes its
       objective, presents the player with an example of a grid
       used in the game, and enables them to manually continue
       further

       Parameters: Scanner
       Returns: Nothing
    */
    public static void greetPlayer(Scanner inputObject)
    {
        System.out.println("\nWelcome to Battleship: Java Edition!\n" +
                             "You heard that right! The classic strategy board game has come to Java.\n" +
                           "\nOBJECTIVE\n" +
                             "Similar to the traditional game, the objective of this version is to successfully destroy the opponent's fleet of ships by iteratively guessing their coordinates. " +
                             "Who's YOUR opponent you may ask? " + 
                             "The computer, of course! " +
                             "As a result, it'll attempt to hit your boats as well, so BE CAREFUL!\n" +
                           "\nOnce you pass this screen, you'll get the chance to choose the starting point and orientation (horizontal or vertical) for each of your boats. The types include:\n" +
                             "- Carrier: Length of 5\n" +
                             "- Battleship: Length of 4\n" +
                             "- Submarine: Length of 3\n" +
                             "- Destroyer: Length of 2\n" +
                           "\nAfter each of your guesses, you'll be presented with a board that updates to indicate whether you hit or missed a coordinate you enter - with the characters 'X' and '|' respectively.");

        // Lets the player manually start the game
        enterToAdvance("Continue", inputObject);
    }

    /* aboutGame
       This procedure lets the user see information about the game,
       like its version number, who developed it, and the date
       it was done so

       Parameters: Scanner
       Returns: Nothing
    */
    public static void aboutGame(Scanner inputObject)
    {
        // Prints the details to the console
        System.out.println("\nAbout Battleship: Java Edition\n" +
                           "\nVersion: 1.0.0 - Wednesday, July 23, 2025\n" +
                             "Developed by: Joseph Maghamez\n" +
                             "Time to Develop: ~96 Hours\n" +
                            "\nThis was a project done for the Intro to Computer Science course CPT - using Java, of course. " +
                              "Enjoyed this program? Check out some of my other ones here: https://codehs.com/sandbox/redzebra8528.");

        enterToAdvance("go Back", inputObject);
    }

    /* populatePlayerGrid
       This method lets the player initially determine the starting coordinates
       of their Carrier, Battleship, Cruiser, and Destroyer and - if done
       successfully - assigns this to their 2-D character array

       Parameters: final char, final char, final int, Scanner, 2-D char array
       Returns: 2-D char array
    */
    public static char[][] populatePlayerGrid(final char VACANT_SLOT, final char OCCUPIED_SPOT,
                                              final int MAX_BOAT_SIZE, Scanner inputObject,
                                              char[][] gridForPlayer)
    {
        // Defines a constant string array that stores the names of
        // the available ships in the game
        final String[] SHIPS = {"CARRIER", "BATTLESHIP", "SUBMARINE", "DESTROYER"};

        // Declares a constant string that is outputted if the program
        // detects a user's starting point is not a valid coordinate
        final String INVALID_COORDINATES = "Those don't look like coordinates. " +
                                           "Ensure you're entering X and Y values between 0 and 9.";

        // Declares a constant string that is outputted if the program
        // detects a user's starting point results in boats overlapping
        final String OVERLAP_ERROR = "The coordinates you entered led to overlapping ships. " +
                                     "This is NOT allowed. Try again, ensuring that your boats don't overlap.";

        // Declares a constant string that is outputted if the program
        // detects a user's starting point results in a scope not
        // within their array
        final String OUT_OF_BOUNDS_ERROR = "The coordinates you entered went outside the scope of the board. " +
                                           "Try again, entering ones that are between 0 and 9 and don't overflow off the grid.";

        // Allows the player's entered coordinates, boat orientation, and parsed X
        // and Y values to be accessed in multiple scopes
        String currentBoatCoordinates; String currentBoatOrientation;
        int boatStarting_X; int boatStarting_Y;

        // Assumes the user's input is initially invalid - since they have
        // not entered anything at this point
        boolean isInvalid = true;

        // Allows the prompt within the loop to correctly access each
        // value within SHIPS as the loop iterates starting from 5
        // instead
        int normalIterator = 0;

        // Creates ships with a length of 5, 4, 3, and
        // 2
        for (int i = MAX_BOAT_SIZE; i >= 2; i--)
        {
            while (isInvalid)
            {
                // Asks the player to input the starting point for each
                // type of ship
                System.out.print("\nEnter the Starting Point For Your " + SHIPS[normalIterator] + " [x, y]: ");
                currentBoatCoordinates = inputObject.nextLine().replaceAll("[\\s()\\[\\]]", ""); // Removes unnecessary spacing and brackets to prepare for parsing

                // Converts the player's coordinates into a manipulable array
                String[] boatStarting_array = currentBoatCoordinates.split(",");

                // Asks the player to input the orientation for each type
                // of ship
                System.out.print("What Orientation Would You Like Your " + SHIPS[normalIterator] + " to be in ([H]orizontal/[V]ertical (Default)): ");
                currentBoatOrientation = inputObject.nextLine();

                // Attempts to convert the user's input into separate integers to
                // work with in the nested for-loop but catches if they
                // enter a non-numerical character
                try
                {
                    // Parses the 2 elements within the boatCoordinates_array as integers to
                    // properly work with
                    boatStarting_X = Integer.parseInt(boatStarting_array[0]);
                    boatStarting_Y = Integer.parseInt(boatStarting_array[1]);
                    
                    for (int j = 0; j < i; j++)
                    {
                        // Checks whether the player prefers to have their boat horizontally
                        // or vertically positioned from their starting point
                        if (currentBoatOrientation.equalsIgnoreCase("horizontal") || currentBoatOrientation.equalsIgnoreCase("h"))
                        {
                            // Attempts to increase either the X or Y coordinate by
                            // j, depending on the ship's orientation, but catches if the
                            // user-entered coordinates go off the board
                            try
                            {
                                // Determines if the coordinates the user has entered leads to
                                // overlap of boats - which is illegal in Battleship
                                if (gridForPlayer[boatStarting_Y][boatStarting_X + j] == OCCUPIED_SPOT)
                                {
                                    // Reverts the grid's values to prevent them from being left
                                    // over if the starting coordinate is valid but the rest
                                    // of a ship's coordinates are not
                                    for (int k = j; k >= 0; k--)
                                    {
                                        try { gridForPlayer[boatStarting_Y][boatStarting_X - k] = VACANT_SLOT; }
                                        catch (ArrayIndexOutOfBoundsException e) { break; }
                                    }

                                    System.out.println(OVERLAP_ERROR);
                                    break;
                                }
                                else { gridForPlayer[boatStarting_Y][boatStarting_X + j] = OCCUPIED_SPOT; }
                            }
                            catch (ArrayIndexOutOfBoundsException exception)
                            {
                                // Reverts the grid's values to prevent them from being left
                                // over if the starting coordinate is valid but the rest
                                // of a ship's coordinates are not
                                for (int k = j; k >= 0; k--)
                                {
                                    try { gridForPlayer[boatStarting_Y][boatStarting_X - k] = VACANT_SLOT; }
                                    catch (ArrayIndexOutOfBoundsException e) { break; }
                                }

                                System.out.println(OUT_OF_BOUNDS_ERROR);
                                break;
                            }
                        }
                        else
                        {
                            // Attempts to increase either the X or Y coordinate by
                            // j, depending on the ship's orientation, but catches if the
                            // user-entered coordinates go off the board
                            try
                            {
                                // Determines if the coordinates the user has entered leads to
                                // overlap of boats - which is illegal in Battleship
                                if (gridForPlayer[boatStarting_Y + j][boatStarting_X] == OCCUPIED_SPOT)
                                {
                                    // Reverts the grid's values to prevent them from being left
                                    // over if the starting coordinate is valid but the rest
                                    // of a ship's coordinates are not
                                    for (int k = j; k >= 0; k--)
                                    {
                                        try { gridForPlayer[boatStarting_Y - k][boatStarting_X] = VACANT_SLOT; }
                                        catch (ArrayIndexOutOfBoundsException e) { break; }
                                    }

                                    System.out.println(OVERLAP_ERROR);
                                    break;
                                }
                                else { gridForPlayer[boatStarting_Y + j][boatStarting_X] = OCCUPIED_SPOT; }
                            }
                            catch (ArrayIndexOutOfBoundsException exception)
                            {
                                // Reverts the grid's values to prevent them from being left
                                // over if the starting coordinate is valid but the rest
                                // of a ship's coordinates are not
                                for (int k = j; k >= 0; k--)
                                {
                                    try { gridForPlayer[boatStarting_Y - k][boatStarting_X] = VACANT_SLOT; }
                                    catch (ArrayIndexOutOfBoundsException e) { break; }
                                }

                                System.out.println(OUT_OF_BOUNDS_ERROR);
                                break;
                            }
                        }

                        // If the user manages to successfully abide by the above
                        // conditions, isInvalid is set to false, allowing them to move
                        // on to the next ship depicted by the parent for-loop
                        isInvalid = !(j == i - 1);
                    }
                }
                catch (NumberFormatException exception) { System.out.println(INVALID_COORDINATES); }
                // Also catches their input if they only enter a single
                // number instead of 2, separated by a comma
                catch (ArrayIndexOutOfBoundsException exception) { System.out.println(INVALID_COORDINATES); }
            }

            normalIterator++; // Increments normalIterator
            isInvalid = true; // Resets the value of isInvalid to trigger the next iteration of the loop 
        }

        System.out.println("\n   YOUR BOARD"); // Indicates the printed grid is the player's board
        return gridForPlayer; // Returns the player's populated grid
    }

    /* populateComputerGrid
       This function enables the computer to randomly generate its board
       without having errors the player could make, including overlapping and
       overflowing ships as well as invalid coordinates

       Parameters: final int, final char, final int, Random, 2-D char array
       Returns: 2-D char array
    */
    public static char[][] populateComputerGrid(final int GRID_SIZE, final char OCCUPIED_SPOT,
                                                final int MAX_BOAT_SIZE, Random randObject,
                                                char[][] gridForComputer)
    {
        for (int i = MAX_BOAT_SIZE; i >= 2; i--)
        {
            int boatStarting_X = 0;
            int boatStarting_Y = 0;
            int horizontalOrVertical;

            // Assigns horizontalOrVertical as a random value between 0 and 1
            // to determine if the current ship (dictated by the for-loop's
            // iterator) will be horizontal or vertical, respectively
            // (Checks whether the current ship is the Carrier)
            if (i < 5) { horizontalOrVertical = randObject.nextInt(2); }
            // Forces the Carrier to be horizontally positioned to prevent overlap
            // with other ships
            else { horizontalOrVertical = 0; }

            // Checks whether a boat is horizontally or vertically oriented to
            // assign an X and Y value accordingly 
            if (horizontalOrVertical == 1)
            {
                boatStarting_X = randObject.nextInt(10);

                // Splits the computer's grid so the Destroyer only exists within
                // the Y-values between 0 and 1, the Cruiser between 2
                // and 4, the Battleship between 5 and 8, and the
                // Carrier on a Y-value of 9
                switch (i)
                {
                    case 3:
                        boatStarting_Y = 2;
                        break;
                    case 4:
                        boatStarting_Y = 5;
                        break;
                }
            }
            else
            {
                // Determines a buffer beginning from the right edge of the
                // grid to prevent a ship from overflowing off it
                boatStarting_X = randObject.nextInt(GRID_SIZE - i);

                // Splits the computer's grid so the Destroyer only exists within
                // the Y-values between 0 and 1, the Cruiser between 2
                // and 4, the Battleship between 5 and 8, and the
                // Carrier on a Y-value of 9
                switch (i)
                {
                    case 3:
                        boatStarting_Y = randObject.nextInt(3) + 2;
                        break;
                    case 4:
                        boatStarting_Y = randObject.nextInt(4) + 5;
                        break;
                    case 5:
                        boatStarting_Y = 9;
                        break;
                }
            }

            for (int j = 0; j < i; j++)
            {
                // Increases either the X or Y coordinate by j, depending
                // on the ship's orientation
                if (horizontalOrVertical == 1) { gridForComputer[boatStarting_Y + j][boatStarting_X] = OCCUPIED_SPOT; }
                else { gridForComputer[boatStarting_Y][boatStarting_X + j] = OCCUPIED_SPOT; }
            }

            // --- TESTING ---
            // Outputs each of the computer's starting points and boat orientations
            //System.out.print(boatStarting_X);
            //System.out.print(boatStarting_Y);
            //System.out.println(horizontalOrVertical);
        }

        // Indicates the program itself has generated its board
        System.out.println("The computer has placed its ships. " +
                           "Let the game BEGIN!");

        return gridForComputer; // Returns the computer's populated grid
    }

    /* playerGuess
       When called, this method allows the player to place a
       guess, targeting the computer's ships, while still validating their input

       Parameters: final int, Scanner
       Returns: int array
    */
    public static int[] playerGuess(final int GRID_SIZE, Scanner inputObject)
    {
        // Declares a constant string that is outputted if the program
        // detects a user's coordinate pair are not valid
        final String INVALID_COORDINATES = "Those don't look like coordinates. " +
                                           "Ensure you're entering X and Y values between 0 and 9.\n";

        // Declares a constant string that is outputted if the program
        // detects a user's coordinate pair results in a scope not
        // within the computer's array
        final String OUT_OF_BOUNDS_ERROR = "The coordinates you entered went outside the scope of the board. " +
                                           "Try again, entering ones that are between 0 and 9 and don't overflow off the grid.\n";

        // Assumes the user's input is initially invalid - since they have
        // not entered anything at this point
        boolean isInvalid = true;

        int playerGuess_X = 0; int playerGuess_Y = 0;

        while (isInvalid)
        {
            // Prompts the player to enter their guess on a new line
            System.out.print("What Coordinates Would You Like to Target [x, y]: ");
            String playerGuessedCoordinates = inputObject.nextLine().replaceAll("[\\s()\\[\\]]", ""); // Removes unnecessary spacing and brackets to prepare for parsing

            // Converts the player's coordinates into a manipulable array
            String[] playerGuess_array = playerGuessedCoordinates.split(",");

            // Attempts to convert the player's entered input as integer coordinates
            // but catches it if they are not integers in the
            // first place
            try
            {
                playerGuess_X = Integer.parseInt(playerGuess_array[0]);
                playerGuess_Y = Integer.parseInt(playerGuess_array[1]);
                
                // Proactively determines if the user's integer input would throw an
                // ArrayIndexOutOfBoundsException, otherwise mark their input as being valid 
                if (playerGuess_X >= GRID_SIZE || playerGuess_Y >= GRID_SIZE) { System.out.println(OUT_OF_BOUNDS_ERROR); }
                else { isInvalid = false; }
            }
            catch (NumberFormatException exception) { System.out.println(INVALID_COORDINATES); }
            // Additionally catches a scenario where the user might enter only
            // a single integer 
            catch (ArrayIndexOutOfBoundsException exception)
            {
                playerGuess_X = Integer.parseInt(playerGuess_array[0]);
                playerGuess_Y = 0; // Sets boatStarting_Y to a default of 0

                isInvalid = false;
            }
        }

        int[] playerGuessedCoordinates = {playerGuess_X, playerGuess_Y};
        return playerGuessedCoordinates; // Returns their guess as an integer array
    }

    /* computerGuess
       When called, this method allows the computer to place a
       guess, targeting the user's ships. It considers if it has
       previously hit a ship to subsequently guess up, down, left,
       or right instead of entirely at random

       Parameters: final int, Random, boolean, int array
       Returns: int array
    */
    public static int[] computerGuess(final int GRID_SIZE, Random randObject,
                                      boolean lastWasHit, int[] previousCoordinates)
    {
        int nextGuessDirection;
        int[] computerGuessedCoordinates = new int[2];

        // Checks whether the computer's last move successfully targeted a ship
        if (lastWasHit)
        {
            // Randomly selects a value ranging from 0 to 3 to
            // select allow the computer to either subsequently guess up, down,
            // left, or right
            nextGuessDirection = randObject.nextInt(4);

            // Determines the cardinal direction the computer should guess within
            switch (nextGuessDirection)
            {
                case 0: // Up
                    computerGuessedCoordinates[0] = previousCoordinates[0];

                    // Attempts to decrease the Y value of the computer's previous
                    // guess but catches this action if the new coordinate overflows
                    // off the board
                    if (previousCoordinates[1] - 1 < 0) { computerGuessedCoordinates[1] = randObject.nextInt(GRID_SIZE); } // Sets the value at random instead
                    else { computerGuessedCoordinates[1] = previousCoordinates[1] - 1; }

                    break;
                case 1: // Right
                    // Attempts to increase the X value of the computer's previous
                    // guess but catches this action if the new coordinate overflows
                    // off the board
                    if (previousCoordinates[0] + 1 >= GRID_SIZE) { computerGuessedCoordinates[0] = randObject.nextInt(GRID_SIZE); } // Sets the value at random instead
                    else { computerGuessedCoordinates[0] = previousCoordinates[0] + 1; }
                    
                    computerGuessedCoordinates[1] = previousCoordinates[1];
                    break;
                case 2: // Down
                    computerGuessedCoordinates[0] = previousCoordinates[0];

                    // Attempts to increase the Y value of the computer's previous
                    // guess but catches this action if the new coordinate overflows
                    // off the board
                    if (previousCoordinates[1] + 1 >= GRID_SIZE) { computerGuessedCoordinates[1] = randObject.nextInt(GRID_SIZE); } // Sets the value at random instead
                    else { computerGuessedCoordinates[1] = previousCoordinates[1] + 1; }

                    break;
                case 3: // Left
                    // Attempts to decrease the X value of the computer's previous
                    // guess but catches this action if the new coordinate overflows
                    // off the board
                    if (previousCoordinates[0] - 1 < 0) { computerGuessedCoordinates[0] = randObject.nextInt(GRID_SIZE); } // Sets the value at random instead
                    else { computerGuessedCoordinates[0] = previousCoordinates[0] - 1; }
                    
                    computerGuessedCoordinates[1] = previousCoordinates[1];
                    break;
            }
        }
        else
        {
            // Sets the X and Y values at random - within the
            // boundaries of the grid
            computerGuessedCoordinates[0] = randObject.nextInt(GRID_SIZE);
            computerGuessedCoordinates[1] = randObject.nextInt(GRID_SIZE);
        }
        
        return computerGuessedCoordinates; // Returns its guess as an integer array
    }

    /* hitTarget
       This function determines whether either the player or the computer
       has successfully hit one of their opponent's ships from a
       guess or if either have already inputted a specific location
       prior

       Parameters: final char, boolean, int array. 2-D char array, 2-D char array, 2-D char array
       Returns: boolean
    */
    public static boolean hitOrMiss(final char OCCUPIED_SPOT, boolean playerGuessing,
                                    int[] guess, char[][] gridForPlayer,
                                    char[][] gridForComputer, char[][] gameplayGrid)
    {
        // Declares constant characters that are to be displayed in a
        // situation of a hit or miss
        final char HIT_SPOT = 'X'; final char MISSED_SPOT = '|';

        // Initializes a boolean to represent the status of the turn
        // - either being a hit as true or a miss or
        // point that was already guessed as false
        boolean targetStatus;

        // Concatenates brackets and a comma around the current guess to
        // present it as (x, y)
        String coordinateAsString = "(" + guess[0] + ", " + guess[1] + ")";

        System.out.println(""); // Adds a new line

        // Checks whether it is currently the player's or computer's turn
        if (playerGuessing)
        {
            // Determines if the corresponding guess points to a coordinate that
            // has been hit from before, otherwise check if it results
            // in a successful hit or misses a ship entirely
            if (gameplayGrid[guess[1]][guess[0]] == HIT_SPOT || gameplayGrid[guess[1]][guess[0]] == MISSED_SPOT)
            {
                // Indicates that the user inputted a guess they already tried
                // prior and suggests that they take advantage of the gameplayGrid
                // to keep track of this
                System.out.println("OOPS! You already guessed that coordinate. " + 
                                   "Use YOUR MOVES to help you gauge where you've attempted to hit before.");

                targetStatus = false;
            }
            else if (gridForComputer[guess[1]][guess[0]] == OCCUPIED_SPOT)
            {
                // Indicates that the user successfully hit one of the computer's
                // ships
                System.out.println("BOOM! You successfully hit one of the computer's ships at " +
                                   coordinateAsString +
                                   ".");
                gameplayGrid[guess[1]][guess[0]] = HIT_SPOT;

                targetStatus = true;
            }
            else
            {
                // Indicates that the user did not hit one of the
                // computer's ships
                System.out.println("SPLASH! Unfortunately, you missed the computer's fleet of ships guessing " +
                                   coordinateAsString +
                                   ". Better luck on your next turn! :(");
                gameplayGrid[guess[1]][guess[0]] = MISSED_SPOT;

                targetStatus = false;
            }
        }
        else
        {
            // Determines if the corresponding guess points to a coordinate that
            // has been hit from before, otherwise check if it results
            // in a successful hit or misses a ship entirely
            if (gridForPlayer[guess[1]][guess[0]] == HIT_SPOT || gridForPlayer[guess[1]][guess[0]] == MISSED_SPOT)
            {
                // Indicates that the computer, "mistakenly" guessed a point on the
                // board it already had before
                System.out.println("LUCKY YOU! The computer accidentally guessed " +
                                   coordinateAsString +
                                   " as it did before!");

                targetStatus = false;
            }
            else if (gridForPlayer[guess[1]][guess[0]] == OCCUPIED_SPOT)
            {
                // Indicates that the computer successfully hit one of the player's
                // ships
                System.out.println("OOPS! Unfortunately, the computer correctly guessed where one of your ships was - at " +
                                   coordinateAsString +
                                   ". :(");
                gridForPlayer[guess[1]][guess[0]] = HIT_SPOT;

                targetStatus = true;
            }
            else 
            {
                // Indicates that the computer did not hit one of the
                // player's ships
                System.out.println("PHEW! Looks like you're safe from the computer - for now - since it only attacked point " +
                                   coordinateAsString +
                                   ".");
                gridForPlayer[guess[1]][guess[0]] = MISSED_SPOT;

                targetStatus = false;
            }

            // Presents the user with the computer's updated moves on their
            // own board on a new line
            System.out.println("\n   COMPUTER'S MOVES");
            outputGrid(gridForPlayer);
        }

        return targetStatus; // Returns the current turn's status
    }

    /* updateRemainingTargets
       This method simply updates the decrementing variable pertaining to the
       number of targets each opponent has left to hit

       Parameters: boolean, int
       Returns: int
    */
    public static int updateRemainingTargets(boolean currentTurn_hit, int remainingTargets)
    {
        // Decrements the number of targets the player or computer must
        // correctly guess if they end up hitting one
        if (currentTurn_hit) { remainingTargets--; }

        return remainingTargets; // Returns the number of targets the specified opponent has left to hit
    }

    /* gameSummary
       This procedure provides the player with insight on how their
       Battleship game resulted, telling them if they won or not
       and their accuracy of their attempts

       Parameters: boolean, int, 2-D char array
       Returns: Nothing
    */
    public static void gameSummary(boolean playerWon, int score,
                                   char[][] gridForComputer)
    {
        // Initializes a Scanner object to let the user, "Press ENTER"
        Scanner inputObject = new Scanner(System.in);

        // Indicates the end of the current game on a new
        // line
        System.out.println("\n--- END OF GAME ---\n");

        // Checks whether the player or computer has won to congratulate
        // one accordingly
        if (playerWon) { System.out.println("CONGRATULATIONS! You win this round of Battleship against the computer, taking you " + score + " turns to do so."); }
        else { System.out.println("AW MAN! The computer wins this round of Battleship."); }

        // Reveals the computer's ship placement
        System.out.println("\n   COMPUTER'S BOARD");
        outputGrid(gridForComputer);

        // Calculates the player's hit accuracy
        final double PLAYER_ACCURACY = (14.0 / score) * 100;
        // Rounds the player's accuracy percentage to 1 decimal place
        final double ACCURACY_ROUNDED = Math.round(PLAYER_ACCURACY * 10) / 10.0;
        // Outputs this ratio to the user
        System.out.println("\nACCURACY: You successfully hit " + ACCURACY_ROUNDED + "% of your guesses.");

        enterToAdvance("go to the Main Menu", inputObject);
    }

    public static void main(String[] args)
    {
        // Initializes a Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Creates a Random object for random number generation
        Random random = new Random();

        // Declares a constant string array that stores the game's menu
        // options to be displayed using the displayMenu function
        final String[] MAIN_MENU = {"\n\nBATTLESHIP: JAVA EDITION",
                                      "MAIN MENU\n",
                                      "a) New Full Game",
                                      "b) New Game (Skip Seeing the Rules)",
                                      "c) Only See the Rules",
                                      "d) About",
                                      "e) EXIT..."};

        // Declares a constant integer that stores the Battleship board's size
        // - in this case being 10 x 10
        final int BOARD_SIZE = 10;

        // Stores the size of the largest ship in Battleship - in
        // this case being the Carrier
        final int LARGEST_SHIP_LENGTH = 5;

        // Assign constant characters that represent what value is to be
        // stored when a spot on the grid is empty or
        // occupied with a boat
        final char EMPTY_SLOT_CHAR = '~'; final char FILLED_SLOT_CHAR = 'O';

        // Sets to true once the user decides to completely exit
        // the program
        boolean exitProgram = false;

        // Defines a boolean to represent if the computer's previous move
        // was a hit to enhance its guessing ability
        boolean computerHitLast = false; int[] computerLastGuess = new int[2]; // Stores its last coordinate guess

        int playerRemainingTargets; int computerRemainingTargets;

        int numberOfMoves = 0; // Stores the amount of moves the player makes

        // Initialize 2-D character arrays representing 10 x 10 boards for
        // both the player and program itself - being the computer
        char[][] playerGrid = new char[BOARD_SIZE][BOARD_SIZE];
        char[][] computerGrid = new char[BOARD_SIZE][BOARD_SIZE];

        // Declares a 2-D character array that stores the player's moves
        // on whether they hit or missed a ship
        char[][] playerMoves = new char[BOARD_SIZE][BOARD_SIZE];

        while (exitProgram == false)
        {
            // Create the actual grids filled with blank slots
            playerGrid = createGrid(BOARD_SIZE, EMPTY_SLOT_CHAR);
            computerGrid = createGrid(BOARD_SIZE, EMPTY_SLOT_CHAR);
            playerMoves = createGrid(BOARD_SIZE, EMPTY_SLOT_CHAR);

            // Outputs the game's main menu to the user
            System.out.print(displayMenu(MAIN_MENU));
            String userSelection = scanner.nextLine().replace(")", ""); // If the user so happens to enter, ")" - as shown in the menu - simply remove it from their input

            // Takes action depending upon which option they select from the
            // menu
            if (userSelection.equalsIgnoreCase("a") || userSelection.equalsIgnoreCase("b"))
            {
                if (userSelection.equalsIgnoreCase("a")) { greetPlayer(scanner); } // Show the game's introduction and rules

                System.out.println(""); // Adds a new line
                outputGrid(playerMoves); // Shows an example board for gameplay

                // Assign integers that represent the number of targets left for
                // both the player and computer - which is decremented until a
                // value of 0 for every successful hit
                playerRemainingTargets = 14; computerRemainingTargets = 14;

                // Sets their number of moves to 0 at the start
                // of each new game
                numberOfMoves = 0;

                // Lets the player input the starting points for each of
                // their ships and uses this information to assign an array
                // for them
                playerGrid = populatePlayerGrid(EMPTY_SLOT_CHAR, FILLED_SLOT_CHAR,
                                                LARGEST_SHIP_LENGTH, scanner,
                                                playerGrid);
                outputGrid(playerGrid); // Outputs their grid to the screen

                // Generates a board for the player's opponent at random
                computerGrid = populateComputerGrid(BOARD_SIZE, FILLED_SLOT_CHAR,
                                                    LARGEST_SHIP_LENGTH, random,
                                                    computerGrid);
                // --- TESTING ---
                // Displays the computer's board with its ships placed
                //outputGrid(computerGrid);

                enterToAdvance("Start", scanner); 

                // Repeatedly allows the player and computer to take turns until
                // either opponent surpasses hitting 14 total targets
                while (true)
                {
                    // Presents the user with their own updated moves on a
                    // grid on a new line
                    System.out.println("\n   YOUR MOVES");
                    outputGrid(playerMoves);

                    // Presents the player with their turn
                    boolean playerTurn_result = hitOrMiss(FILLED_SLOT_CHAR, true,
                                                          playerGuess(BOARD_SIZE, scanner), playerGrid,
                                                          computerGrid, playerMoves);
                    numberOfMoves++; // Increments the number of the player's moves

                    // Updates the player's number of remaining targets
                    playerRemainingTargets = updateRemainingTargets(playerTurn_result, playerRemainingTargets);

                    // Determine whether they have completely destroyed the computer's fleet of
                    // boats to break out of the while loop
                    if (playerRemainingTargets == 0)
                    {
                        // Displays the game's results
                        gameSummary(true, numberOfMoves, computerGrid);
                        break;
                    }

                    // Lets the player manually continue with the computer's turn
                    enterToAdvance("See the Computer's Guess", scanner);

                    // Enables the computer to take its turn
                    int[] generatedComputerGuess = computerGuess(BOARD_SIZE, random,
                                                                 computerHitLast, computerLastGuess);
                    boolean computerTurn_result = hitOrMiss(FILLED_SLOT_CHAR, false,
                                                            generatedComputerGuess, playerGrid,
                                                            computerGrid, playerMoves);
                    
                    // Updates the computer's number of remaining targets
                    computerRemainingTargets = updateRemainingTargets(computerTurn_result, computerRemainingTargets);
                    // Reassigns computerLastGuess to generatedComputerGuess
                    computerLastGuess = generatedComputerGuess;
                    computerHitLast = computerTurn_result;

                    // Determine whether the computer has completely destroyed the player's fleet
                    // of boats to break out of the while loop
                    if (computerRemainingTargets == 0)
                    {
                        // Displays the game's results
                        gameSummary(false, numberOfMoves, computerGrid);
                        break;
                    }

                    // Lets the player manually reiterate the loop and take another
                    // turn
                    enterToAdvance("Make Your Next Move", scanner);
                }
            }
            else if (userSelection.equalsIgnoreCase("c")) { greetPlayer(scanner); } // Show the game's introduction and rules
            else if (userSelection.equalsIgnoreCase("d")) { aboutGame(scanner); }
            else if (userSelection.equalsIgnoreCase("e")) { exitProgram = true; }
            // Fallback message for when the user's menu selection does not
            // point to an option listed on the program menu itself
            else { System.out.println("That menu option doesn't exist! Try again."); } 
        }

        // Indicates that the program is going to stop execution on
        // a new line
        System.out.print("\nThanks for playing! Hope you had fun.");
    }
}
