package gitlet;

import java.io.File;
import java.io.IOException;

import static gitlet.Utils.*;

public class Blob {
    /** The blob folder contains blob object. */
    public static final File BLOB_FOLDER = join(Repository.GITLET_DIR, "blob");

    /** Saved contents. */
    private final byte[] contents;

    /** Constructor. */
    Blob(String fileName) {
        File file = join(Repository.CWD, fileName);
        byte[] content = readContents(file);
        this.contents = content;
    }

    /** Return contents in specific Blob. */
    public byte[] getContents(String blobName) {
        File requiredBlob = join(BLOB_FOLDER, blobName);
        requiredBlob.mkdirs();
        return readContents(requiredBlob);
    }

    /** Save contents in a file named by sha-1. */
    public String saveBlob() {
        File newBlob = join(BLOB_FOLDER, sha1(contents));
        try {
            newBlob.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeContents(newBlob, contents);
        return sha1(contents);
    }
}
