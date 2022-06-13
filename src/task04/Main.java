/*
    Урок 4. Связанные списки
    1.Реализовать класс итератора на двусвязном списке;
    2.Написать очередь, использующую двусвязный список.
*/
package task04;

public class Main {
    public static void main(String[] args) {
        DoubleLinkedList<Integer> dllist = new DoubleLinkedList<>();
        Iterable<Integer> iter = dllist.getIterator();
        dllist.insert(1); dllist.insert(2); dllist.insert(3); dllist.insert(4);
        dllist.display("Очередь после добавления элементов (текущий="+iter.getCurrent()+"):");
        System.out.println("Очередь от хвоста к голове:"+dllist.toStringUpsideDown());
        dllist.display("После извлечения элемента "+dllist.remove()+" текущий="+iter.getCurrent()+":");
        int n = 3;
        System.out.println("Поиск числа "+n+" в очереди: "+
                (iter.getFirstAppearance(n) == null ? "не найдено" : iter.getCurrent()));
        if (!iter.contains(5))
            dllist.display("Вставка числа перед текущим - "+
                (iter.insertBefore(5) ? "" : "не ")+"успешно:");
        n = 4;
        System.out.println("Поиск числа "+n+" в очереди: "+
                (iter.getFirstAppearance(n) == null ? "не найдено" : iter.getCurrent()));
        dllist.display("Вставка числа после текущего - "+
                (iter.insertAfter(5)?"":"не ")+"успешно:");
        System.out.print("Удаление текущего элемента ");
        try {
            iter.deleteCurrent();
            dllist.display("прошло успешно:");
        } catch (RuntimeException ex) {
            System.out.println("не возможно");
        }
        iter.reset();
        System.out.print("После перемещения к началу очереди удаление текущего элемента ");
        try {
            iter.deleteCurrent();
            dllist.display("прошло успешно:");
        } catch (RuntimeException ex) {
            System.out.println("не возможно");
        }
        iter.moveToEnd();
        System.out.println("Головной элемент очереди: "+dllist.peek());
        System.out.println("Хвостовой элемент очереди: "+iter.getCurrent());
        dllist.insert(3); dllist.insert(2);
        dllist.display("После добавления еще двух элементов");
        n = 3;
        System.out.println(
                iter.getLastAppearance(n) == null
                    ? "Число "+n+" в очереди не найдено"
                    : "За последним числом "+n+" находится "+iter.next());
        dllist.remove(); dllist.remove(); dllist.remove();
        try {
            dllist.display("После извлечения трех элементов:");
        } catch (RuntimeException ex) {
            System.out.println(" очередь пуста");
        }
   }
}