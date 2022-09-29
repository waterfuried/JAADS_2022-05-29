package task08;

class HashTable {
  private Item[] hashArr;
  private int arrSize;
  private final static int MAX_CHAIN_LEN = 5; // 0 для неограниченного размера
  private static final int NONITEM = -1;
  
  public HashTable(int size) {
    this.arrSize = size;
    hashArr = new Item[arrSize];
  }

  private static boolean isValidItem(int key) { return key != NONITEM; }

  public void display() {
    for (int i=0; i<arrSize; i++) {
        // отобразить основные значения
        System.out.print(hashArr[i] != null && isValidItem(hashArr[i].getKey()) ? hashArr[i].getKey() : "*");
        if (hashArr[i] != null) {
            // отобразить значения из цепочек
            boolean started = false;
            Item p = hashArr[i].getNext();
            while (p != null) {
                if (isValidItem(p.getKey())) {
                    System.out.print((started ? "/" : "->(")+p.getKey());
                    started = true;
                }
                p = p.getNext();
            }
            if (started) System.out.print(")");
        }
        if (i < arrSize-1) System.out.print(", ");
    }
    System.out.println();
  }

  public int hashFunc(int key) { return key % arrSize; }

  private void rebuildTable() {
      //System.out.println("rebuilding table");
      Item[] tmp = new Item[arrSize];
      System.arraycopy(hashArr, 0, tmp, 0, arrSize);
      hashArr = new Item[arrSize<<=1];
      for (Item p : tmp)
          if (p != null) {
              // пройти по основным значениям
              if (isValidItem(p.getKey())) insert(p.getKey());
              Item chain = p.getNext();
              while (chain != null) {
                  // пройти по элементам цепочек
                  if (isValidItem(chain.getKey())) insert(chain.getKey());
                  chain = chain.getNext();
              }
          }
  }

  public boolean insert(int key) {
      int hashVal = hashFunc(key);
      //System.out.println("hash for "+key+" = "+hashVal);
      Item item = new Item(key), free = null, p;
      int i = 0;
      p = hashArr[hashVal];
      if (p == null)
          // добавить элемент в основные значения
          hashArr[hashVal] = item;
      else {
          if (p.getKey() == key) return false;
          p = p.getNext();
          if (p == null)
              hashArr[hashVal].setNext(item);
          else {
              do {
                  if (p.getKey() == key) return false;
                  if (!isValidItem(p.getKey()) && free == null) free = p;
                  i++;
                  if (p.getNext() == null) break;
                  p = p.getNext();
              } while (p != null);
              // если есть освобожденное место, добавить (вставить) элемент в него
              if (free != null)
                  free.setKey(key);
              // иначе или добавить элемент в цепочку, если ее длина не достигла предела,
              // или перестроить таблицу
              else {
                  //System.out.println("len["+hashVal+"]="+i);
                  if (i < MAX_CHAIN_LEN)
                      p.setNext(item);
                  else {
                      rebuildTable();
                      insert(key);
                  }
              }
          }
      }
      return true;
  }

  public boolean delete(int key) {
      int hashVal = hashFunc(key);
      Item p = hashArr[hashVal];
      while (p != null) {
          if (p.getKey() == key) {
              p.setKey(NONITEM);
              return true;
          }
          p = p.getNext();
      }
      return false;
  }
  
  public Item find(int key) {
      int hashVal = hashFunc(key);
      Item p = hashArr[hashVal];
      while (p != null) {
          if (p.getKey() == key) return p;
          p = p.getNext();
      }
      return null;
  }
  
}