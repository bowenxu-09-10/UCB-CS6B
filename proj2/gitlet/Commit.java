package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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

    /** The pid of this commit. */
    private String pid;

    /** The file current commit tracked. */
    private HashMap<String, String> fileNameToBLOB;

    Commit(String message, String parent, String secondParent) {
        this.message = message;
        this.parent = parent;
        this.secondParent = secondParent;
        this.fileNameToBLOB = new HashMap<>(getParent().getFileNameToBLOB());
    }

    /** Initial commit. */
    Commit() {
        this.message = "initial commit";
        this.timeStamp = new Date(0);
        this.parent = null;
        this.secondParent = null;
        this.fileNameToBLOB = new HashMap<>();
    }

    /** Get commit by sha1. */
    public static Commit getCommit(String pid) {
        File commit = join(COMMIT_DIR, pid);
        if (!commit.exists()) {
            System.out.println("No commit with that id exists.");
            System.exit(0);
        }
        return readObject(commit, Commit.class);
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

        if (this.fileNameToBLOB.isEmpty()) {
            this.fileNameToBLOB = new HashMap<>(stagedAdditon);
            return;
        }

        // Remove
        for (String fileName : stagedRemoval) {
            this.fileNameToBLOB.remove(fileName);
        }
        // Addition
        this.fileNameToBLOB.putAll(stagedAdditon);

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
        setPid();
        File commit = join(COMMIT_DIR, getPid());
        try {
            commit.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeObject(commit, this);
        Stage.clear();
    }

    /** Get timestamp. */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /** Get message. */
    public String getMessage() {
        return message;
    }

    /** Get fileNameToBlob. */
    public HashMap<String, String> getFileNameToBLOB() {
        return fileNameToBLOB;
    }

    /** Get commit id. */
    public String getPid() {
        return pid;
    }

    /** Set commit id. */
    public void setPid() {
        this.pid = sha1(this.parent + this.secondParent + this.message + this.timeStamp);
    }

    /** Remove all the tracked files but not tracked in commit. */
    public static void removeFile(Commit commit) {
        HashMap<String, String> blobs = commit.getFileNameToBLOB();
        for (String fileName : plainFilenamesIn(Repository.CWD)) {
            File fileInCWD = join(Repository.CWD, fileName);
            if (!blobs.keySet().contains(fileInCWD)) {
                fileInCWD.delete();
            }
        }
    }

    /** Import all the file in commit. */
    public static void importFile(Commit commit) {
        HashMap<String, String> blobs = commit.getFileNameToBLOB();
        for (String fileName : blobs.keySet()) {
            File fileInCWD = join(Repository.CWD, fileName);
            File fileInBlob = join(Blob.BLOB_FOLDER, blobs.get(fileName));
            if (fileInCWD.exists()) {
                writeContents(fileInCWD, readContents(fileInBlob));
            } else {
                try {
                    fileInCWD.createNewFile();
                    writeContents(fileInCWD, readContents(fileInBlob));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /** Find the split commit of current head and branch given. */
    public static String findSplitCommit(String branchName) {
        File branch = join(Branch.BRANCH_DIR, branchName);
        Commit inBranch = getCommit(readContentsAsString(branch));
        Commit curr = getHeadCommit();
        Set<String> idInCurr = new HashSet<>();
        idInCurr.add(curr.pid);
        // Log all the commit id of current head.
        while (curr.parent != null) {
            idInCurr.add(curr.parent);
            curr = getCommit(curr.parent);
        }
        // Register the first id that in the set.
        if (idInCurr.contains(inBranch.pid)) {
            return inBranch.pid;
        }
        while (inBranch.parent != null) {
            if (idInCurr.contains(inBranch.parent)) {
                return inBranch.parent;
            }
            inBranch = getCommit(inBranch.parent);
        }
        return null;
    }

    /** Determine whether the given branch is the ancestor of current.
     *  Return 'branchIsAncestor' if the given branch is ancestor.
     *  Return 'headIsAncestor' if current head is ancestor.
     *  Return 'neitherIsAncestor' if neither is ancestor. */
    public static String ancestorCheck(String branchName) {
        File branch  = join(Branch.BRANCH_DIR, branchName);
        String branchID = readContentsAsString(branch);
        Commit inBranch = getCommit(branchID);
        Commit head = getHeadCommit();
        // If branch is ancestor.
        String split = findSplitCommit(branchName);
        if (split.equals(branchID)) {
            System.out.println("Given branch is an ancestor of the current branch.");
            return "branchIsAncestor";
        }

        // If current head is ancestor of given branch.
        if (split.equals(head.pid)) {
            return "headIsAncestor";
        }
        return "neitherIsAncestor";
    }
}
