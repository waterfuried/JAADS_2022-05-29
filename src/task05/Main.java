/*
    Урок 5. Рекурсия
    1. Написать программу по возведению числа в степень с помощью рекурсии.
    2. *** Написать программу обхода шахматным конём доски (рекурсивно)
*/
package task05;

public class Main {
    public static float power(int base, int exp) {
        if (exp == 0) return 1f;
        float res = base*power(base,Math.abs(exp)-1);
        if (exp < 0) res = 1f/res;
        return res;
    }

    public static void main(String[] args) {
        System.out.println(power(-3,-4));
        System.out.println(power(7,8));
    }
}