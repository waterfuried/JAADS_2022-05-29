package task03;

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

    private static class Queue {
        private int maxSize; // размер
        private int[] queue; // место хранения
        private int head;    // отсюда уходят
        private int tail;    // сюда приходят
        private int items;   // текущее количество

        public Queue(int s) {
            maxSize = s;
            queue = new int[maxSize];
            head = 0;
            tail = -1;
            items = 0;
        }

        public boolean isEmpty() { return (items == 0); }
        public boolean isFull() { return (items == maxSize); }
        public int size() { return items; }

        public void insert(int i) {
            if (isFull()) {
                maxSize *= 2;
                int[] tmpArr = new int[maxSize];
                if (tail >= head) {
                    System.arraycopy(queue, 0, tmpArr, 0, queue.length);
                } else {
                    System.arraycopy(queue, 0, tmpArr, 0, tail + 1);
                    System.arraycopy(queue, head, tmpArr,
                            maxSize - (queue.length - head), queue.length - head);
                    head = maxSize - head - 1;
                }
            }
            if (tail == maxSize - 1) {
                tail = -1;
            }
            queue[++tail] = i;
            ++items;
        }

        public int remove() {
            int temp =  queue[head++];
            head %= maxSize;
            items--;
            return temp;
        }

        public int peek(){
            return queue[head];
        }
    }

    public static void main(String[] args) {
    }
}

// public static void check(String input) {...}