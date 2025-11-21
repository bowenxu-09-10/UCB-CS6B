package gitlet;

import static gitlet.Utils.plainFilenamesIn;

public class Checkout {

    private static void checkoutFile(String[] args) {
        if (!checkCommendFormat(args)) {
            System.exit(0);
        }
    }

    private static void checkoutBranch(String branch) {

    }

    private static void checkoutCommit(String[] args) {
        if (!checkCommendFormat(args)) {
            System.exit(0);
        }
    }

    private static boolean checkPrefix(String prefix) {
        int count = 0; // Check if this id is the only one with such prefix.
        String target = "";
        for (String commit : plainFilenamesIn(Commit.COMMIT_DIR)) {
            if (commit.equals(prefix)) {
                return true;
            } else if (commit.contains(prefix)){
                target = commit;
                count++;
            }
            // If current id prefix is not
            if (count > 1) {
                // ToDo: see if there are designated message.
                System.out.println("Please enter the right commit ID.");
                System.exit(0);
            }
        }
        if (count == 1) {
            return true;
        }
        System.exit(0);
        return false;
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
     * Check if the given prefix is the only one.
     */
    public static boolean getCheckPrefix(String prefix) {
        return checkPrefix(prefix);
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
        return false;
    }
}
