package cs2103t.duke.command;

import cs2103t.duke.exception.DukeException;
import cs2103t.duke.exception.EmptyDescriptionException;
import cs2103t.duke.exception.InvalidIdException;
import cs2103t.duke.file.Storage;
import cs2103t.duke.task.Note;
import cs2103t.duke.task.NoteList;
import cs2103t.duke.task.Task;
import cs2103t.duke.task.TaskList;
import cs2103t.duke.ui.Ui;

import java.util.List;
import java.util.Scanner;

public class DeleteNotesCommand extends Command {
    private String content;
    private boolean isDeletingGeneral;
    private int id;

    public DeleteNotesCommand(String msg) {
        distinguishCommand(msg);
    }

    private void distinguishCommand(String msg) {
        Scanner sc = new Scanner(msg);
        if (!sc.hasNext()) {
            throw new EmptyDescriptionException("");
        }

        String token1 = sc.next();
        switch (token1.toUpperCase()) {
        case "GENERAL":
            isDeletingGeneral = true;
            break;
        case "TASK":
            isDeletingGeneral = false;
            break;
        default:
            throw new DukeException("must give either general or task keywords");
        }

        if (!sc.hasNextInt()) {
            throw new InvalidIdException(sc.next());
        }

        id = sc.nextInt();
        sc.close();
    }

    @Override
    public String execute(TaskList taskList, Ui ui, Storage storageTasks, Storage storageNotes, NoteList noteList) {
        if (isDeletingGeneral) {
            return ui.dukeRespond(deleteGeneralNote(noteList, id));
        } else {
            return ui.dukeRespond(deleteTaskNote(taskList, noteList, id));
        }
    }

    private String deleteGeneralNote(NoteList notes, int id) {
        List<Note> genNotes = notes.getGeneralNotes();
        if (id > genNotes.size() || id  < 1) {
            throw new InvalidIdException("" + id);
        }

        Note n = notes.deleteNote(notes.getGenNoteId(id - 1));
        notes.getGeneralNotes().remove(id - 1);

        return String.format("Got it. Deleted general note: %s", n.toString());
    }

    private String deleteTaskNote(TaskList tasks, NoteList notes, int id) {
        if (id > tasks.getSize() || id < 1) {
            throw new InvalidIdException("" + id);
        }

        Task t = tasks.retrieveTask(id);
        int noteid = t.getNoteId();
        t.deleteNotes();
        notes.deleteNote(noteid);

        return String.format("Got it. Deleted note for task %d", id);
    }
}
