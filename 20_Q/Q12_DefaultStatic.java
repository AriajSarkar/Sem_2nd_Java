interface DemoIntf {
    default void foo() {
        System.out.println("Default method foo() called");
    }
    
    static void bar() {
        System.out.println("Static method bar() called");
    }
}

public class Q12_DefaultStatic implements DemoIntf {
    public static void main(String[] args) {
        new DemoIntf(){}.foo();
        DemoIntf.bar();
    }
}
