package task03;

import java.util.Arrays;

public class Main {
    private static class Stack {
        private int maxSize; // размер
        private int[] stack; // место хранения
        private int head;    // вершина

        public Stack(int size) {
            this.maxSize = size;
            this.stack = new int[this.maxSize];
            this.head = -1;
        }

        public boolean isEmpty() { return this.head == -1; }
        public boolean isFull() { return this.head == this.maxSize - 1; }

        public void push(int i) {
            if (isFull()) {
                this.maxSize *= 2;
                int[] newStack = new int[this.maxSize];
                System.arraycopy(this.stack, 0, newStack, 0, this.stack.length);
                this.stack = newStack;
            }
            this.stack[++this.head] = i;
        }

        public int pop() {
            if (isEmpty()) throw new RuntimeException("Stack is empty"); //ret -1
            return this.stack[this.head--];
        }

        public int peek() {
            return this.stack[this.head];
        }
    }

    private static class Queue { // класс модифицирован под дальнейшее наследование от него
        private int maxSize; // размер
        private int[] queue; // место хранения
        private int head;    // отсюда уходят
        private int tail;    // сюда приходят
        private int items;   // текущее количество

        public Queue(int s) {
            maxSize = s;
            queue = new int[maxSize];
            nullify();
        }

        final void nullify() { head = 0; tail = -1; items = 0; }
        public boolean isEmpty() { return (items == 0); }
        public boolean isFull() { return (items == maxSize); }
        public int size() { return items; }

        public int getMaxSize() { return maxSize; }
        public int getHead() { return head; }
        public void setHead(int head) { this.head = head; }
        public int getTail() { return tail; }
        public void setTail(int tail) { this.tail = tail; }
        public int getItemsCount() { return items; }
        public void incItems() { items++; }
        public int[] getQueue() { return queue; }
        public void setQueue(int[] queue) { this.queue = queue; }

        public void setQueueAt(int ptr, int val) throws RuntimeException {
            if (ptr < 0 || ptr > maxSize-1)
                throw new RuntimeException("Queue index out of bounds");

            queue[ptr] = val;
        }
        public int getQueueAt(int ptr) throws RuntimeException {
            if (ptr < 0 || ptr > maxSize-1)
                throw new RuntimeException("Queue index out of bounds");

            return queue[ptr];
        }

        public void enlarge() {
            maxSize *= 2;
            int[] tmpArr = new int[maxSize];
            if (tail >= head) {
                System.arraycopy(queue, 0, tmpArr, 0, queue.length);
            } else {
                int n = items-head, newPos = maxSize-n;
                System.arraycopy(queue, 0, tmpArr, 0, tail+1);
                System.arraycopy(queue, head, tmpArr, newPos, n);
                head = newPos;
            }
            queue = tmpArr;
        }

        void commonInsert(int i) {
            if (items==0) {
                queue[0] = i;
                tail = 0;
            } else
                if (items == maxSize) enlarge();
            items++;
        }

        void commonRemove() {
            if (--items == 0) nullify();
        }

        public void insert(int i) {
            commonInsert(i);
            if (items > 1) {
                if (tail == maxSize - 1) tail = -1;
                queue[++tail] = i;
            }
        }

        public int remove() throws RuntimeException {
            if (items == 0) throw new RuntimeException("Queue is empty");

            int tmp = queue[head++];
            if (head == maxSize) head = 0;
            // в этом (родительском) классе стоит не вызывать метод commonRemove,
            // а записать его тело прямо здесь
            if (--items == 0) nullify();
            return tmp;
        }

        public int peek() throws RuntimeException {
            if (items == 0) throw new RuntimeException("Queue is empty");

            return queue[head];
        }

        public void display() throws RuntimeException {
            if (items == 0) throw new RuntimeException("Queue is empty");

            for (int i = 0; i < maxSize; i++) System.out.print(" "+queue[i]);
            System.out.println(" (head=#"+head+", tail=#"+tail+")");
        }
    }

// 1. Создать класс для реализации дека.
    private static class Deque extends Queue { // двусторонняя очередь
        public Deque(int size) {
            super(size);
        }

        public void insertToTail(int i) { insert(i); }
        public int removeFromHead() throws RuntimeException { return remove(); }
        public int peekHead() throws RuntimeException { return peek(); }

        public void insertToHead(int i) {
            commonInsert(i);
            if (getItemsCount() > 1) {
                int h = getHead();
                if (h == 0) h = getMaxSize();
                setHead(--h);
                setQueueAt(h,i);
            }
        }

        public int removeFromTail() throws RuntimeException {
            if (isEmpty()) throw new RuntimeException("Queue is empty");

            int t = getTail(), tmp = getQueueAt(t);
            if (t == 0) t = getMaxSize();
            setTail(--t);
            commonRemove();
            return tmp;
        }

        public int peekTail() throws RuntimeException {
            if (isEmpty()) throw new RuntimeException("Queue is empty");

            return getQueueAt(getTail());
        }

        @Override public void display() throws RuntimeException {
            super.display();
            System.out.println(peekHead()+" "+peekTail());
        }
    }

// 2. Создать класс для реализации приоритетной очереди (выбрать только один из вариантов)
    public static class PrioritizedElement {
        private int value, priority;

        public PrioritizedElement(int value, int priority) {
            this.value = value;
            this.priority = priority;
        }

        public int getValue() { return value; }
        public int getPriority() { return priority; }
    }

    private static class PrioritizedQueue extends Queue {
        private int[] priority; // приоритеты членов очереди

        public PrioritizedQueue(int size) {
            super(size);
            priority = new int[size];
        }

        @Override public void enlarge() {
            int h = getHead();
            super.enlarge();
            int[] tmpArr = new int[getMaxSize()];
            if (getTail() >= h) {
                System.arraycopy(priority, 0, tmpArr, 0, priority.length);
            } else {
                int n = getItemsCount()-h, newPos = getMaxSize()-n;
                System.arraycopy(priority, 0, tmpArr, 0, getTail()+1);
                System.arraycopy(priority, h, tmpArr, newPos, n);
            }
            priority = tmpArr;
        }

        private void insertToHead(int val, int newPriority) {
            commonInsert(val);
            int h = getHead();
            if (getItemsCount() > 1) {
                if (h == 0) h = getMaxSize();
                setHead(--h);
                setQueueAt(h,val);
            }
            priority[h] = newPriority;
        }

        private void insertToTail(int val, int newPriority) {
            super.insert(val);
            priority[getTail()] = newPriority;
        }

        public void insert(int val, int newPriority) {
            /*
                если новый элемент имеет приоритет выше, чем находящийся в голове очереди,
                поставить его в голову (переместить ее указатель на новый),
                иначе с конца очереди искать элемент, приоритет которого не меньше нового,
                и добавить новый за найденным
             */
            if (isEmpty() || priority[getHead()] < newPriority)
                insertToHead(val, newPriority);
            else {
                if (getItemsCount() == getMaxSize()) enlarge();
                // с хвоста двигаться к голове очереди, пока приоритет нового больше очередного
                // (хвост очереди растет в направлении увеличения индекса, голова - в направлении убывания)
                int t = getTail(), h = getHead(), cnt = 0, i = t;
                while (i < h && newPriority > priority[i]) {
                    cnt++;
                    if (t < h) {
                        if (--i < 0) i = getMaxSize()-1;
                    } else {
                        if (++i == getMaxSize()) i = 0;
                    }
                }
                if (i != t) {
                    // очередь либо уже удвоена, либо в ней есть еще хотя бы 1 место
                    int[] queue = getQueue(),
                          tmpq = Arrays.copyOf(queue,queue.length),
                          tmpp = Arrays.copyOf(priority,priority.length);
                    if (t < h) {
                        int src = i == getMaxSize()-1 ? 0 : i+1;
                        System.arraycopy(queue, src, tmpq, src+1, cnt);
                        tmpq[src] = val;
                        setQueue(tmpq);
                        System.arraycopy(priority, src, tmpp, src+1, cnt);
                        priority = tmpp;
                        priority[src] = newPriority;
                        setTail(t+1);
                    } else {
                        // анализатор кода подсвечивает условие "хвост меньше головы"
                        // как всегда истинное, практически смоделировать такую ситуацию
                        // не удалось, так что здесь оставляю "заглушку"
                        System.out.println("TODO: head<=tail");
                    }
                    incItems();
                } else
                    insertToTail(val, newPriority);
            }
        }

        @Override public void display() throws RuntimeException {
            if (isEmpty())
                throw new RuntimeException("Queue is empty");
            for (int i = 0; i < getMaxSize(); i++)
                if (priority[i] == 0)
                    System.out.print(" (- -)");
                else
                    System.out.print(" (val="+getQueueAt(i)+", pr="+priority[i]+")");
            System.out.println(" (head=#"+getHead()+", tail=#"+getTail()+")");
        }

        // несмотря на наличие дополнительного массива приоритетов,
        // метод извлечения из такой очереди ничем не отличается от родительского,
        // этот же позволяет узнать приоритет извлекаемого элемента
        public PrioritizedElement priorityRemove() throws RuntimeException {
            if (isEmpty()) throw new RuntimeException("Queue is empty");

            int h = getHead(), v = getQueueAt(h), p = priority[h];
            remove();
            return new PrioritizedElement(v, p);
        }
    }

// 3. описать метод проверки скобочной последовательности (см. код урока, комментарий под главным классом)
    static int indexOf(String s, char c) {
        int i = 0;
        while (i < s.length() && c != s.charAt(i)) i++;
        return i < s.length() ? i : -1;
    }

    public static void check(String input) {
        final String opening = "([{<", closing = ")]}>";
        Stack s = new Stack(input.length()/2);
        boolean balanced = true;
        int i = 0, j;
        while (i < input.length() && balanced) {
            if ((j = indexOf(opening, input.charAt(i))) >= 0)
                s.push(j);
            else
                if ((j = indexOf(closing, input.charAt(i))) >= 0)
                    try {
                        balanced = s.pop() == j;
                    } catch (RuntimeException ex) {
                        balanced = false;
                    }
            if (balanced) i++;
        }
        System.out.println(
            "Баланс открытых и закрытых скобок "+
            (balanced ? "" : "не ")+"соблюдается"+
            (balanced ? "" : " в позиции "+i));
    }

    public static void main(String[] args) {
        Queue q = new Queue(5);
        q.insert(5); q.insert(2); q.insert(1); q.insert(1); q.insert(4); q.display();
        q.remove(); q.remove(); q.display();
        q.insert(7); q.insert(3); q.display();

        Deque d = new Deque(5);
        d.insertToHead(1); d.display();
        d.insertToTail(2); d.display();
        d.insertToHead(3); d.display();
        d.insertToTail(4); d.display();
        d.insertToTail(5); d.display();
        d.insertToHead(6); d.display();
        d.insertToTail(7); d.display();
        d.insertToHead(8); d.display();
        d.insertToTail(9); d.display();
        d.insertToHead(0); d.display();
        System.out.println("->"+d.removeFromHead()); d.display();
        System.out.println("->"+d.removeFromTail()); d.display();
        System.out.println("->"+d.removeFromTail()); d.display();
        System.out.println("->"+d.removeFromHead()); d.display();
        System.out.println("->"+d.removeFromHead()); d.display();
        System.out.println("->"+d.removeFromHead()); d.display();
        System.out.println("->"+d.removeFromTail()); d.display();
        System.out.println("->"+d.removeFromHead()); d.display();

        PrioritizedQueue pq = new PrioritizedQueue(5);
        pq.insert(1,1); pq.display();
        pq.insert(2,3); pq.display();
        pq.insert(1,3); pq.display();
        pq.insert(2,1); pq.display();
        pq.insert(0,2); pq.display();
        pq.insert(1,4); pq.display();
        pq.insert(7,4); pq.display();
        PrioritizedElement pqe = pq.priorityRemove();
        System.out.println("->val="+pqe.getValue()+" pr="+pqe.getPriority()); pq.display();
        pq.insert(9,7); pq.display();
        pqe = pq.priorityRemove();
        System.out.println("->val="+pqe.getValue()+" pr="+pqe.getPriority()); pq.display();

        check("([({{([<a>b]c)d}e}f]g))");
    }
}