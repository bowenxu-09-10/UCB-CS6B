package gitlet;

import java.io.File;
import java.io.IOException;

import static gitlet.Utils.*;

public class Blob {
    /** The blob folder contains blob object. */
    public static final File BLOB_FOLDER = join(Repository.GITLET_DIR, "blob");

    /** Saved contents. */
    private final String contents;

    /** File name. */
    private final String name;

    /** Blob id. */
    private final String id;

    /** Constructor. */
    Blob(String fileName) {
        File file = join(Repository.CWD, fileName);
        String content = readContentsAsString(file);
        this.contents = content;
        this.name = fileName;
        this.id = sha1(contents + name);
    }

    /** Get blob id. */
    public String getId() {
        return id;
    }

    /** Save contents in a file named by sha-1, ana return its pid.*/
    public String saveBlob() {
        String id = sha1(contents + name);
        File newBlob = join(BLOB_FOLDER, id);
        if (newBlob.exists()) {
            return id;
        } else {
            try {
                newBlob.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writeContents(newBlob, contents);
        }
        return id;
    }
}
