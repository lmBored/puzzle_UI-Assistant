package ypa.command; // <<<<< TODO: Comment this line out when submitting to Momotor!

/**
 * Facilities for an undo-redo mechanism, on the basis of commands.
 *
<!--//# BEGIN TODO: Names, student IDs, group name, and date-->
<p><b>Replace this line</b></p>
<!--//# END TODO-->
 */
public class UndoRedo {

//# BEGIN TODO: Representation in terms of instance variables, incl. rep. inv.
    // Replace this line
//# END TODO

    /**
     * Returns whether an {@code undo} is possible.
     *
     * @return whether {@code undo} is possible
     */
    public boolean canUndo() {
//# BEGIN TODO: Implementation of canUndo
        // Replace this line
        return false;
//# END TODO
    }

    /**
     * Returns whether a {@code redo} is possible.
     *
     * @return {@code redo().pre}
     */
    public boolean canRedo() {
//# BEGIN TODO: Implementation of canRedo
        // Replace this line
        return false;
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
        // Replace this line
        return null;
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
        // Replace this line
        return null;
//# END TODO
    }

    /**
     * Clears all undo-redo history.
     *
     * @modifies {@code this}
     */
    public void clear() {
//# BEGIN TODO: Implementation of clear
        // Replace this line
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
        // Replace this line
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
        // Replace this line
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
        // Replace this line
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
        // Replace this line
//# END TODO
    }

    /**
     * Redo all undone commands.
     *
     * @modifies {@code this}
     */
    public void redoAll() {
//# BEGIN TODO: Implementation of redoAll
        // Replace this line
//# END TODO
    }

}
