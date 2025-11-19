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
    Blob(byte[] contents) {
        this.contents = contents;
    }

    /** Return contents in specific Blob. */
    public byte[] getContents(String blobName) {
        File requiredBlob = join(BLOB_FOLDER, blobName);
        requiredBlob.mkdirs();
        return readContents(requiredBlob);
    }

    /** Save contents in a file named by sha-1. */
    public void saveBlob() {
        File newBlob = join(BLOB_FOLDER, sha1(contents));
        try {
            newBlob.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writeContents(newBlob, contents);
    }
}
