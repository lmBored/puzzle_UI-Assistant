/**
 * Package containing the model of a Kakuro puzzle.
 * The class {@link KPuzzle} also acts as a <em>façade</em> for this package.
 * However, also class {@link kpa.model.KCell} is important:
 * <ul>
 * <li>To iterate over all cells:
 *      {@code for (KCell cell : puzzle.getCells())}</li>
 * <li>To create a new {@link kpa.command.SetCommand}</li>
 * <li>To check whether a cell is empty:
 *      {@link kpa.model.KCell#isEmpty()}</li>
 * <li>To obtain the empty cell state: {@link kpa.model.KCell#EMPTY}</li>
 * </ul>
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
package ypa.model;
