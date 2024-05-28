/**
 * Package containing the Kakuro Puzzle Assistant.
 * See <a href="http://en.wikipedia.org/wiki/Kakuro">Wikipedia</a>
 * for an explanation of Kakuro puzzles.
 *
 * <p>
 * This Kakuro Puzzle Assistant has the following goals:
 * <ul>
 * <li>Show a graphical view of a Kakuro puzzle loaded from a text file.
 * <li>Let you solve a Kakuro puzzle interactively in the graphical view.
 * <li>Automatically apply reasoning strategies,
 *   to help solve a Kakuro puzzle.
 * <li>Automatically solve a Kakuro puzzle.
 * <li>Let you construct and edit Kakuro puzzles.
 * </ul>
 *
 * <p>
 * Development status:
 * <ul>
 * <li>[TODO] Default close action of main frame equals Exit menu item.
 * <li>[Done] Load puzzle state from text file.
 * <li>[Done] Save puzzle state to text file.
 * <li>[Done] Show graphical view of puzzle state.
 * <li>[Done] Show textual view of puzzle state in message area
 *   (File &gt; Dump).
 * <li>[Done] Text can be copied to clipboard from message area.
 * <li>[Done] Clear message area.
 * <li>[Done] Change non-blocked cell states interactively.
 * <li>[TODO] Undo, Undo All, Redo, Redo All (menu items exist).
 * <li>[Done] Clear non-blocked cells (without confirmation).
 * <li>[Done] Change mode (but Edit mode is not supported).
 * <li>[TODO] Support Edit mode.
 * <li>[Done] Confirmation on discarding a changed puzzle state.
 * <li>[Done] Enable/disable highlighting
 *   (is supported in {@link kpa.gui.PuzzlePanel PuzzlePanel},
 *   but not yet used in {@link kpa.gui.MainFrame MainFrame}).
 * <li>[Done] Reasoning strategies.
 * <li>[Done] Backtrack solver.
 * <li>[Done] Highlight cells that were changed in the previous action.
 * <li>[Done] Help/About menu items with rules and developer info.
 * <li>[TODO] Keep track of some statistics.
 * <li>[TODO] Make cell size modifiable (also needs to zoom fonts).
 * <li>[Done] Smoke tests.
 * <li>[TODO] Incorporate more extensive test cases.
 * </ul>
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
package kpa;
