// 5. To convert a decimal to binary number

import java.util.Scanner;


class BinaryConverter {

    void convertToBinary() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a decimal number: ");
        int decimal = sc.nextInt();

        StringBuilder binary = new StringBuilder();

        if (decimal == 0) {
            binary.append(0);
        } else {
            while (decimal > 0) {
                int remainder = decimal % 2;
                binary.insert(0, remainder);
                decimal = decimal / 2;
            }
        }
        System.out.println("Binary representation: " + binary.toString());
    }
}

public class DecimalToBinary {

    public static void main(String[] args) {
        BinaryConverter bc = new BinaryConverter();
        bc.convertToBinary();
    }
}
