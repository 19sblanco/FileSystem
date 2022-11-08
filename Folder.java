public class Folder extends ContainsFile{

    public Folder(String name, String path) {
        super(name, path);
    }

    @Override
    public void printMe(String indent) {
        System.out.println(indent + name + ": size" + size);

        indent += "\t";
        for (File f: super.files) {
            f.printMe(indent);
        }
        
    }

    @Override
    public void printMe() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected int update_size() {
        int size = 0;

        for (File f: super.files) {
            size += f.update_size();
        }

        super.size = size;
        return size;
    }

    
}
