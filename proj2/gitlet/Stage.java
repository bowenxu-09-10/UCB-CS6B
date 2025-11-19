package gitlet;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import static gitlet.Utils.join;

public class Stage {
    /** A Map for staged file for addition. */
    private HashMap stagedAddition;
    /** A Set for staged file for removal. */
    private Set<String> stagedRemoval;

    /** The index file acts as staging area. */
    public static final File INDEX = join(Repository.GITLET_DIR, "index");
}
