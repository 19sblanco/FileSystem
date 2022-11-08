public class ZipFile extends ContainsFile{
    public ZipFile(String name, String path) {
        super(name, path);
    }

    @Override
    public void printMe() {
        // TODO Auto-generated method stub
        
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
    protected int update_size() {
        int size = 0;

        for (File f: super.files) {
            size += f.update_size();
        }

        size = size / 2;
        super.size = size;
        return size;
    }
}
