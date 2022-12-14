public class Pair<T1, T2> {
    private T1 first;
    private T2 second;

    public Pair(){
        this.first = null;
        this.second = null;
    }
    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public T1 getFirst(){
        return first;
    }

    public T2 getSecond(){
        return second;
    }

    public void setFirst(T1 a){
        this.first = a;
    }

    public void setSecond(T2 b){
        this.second = b;
    }

    @Override
    public String toString(){
        return "{" + this.first + " " + this.second + "}";
    }
}
