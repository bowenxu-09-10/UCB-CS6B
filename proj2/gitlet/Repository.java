package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static gitlet.Utils.*;

/** Represents a gitlet repository.
 *  The structure of a gitlet is as follows:
 *  .gitlet/ -- top level folder for all persistent data
 *    - commit -- store commit info
 *    - index -- store staging files
 *    - HEAD -- store HEAD reference
 *  @author Bowen
 */
public class Repository {
    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The index file acts as staging area. */
    public static final File INDEX = join(GITLET_DIR, "index");

    /** A Map for staged file for addition. */
    private Map<String, String> stagedAddition;
    /** A Set for staged file for removal. */
    private Set<String> stagedRemoval;

    /** Create filesystem to allow for persistence. */
    public static void setUpPersistence() {
        try {
            GITLET_DIR.mkdir();
            Commit.COMMIT_DIR.mkdir();
            Branch.HEAD.createNewFile();
            Branch.BRANCH_DIR.mkdirs();
            INDEX.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Creates a new Gitlet version-control system in the current
     *  directory.
     *  The structure of a gitlet is as follows:
     *  .gitlet/ -- top level folder for all persistent data
     *      - commit -- store commit info
     *      - index -- store staging files
     *      - HEAD -- store current commit reference
     *      - branches -- store branches
     *  This will automatically start with one commit with message
     *  "initial commit".
     *  It will have a single branch "master" pointing to this
     *  initial commit.
     *  Timestamp will be 00:00:00 UTC, Thursday, 1 January 1970.
     */
    public static void initCommend() {
        setUpPersistence();
        Commit initialCommit = new Commit();
        Branch.newBranch("Master");
        // ToDo: create a master branch, and save current commit.

    }

    /** Saves a snapshot of tracked files in the current commit and
     *  staging area so they can be restored at a later time, creating
     *  a new commit.
     */
    public void makeCommit() {
        // ToDo
    }

    /** Add new created files or edited files to staging area. */
    public static void addFile(String[] args) {
        checkFolderGitleted();
        for (int i = 1; i < args.length; i++) {
            File curr = join(CWD, args[i]);
            if (!curr.exists()) {
                System.out.println("File does not exist.");
                System.exit(0);
            }
        }
        Commit head = Commit.getHeadCommit();
        HashMap tractedFile = head.fileNameToBLOB;

    }

    /** Check whether current folder is gitleted folder. */
    public static void checkFolderGitleted() {
        if (!GITLET_DIR.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
    }
}
