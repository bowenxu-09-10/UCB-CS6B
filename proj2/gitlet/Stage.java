package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import static gitlet.Utils.*;

public class Stage implements Serializable {
    /** A Map for staged file for addition. */
    private HashMap<String, String> stagedAddition;
    /** A Set for staged file for removal. */
    private Set<String> stagedRemoval;

    /** The index file acts as staging area. */
    public static final File INDEX = join(Repository.GITLET_DIR, "index");
    Stage() {
        this.stagedAddition = new HashMap<>();
        this.stagedRemoval = new HashSet<>();
    }
    /** Load stage index content to this stage. */
    public static Stage load() {
        if (INDEX.length() == 0) {
            return new Stage();
        }
        return readObject(INDEX, Stage.class);
    }

    /** Save file as blob file, and return its pid. */
    private void saveFile(String fileName) {
        // Read contents in file and make a blob.
        Blob blob = new Blob(fileName);
        blob.saveBlob();
    }

    /** Check if current file added is the same as commited.
     *  True for is the same as committed. */
    private boolean checkSameFile(String fileName) {
        Commit head = Commit.getHeadCommit();
        if (head.fileNameToBLOB == null) {
            return false;
        }
        HashMap<String, String> fileTracked = head.fileNameToBLOB;
        Blob blob = new Blob(fileName);
        return fileTracked.containsValue(blob.getId());
    }

    /** Add file to staging area. If added one is the same as commited, but in staged area,
     *  then remove it from stagedAddition. */
    public void addStage(String fileName) {
        // If file added is staged for removal, then remove it from remove staging area.
        stagedRemoval.remove(fileName);
        Blob blob = new Blob(fileName);
        String blobId = blob.getId();
        // If added one is the same as commited, remove it from stagedAddition
        if (checkSameFile(fileName)) {
            stagedAddition.remove(fileName);
            saveStage(this);
            return;
        }
        this.stagedAddition.put(fileName, blobId);
        saveStage(this);
    }

    /** Remove file to staging area, if file is in the stage area then unstage it.
     *  If the file is tracked, then staged for removal. */
    public void removeStage(String fileName) {
        // If the file is staged, then unstage.
        if (stagedAddition.containsKey(fileName)) {
            stagedAddition.remove(fileName);
            saveStage(this);
            return;
        }
        // If the file is tracked, then stage it removal and delete it.
        Commit head = Commit.getHeadCommit();
        if (head.fileNameToBLOB.containsKey(fileName)) {
            stagedRemoval.add(fileName);
            File removedFile = join(Repository.CWD, fileName);
            removedFile.delete();
            saveStage(this);
            return;
        }
        System.out.println("No reason to remove the file.");
        System.exit(0);
    }

    /** Get staded for addition hashmap. */
    public HashMap<String, String> getStagedAddition() {
        return stagedAddition;
    }

    /** Get staded for removal hashmap. */
    public  Set<String> getStagedRemoval() {
        return stagedRemoval;
    }

    /** Clear all the stage area. */
    public static void clear() {
        saveStage(new Stage());
    }
    /** Save staging area into a file. */
    public static void saveStage(Stage stage) {
        writeObject(INDEX, stage);
    }

    /** Print status. */
    public void printStatus() {
        System.out.println("=== Branches ===");
        Branch.printBranch();
        System.out.println();
        System.out.println("=== Staged Files ===");
        SortedSet<String> addedFileName = new TreeSet<>(stagedAddition.keySet());
        if (addedFileName.size() == 0) {
            System.out.println();
        } else {
            for (String file : addedFileName) {
                System.out.println(file);
            }
            System.out.println();
        }
        System.out.println("=== Removed Files ===");
        SortedSet<String> removedFileName = new TreeSet<>(stagedRemoval);
        if (removedFileName.size() == 0) {
            System.out.println();
        } else {
            for (String file : removedFileName) {
                System.out.println(file);
            }
            System.out.println();
        }
        System.out.println("=== Modifications Not Staged For Commit ===");
        System.out.println();
        System.out.println("=== Untracked Files ===");
        System.out.println();
    }
}