package ypa.command; // <<<<< TODO: Comment this line out when submitting to Momotor!

import java.util.Stack;

/**
 * Facilities for an undo-redo mechanism, on the basis of commands.
 *
<!--//# BEGIN TODO: Names, student IDs, group name, and date-->
<p><b>Group 6, dd/mm/yy</b></p>
<p><b>Miquel Ibáñez Solbes, 2118998</b></p>
<p><b>Vinh Nguyen, 1957104</b></p>
<p><b>Nam Mai, 1959190</b></p>
<p><b>Replace this line</b></p>
<p><b>Replace this line</b></p>
<p><b>Replace this line</b></p>
<!--//# END TODO-->
 */
public class UndoRedo {

    //# BEGIN TODO: Representation in terms of instance variables, incl. rep. inv.
    /** The undo stack. */
    private final Stack<Command> undoStack = new Stack<>();

    /** The redo stack. */
    private final Stack<Command> redoStack = new Stack<>();

    /* Rep invariant:
     *
     * In the current state, the sequence of commands on the undoStack
     * can be undone (from top to bottom; their preconditions are satisfied).
     *
     * In the current state, the sequence of commands on the redoStack
     * can be executed (from top to bottom; their preconditions are satisfied).
     */
    //# END TODO

    /**
     * Returns whether an {@code undo} is possible.
     *
     * @return whether {@code undo} is possible
     */
    public boolean canUndo() {
        //# BEGIN TODO: Implementation of canUndo
        return !undoStack.isEmpty();
        //# END TODO
    }

    /**
     * Returns whether a {@code redo} is possible.
     *
     * @return {@code redo().pre}
     */
    public boolean canRedo() {
        //# BEGIN TODO: Implementation of canRedo
        return !redoStack.isEmpty();
        //# END TODO
    }

    /**
     * Returns command most recently done.
     *
     * @return command at top of undo stack
     * @throws IllegalStateException if precondition failed
     * @pre {@code canUndo()}
     */
    public Command lastDone() throws IllegalStateException {
        //# BEGIN TODO: Implementation of lastDone
        if (!canUndo()) {
            throw new IllegalStateException("Precondition violated");
        } else {
            return undoStack.peek();
        }
        //# END TODO
    }

    /**
     * Returns command most recently undone.
     *
     * @return command at top of redo stack
     * @throws IllegalStateException if precondition failed
     * @pre {@code canRedo()}
     */
    public Command lastUndone() throws IllegalStateException {
        //# BEGIN TODO: Implementation of lastUndone
        if (canRedo()) {
            return redoStack.peek();
        } else {
            throw new IllegalStateException("Precondition violated");
        }
        //# END TODO
    }

    /**
     * Clears all undo-redo history.
     *
     * @modifies {@code this}
     */
    public void clear() {
        //# BEGIN TODO: Implementation of clear
        undoStack.clear();
        redoStack.clear();
        //# END TODO
    }

    /**
     * Adds given command to the do-history.
     * If the command was not yet executed, then it is first executed.
     *
     * @param command the command to incorporate
     * @modifies {@code this}
     */
    public void did(final Command command) {
        //# BEGIN TODO: Implementation of did
        if (!command.isExecuted()) {
            command.execute();
        }
        undoStack.push(command);
        redoStack.clear();
        //# END TODO
    }

    /**
     * Undo the most recently done command, optionally allowing it to be redone.
     *
     * @param redoable whether to allow redo
     * @throws IllegalStateException if precondition violated
     * @pre {@code canUndo()}
     * @modifies {@code this}
     */
    public void undo(final boolean redoable) throws IllegalStateException {
        //# BEGIN TODO: Implementation of undo
        if (!canUndo()) {
            throw new IllegalStateException("Precondition violated");
        }

        Command command = undoStack.pop();
        command.revert();

        if (redoable) {
            redoStack.push(command);
        }
        //# END TODO
    }

    /**
     * Redo the most recently undone command.
     *
     * @throws IllegalStateException if precondition violated
     * @pre {@code canRedo()}
     * @modifies {@code this}
     */
    public void redo() throws IllegalStateException {
        //# BEGIN TODO: Implementation of redo
        if (!canRedo()) {
            throw new IllegalStateException("Precondition violated");
        }
        Command command = redoStack.pop();
        command.execute();
        undoStack.push(command);
        //# END TODO
    }

    /**
     * Undo all done commands.
     *
     * @param redoable whether to allow redo
     * @modifies {@code this}
     */
    public void undoAll(final boolean redoable) {
        //# BEGIN TODO: Implementation of undoAll
        while (!undoStack.isEmpty()) {
            undo(redoable);
        }
        //# END TODO
    }

    /**
     * Redo all undone commands.
     *
     * @modifies {@code this}
     */
    public void redoAll() {
        //# BEGIN TODO: Implementation of redoAll
        while (!redoStack.isEmpty()) {
            redo();
        }
        //# END TODO
    }

}
