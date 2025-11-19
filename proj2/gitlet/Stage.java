package gitlet;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import static gitlet.Utils.*;

public class Stage {
    /** A Map for staged file for addition. */
    private static HashMap<String, String> stagedAddition;
    /** A Set for staged file for removal. */
    private static Set<String> stagedRemoval;

    /** The index file acts as staging area. */
    public static final File INDEX = join(Repository.GITLET_DIR, "index");

    /** Save file as blob file, and return whether the file already exist.
     *  True for exist, false for non-exist. */
    private static boolean saveFile(String fileName) {
        // Read contents in file and make a blob.
        Blob blob = new Blob(fileName);
        return blob.saveBlob();
    }

    /** Check if current file added is the same as commited.
     *  True for is the same as committed. */
    private static boolean checkSame(String fileName) {
        String pid = sha1(fileName);
        Commit head = Commit.getHeadCommit();
        HashMap fileTracted = head.fileNameToBLOB;
        if (fileTracted.containsValue(pid)) {
            return true;
        }
        return false;
    }

    /** Add file to staging area. */
    private static void addStage(String fileName) {
        if (checkSame(fileName)) {
            return;
        }
        stagedAddition.put(fileName, sha1(fileName));
    }

    /** Get staded for addition hashmap. */
    public static HashMap getStagedAddition() {
        return stagedAddition;
    }

    /** Get staded for removal hashmap. */
    public static Set getStagedRemoval() {
        return stagedRemoval;
    }
}
