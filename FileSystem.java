import java.util.ArrayList;
import java.util.Scanner;

public class FileSystem {
    ArrayList<File> files = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);



    public FileSystem() {

    }

    public void run () {
        while (true) {
            display();
            getInput();
        }
    }

    /*
     * print the files in Files as a tree structure
    */
    public void display() {
        System.out.println("Enter C to create a file");
        System.out.println("Enter D to delete a file");
        System.out.println("Enter M to move a file");
        System.out.println("Enter W to write to a text file");
        System.out.println("Enter X to exit");

        for (File f: files) {
            f.update_size();
            f.printMe();
        }
    }

    public void getInput() {
        while (true) {
            String option = this.scanner.nextLine();
            option = option.toLowerCase();

            if (option.equals("c")) {
                create();
                break;
            }
            else if (option.equals("d")) {
                delete();
                break;
            }
            else if (option.equals("m")) {
                move();
                break;
            }
            else if (option.equals("w")) {
                write();
                break;
            }
            else {
                System.out.println("invalid option, try again");
            }
        }
    }

    /*
     * create a file based on user input
     * does error checking to make sure that user input is valid
     * if user input is invalid it will print an error message and not create the file
     */
    public void create() {
        System.out.println();
        System.out.println("[type] [name] [pathOfParent]");
        System.out.println("d for drive | f for folder | t for text file | z for zip file");
        System.out.println("Ex: d drive1");
        System.out.println("Ex: t helloWorld.txt drive1");
        System.out.println();

        while (true) {
            String fileToCreate = this.scanner.nextLine();
            String[] fileToCreateArray = fileToCreate.split(" ", 3);
            
            String fileType = fileToCreateArray[0].toLowerCase();
            String fileName = null;
            String pathOfParent = null;

            if (fileToCreateArray.length > 1) {
                fileName = fileToCreateArray[1];
            }
            if (fileToCreateArray.length == 3) {
                pathOfParent = fileToCreateArray[2].toLowerCase();
            }


            if (fileType.equals("d") && fileToCreateArray.length == 2) {
                Drive drive = new Drive(fileName);
                this.files.add(drive);
                break;
            }
            else if (fileType.equals("f") && pathOfParent != null) {
                Folder folder = new Folder(fileName, pathOfParent);
                ContainsFile parent = (ContainsFile) getFileFromPath(pathOfParent);
                if (parent != null) {
                    parent.add(folder);
                }
                else {
                    System.out.println("Error adding file");
                }
                break;
            }
            else if (fileType.equals("z") && pathOfParent != null) {
                ZipFile zipFile = new ZipFile(fileName, pathOfParent);
                ContainsFile parent = (ContainsFile) getFileFromPath(pathOfParent);
                if (parent != null) {
                    parent.add(zipFile);
                }
                else {
                    System.out.println("Error adding file");
                }
                break;
            }
            else if (fileType.equals("t") && pathOfParent != null) {
                TextFile textFile = new TextFile(fileName, pathOfParent);
                ContainsFile parent = (ContainsFile) getFileFromPath(pathOfParent);
                if (parent != null) {
                    parent.add(textFile);
                }
                else {
                    System.out.println("Error adding file");
                }
                break;
            }
            else{
                System.out.println("invalid input");
            }
        }
    }

    public void delete() {
        System.out.println("[path]");
        while (true) {
            String fileToDeleteName = this.scanner.nextLine();
            String[] fileToDeleteArray = fileToDeleteName.split("\\u005c");

            if (fileToDeleteArray.length == 1) {
                File fileToDelete = getFileFromPath(fileToDeleteName);
                if (this.files.contains(fileToDelete)) {
                    this.files.remove(fileToDelete);
                }
                break;
            }
            else if (fileToDeleteArray.length > 1) {
                String parentPath = "";
                for (int i = 0; i < fileToDeleteArray.length - 1; i++) { // -2 becuase we want the parent of the file to delete
                    parentPath += fileToDeleteArray[i];
                }

                ContainsFile parentFile = (ContainsFile) getFileFromPath(parentPath);
                File fileToDelete = getFileFromPath(fileToDeleteName);

                parentFile.remove(fileToDelete);
                break;
            }
            else {
                System.out.println("invalid input");
            }
        }
    }

    public void move() {
        System.out.println("[source path] [destination path]");
        while (true) {
            String fileToMove = this.scanner.nextLine();
            String[] fileToMoveArray = fileToMove.split(" ", 2);

            String sourcePath = fileToMoveArray[0].toLowerCase();
            String destinationPath = null;

            if (fileToMoveArray.length > 1) {
                destinationPath = fileToMoveArray[1];
            }
            else {
                System.out.println("invalid input");
                continue;
            }

            /*
             * delete file from source
             * add to child of destination path
             */
            File sourceFile = getFileFromPath(sourcePath);



        }

    }

    public void write() {
        System.out.println("[path] [new content]");
        while (true) {
            String fileToWriteTo = this.scanner.nextLine();
            String[] fileToWriteToArray = fileToWriteTo.split(" ", 2);

            String textFilePath = fileToWriteToArray[0].toLowerCase();
            String content = null;

            if (fileToWriteToArray.length > 1) {
                content = fileToWriteToArray[1];
            }

            TextFile textFile = (TextFile) getFileFromPath(textFilePath);
            if (textFile == null) {
                System.out.println("invalid path");
            }
            else {
                textFile.change_content(content);
                break;
            }
        }
    }

    private void get_input() {
        
    }


    /*
     * given a file path as a string, This method returns
     * the file from that path
     */
    public File getFileFromPath(String path) {
        // u005c is unicode for a backslash
        String[] pathAsArray = path.split("\\u005c");

        ArrayList<File> currentDirectory = this.files;
        File currentFile = null;
        for (int i = 0; i < pathAsArray.length; i++) {
            String currentFileName = pathAsArray[i];

            for (File f: currentDirectory) {
                if (f.get_name().equals(currentFileName)) {
                    currentFile = f;
                    if (f instanceof ContainsFile) {
                        currentDirectory = ((ContainsFile) f).get_children();
                    }
                    continue;
                }
            }
        }
        if (currentFile != null && !currentFile.get_name().equals(pathAsArray[pathAsArray.length - 1])) {
            currentFile = null; // file found != to the file user was trying to find
        }
        return currentFile;
    }
}

/*
 * 
 * todo: make a function for getting input
 *      get user input
 *      split into array and return
 * 
 *      pass array into move, delete, create, etc as parameters
 * 
 * todo: make sure that you are doing good object oriented programming
 *      * look at duplicate code
 *      * look at the overall structrue of the code (maybe write on paper)
 * 
 * 
 * todo: make sure that you can't make fiels of the same name
 * 
 * 
 */


 /**
  * At the end
        Make sure that the correct files are made public / private
        make sure that when you try to create files you cant create in text files
        make sure that the path is valid and that you aren't tryin gto grab files in text files
        look up how to structure java files, abstract java files and hierachy things

  */
