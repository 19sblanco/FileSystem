
public class Drive extends ContainsFile {

    public Drive(String name) {
        super(name, "");
    }

    @Override
    public void printMe() {
        System.out.println(name + ": size" + size);

        String indent = "\t";
        for (File f: super.files) {
            f.printMe(indent);
        }
        
    }

    @Override
    public void printMe(String indent) {
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
