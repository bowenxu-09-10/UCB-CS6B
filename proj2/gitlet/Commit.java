package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import static gitlet.Utils.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Bowen
 */
public class Commit implements Serializable {
    /** The commit directory in .gitlet. */
    public static final File COMMIT_DIR = join(Repository.GITLET_DIR, "commit");

    /** The message of this Commit. */
    private String message;

    /** The timestamp if this Commit. */
    private Date timeStamp;

    /** The parent commit of current commit. */
    private String parent;

    /** The file current commit tracked. */
    private HashMap<String, String> fileNameToBLOB;

    Commit(String message, String parent) {
        this.message = message;
        this.parent = parent;
    }

    /** Initial commit. */
    Commit() {
        this.message = "initial commit";
        this.timeStamp = new Date(0);
        this.parent = null;
    }

    // ToDo: Somehow make everything in one go so that
    //       it can be called by makeCommit() in Repository

    /** Commit a commit and set up EVERYTHING in one go. */
    public void makeCommit() {
        // ToDo
    }

    /** Get parent commit. */
    public Commit getParent() {
        if (this.parent == null) {
            return null;
        }
        File parentFile = join(COMMIT_DIR, this.parent);
        return readObject(parentFile, Commit.class);
    }

    /** Copy its parent tracking file and update it according to
     *  the staging area. */
    private void updateFile() {
        Commit parent = this.getParent();
        fileNameToBLOB = parent.fileNameToBLOB;
        // ToDo: check staging area. add or remove.
    }

    /** Get head commit. */
    public static Commit getHeadCommit() {
        String headSha1 = Branch.getHeadBranch();
        File headCommit = join(COMMIT_DIR, headSha1);
        return readObject(headCommit, Commit.class);
    }
}
