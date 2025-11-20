package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

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
    public HashMap<String, String> fileNameToBLOB;

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

    /** Commit a commit and set up EVERYTHING in one go. */
    public void makeCommit() {
        updateFile();
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
        HashMap<String, String> parentFileTrack = parent.fileNameToBLOB;
        // Copy parent Commit file map.
        this.fileNameToBLOB = new HashMap<>(fileNameToBLOB);
        for (String fileName : fileNameToBLOB.keySet()) {
            Set<String> stagedRemoval = Stage.getStagedRemoval();
            HashMap<String, String> stagedAdditon = Stage.getStagedAddition();

            if (stagedAdditon.isEmpty() && stagedRemoval.isEmpty()) {
                System.out.println("No changes added to the commit.");
                System.exit(0);
            }
            // Remove
            if (stagedRemoval.contains(fileName)) {
                fileNameToBLOB.remove(fileName);
            }
            // Addition
            if (stagedAdditon.containsKey(fileName)) {
                fileNameToBLOB.put(fileName, Stage.saveAndGetFilePID(fileName));
            }
        }
    }

    /** Get head commit. */
    public static Commit getHeadCommit() {
        String headSha1 = Branch.getHeadBranch();
        File headCommit = join(COMMIT_DIR, headSha1);
        return readObject(headCommit, Commit.class);
    }

    /** Save commit into a file to make persistence. */
    public void saveCommit() {
        this.timeStamp = new Date();
        File commit = join(COMMIT_DIR, sha1(this));
        try {
            commit.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeObject(commit, this);
        Stage.clear();
    }
}
