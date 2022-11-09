/*
 * File class: the class that all other files inherit from (drives, folders, textfiles, zipfiles)
 */
public abstract class File {
    protected String name;
    protected String path;
    protected int size;

    File(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public abstract void printMe();

    public abstract void printMe(String indent);

    public String get_name() {
        return this.name;
    }

    public String get_path() {
        return this.path;
    }

    public void set_path(String path) {
        this.path = path;
    }

    protected abstract int update_size();
}
