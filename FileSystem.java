import java.util.ArrayList;
import java.util.Scanner;

public class FileSystem {
    ArrayList<File> Files;
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

        display_files();
    }

    public void display_files() {
        System.out.println("<display files here>");
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
            }
            else {
                System.out.println("invalid option, try again");
            }
        }
    }

    public void create() {
        /*
         * cehck if file can be made 
         * if so :
         *      create file (setting correct values)
         *      put in right place within tree
         */
        System.out.println("creating file");
        
    }

    public void delete() {
        System.out.println("deleting file");
    }

    public void move() {
        System.out.println("moving file");
    }

    public void write() {
        System.out.println("writing to a file");
    }
}
