package task08;

class Item {
  private int data;
  private Item next;

  public Item(int data) {
    this.data = data;
    this.next = null;
  }

  public int getKey() { return data; }
  public void setKey(int data) { this.data = data; }
  public Item getNext() { return next; }
  public void setNext(Item next) { this.next = next; }
}