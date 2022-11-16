public class Transition {
    String first;
    String second;
    String value;

    public Transition(String first, String second, String value) {
        this.first = first;
        this.second = second;
        this.value = value;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
