package cs2103t.duke.task;

import cs2103t.duke.ui.Ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class NoteList {
    private List<Note> notes;
    private List<Note> generalNotes;
    /** Ids of all the notes deleted so far. */
    private TreeSet<Integer> deletedIds;

    public NoteList() {
        this.notes = new ArrayList<>();
        deletedIds = new TreeSet<>();
        generalNotes = new ArrayList<>();
    }

    public NoteList(List<Note> notes) {
        this.notes = notes;
        deletedIds = new TreeSet<>();
        generalNotes = new ArrayList<>();
        extractGeneralNotes(generalNotes, notes);
    }

    private void extractGeneralNotes(List<Note> genNotes, List<Note> notes) {
        for (Note n : notes) {
            if (n.isGeneral()) {
                genNotes.add(n);
            }
        }
    }

    /**
     * Gets note at id.
     * @param id 0-based id
     * @return note
     */
    public Note getNote(int id) {
        return this.notes.get(id);
    }

    /**
     * Gets the list of notes.
     * @return list of all notes.
     */
    public List<Note> getNoteList() {
        return notes;
    }

    public List<Note> getGeneralNotes() {
        return generalNotes;
    }

    public int getNoteListSize() {
        return notes.size();
    }

    public void addNote(Note n) {
        notes.add(n);
    }

    public void addGeneralNote(Note n) {
        generalNotes.add(n);
    }

    /**
     * Marks note as deleted. Note will be deleted when program is terminating.
     * @param id id of the note to be deleted.
     * @return the note to be deleted.
     */
    public Note deleteNote(int id) {
        this.deletedIds.add(id);
        Note n = getNote(id);
        n.setDeleted();
        return n;
    }

    public int getGenNoteId(int id) {
        int genId = 0;
        Note gen = generalNotes.get(id);
        for (Note n : notes) {
            if (n == gen) {
                break;
            }
            genId++;
        }
        return genId;
    }

    /**
     * Gets the updated task note id. //This will be used before writing to the file.
     * @param id current (not updated) id of note (of some task).
     * @return updated id of note.
     */
    public int getUpdatedTaskNoteId(int id) {
        Iterator<Integer> it = deletedIds.iterator();
        int numDeletedIdsLessThanId = 0;
        while (it.hasNext() && id > it.next()) {
            numDeletedIdsLessThanId++;
        }
        System.out.println(id - numDeletedIdsLessThanId);
        return id - numDeletedIdsLessThanId;
    }


    /**
     * Prints out the list of general notes.
     * @param ui ui in charge of printing.
     */
    public String listGeneralNotes(Ui ui) {
        String[] noteStrings = new String[generalNotes.size() + 1];
        noteStrings[0] = "Here are the general notes in your list:";
        int listIndex = 0;
        for (Note n : generalNotes) {
            listIndex++;
            noteStrings[listIndex] = String.format("%d.%s", listIndex, n.toString());
        }
        return ui.dukeRespond(noteStrings);
    }
}
