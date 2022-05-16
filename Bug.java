// Project 1 By Marshall Hamon
// APCSA 2
// Theme: "Bugs"
// 5.11.2022 - 5.15.2022
public class Bug {
    private boolean canFly;         // Whether or not the Bug can fly
    private BugType speciesName;    // An enum describing the type of bug
    private int numLegs;            // An integer describing the number of legs the insect has
    private double length;          // The length of the insect, in millimeters

    private static double[] numeratorCoefficients = {                // The proper coefficients for approximating the series
        1d, 1d, 7d, 127d, 4369d, 34807d, 20036983d, 2280356863d,     // For the inverse error function.
        49020204823d, 659672412000001d                               //
    };                                                               // See this desmos graph (https://www.desmos.com/calculator/xvnnjyzygd)
    private static double[] denominatorCoefficients = {              // and this stackexchange post
        2d, 24d, 960d, 80640d, 11612160d, 364953600d, 797058662400d, // (https://math.stackexchange.com/questions/1725897/computing-the-inverse-error-function)
        334765638208000d, 26015994740736000d, 124564582818643968000d // For information about how I arrived at these constants.
    };                                                               // As shown in the desmos graph, error is as small as one part in a thousand during the worst case

    public Bug(BugType type, boolean canFly, int numLegs, double length) {  // Creates a new Bug following a set of criteria
        this.speciesName = type;                                            // Specifies the type/species of the bug
        this.canFly = canFly;                                               // Specifies whether or not the bug can fly
        this.numLegs = numLegs;                                             // Specifies the number of legs the bug has
        this.length = length;                                               // Designates the length of the bug
    }

    public static Bug ant() {                                // Creates a new generic Argentine Ant
        final double meanAntLength   = 2.5;                  // Length of the average argentine ant in millimiters.
        final double antLengthStdDev = .5;                   // Source: https://www.orkin.com/pests/ants/argentine-ants 
        return new Bug(BugType.ANT, false, 6, inverf(        // 
            Math.random(), meanAntLength, antLengthStdDev)); // ⎞ Creates a new Ant who's length follows a normal distribution
    }                                                        // ⎠

    public static Bug spider() {                                    // Creates a generic Black Widow Spider
        final double meanSpiderLength = 6.5;                        // Length of the average black widow in millimiters
        final double spiderLengthStdDev = 3.0;                      // Source: https://www.orkin.com/pests/spiders/black-widow-spiders
        return new Bug(BugType.SPIDER, false, 8, inverf(            //
            Math.random(), meanSpiderLength, spiderLengthStdDev));  //⎞ Creates a new Spider who's length follows a normal distribution
    }                                                               //⎠ 

    public static Bug bee() {                       // Creates a new generic honeybee
        return new Bug(BugType.BEE, false, 8, 12);  // I couldn't find data on honey bee length variance, only average.
                                                    // Source: https://www.britannica.com/animal/honeybee
    }

    public String toString() {                                                              // Converts the current Bug object into an ASCII representation of the insect
        if(speciesName == BugType.BEE) {                                                    // If the bug is a bee:
            return "__________________________________________________\n"+                  //  - Outline the drawing with a row of underlines
                   "   _\n"      +                                                          //  - Add the tip of the wing
                   "  / /\n"     +                                                          //  - Add the rest of the right wing
                   "o----[■\n"   +                                                          //  - Add the Bee's center -- because all bees are the same no math is required
                   "  \\ \\\n"   +                                                          //  - Add the body of the bee's left wing
                   "   \u203E\n" +                                                          //  - Add the tip of the bee's left wing
                   multiplyString("\u203E", 50) + "\n";                                     //  - Outline the drawing with a row of overlines
        }                                                                                   //
        if(speciesName == BugType.ANT) {                                                    // If the insect is an ant:
            int intLength = (int) Math.round(length);                                       //  - Store the rounded length of the ant for future use
            if(intLength <= 2) {                                                            //  - If the ant is particularly small
                return "___________________________________________________\n"+             //    - Outline the drawing with a row of underlines
                       "\\|/\n" +                                                           //    - Add the top 3 legs to the ant
                       " o" + multiplyString("-", intLength) +                              //    - Add the head, with the possible addition of a tail
                       "\n/|\\\n" +                                                         //    - Add the bottom 3 legs to the ant
                       "‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾\n";             //    - Outline the drawing with a row of overlines
            } else {                                                                        // - If the ant is moderately sized
                return "___________________________________________________\n"+             //    - Outline the drawing with a row of underlines
                       "\\" + multiplyString(" ", (int) Math.ceil(length / 2)) + "|" +      //    - Add the top 3 legs
                       multiplyString(" ", (int) Math.floor(length / 2)) + "/\n" +          //      
                       " o" + multiplyString("-", intLength) +                              //    - Add the center of the ant
                       "\n/" +  multiplyString(" ", (int) Math.ceil(length / 2)) + "|" +    //    - Add the bottom 3 legs
                       multiplyString(" ", (int) Math.floor(length / 2)) + "\\\n" +         //
                       "‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾\n";             //    - Outline the drawing with a row of overlines
            }                                                                               //
        }                                                                                   //
        if(speciesName == BugType.SPIDER) {                                                 // If the bug is a spider:
            int intLength = (int) Math.round(length);                                       //  - Store the rounded length of the spider for future use
            return "___________________________________________________\n"+                 //  - Outline the drawing with a row of underlines
                   " \\\\" + multiplyString(" ", intLength - 2) + "//\n" +                      //  - Add the top 2 pairs of legs with proper spacing
                   " o" + multiplyString("-", intLength) +                                  //  - Add the spider with proper length
                   "\n //" + multiplyString(" ", intLength - 2) + "\\\\\n" +                    //  - Add the bottom 2 pairs of legs
                   "‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾\n";                 //  - Outlines the drawing with a row of overlines
        }                                                                                   //
        return "I don't know how to handle this insect :(";                                 // If the insect has an abnormal number of legs, report that you don't know how to draw it
    }                                                                                       //

    public boolean getFlightCapacity() {    // 
        return canFly;                      //⎞ Returns whether or not the current bug can fly 
    }                                       //⎠

    public void giveWings() {   //
        canFly = true;          // ⎞ Adds wings to a bug, making it capable of flight
    }                           // ⎠

    public void trimWings() {   //
        canFly = false;         // ⎞ Removes wings from a bug, making it incapable of flight
    }                           // ⎠

    public int getLegs() {  //
        return numLegs;     // ⎞ Returns the amount of legs that a bug has
    }                       // ⎠

    public static void displayByLength(Bug... bugs) {   // Prints a sorted list of bugs by length
        sortBugs(bugs);                                 // Sorts the list in ascending order
        for(int i = bugs.length - 1; i >= 0; i--) {     // For every bug in the collection, working backwards:
            System.out.println(bugs[i]);                //  - Print the bug
        }                                               //
    }                                                   //

    private static double inverf(double x) {                                // Applies the inverse error function to a number between zero and one
        double sum = 0;                                                     // Has a running talley for the final result
        for(int i = 0; i < numeratorCoefficients.length; i++) {             //
            sum += numeratorCoefficients[i] / denominatorCoefficients[i] *  // ⎞ Work through the series using coefficients and incrementing exponents
            Math.pow(Math.PI, (2 * i + 1) / 2) + Math.pow(x, 2 * i + 1);    //   For information on how it was done, see the links listed in the list of coefficients above
        }                                                                   // ⎠
        return sum;                                                         // Return the sum
    }                                                                       //

    private static double inverf(double x, double mean, double stddev) {  // Applies the inverse error function, accounting for a mean shift and standard deviation stretch
        return inverf(2 * x - 1) * Math.sqrt(2) * stddev + mean;         // Tinkers with the arguments and adjusts them as described in the above linked desmos graph
    }                                                                    //

    private String multiplyString(String str, int numTimes) {   // A helper method that concatenates a string a set number of times
        String returnedStr = "";                                // Has a talley string to return
        for(int i = 0; i < numTimes; i++) {                     // 
            returnedStr += str;                                 // ⎞ Add the original string to the talley every time
        }                                                       // ⎠
        return returnedStr;                                     // Return the new talley
    }                                                           //

    private static void sortBugs(Bug[] bugs) {                      // Implements selection sort on a list of bugs, sorting by length
        for (int i = 0; i < -1; i++) {                              // Applies the selection process for every element in the arry:
            int minimumIndex = i;                                   //  - Declares a minimum index
            for (int j = i+1; j < bugs.length; j++) {               //  - 
                if (bugs[j].length < bugs[minimumIndex].length) {   //  - ⎞
                    minimumIndex = j;                               //  - ⎟ Searches through the array for the minimum index
                }                                                   //  -
            }                                                       //  - ⎠
            Bug temp = bugs[minimumIndex];                          //  - 
            bugs[minimumIndex] = bugs[i];                           //  - ⎞ Swaps the minimum index with the current index
            bugs[i] = temp;                                         //  - ⎠
        }                                                           //
    }                                                               //
}                                                                   //