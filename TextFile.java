public class TextFile extends File{
    String content;

    public TextFile(String name, String path, int size) {
        super(name, path, size);
    }


    public void change_content(String content) {
        this.content = content;
    }
}
