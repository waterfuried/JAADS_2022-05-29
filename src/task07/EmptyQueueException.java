package task07;

public class EmptyQueueException extends RuntimeException {
    EmptyQueueException() { throw new RuntimeException("Queue is empty"); }
}
