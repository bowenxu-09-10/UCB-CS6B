package gitlet;

import java.io.File;
import java.util.HashMap;

import static gitlet.Utils.*;

public class Checkout {
    /** Checkout file in the head. More details see getCheckOutFile. */
    private static void checkoutFile(String[] args) {
        if (!checkCommendFormat(args)) {
            System.exit(0);
        }
        Commit head = Commit.getHeadCommit();
        String fileName = args[2];
        if (!head.fileNameToBLOB.containsKey(fileName)) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
        HashMap<String, String> blobs = head.fileNameToBLOB;
        File file = join(Repository.CWD, fileName);
        String blob = blobs.get(fileName);
        File replaceFile = join(Blob.BLOB_FOLDER, blob);
        byte[] content = readContents(replaceFile);
        writeContents(file, content);
    }

    /** Checkout branch. More details see getCheckOutBranch.*/
    private static void checkoutBranch(String branchName) {
        File branch = join(Branch.BRANCH_DIR, branchName);
        if (!branch.exists()) {
            System.out.println("No such branch exists.");
            System.exit(0);
        }
        if (Branch.readHead().equals(branchName)) {
            System.out.println("No need to checkout the current branch.");
            System.exit(0);
        }
        Commit given = Commit.getCommit(readContentsAsString(branch));
        checkTrack(given);
        Commit.importFile(given);
        Commit.removeFile(given);
        writeContents(Branch.HEAD, branchName);
    }

    /** Check is there any other file in CWD that is not tracked in head and would be
     * overwritten by given branch. */
    public static void checkTrack(Commit given) {
        Commit head = Commit.getHeadCommit();
        for (String fileName : plainFilenamesIn(Repository.CWD)) {
            File file = join(Repository.CWD, fileName);
            boolean trackedInHead = head.fileNameToBLOB.containsKey(fileName);
            boolean trackedGiven = given.fileNameToBLOB.containsKey(fileName);
            boolean sameAsGiven = sha1(readContents(file) + fileName)
                    .equals(given.fileNameToBLOB.get(fileName));
            if (!trackedInHead && trackedGiven && !sameAsGiven) {
                System.out.println("There is an untracked file in the way;"
                        + "delete it, or add and commit it first.");
                System.exit(0);
            }
        }
    }

    /** Checkout a given commit. More details see getCheckoutCommit. */
    private static void checkoutCommit(String[] args) {
        String commitID = checkPrefix(args[1]);
        // Check is the commend legal.
        if (!(checkCommendFormat(args) || commitID.equals(""))) {
            System.exit(0);
        }
        Commit commit = Commit.getCommit(commitID);
        String fileName = args[3];
        HashMap<String, String> blobs = commit.fileNameToBLOB;
        if (!blobs.containsKey(fileName)) {
            System.out.println("File does not exist in that commit.");
            System.exit(0);
        }
        File file = join(Repository.CWD, fileName);
        File replaceFile = join(Blob.BLOB_FOLDER, blobs.get(fileName));
        byte[] content = readContents(replaceFile);
        writeContents(file, content);
    }

    /**
     * Check if commit id is the only one with such prefix.
     * @return the according commitID.
     */
    private  static String checkPrefix(String prefix) {
        int count = 0;
        String target = "";
        for (String commit : plainFilenamesIn(Commit.COMMIT_DIR)) {
            if (commit.equals(prefix)) {
                return commit;
            } else if (commit.startsWith(prefix)) {
                target = commit;
                count++;
            }
            // If current id prefix is not the only one
            if (count > 1) {
                System.out.println("multiple commit with that prefix.");
                System.exit(0);
            }
        }
        if (count == 1) {
            return target;
        }
        System.out.println("No commit with that id exists.");
        System.exit(0);
        return "";
    }

    /** Checkout fileName from the given commit, write into CWD, and stage it. */
    public static void checkoutAndStage(String fileName, Commit fromCommit, Stage stage) {
        String blobID = fromCommit.fileNameToBLOB.get(fileName);
        File blobFile = join(Blob.BLOB_FOLDER, blobID);
        byte[] content = readContents(blobFile);

        File outFile = join(Repository.CWD, fileName);
        writeContents(outFile, content);

        stage.addStage(fileName);
    }
    /**
     *  Takes the version of the file as it exists in the head commit and puts
     *  it in the working directory, overwriting the version of the file that’s
     *  already there if there is one. The new version of the file is not staged.
     */
    public static void getCheckoutFile(String[] args) {
        checkoutFile(args);
    }

    /**
     *  Takes the version of the file as it exists in the commit with the given
     *  id, and puts it in the working directory, overwriting the version of the
     *  file that’s already there if there is one. The new version of the file is
     *  not staged.
     */
    public static void getCheckoutBranch(String branch) {
        checkoutBranch(branch);
    }

    /**
     *  Takes all files in the commit at the head of the given branch, and puts
     *  them in the working directory, overwriting the versions of the files that
     *  are already there if they exist. Also, at the end of this command, the
     *  given branch will now be considered the current branch (HEAD). Any files
     *  that are tracked in the current branch but are not present in the
     *  checked-out branch are deleted.
     */

    public static void getCheckoutCommit(String[] args) {
        checkoutCommit(args);
    }

    /**
     * Check if commend is obey the format.
     */
    public static boolean checkCommendFormat(String[] args) {
        if (args.length == 3 && args[1].equals("--")) {
            return true;
        }
        if (args.length == 4 && args[2].equals("--") && args[1].length() <= 40) {
            return true;
        }
        System.out.println("Incorrect operands.");
        System.exit(0);
        return false;
    }

    public static String getCheckPrefix(String commitID) {
        return checkPrefix(commitID);
    }
}
