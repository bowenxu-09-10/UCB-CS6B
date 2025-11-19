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

    /** Save file as blob file, and return its pid. */
    private static String saveFile(String fileName) {
        // Read contents in file and make a blob.
        Blob blob = new Blob(fileName);
        return blob.saveBlob();
    }

    /** Check if current file added is the same as commited.
     *  True for is the same as committed. */
    private static boolean checkSameCommit(String fileName) {
        Commit head = Commit.getHeadCommit();
        HashMap<String, String> fileTracted = head.fileNameToBLOB;
        return fileTracted.containsValue(saveFile(fileName));
    }

    /** Check if current file added is the same as staged.
     *  True for is the same as committed. */
    private static boolean checkSameStage(String fileName) {
        Commit head = Commit.getHeadCommit();
        HashMap<String, String> fileTracted = head.fileNameToBLOB;
        return fileTracted.containsValue(saveFile(fileName));
    }

    /** Add file to staging area. If added one is the same as commited, but in staged area,
     *  then remove it from stagedAddition. */
    public static void addStage(String fileName) {
        if (checkSameCommit(fileName)) {
            if (stagedAddition.containsValue(saveFile(fileName))) {
                stagedAddition.remove(fileName);
            }
        }
        stagedAddition.put(fileName, sha1(fileName));
    }

    /** Get staded for addition hashmap. */
    public static HashMap<String, String> getStagedAddition() {
        return stagedAddition;
    }

    /** Get staded for removal hashmap. */
    public static Set<String> getStagedRemoval() {
        return stagedRemoval;
    }

    /** Clear all the stage area. */
    public static void clear() {
        stagedAddition = null;
        stagedRemoval = null;
    }
}
