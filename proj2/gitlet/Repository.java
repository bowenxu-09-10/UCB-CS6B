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

    /** Staging area. */
    private static Stage stageArea;

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
        Commit initialCommit = new Commit();
        Branch.newBranch("Master");
        Branch.writeBranch(sha1(initialCommit));
    }

    /** Saves a snapshot of tracked files in the current commit and
     *  staging area so they can be restored at a later time, creating
     *  a new commit.
     */
    public void makeCommit() {
        // ToDo
    }

    /** Add new created files or edited files to staging area. */
    public static void addCommend(String fileName) {
        checkFolderGitleted();
        File file = join(CWD, fileName);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        }
        stageArea.addStage(fileName);
    }

    /** Check whether current folder is gitleted folder. */
    public static void checkFolderGitleted() {
        if (!GITLET_DIR.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
    }
}
