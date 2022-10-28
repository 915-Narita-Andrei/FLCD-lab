import java.util.ArrayList;
import java.util.List;

public class HashTable {

    private List<List<String>> elems;
    private int size;

    public HashTable(int size){
        this.size = size;
        this.elems = new ArrayList<>();
        for(int i=0; i<this.size; i++){
            this.elems.add(new ArrayList<>());
        }
    }

    public int hash(String val){
        int sum = 0;
        for(int i=0; i<val.length(); i++){
            sum += val.charAt(i);
        }
        return sum % size;
    }

    public int getVal(int hashValue, String val){
        for(int i=0; i<this.elems.get(hashValue).size(); i++){
            if(this.elems.get(hashValue).get(i).equals(val))
                return i;
        }
        return 0;
    }

    public List<Integer> add(String val){
        int hashValue = this.hash(val);
        if(this.elems.get(hashValue).contains(val)){
            return List.of(hashValue, this.getVal(hashValue, val));
        }
        this.elems.get(hashValue).add(val);
        return List.of(hashValue, this.elems.get(hashValue).size()-1);
    }
}
