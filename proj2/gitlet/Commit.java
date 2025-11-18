package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashSet;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit {
    /** The commit directory in .gitlet. */
    public static final File COMMIT_DIR = new File(Repository.GITLET_DIR, "commit");
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;

    /** The timestamp if this Commit. */
    private Date timeStamp;

    /** The parent commit of current commit. */
    private String parent;

    /** The file current commit tracked. */
    private HashSet<String> fileNameToBLOB;

    /** Number of tracked file. */
    private int size;

    /* TODO: fill in the rest of this class. */
    Commit(String message) {
        this.message = message;
    }

    /** Initial commit. */
    Commit() {
        this.message = "initial commit";
        this.timeStamp = new Date(0);
    }

    // ToDo: get the parent commit
}
