package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;
import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /** The commit directory in .gitlet. */
    public static final File COMMIT_DIR = join(Repository.GITLET_DIR, "commit");
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
    private HashMap<String, String> fileNameToBLOB;


    /* TODO: fill in the rest of this class. */
    Commit(String message, String parent) {
        this.message = message;
        this.parent = parent;
    }

    /** Initial commit. */
    Commit() {
        this.message = "initial commit";
        this.timeStamp = new Date(0);
    }

    // ToDo: get the parent commit
    // ToDo: Somehow make everything in one go so that
    //       it can be called by makeCommit() in Repository
    // ToDo: Track and save new file in fileNameToBlob that parent didn't track

    /** Commit a commit and set up EVERYTHING in one go. */
    public void makeCommit() {
        // ToDo
    }

    // ToDo: check staging area, if there's same filename but different Blob,
    //       update fileNameToBlob or if there's same Blob but differnet name,
    //       remove the former name and add current name.

    // ToDo: add file that staged add.

    // ToDo: remove the file that staged deleted.

    /** Get parent commit. */
    public Commit getParent() {
        File parent = join(COMMIT_DIR, this.parent);
        if (!parent.exists()) {
            return null;
        }
        return readObject(parent, Commit.class);
    }
}
