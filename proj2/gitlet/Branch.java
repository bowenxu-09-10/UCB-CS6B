package gitlet;

import java.io.File;
import java.io.IOException;

import static gitlet.Utils.*;

public class Branch {

    /** The HEAD reference. */
    public static final File HEAD = join(Repository.GITLET_DIR, "HEAD");
    public static final File BRANCH_DIR = join(Repository.GITLET_DIR, "branches");

    /** Read Head to get the current branch name. */
    public static String readHead() {
        return readContentsAsString(HEAD);
    }

    /** Write current branch name into the HEAD. */
    public static void writeHead(String branchName) {
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
        if (branch.exists()) {
            System.out.println("A branch with that name already exists.");
            System.exit(0);
        }
        try {
            branch.createNewFile();
            Branch.writeOtherBranch(Commit.getHeadCommit().getPid(), branchName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Receive commitPID and write it into the current branch file. */
    public static void writeBranch(String commitPID) {
        File branch = join(BRANCH_DIR, readHead());
        if (!branch.exists()) {
            try {
                branch.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        writeContents(branch, commitPID);
    }

    /** Receive commitPID and write it into other branch file. */
    public static void writeOtherBranch(String commitPID, String branchName) {
        File branch = join(BRANCH_DIR, branchName);
        writeContents(branch, commitPID);
    }

    /** Print all the branches. */
    public static void printBranch() {
        for (String fileName : plainFilenamesIn(BRANCH_DIR)) {
            if (fileName.equals(readHead())) {
                System.out.println("*" + fileName);
            } else {
                System.out.println(fileName);
            }
        }
    }
}
