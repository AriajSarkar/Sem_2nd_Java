class Cars {
    String brand, year;
    int price;

    Cars(String b, String y, int p) {
            brand = b;
            year = y;
            price = p;
    }

    void print() {
        System.out.println(
                "Brand: " + brand + " Year: " + year + "  Price: " + price
        );
    }
}

public class CarInfo {
    public static void main(String[] args) {
        Cars c = new Cars("BMW", "2024", 2000);

        c.print();
    }
}