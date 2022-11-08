import java.util.ArrayList;

public abstract class ContainerFile extends File {
    ArrayList<File> files = new ArrayList<>();

    public ContainerFile(String name, String path) {
        super(name, path);
    }

    public 

    abstract void add(File file);
}
