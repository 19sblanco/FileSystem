import java.util.ArrayList;

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
