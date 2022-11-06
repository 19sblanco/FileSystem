// Your First Program

public abstract class File {
    String name;
    String path;
    int size;

    File(String name, String path, int size) {
        this.name = name;
        this.path = path;
        this.size = size;
    }


    public void printMe() {
        System.out.println(this.name + "length: " + this.size);
    }
}

// class Drive extends File {
    
// }

