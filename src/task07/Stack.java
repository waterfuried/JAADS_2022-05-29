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
    public boolean hasElements() { return top >= 0; }
    public void push(int i) {
        if (top == this.maxSize - 1) {
            int[] tmp = new int[maxSize <<= 1];
            System.arraycopy(stackArr, 0, tmp, 0, stackArr.length);
            stackArr = tmp;
        }
        stackArr[++top] = i;
    }

    public int pop() {
        return stackArr[top--];
    }

    public int peek() {
        return stackArr[top];
    }

    @Override public String toString() {
        if (isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        int t = top;
        while (t >= 0) sb.append(" ").append(stackArr[t--]);
        return sb.toString();
    }

    public String toString(String separator, boolean asChar) {
        if (isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        int t = top;
        while (t >= 0) {
            if (asChar)
                sb.append((char)((int)'A'+stackArr[t]));
            else
                sb.append(stackArr[t]);
            if (--t >= 0) sb.append(separator);
        }
        return sb.toString();
    }

    public void display(String ... s) { display(false, s); }

    public void display(boolean asChar, String ... s) {
        String str = null;
        if (s != null && s.length > 0) {
            str = s[0];
            if (s.length > 1) str += this.toString(s[1], asChar);
        }
        if (str == null) str = this.toString();
        System.out.println(str);
    }
}