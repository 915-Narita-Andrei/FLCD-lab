import java.util.List;

public class SymbolTable {

    HashTable hashTable;

    public SymbolTable(){
        this.hashTable = new HashTable(1009);
    }

    public Pair<Integer, Integer> add(String val){
        return this.hashTable.add(val);
    }

    public HashTable getHashTable() {
        return hashTable;
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "hashTable=" + hashTable +
                '}';
    }
}
