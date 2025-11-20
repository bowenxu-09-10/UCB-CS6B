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
    public static final File COMMIT_DIR = join(Repository.GITLET_DIR, "commits");

    /** The message of this Commit. */
    private String message;

    /** The timestamp if this Commit. */
    private Date timeStamp;

    /** The parent commit of current commit. */
    private String parent;

    /** The second parent of current commit. (It'll happen in merge.) */
    private String secondParent;

    /** The file current commit tracked. */
    public HashMap<String, String> fileNameToBLOB;

    Commit(String message, String parent, String secondParent) {
        this.message = message;
        this.parent = parent;
        this.secondParent = secondParent;
    }

    /** Initial commit. */
    Commit() {
        this.message = "initial commit";
        this.timeStamp = new Date(0);
        this.parent = null;
        this.secondParent = null;
        Stage stage = Stage.load();
        System.out.println("Before commit: " + stage.getStagedAddition());
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

        Stage stage = readObject(Stage.INDEX, Stage.class);
        HashMap<String, String> stagedAdditon = stage.getStagedAddition();
        Set<String> stagedRemoval = stage.getStagedRemoval();

        Commit parent = this.getParent();
        if (parent.fileNameToBLOB == null) {
            this.fileNameToBLOB = new HashMap<>(stagedAdditon);
            return;
        }

        // Copy parent Commit file map.
        this.fileNameToBLOB = new HashMap<>(parent.fileNameToBLOB);

        // Remove
        for (String fileName : stagedRemoval) {
            this.fileNameToBLOB.remove(fileName);
        }
        // Addition
        for (String fileName : stagedRemoval) {
            this.fileNameToBLOB.put(fileName, stage.saveAndGetFilePID(fileName));
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
        if (timeStamp == null) {
            this.timeStamp = new Date();
        }
        File commit = join(COMMIT_DIR, sha1((Object) serialize(this)));
        try {
            commit.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeObject(commit, this);
        Stage.clear();
    }
}
