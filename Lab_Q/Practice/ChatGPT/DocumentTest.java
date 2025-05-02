interface Printer {
    void  printData();
}

class Document implements Printer {

    @Override
    public void printData() {
        System.out.println("Printing Document...");
    }
}

public class DocumentTest {
    public static void main(String[] args) {
        Printer printer = new Document();
        printer.printData();
    }
}