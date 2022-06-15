package task07;

public class Queue {
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

    final void nullify() {
        head = 0;
        tail = -1;
        items = 0;
    }

    public boolean isEmpty() {
        return items == 0;
    }

    public boolean isFull() {
        return items == maxSize;
    }

    public int size() {
        return items;
    }

    public void enlarge() {
        maxSize *= 2;
        int[] tmp = new int[maxSize];
        if (tail >= head) {
            System.arraycopy(queue, 0, tmp, 0, queue.length);
        } else {
            int n = items-head, newPos = maxSize-n;
            System.arraycopy(queue, 0, tmp, 0, tail+1);
            System.arraycopy(queue, head, tmp, newPos, n);
            head = newPos;
        }
        queue = tmp;
    }

    public void insert(int i) {
        if (items == maxSize) enlarge();
        if (tail == maxSize-1) tail = -1;
        queue[++tail] = i;
        items++;
    }

    public int remove() throws EmptyQueueException {
        if (items == 0) throw new EmptyQueueException();

        int tmp = queue[head++];
        if (head == maxSize) head = 0;
        if (--items == 0) nullify();
        return tmp;
    }

    public int peek() throws EmptyQueueException {
        if (items == 0) throw new EmptyQueueException();

        return queue[head];
    }

    @Override public String toString() throws EmptyQueueException {
        if (items == 0) throw new EmptyQueueException();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxSize; i++) sb.append(" ").append(queue[i]);
        sb.append(">> head=#").append(head).append(", tail=#").append(tail);
        return sb.toString();
    }

    public void display() throws EmptyQueueException {
        if (items == 0) throw new EmptyQueueException();

        System.out.println(this);
    }
}