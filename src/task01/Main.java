/*
    Описать простейшие алгоритмы (код + блок-схема):
    1. возведение в степень
    2. возведение в степень (с использованием свойства чётности степени)
    3. получить сумму всех чисел в ряду от 0 до 100.
 */
package task01;

public class Main {
    // возведение в степень
    static float power(int base, int exponent) {
        float res = 1f;
        if (exponent != 0) {
            if (Math.abs(exponent) == 1)
                res = base;
            else {
                int counter = Math.abs(exponent);
                while (counter-- > 0) res *= base;
            }
            if (exponent < 0) res = 1 / res;
        }
        return res;
    }

    // возведение в степень (с использованием свойства чётности степени)
    static float power_parity(int base, int exponent) {
        float res = 1f;
        if (exponent != 0) {
            if (Math.abs(exponent) == 1)
                res = base;
            else {
                while (exponent % 2 == 0) {
                    base *= base;
                    exponent /= 2;
                }
                int counter = Math.abs(exponent);
                while (counter-- > 0) res *= base;
            }
            if (exponent < 0) res = 1 / res;
        }
        return res;
    }

    // получить сумму всех чисел в ряду от 0 до 100.
    static int series_sum() {
        int counter = 0, res = 0;
        while (counter <= 100) res += counter++;
        return res;
    }

    public static void main(String[] args) {
        System.out.println(power(3,5));
        System.out.println(power_parity(2,-8));
        System.out.println(series_sum());
    }
}