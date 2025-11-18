package gitlet;

import java.io.File;
import java.io.IOException;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  The structure of a gitlet is as follows:
 *  .gitlet/ -- top level folder for all persistent data
 *    - commit -- store commit info
 *    - index -- store staging files
 *    - HEAD -- store HEAD reference
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /* TODO: fill in the rest of this class. */

    /** Create filesystem to allow for persistence. */
    public static void setUpPersistence() {
        try {
            GITLET_DIR.mkdir();
            Commit.COMMIT_DIR.mkdir();
            Branch.HEAD.createNewFile();
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
     *  This will automatically start with one commit with message
     *  "initial commit".
     *  It will have a single branch "master" pointing to this
     *  initial commit.
     *  Timestamp will be 00:00:00 UTC, Thursday, 1 January 1970.
     */
    public void initSystem() {
        setUpPersistence();
        Commit intialCommit = new Commit();
        // ToDo: to save the commit into a file
        // Call branch, and make it point to it
    }

    /** Saves a snapshot of tracked files in the current commit and
     *  staging area so they can be restored at a later time, creating
     *  a new commit.
     */
    public void makeCommit() {
        // ToDo
    }

}
