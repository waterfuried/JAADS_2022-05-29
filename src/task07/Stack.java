package task07;

// класс стека
class Stack {
    private int maxSize;
    private int[] stackArr;
    private int top;

    public Stack(int size) {
        this.maxSize = size;
        this.stackArr = new int[size];
        this.top = -1;
    }

    public boolean isEmpty() { return top == -1; }

    public void push(int i) {
        stackArr[++top] = i;
    }

    public int pop() {
        return stackArr[top--];
    }

    public int peek() {
        return stackArr[top];
    }
}