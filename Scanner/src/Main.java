public class Main {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable(199);
        System.out.println(hashTable.add("a"));
        System.out.println(hashTable.add("b"));
        System.out.println(hashTable.add("ab"));
        System.out.println(hashTable.add("ba"));
    }
}
