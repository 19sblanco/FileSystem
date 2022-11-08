public class TextFile extends File{
    String content = "";

    public TextFile(String name, String path) {
        super(name, path);
    }


    public void change_content(String content) {
        this.content = content;
    }


    @Override
    public void printMe() {
        
        
    }


    @Override
    public void printMe(String indent) {
        System.out.println(indent + name + ": size" + size);
        
    }


    @Override
    protected int update_size() {
        int size = content.length();
        super.size = size;
        return size;
    }
}
