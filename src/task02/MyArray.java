package task02;

import java.util.ArrayList;
import java.util.Arrays;

public class MyArray {
    private int[] arr;
    private int capacity;

    public static final int SORT_BUBBLE = 0;
    public static final int SORT_SELECT = 1;
    public static final int SORT_INSERT = 2;
    public static final int SORT_CALCULATED = 3;

    public MyArray(int size) {
        // есть некоторая смысловая неувязка: если метод append воспринимать как альтернативу set -
        // задание значений элементов, считая с индекса 0, то можно задавать здесь емкость как 0,
        // но при этом, во избежание путаницы, метод set вообще стоит удалить, поскольку он изменяет
        // элементы массива, никак не меняя значения их действительного количества (capacity);
        // если его (set) оставить и задать с его помощью несколько элементов, то вызов append будет
        // предполагать желание добавить (подчеркиваю) новый элемент, то есть раширить массив -
        // в этом случае запись в append должна происходть в позицию size, а не 0
        capacity = size;//0
        arr = new int[size];
    }

    public MyArray(int[] init) {
        capacity = init.length;
        arr = init;
    }

    void display() {
        for (int i = 0; i < capacity; ++i)
            System.out.print(arr[i] + " ");
        if (this.capacity > 0) System.out.println();
    }

    public int get(int idx) { return arr[idx]; }

    public void set(int idx, int value) { arr[idx] = value; }

    boolean delete(int value) {
        for (int i = 0; i < capacity; i++)
            if (this.arr[i] == value) {
                System.arraycopy(arr, i + 1, arr, i, capacity - i - 1);
                --capacity;
                return true;
            }
        return false;
    }

    boolean deleteAll(int value) {
        int i = 0, atLeast = 0;
        while (i < capacity)
            if (arr[i] == value) {
                // искать последовательность удаляемых значений
                int starter = i, counter = atLeast = 1;
                while (i < capacity - 1 && arr[i+1] == value) {
                    counter++;
                    i++;
                }
                System.arraycopy(arr, i + 1, arr, starter, capacity - i - 1);
                capacity -= counter;
            } else
                i++;
        return atLeast > 0;
    }

    boolean deleteAll() { capacity = 0; return true; }

    // добавить новый элемент в конец массива - расширить его размерность,
    // для изменения имеющихся элементов есть сеттер
    void append(int value) {
        if (capacity == arr.length) {
            int[] old = arr;
            this.arr = new int[old.length * 2];
            System.arraycopy(old, 0, arr, 0, old.length);
        }
        arr[capacity++] = value;
    }

    // вставить число в позицию в массиве, расширив его размерность
    void insert(int idx, int value) {
        if (idx >= 0 && idx < capacity) {
            int[] old = arr;
            if (capacity == arr.length) arr = new int[old.length * 2];
            // если вставка делается не в начало массива, скопировать все элементы до позиции вставки
            if (idx > 0) System.arraycopy(old, 0, arr, 0, idx);
            // скопировать все элементы после позиции вставки: по меньшей мере 1 - последний
            System.arraycopy(old, idx, arr, idx + 1, capacity - idx);
            arr[idx] = value;
            capacity++;
        }
    }

    public boolean isInArray(int value) { // O(n)
        for (int i = 0; i < capacity; i++)
            if (arr[i] == value) return true;
        return false;
    }

    //O(log(N))
    public boolean hasValue(int value) {
        int low = 0, high = capacity - 1, mid;
        while (low < high) {
            mid = (low + high) / 2;
            if (value == arr[mid])
                return true;
            else
                if (value < arr[mid])
                    high = mid;
                else
                    low = mid + 1;
        }
        return false;
    }

    private void swap(int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    public long doSort(int type) {
        long start = System.nanoTime();
        // switch оставлен в такой форме для совместимости с Java 8
        switch (type) {
            case SORT_SELECT: sortSelect(); break;
            case SORT_INSERT: sortInsert(); break;
            case SORT_CALCULATED: sortCalculated(); break;
            default: sortBubble();
        }
        return System.nanoTime() - start;
    }

    // без оптимизации будет n*n = n^2 операций,
    // с ней - в 2 раза меньше - (n) + (n-1) + ... + 1 = n*(n+1)/2 ~ n^2/2
    public void sortBubble() {
        /* исходный вариант:
        for (int iter = 0; iter < capacity; iter++)
            for (int idx = 0; idx < capacity - 1; idx++)
                if (arr[idx] > arr[idx + 1]) swap(idx, idx + 1);//*/
        // улучшение: поскольку в каждой итерации самый тяжелый "всплывает" наверх,
        // нет смысла в следующей обращаться к уже "всплывшим";
        // если за итерацию не было ни одной перестановки,
        // значит, массив уже отсортирован
        for (int iter = 0; iter < capacity; iter++) {
            boolean bubble = false;
            for (int idx = 0; idx < capacity-iter-1; idx++)
                if (arr[idx] > arr[idx+1]) {
                    swap(idx, idx+1);
                    bubble = true;
                }
            if (!bubble) break;
        }//*/
    }

    // число операций: (n-1) + (n-2) + ... + 1 = n*(n-1)/2 ~ n^2/2
    public void sortSelect() {
        for (int idx = 0; idx < capacity; idx++) {
            int curr = idx;
            for (int srch = idx + 1; srch < capacity; srch++)
                if (arr[srch] < arr[curr]) curr = srch;
            if (curr != idx) swap(idx, curr);
        }
    }

    // число операций: (n-1) итераций,
    // на каждой из которых 1 операция и число сравнений увеличивается на 1
    // (1+1) + (1+2) + ... + (1 + n-1) =
    // (n-1) + (1 + 2 + ... + (n-1)) =
    // (n-1) + n*(n-1)/2 = (n-1)*(1+n/2) ~ n^2/2
    public void sortInsert() {
        for (int curr = 1; curr < capacity; curr++) {
            int temp = arr[curr];
            int move = curr;
            while (move > 0 && arr[move-1] >= temp)
                arr[move] = arr[--move];
            arr[move] = temp;
        }
    }
    // поскольку при оценке вычислительной сложности O(t) не учитывются константы,
    // для всех написанных выше методов сортировки она будет порядка O(n^2)

    // сортировка подсчетом
    public void sortCalculated() {
        /*
         в общем случае массив может содержать числа любого знака, нули и иметь одиночные
         резкие скачки, например, 42017 на фоне остальных чисел в диапазоне 0..100;
         поэтому для хранения вспомогательных данных - связок значений чисел и их частот -
         используется этот (созданный здесь) класс массива;
         глупость заключается в том, что при выполнении сортировки исходных данных происходит
         и сортировка вспомогательных данных, из-за которой увеличивается число операций:
         при создании вспомогательных данных на каждой итерации (по размеру N исходных данных)
         происходит от 1 до N-1 сравнений, а значит - от 1 до N-1 операций, то есть -
         порядка N^2 операций, при формировании выходных данных - еще до N операций;
         таким образом, общее число операций - до N+N^2, а значит, его реализация получилась
         более затратная (в том числе - по используемой памяти) по сравнению с написанными выше
         методами сортировки
         */
        MyArray cnt = new MyArray(capacity), val = new MyArray(capacity);
        int i, j, k, offs = 0, counter = 0;

        // сформировать и упорядочить данные о частотах чисел в исходных данных
        for (i = 0; i < capacity; i++)
            // если массив частот пуст, задать его начальный элемент
            if (counter == 0) {
                val.set(counter, arr[i]);
                cnt.set(counter++, 1);
            } else {
                if (arr[i] > val.get(counter-1)) {
                    // если символ из исходных данных больше последнего элемента
                    // вспомогательного массива, поместить символ в конец вспомогательного
                    if (counter < capacity)
                        val.set(counter, arr[i]);
                    else
                        val.append(arr[i]);
                    cnt.set(counter++, 1);
                } else {
                    // иначе либо увеличить частоту символа из исходных данных,
                    // либо добавить новый символ во вспомогательный массив с
                    // упорядочиванием последнего и частот
                    j = counter-1;
                    while (j >= 0 && arr[i] < val.get(j)) j--;
                    if (j >= 0 && arr[i] == val.get(j)) {
                        cnt.set(j, cnt.get(j)+1);
                    } else {
                        val.insert(j+1, arr[i]);
                        cnt.insert(j+1, 1);
                        counter++;
                    }
                }
            }

        // сформировать выходные данные: в порядке возрастания величины символа из исходных данных
        // записывать его n раз (n - частота использования символа в исходных данных)
        if (counter > 0)
            for (i = 0; i < counter; i++)
                if ((k = cnt.get(i)) > 0) {
                    for (j = 0; j < k; j++) arr[offs+j] = val.get(i);
                    offs += k;
                }
    }
}