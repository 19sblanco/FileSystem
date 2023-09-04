# FileSystem
This is a mock file system with a root drive, directories, files and zip files. I invite you to pull "FileSystem" and test this text based application in your commandline!

### Types of files:
* root drive - the root of the file tree. This is a directory that cannot deleted
* directory - a container to hold other files, zipfiles or directories
* file - a text file
* zipfile - a text file with half the size in bytes of a regular text file

### Operations:
* create - create a file, zipfile or a directory
* delete - delete a file, zipfile or a directory
* move - move a file, zipfile or a directory
* write - write to a file or a zip file

### Challenges while implimenting my mock filesystem:
* while implementing the move operations there are a few special cases that are easy to miss. One is you cannot move the root of the file system. Two you cannot put a file inside one of its children because then you will disconnect from the rest of the file tree structure. Third and lastly if a file or directory is moved into a destination directory where there is a name conflict with a previously existing file, the developer needs decide how to resolve this conflict, I opted to override the old file that was already existing withing the directory but there are multiple valid solutions based on the user requirements/specifications.


