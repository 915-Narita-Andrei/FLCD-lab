import java.util.List;

public class SymbolTable {

    HashTable hashTable;

    public SymbolTable(){
        this.hashTable = new HashTable(97);
    }

    public List<Integer> getPos(String val){
        return this.hashTable.add(val);
    }
}
