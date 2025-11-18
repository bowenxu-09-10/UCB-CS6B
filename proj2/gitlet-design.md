# Gitlet Design Document

**Name**:

## Classes and Data Structures

### Repository

1. setUpPersistence(): Create dirs and files allow persistent data.
2. initSystem(): Create a version control system. 

### Commit

#### Fields

1. String message: The commit message
2. String timestamp: The timestamp current COMMIT commit
3. String parent: The parent commit, in SHA-1
4. HashMap<String, String> fileNameToBlob: A set store the file being tracked.
5. Commit()：Initial commit, set message = "intial commit", timestamp = 00:00:00 UTC, Thursday, 1 January 1970
6. Commit(String message, String parent): new commit
7. getParent(): Get parent commit.

### Branch

#### Fields

1. readHead(): Read Head and get the current branch name.
2. writeHead(): Write current branch name into the HEAD.
3. updateBranch(String branchName, Commit commit): Update the existed branch with new commitPID.
4. newBranch(String branchName): Create a new branch file.
5. writeBranch(String commitPID, String branchName): Write commit sha-1 into the branch file.

### Blob

1. contents: saved file contents.
2. getContents(): return contents in specific Blob.
3. saveBlob(): save contents in a file named by sha-1 created by its content.

## Algorithms

## Persistence

- .gitlet: where store all the metadata.
  - commit: where commit files locate.
  - index: the staging area.
  - HEAD: store the current branch.
  - branches: branch area.