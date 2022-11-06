import java.util.ArrayList;
import java.util.List;

public class ProgramInternalForm {

    private List<Pair<String, Pair<Integer, Integer>>> pif;

    public ProgramInternalForm(){
        this.pif = new ArrayList<>();
    }

    public void add(String token, Pair<Integer, Integer> value){
        var x = new Pair<>(token, value);
        pif.add(x);
    }

    @Override
    public String toString() {
        String s = "";
        for(int i=0; i<pif.size(); i++){
            s += pif.get(i) + "\n";
        }
        return s;
    }
}
