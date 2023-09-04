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

    public ArrayList<File> getChildren() {
        return this.files;
    }

    public ArrayList<File> getAllChildren(ArrayList<File> allChildren) {
        allChildren.addAll(this.files);

        for (File f: this.files) {
            if (f instanceof ContainsFile) {
                ContainsFile fAsContainsFile = ContainsFile.class.cast(f);
                ArrayList<File> fChildren = fAsContainsFile.getAllChildren(allChildren);
                for (File child: fChildren) {
                    if (!allChildren.contains(child)) {
                        allChildren.add(child);
                    }
                }
            }
        }
        return allChildren;
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
