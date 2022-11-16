import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FiniteAutomata {
    List<String> states;
    List<String> alphabet;
    String initalState;
    List<String> finalStates;
    List<Transition> transitions;

    public FiniteAutomata(String file){
        states = new ArrayList<>();
        alphabet = new ArrayList<>();
        initalState = "";
        finalStates = new ArrayList<>();
        transitions = new ArrayList<>();
        readData(file);
    }

    public void run(){
        boolean done = false;
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        while (!done){
            printOptions();
            try {
                var line = reader.readLine();
                if (line.equals("0")) {
                    done = true;
                } else if (line.equals("1")) {
                    states.forEach(System.out::println);
                } else if (line.equals("2")) {
                    alphabet.forEach(System.out::println);
                } else if (line.equals("3")) {
                    System.out.println(initalState);
                } else if (line.equals("4")) {
                    finalStates.forEach(System.out::println);
                } else if (line.equals("5")) {
                    transitions.forEach((transition -> {
                        System.out.println(transition.first + ", " + transition.value + " -> " + transition.second);
                    }));
                } else if (line.equals("6")) {
                    var sequence = reader.readLine();
                    if (!checkDFA()) {
                        System.out.println("Not DFA");
                        continue;
                    }
                    if (isAccepted(sequence)) {
                        System.out.println(sequence + " is accepted");
                    } else {
                        System.out.println(sequence + " is not accepted");
                    }
                }
                else {
                    System.out.println("Wrong Input!");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isAccepted(String sequence) {
        if(sequence.length() == 0)
            return finalStates.contains(initalState);

        String state = initalState;
        for (int i=0; i<sequence.length(); i++){
            var found = false;
            for(Transition transition : transitions)
                if(transition.first.equals(state) && transition.value.equals(String.valueOf(sequence.charAt(i)))){
                    state = transition.second;
                    found = true;
                    break;
                }
            if(!found)
                return false;
        }
        return finalStates.contains(state);
    }


    private boolean checkDFA(){
        for(int i=0; i<transitions.size(); i++){
            for(int j=i+1; j<transitions.size(); j++){
                if(transitions.get(i).first.equals(transitions.get(j).first) &&
                        transitions.get(i).value.equals(transitions.get(j).value))
                    return false;
            }
        }
        return true;
    }

    private void printOptions(){
        System.out.println("0.Exit");
        System.out.println("1.Display set of states");
        System.out.println("2.Display alphabet");
        System.out.println("3.Display initial state");
        System.out.println("4.Display final states");
        System.out.println("5.Display transitions");
        System.out.println("6.Check sequence");
    }

    private void readData(String fileName){
        File program = new File(fileName);
        Scanner reader;
        try {
            reader = new Scanner(program);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(reader.hasNextLine()){
            var line = reader.nextLine();
            states = List.of(line.split(","));
        }
        if(reader.hasNextLine()){
            var line = reader.nextLine();
            alphabet = List.of(line.split(","));
        }
        if(reader.hasNextLine()){
            var line = reader.nextLine();
            initalState = line;
        }
        if(reader.hasNextLine()){
            var line = reader.nextLine();
            finalStates = List.of(line.split(","));
        }
        while (reader.hasNextLine()){
            var line = reader.nextLine();
            var l = line.split(",");
            Transition transition = new Transition(l[0], l[2], l[1]);
            transitions.add(transition);
        }
    }
}
