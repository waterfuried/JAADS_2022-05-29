/*
    Урок 2. Массивы и сортировка

    1. boolean deleteAll(int value);
    2. boolean deleteAll()
    3. void insert(int idx, int value); // shift the tail
    4.0. подсчитать количество действий для каждой сортировки и сравнить со сложностью О-большое
    4. улучшить пузырьковую сортировку
    5.** реализовать сортировку подсчётом.
 */
package task02;

public class Main {
    public static void main(String[] args) {
        MyArray arr = new MyArray(new int[] { 3, 2, 3, 4, 5, 4, 1, -6, 5, 12345, -7, 0, 2 } );
        /*MyArray arr = new MyArray(5);
        arr.set(0,3);
        arr.set(1,2);
        arr.set(2,3);
        arr.set(3,4);
        arr.set(4,5);*/
        arr.display();
        arr.append(10);
        arr.display("Массив после добавления числа 10:");
        if (arr.delete(3)) arr.display("Массив после удаления числа 3:");
        arr.insert(4,5);
        arr.display("Массив после вставки числа 5:");
        if (arr.deleteAll(5)) arr.display("Массив после удаления всех чисел 5:");
        //System.out.println("Пузырьковая сортировка: "+ arr.doSort(MyArray.SORT_BUBBLE) * 0.000001f + "мс");
        System.out.println("Сортировка подсчетом: "+ arr.doSort(MyArray.SORT_CALCULATED) * 0.000001f + "мс");
        arr.display();
    }
}