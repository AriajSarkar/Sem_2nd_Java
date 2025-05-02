
public class StudentTest {

    public static void main(String[] args) {
        StudentMarks st = new StudentMarks();

        st.inputMarks();
        st.displayMarks();
        st.getTotal();

        System.out.println("Average Marks: " + st.getAverage());
        System.out.println("Highest Marks: " + st.getHighest());
    }
}
