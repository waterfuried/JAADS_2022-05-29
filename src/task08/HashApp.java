package task08;

import java.io.*;

public class HashApp {
  public static void main(String[] args) throws IOException {
    int aKey;
    final int size, n, keysPerCell = 10;
    boolean enough = false;
    
    // Ввод размеров
    System.out.print("Enter size of hash table: ");
    size = getInt();
    System.out.print("Enter initial number of items: ");
    n = getInt();
    // Создание таблицы
    HashTable theHashTable = new HashTable(size);
    for (int j=0; j<n; j++) {
      theHashTable.insert((int)(Math.random() * keysPerCell * size));
    }
    
    while (!enough) {
      System.out.print("Enter first letter of show, insert, delete, find, or exit: ");
      char choice = getChar();
      switch (choice) {
        case 's':
          theHashTable.display();
          break;
        case 'i':
          System.out.print("Enter key value to insert: ");
          aKey = getInt();
          System.out.println(aKey + " has " + (theHashTable.insert(aKey) ? "" : "not ") + "been inserted");
          break;
        case 'd':
          System.out.print("Enter key value to delete: ");
          aKey = getInt();
          System.out.println(aKey + (theHashTable.delete(aKey) ? " has been deleted" : ": no such value"));
          break;
        case 'f':
          System.out.print("Enter key value to find: ");
          aKey = getInt();
          System.out.println(theHashTable.find(aKey) != null ? aKey + " found" : "Could not find " + aKey);
          break;
        case 'e':
          enough = true;
          break;
        default:
          System.out.print("Invalid entry\n");
      }
    }
  }
  
  public static String getString() throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    return br.readLine();
  }
  
  public static char getChar() throws IOException {
    return getString().charAt(0);
  }

  public static int getInt() throws IOException {
    return Integer.parseInt(getString());
  }
}