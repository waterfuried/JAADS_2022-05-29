package task04;

// 2.Написать очередь, использующую двусвязный список.
public class DoubleLinkedList<T> {
    // 1.Реализовать класс итератора на двусвязном списке
    private class DoubleGenericLinkedIterator implements Iterable<T> {
        GenericLink<T> current;

        @Override public void reset() { current = getHead(); }
        @Override public void moveToEnd() { current = getTail(); }

        @Override public T next() {
            current = current.next;
            return current.data;
        }

        @Override public boolean hasNext() {
            return current.next != null;
        }

        public boolean contains(T t) {
            // менять положение итератора при проверке не стоит
            GenericLink<T> l = head;
            while (l != null)
                if (l.data.equals(t))
                    return true;
                else
                    l = l.next;
            return false;
        }

        public GenericLink<T> getFirstAppearance(T t) {
            current = head;
            while (current != null)
                if (current.data.equals(t))
                    return current;
                else
                    current = current.next;
            return null;
        }

        public GenericLink<T> getLastAppearance(T t) {
            current = tail;
            while (current != null)
                if (current.data.equals(t))
                    return current;
                else
                    current = current.prev;
            return null;
        }

        @Override public T getCurrent() {
            return current == null ? null : current.data;
        }

        @Override public T deleteCurrent() throws RuntimeException {
            if (current != getHead())
                throw new RuntimeException("Not at head now. Cannot remove.");

            T t = current.data;
            if (current.next != null) current.next.prev = null;
            current = current.next;
            setHead(current);
            return t;
        }

        // у головы нет предыдущего элемента (если список не закольцован)
        @Override public boolean atBegin() {
            return current.prev == null;
        }

        // у хвоста нет следующего элемента (если список не закольцован)
        @Override public boolean atEnd() {
            return current.next == null;
        }

        // возвращает истину, если вставка не противоречит принципу добавления в очередь
        @Override public boolean insertAfter(T t) {
            if (current != getTail()) return false;

            GenericLink<T> l = new GenericLink<>(t);
            l.prev = current;
            current.next = l;
            setTail(l);
            return true;
        }

        // в случае обычной очереди (не двусторонней и не с приоритетатми) добавление элемента
        // перед каким-либо противоречит принципам добавления в очередь
        @Override public boolean insertBefore(T t) {
            return false;
        }

        @Override public GenericLink<T> getCurrentPos() { return current; }
        @Override public void setCurrentPos(GenericLink<T> GenericLink) { current = GenericLink; }
    }

    // очередь, использующая двусвязный список
    private GenericLink<T> head, tail;
    private Iterable<T> iterator;

    public GenericLink<T> getHead() { return head; }
    public void setHead(GenericLink<T> head) { this.head = head; }
    public GenericLink<T> getTail() { return tail; }
    public void setTail(GenericLink<T> tail) { this.tail = tail; }

    // итератор имело бы смысл создавать только после добавления первого элемента в очередь,
    // но чтобы не нарушать общую концепцию, пусть он будет создаваться при создании очереди,
    // и являться при этом пустым, как и сама очередь
    public DoubleLinkedList() {
        head = null;
        tail = null;
        iterator = new DoubleGenericLinkedIterator();
    }

    public boolean isEmpty() { return head == null; }

    // вставить новый элемент в конец очереди;
    // где бы ни находился итератор, менять его положение не имеет смысла
    public void insert(T t) {
        GenericLink<T> l = new GenericLink<>(t);
        if (head == null) {
            head = l;
            iterator.reset();
        } else {
            if (head.next == null) head.next = l;
            l.prev = tail;
            tail.next = l;
        }
        tail = l;
    }

    // элементы извлекаются из головы очереди
    // если итератор указывал на голову очереди, его следует обновить
    public T remove() throws RuntimeException {
        if (head == null) throw new RuntimeException("Queue is empty");

        T t = head.data;
        if (head.next != null) head.next.prev = null;
        head = head.next;
        if (iterator.atBegin()) iterator.reset();
        return t;
    }

    // посмотреть данные элемента в голове очереди
    public T peekHead() throws RuntimeException {
        if (head == null) throw new RuntimeException("Queue is empty");
        
        return head.data;
    }
    // синоним операции
    public T peek() throws RuntimeException { return peekHead(); }

    // посмотреть данные элемента в голове очереди
    public T peekTail() throws RuntimeException {
        if (tail == null) throw new RuntimeException("Queue is empty");

        return tail.data;
    }

    public void display(String ... msg) {
        if (msg != null && msg.length > 0) System.out.print(msg[0]);
        System.out.println(this);
    }

    @Override public String toString() throws RuntimeException {
        if (head == null) throw new RuntimeException("Queue is empty");

        StringBuilder sb = new StringBuilder();
        GenericLink<T> l = iterator.getCurrentPos();
        iterator.reset();
        while (iterator.hasNext()) {
            sb.append(" ").append(iterator.getCurrent());
            iterator.next();
        }
        sb.append(" ").append(iterator.getCurrent());
        iterator.setCurrentPos(l);
        return sb.toString();
    }

    public String toStringUpsideDown() throws RuntimeException {
        if (head == null) throw new RuntimeException("Queue is empty");

        StringBuilder sb = new StringBuilder();
        GenericLink<T> l = tail;
        while (l != null) {
            sb.append(" ").append(l.data);
            l = l.prev;
        }
        return sb.toString();
    }

    public Iterable<T> getIterator() { return iterator; }
}