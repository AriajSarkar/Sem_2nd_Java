interface Printable {
    void print();
}

interface Readable {
    void read();
}

class DocumentTest2 implements Printable, Readable {
    @Override
    public void print() {
        System.out.println("Document printing...");
    }
    
    @Override
    public void read() {
        System.out.println("Document reading...");
    }
}

public class Q11_MultiInterface {
    public static void main(String[] args) {
        DocumentTest2 document = new DocumentTest2();
        document.print();
        document.read();
    }
}
