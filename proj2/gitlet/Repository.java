package gitlet;

import java.io.File;
import java.io.IOException;

import static gitlet.Utils.*;

/** Represents a gitlet repository.
 *  The structure of a gitlet is as follows:
 *  .gitlet/ -- top level folder for all persistent data
 *    - commit -- store commit info
 *    - branches -- store branch
 *    - index -- store staging files
 *    - HEAD -- store HEAD reference
 *  @author Bowen
 */
public class Repository {
    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /** Create filesystem to allow for persistence. */
    public static void setUpPersistence() {
        try {
            GITLET_DIR.mkdir();
            Commit.COMMIT_DIR.mkdir();
            Branch.HEAD.createNewFile();
            Branch.BRANCH_DIR.mkdirs();
            Stage.INDEX.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Creates a new Gitlet version-control system in the current
     *  directory.
     *  This will automatically start with one commit with message
     *  "initial commit".
     *  It will have a single branch "master" pointing to this
     *  initial commit.
     *  Timestamp will be 00:00:00 UTC, Thursday, 1 January 1970.
     */
    public static void initCommend() {
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        setUpPersistence();
        Stage initStage = new Stage();
        Stage.saveStage(initStage);
        Commit initialCommit = new Commit();
        initialCommit.saveCommit();
        Branch.newBranch("Master");
        Branch.writeBranch(sha1((Object) serialize(initialCommit)));
    }

    /** Saves a snapshot of tracked files in the current commit and
     *  staging area so they can be restored at a later time, creating
     *  a new commit.
     */
    public static void makeCommit(String test) {
        // No log message
//        if (args.length == 1) {
//            System.out.println("Please enter a commit message.");
//            System.exit(0);
//        }
        Stage stage = Stage.load();
        if (stage.getStagedAddition().isEmpty() && stage.getStagedRemoval().isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        }
        Commit newCommit = new Commit(test, Commit.getHeadCommit().getCommitID(), null);
        newCommit.makeCommit();
        newCommit.saveCommit();
        Branch.writeBranch(newCommit.getCommitID());
    }

    /** Add new created files or edited files to staging area. */
    public static void addCommend(String fileName) {
        checkFolderGitleted();
        File file = join(CWD, fileName);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        }
        Stage stage = Stage.load();
        stage.addStage(fileName);
    }

    /** Remove files. */
    public static void rmCommend(String fileName) {
        checkFolderGitleted();
        File file = join(CWD, fileName);
        Stage stage = Stage.load();
        stage.removeStage(fileName);
    }

    /** Check whether current folder is gitleted folder. */
    public static void checkFolderGitleted() {
        if (!GITLET_DIR.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
    }

    /** Log all the message in current branch. */
    public static void logCommend() {
        checkFolderGitleted();
        Commit head = Commit.getHeadCommit();
        Commit curr = head;
        System.out.println("===");
        System.out.println("commit " + curr.getCommitID());
        System.out.println("Date: " + curr.getTimeStamp());
        System.out.println(curr.getMessage());
        System.out.println();
        // Todo: If have two parent commit, add one line. merged ...
        while (curr.getParent() != null) {
            curr = curr.getParent();
            System.out.println("===");
            System.out.println("commit " + curr.getCommitID());
            System.out.println("Date: " + curr.getTimeStamp());
            System.out.println(curr.getMessage());
            System.out.println();
        }
    }

    /** Log all the message. */
    public static void globalLogCommend() {
        for (String fileName : plainFilenamesIn(Commit.COMMIT_DIR)) {
            File file = join(Commit.COMMIT_DIR, fileName);
            Commit curr = readObject(file, Commit.class);
            System.out.println("===");
            System.out.println("commit " + curr.getCommitID());
            System.out.println("Date: " + curr.getTimeStamp());
            System.out.println(curr.getMessage());
            System.out.println();
        }
    }
}
