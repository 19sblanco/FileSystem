import java.util.ArrayList;
/*
 * Contains file: the class that all other files that can hold other files can inherit from (drives, folders, zipfiles)
 * This inhertits from files but adds functionality for holding children files
 */
public abstract class ContainsFile extends File {
    ArrayList<File> files = new ArrayList<>();

    public ContainsFile(String name, String path) {
        super(name, path);
    }

    public ArrayList<File> get_children() {
        return this.files;
    }

    public void add(File file) {
        this.files.add(file);
    }

    public Boolean remove(File file) {
        if (this.files.contains(file)) {
            this.files.remove(file);
            return true;
        }
        else {
            return false;
        }
    }
}
