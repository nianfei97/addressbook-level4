package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.ModelManagerTestUserStub;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;

public class UndoCommandTest {

    private final Model model = new ModelManagerTestUserStub(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManagerTestUserStub(getTypicalAddressBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model.matchUserToPerson("Alice Pauline");
        expectedModel.matchUserToPerson("Alice Pauline");

        model.updateTimeTable(model.getUser().getTimeTable());
        expectedModel.updateTimeTable(expectedModel.getUser().getTimeTable());

        // set up of models' undo/redo history
        deleteFirstPerson(model);
        deleteFirstPerson(model);

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
