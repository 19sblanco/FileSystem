import java.util.ArrayList;
import java.util.Scanner;


public class FileSystem {
    private ArrayList<File> files = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    private String mainMenu = "Enter C to create a file\n" +
                        "Enter D to delete a file\n" +
                        "Enter M to move a file\n" + 
                        "Enter W to write to a text file\n" +
                        "Enter X to exit\n";

    private String createMenu = "[type] [name] [path of parent]\n" +
    "types: d for drive | f for folder | t for text file | z for zip file\n" +
    "you must exclude path of parent for drive, but must include it for everything else\n" +
    "Ex: d drive1       Ex: t textfile1 drive1\n";


    /*
     * display tree structure and main menu
     * then prompts user to create, delete, move, or write to a text file, or just to exit the program
     * gathers user input into an array and pass that onto the appropriate function to create, delete, move or write
     * after finishing it will loop again and repeat
     */
    public void run () {
        while (true) {
            System.out.println("\n");
            for (File f: files) {
                f.update_size();
                System.out.println("==Tree==");
                f.printMe();
                System.out.println("====");

            }
            String menuOption = getInput(this.mainMenu, 1)[0];

            if (menuOption.equals("c")) {
                String[] input = getInput(this.createMenu, 3);
                create(input);
            }
            else if (menuOption.equals("d")) {
                String[] input = getInput("[path]", 1);
                delete(input);
            }
            else if (menuOption.equals("m")) {
                String[] input = getInput("[sourcePath] [destinationPath]", 2);
                move(input);
            }
            else if (menuOption.equals("w")) {
                String[] input = getInput("[path] [new content]", 2);
                write(input);
            }
            else if (menuOption.equals("x")) {
                break;
            }
            else {
                System.out.println("<invalid input try again>");
            }
        }
        System.out.println("Program terminated");
    }

     /*
     * A general function for getting input by
     * displaying a prompt
     * ask for input till we get the number of items requested by inputSize
     * normalize input(lowercase) then return
     */
    private String[] getInput(String prompt, int limit) {
        System.out.println(prompt);
        String[] optionsArray;

        String options = this.scanner.nextLine();
        options = options.toLowerCase();
        optionsArray = options.split(" ", limit);

        return optionsArray;
    }

    /*
     * create a file based on user input [type] [name] [path of parent]
     * checks for the correct number of arguments (2 or 3) and assigns variables based on that
     * drives will have 2 arguments, all other files will have 3
     */
    public void create(String[] args) {
        String type = null;
        String name = null;
        String pathOfParent = null;

        // map the input correctly the the variables
        if (args.length < 2 || args.length > 3) {
            System.out.println("invalid arguments");
            return;
        }
        if (args.length >= 2) {
            type = args[0];
            name = args[1];
        }
        if (args.length == 3) { // drives will have an input of size 2 because they don't have parents
            pathOfParent = args[2];
        }

        // add drive
        if (type.equals("d")) {
            Drive drive = new Drive(name, name);
            this.files.add(drive);
            return;
        }
        try {
            // check for valid parent for folders, textfiles and zipfiles
            ContainsFile parent = (ContainsFile) getFileFromPath(pathOfParent);
            String path = pathOfParent + "\\" + name;

            // check for duplicate names
            for (File f: parent.getChildren()) {
                if (name.equals(f.get_name())) {
                    System.out.println("cannot have duplicate names");
                    return;
                }
            }

            // add file
            if (type.equals("f")) {
                Folder f = new Folder(name, path);
                parent.add(f);
            }
            else if (type.equals("z")) {
                ZipFile z = new ZipFile(name, path);
                parent.add(z);
            }
            else if (type.equals("t")) {
                TextFile t = new TextFile(name, path);
                parent.add(t);
            }
            else {
                System.out.println("invalid input");
            }
        }
        catch (java.lang.ClassCastException e) {
            System.out.println("text files cannot hold other files");
        }
        catch (Exception e) {
            System.out.println("invalid arguments");
        }
    }

    /*
     * delete a file given a path
     * if its a drive, delete it directly from the files array list
     * otherwise find the parent and remove the file from the parent
     * 
     * This method deletes a file with all its children
     */
    public void delete(String[] args) {
        String path = null;

        if (args.length != 1) {
            System.out.println("invalid arguments");
            return;
        }

        try {
            path = args[0];
            File fileToDelete = getFileFromPath(path);
    
            if (fileToDelete instanceof Drive) {
                this.files.remove(fileToDelete);
            }
            else {
                String parentPath = getParentPath(path);
                ContainsFile parentFile = (ContainsFile) getFileFromPath(parentPath);
                parentFile.remove(fileToDelete);
            }
        }
        catch (Exception e) {
            System.out.println("invalid arguments");
        }
    }

    /*
     * given [path] [content] as an array
     * if path is a text file, write content to that text file
     */
    public void write(String[] args) {
        String path = null;
        String content = null;

        if (args.length != 2) {
            System.out.println("invalid arguments");
            return;
        }
        
        try {
            path = args[0];
            content = args[1];

            TextFile textFile = (TextFile) getFileFromPath(path);
            textFile.change_content(content);
        }
        catch (java.lang.ClassCastException e) {
            System.out.println("only text files can be written to");
        }
        catch(Exception e) {
            System.out.println("invalid arguments");
        }
    }

    /*
     * move a file from a source path to a destination path
     * function will terminate if the source path or the destination path are invalid
     */
    public void move(String[] args) {
        String sourcePath = null;
        String destinationPath = null;

        if (args.length != 2) {
            System.out.println("invalid arguments");
            return;
        }
       
        try {
            sourcePath = args[0];
            destinationPath = args[1];

            File sourceFile = getFileFromPath(sourcePath);
            ContainsFile destinationFile = (ContainsFile) getFileFromPath(destinationPath);

            // error checking
            if (sourceFile instanceof Drive) {
                return; // drives always sit in the first layer of the file system and cannot be moved
            }
            else if (sourcePath.equals(destinationPath)) {
                System.out.println("file cannot contain itself");
                return;
            }
            // check the source file doesn't contain the destination file
            else if (sourceFile instanceof ContainsFile) {
                ArrayList<File> sourceAllChildren = new ArrayList<>();
                ((ContainsFile)sourceFile).getAllChildren(sourceAllChildren);
                if (sourceAllChildren.contains(destinationFile)) {
                    System.out.println("source cannot contain destination");
                    return;
                }
            }

            // delete file from old location
            String oldParentPath = getParentPath(sourcePath);
            ContainsFile oldParent = (ContainsFile) getFileFromPath(oldParentPath);
            oldParent.remove(sourceFile);

            // override files with the same name
            ArrayList<File> copy = new ArrayList<>(destinationFile.getChildren());
            for (File f: copy) {
                if (f.get_name().equals(sourceFile.get_name())) {
                    System.out.println("overrode file");
                    destinationFile.remove(f);
                }
            }

            // add to new location
            destinationFile.add(sourceFile);
            String newPath = destinationPath + "\\" + sourceFile.get_name();
            sourceFile.set_path(newPath);
        }
        catch (java.lang.ClassCastException e) {
            System.out.println("Must move to a file that can contain other files");
        }
        catch (Exception e) {
            System.out.println("invalid paths");
        }
    }

    /*
     * split a path by the \ character
     */
    private String[] getPathAsArray(String path) {
        return path.split("\\u005c");
    }

    /*
     * given the path of a child node, return the path of its parent
     */
    private String getParentPath(String childPath) {
        String[] pathAsArray = getPathAsArray(childPath);
        String parentPath = "";

        for (int i = 0; i < pathAsArray.length - 1; i++) {
            parentPath += pathAsArray[i];
            if (i != pathAsArray.length - 2) { // don't add '\' on last iteration
                parentPath += "\\";
            }
        }

        return parentPath;
    }

    /*
     * given a file path as a string, This method returns the file from that path
     * this method will return null if there was an error in retrieving that file
     */
    private File getFileFromPath(String path) throws Exception {
        path = path.replaceAll("\\+$", ""); // trim ending \ characters
        // u005c is unicode for a backslash
        String[] pathAsArray = getPathAsArray(path);

        ArrayList<File> currentDirectory = this.files;
        File currentFile = null;
        for (int i = 0; i < pathAsArray.length; i++) {
            String currentFileName = pathAsArray[i];

            for (File f: currentDirectory) {
                if (f.get_name().equals(currentFileName)) {
                    currentFile = f;
                    if (f instanceof ContainsFile) {
                        currentDirectory = ((ContainsFile) f).getChildren();
                    }
                    continue;
                }
            }
        }
        if (currentFile == null || currentFile != null && !currentFile.get_path().equals(path)) {
            throw new Exception(); // didn't find the right file
        }
        return currentFile;
    }
}
