class Student {
    String name, course;
    int age;

    void print() {
        System.out.println(
            "Name: " + name + " Age: " + age + " Course: " + course
            );
    }
}

public class StudentInfo {
    public static void main(String[] args) {
        Student sc = new Student();
        sc.name = "John";
        sc.age = 24;
        sc.course = "BCA";

        sc.print();
    }
}