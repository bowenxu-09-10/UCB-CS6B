package gitlet;

import java.io.File;
import java.io.IOException;

import static gitlet.Utils.*;

public class Branch {

    /** The HEAD reference. */
    public static final File HEAD = join(Repository.GITLET_DIR, "HEAD");
    public static final File BRANCH_DIR = join(Repository.GITLET_DIR, "branches");

    /** Read Head to get the current branch name. */
    private static String readHead() {
        return readContentsAsString(HEAD);
    }

    /** Write current branch name into the HEAD. */
    private static void writeHead(String branchName) {
        writeContents(HEAD, branchName);
    }

    /** Return HEAD branch's SHA-1. */
    public static String getHeadBranch() {
        String branchName = readContentsAsString(Branch.HEAD);
        File head = join(Branch.BRANCH_DIR, branchName);
        return readContentsAsString(head);
    }

    /** Create a new branch file. */
    public static void newBranch(String branchName) {
        File branch = join(BRANCH_DIR, branchName);
        try {
            branch.createNewFile();
            writeHead(branchName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Receive commitPID and write it into the branch file. */
    public static void writeBranch(String commitPID) {
        File branch = join(BRANCH_DIR, readHead());
        writeContents(branch, commitPID);
    }
}
