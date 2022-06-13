package task04;
//package task04;
/*
    Как правило, итераторы содержат следующие методы:
    ● reset() — перемещение в начало списка;
    ● nextGenericLink() — перемещение итератора к следующему элементу;
    ● getCurrent() — получение элемента, на который указывает итератор;
    ● deleteCurrent() — удаление элемента в текущей позиции итератора.
    ● atEnd() — возвращает true, если итератор находится в конце списка;
    ● insertAfter() — вставка элемента после итератора;
    ● insertBefore() — вставка элемента до итератора;
*/

import java.util.Iterator;

public interface Iterable<T> extends Iterator<T> {
    void reset();
    void moveToEnd();
    // в Iterator объявлен метод next(), возвращающий следующий элемент в итерации
    // а также есть boolean hasNext(), проверяющий наличие следующего элемента
    @Override T next();
    @Override boolean hasNext();
    // проверить наличие элемента в очереди
    boolean contains(T t);
    // найти первый подходящий элемент в очереди
    GenericLink<T> getFirstAppearance(T t);
    // найти последний подходящий элемент в очереди
    GenericLink<T> getLastAppearance(T t);
    T getCurrent();
    // метод remove в Iterator описан как удаление последнего возвращенного итератором элемента
    T deleteCurrent();
    boolean atBegin();
    boolean atEnd();
    boolean insertAfter(T t);
    boolean insertBefore(T t);
    GenericLink<T> getCurrentPos();
    void setCurrentPos(GenericLink<T> GenericLink);
}