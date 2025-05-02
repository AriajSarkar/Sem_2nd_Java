
public class Functions {

    public static void main(String[] args) {
        greet("Raj");
        // int sum = add(5, 7);
        System.out.println("Sum: " + add(5, 7));
    }

    static void greet(String name) {
        System.out.println("Hello, " + name + "!");
    }

    static int add(int a, int b) {
        return a + b;
    }
}
