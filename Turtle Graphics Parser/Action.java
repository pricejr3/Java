/**
 * Jarred Price
 * This is a turtle program that is used with Parser.java
 * to draw the Turtle commands to the Turtle Canvas.
 *
 */

/**
 * Represents a drawable turtle action.
 */
public class Action {

    ActionType type;
    String displayString;
    LineSegment lineSeg;

    public Action(ActionType type, String displayString, LineSegment lineSeg) {
        this.type = type;
        this.displayString = displayString;
        this.lineSeg = lineSeg;
    }

    public String toString() {
        if (displayString == null) {
            return "";
        } else {
            return displayString;
        }
    }
}

/**
 * Enumeration of turtle action types.
 */
enum ActionType {
    FORWARD, TURN, COLOR
}
