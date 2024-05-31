package ypa.command;

/**
 * Base class to represent an executable and revertible command,
 * applied to a receiving object of type {@code R}.
 * Concrete command classes extend this base class,
 * by adding parameters (when needed), revert state (when needed), and
 * overriding execute() and revert().
 *
 * This base class can already be used as a command that does nothing.
 *
 * @param <R>  the type of the receiver
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class GenericCommand<R> extends Command {

    /** The receiving object. */
    protected final R receiver;

    /**
     * Constructs a command for a given receiver.
     *
     * @param receiver  the given receiver
     * @pre {@code receiver != null}
     * @throws NullPointerException  if {@code receiver == null}
     */
    public GenericCommand(final R receiver) throws NullPointerException {
        super(false);
        if (receiver == null) {
            throw new NullPointerException(getClass().getSimpleName()
                    + "(receiver).pre failed: receiver == null");
        }
        this.receiver = receiver;
    }

}
