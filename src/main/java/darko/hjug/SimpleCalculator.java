package darko.hjug;

import java.util.Scanner;

public class SimpleCalculator {

    public SimpleCalculator() {
        simpleCalculator();
    }

    private void simpleCalculator() {
        System.out.println("Please enter your calculation");
        Scanner scanner = new Scanner(System.in);
        while(true) {
            int left = scanner.nextInt();
            String op = scanner.next();
            int right = scanner.nextInt();
            System.out.println(compute(left, op, right));
        }
    }

    public static void main(String[] args) {
        new SimpleCalculator();
    }

    private int compute(int left, String op, int right) {
        switch (op.charAt(0)) {
            case '+':
                return left + right;
            case '-':
                return left - right;
            case '*':
                return left * right;
            case '/':
                return left / right;
        }
        throw new IllegalArgumentException("Unknown operator:" + op);
    }
}
