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
            Blob.BLOB_FOLDER.mkdir();
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
    public static void initCommend(String[] args) {
        operandsCheck(args, 1);
        if (GITLET_DIR.exists()) {
            System.out.println("A Gitlet version-control system already exists in the current directory.");
            System.exit(0);
        }
        setUpPersistence();
        Stage initStage = new Stage();
        Stage.saveStage(initStage);
        Commit initialCommit = new Commit();
        initialCommit.saveCommit();
        Branch.writeHead("master");
        Branch.writeBranch(initialCommit.getPid());
    }

    /** Saves a snapshot of tracked files in the current commit and
     *  staging area so they can be restored at a later time, creating
     *  a new commit.
     */
    public static void commitCommend(String[] args) {
        operandsCheck(args, 2);
        checkFolderGitleted();
        Stage stage = Stage.load();
        if (args[1].equals("")) {
            System.out.println("Please enter a commit message.");
            System.exit(0);
        }
        if (stage.getStagedAddition().isEmpty() && stage.getStagedRemoval().isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        }
        String headID = Branch.getHeadBranch();
        Commit newCommit = new Commit(args[1], headID, null);
        newCommit.makeCommit();
        newCommit.saveCommit();
        Branch.writeBranch(newCommit.getPid());
    }

    /** Add new created files or edited files to staging area. */
    public static void addCommend(String[] args) {
        checkFolderGitleted();
        operandsCheck(args, 2);
        File file = join(CWD, args[1]);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            System.exit(0);
        }
        Stage stage = Stage.load();
        stage.addStage(args[1]);
    }

    /** Remove files. */
    public static void rmCommend(String[] args) {
        checkFolderGitleted();
        operandsCheck(args, 2);
        File file = join(CWD, args[1]);
        Stage stage = Stage.load();
        stage.removeStage(args[1]);
    }

    /** Check whether current folder is gitleted folder. */
    public static void checkFolderGitleted() {
        if (!GITLET_DIR.exists()) {
            System.out.println("Not in an initialized Gitlet directory.");
            System.exit(0);
        }
    }

    /** Log all the message in current branch. */
    public static void logCommend(String[] args) {
        checkFolderGitleted();
        operandsCheck(args, 1);
        Commit head = Commit.getHeadCommit();
        Commit curr = head;
        while (curr != null) {
            System.out.println("===");
            System.out.println("commit " + curr.getPid());
            if (curr.getSecondParent() != null) {
                String merge = "Merge: " + curr.getParentID().substring(7) + " " +
                        curr.getSecondParent().substring(7);
                System.out.println(merge);
            }
            System.out.println("Date: " + curr.getGitTime());
            System.out.println(curr.getMessage());
            if (curr.getSecondParent() == null) {
                System.out.println();
            }
            curr = curr.getParent();
        }
    }

    /** Log all the message. */
    public static void globalLogCommend(String[] args) {
        operandsCheck(args, 1);
        checkFolderGitleted();
        for (String fileName : plainFilenamesIn(Commit.COMMIT_DIR)) {
            File file = join(Commit.COMMIT_DIR, fileName);
            Commit curr = readObject(file, Commit.class);
            System.out.println("===");
            System.out.println("commit " + curr.getPid());
            System.out.println("Date: " + curr.getGitTime());
            System.out.println(curr.getMessage());
            System.out.println();
        }
    }

    /** Find commit with given message. */
    public static void findCommend(String[] args) {
        operandsCheck(args, 2);
        checkFolderGitleted();
        boolean find = false;
        for (String fileName : plainFilenamesIn(Commit.COMMIT_DIR)) {
            File file = join(Commit.COMMIT_DIR, fileName);
            Commit curr = readObject(file, Commit.class);
            if (curr.getMessage().equals(args[1])) {
                System.out.println(fileName);
                find = true;
            }
        }
        if (!find) {
            System.out.println("Found no commit with that message.");
        }
    }

    /** Print status */
    public static void printStatus(String[] args) {
        operandsCheck(args, 1);
        checkFolderGitleted();
        Stage stage = Stage.load();
        stage.printStatus();
    }

    /** Check out files.
     *  1. java gitlet.Main checkout -- [file name]:
     *  Takes the version of the file as it exists in the head commit and puts
     *  it in the working directory, overwriting the version of the file that’s
     *  already there if there is one. The new version of the file is not staged.
     *
     *  2. java gitlet.Main checkout [commit id] -- [file name]
     *  Takes the version of the file as it exists in the commit with the given
     *  id, and puts it in the working directory, overwriting the version of the
     *  file that’s already there if there is one. The new version of the file is
     *  not staged.
     *
     *  3. java gitlet.Main checkout [branch name]
     *  Takes all files in the commit at the head of the given branch, and puts
     *  them in the working directory, overwriting the versions of the files that
     *  are already there if they exist. Also, at the end of this command, the
     *  given branch will now be considered the current branch (HEAD). Any files
     *  that are tracked in the current branch but are not present in the
     *  checked-out branch are deleted.
     */
    public static void checkoutCommend(String[] args) {
        checkFolderGitleted();
        if (args.length == 3) {
            Checkout.getCheckoutFile(args);
        } else if (args.length == 2) {
            Checkout.getCheckoutBranch(args[1]);
        }else if (args.length == 4) {
            Checkout.getCheckoutCommit(args);
        } else {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
    }

    /** Create a new Branch, but HEAD still point at current branch. */
    public static void branchCommend(String[] args) {
        Branch.newBranch(args[1]);
    }

    /** Remove branch. */
    public static void removeBranch(String[] args) {
        operandsCheck(args, 2);
        Branch.removeBranch(args[1]);
    }

    /** Checks out all the files tracked by the given commit.
     *  Removes tracked files that are not present in that commit.
     *  Also moves the current branch’s head to that commit node. */
    public static void resetCommend(String[] args) {
        checkFolderGitleted();
        operandsCheck(args, 2);
        String id = Checkout.getCheckPrefix(args[1]);
        Commit commit = Commit.getCommit(id);
        Checkout.checkTrack(commit);
        Commit.importFile(commit);
        Commit.removeFile(commit);
        String branchName = Branch.readHead();
        File branch = join(Branch.BRANCH_DIR, branchName);
        writeContents(branch, id);
        Stage.clear();
    }

    /** Merges files from the given branch into the current branch. */
    public static void mergeCommend(String[] args) {
        checkFolderGitleted();
        operandsCheck(args, 2);
        String branchName = args[1];
        precheckMerge(branchName);
        Commit.ancestorCheck(branchName);
        String headID = Branch.getHeadBranch();
        File branch = join(Branch.BRANCH_DIR, args[1]);
        String branchID  = readContentsAsString(branch);
        Commit newCommit = new Commit("Merged " + branchName + " into "
                + Branch.readHead() + ".", headID, branchID);
        Commit.mergeRule(branchName);
        newCommit.makeCommit();
        newCommit.saveCommit();
        Branch.writeBranch(newCommit.getPid());
    }

    /** Precheck before merge. */
    private static void precheckMerge(String branchName) {
        Stage stage = Stage.load();
        // If there's file in stage area.
        if (!stage.getStagedAddition().isEmpty() || !stage.getStagedRemoval().isEmpty()) {
            System.out.println("You have uncommitted changes.");
            System.exit(0);
        }
        // If the branch is not exist.
        if (!join(Branch.BRANCH_DIR, branchName).exists()) {
            System.out.println("A branch with that name does not exist.");
            System.exit(0);
        }
        // If the branch wanting to merge is the current branch.
        if (readContentsAsString(Branch.HEAD).equals(branchName)) {
            System.out.println("Cannot merge a branch with itself.");
            System.exit(0);
        }
        // If merge will overwrite a untracked file.
        File branch = join(Branch.BRANCH_DIR, branchName);
        Commit inBranch = Commit.getCommit(readContentsAsString(branch));
        Checkout.checkTrack(inBranch);
    }

    /** Operands check.
     * If only one or more than two operands, then issue. */
    private static void operandsCheck(String args[], int num) {
        if (args.length != num) {
            System.out.println("Incorrect operands.");
            System.exit(0);
        }
    }
}