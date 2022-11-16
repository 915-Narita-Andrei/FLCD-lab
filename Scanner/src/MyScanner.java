import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MyScanner {
    public SymbolTable identifiersST;
    public SymbolTable constantsST;
    public ProgramInternalForm pif;

    List<String> operators;
    List<String> separators;
    String separatorsString;
    List<String> reservedWords;
    String errors = "";

    public MyScanner(){
        identifiersST = new SymbolTable();
        constantsST = new SymbolTable();
        pif = new ProgramInternalForm();
        operators = new ArrayList<>();
        separators = new ArrayList<>();
        reservedWords = new ArrayList<>();
        init();
    }

    public void init(){
        readTokens("D:\\Stuff\\faculta\\anu3-sem1\\FLCD\\lab\\project\\FLCD-lab\\scannerData\\Token.in");
        separatorsString = "";
        separators.forEach(separator -> {
            separatorsString+=separator;
        });
    }


    public void scan(String fileName) {
        File program = new File(fileName);
        Scanner reader;
        try {
          reader = new Scanner(program);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int lineNumber = 1;
        while (reader.hasNextLine()){
            var line = reader.nextLine();
            line = line.strip();
            scanLine2(line, lineNumber);
            lineNumber++;
        }

    }

    private void scanLine(String line, int lineNumber){
        String token = "";
        for(int i=0; i<line.length(); i++){
            token = "";
            int iBefore = i;
            Pair<Integer,Integer> pifValue = new Pair<>(-1,-1);
            if(separators.contains(String.valueOf(line.charAt(i)))) {
                if (line.charAt(i) != ' ')
                    pif.add(String.valueOf(line.charAt(i)), pifValue);
                continue;
            }
            else if(isPartOfOperator(String.valueOf(line.charAt(i)))) {
                token = "";
                while (i < line.length() && isPartOfOperator(token + line.charAt(i))) {
                    token += line.charAt(i);
                    i++;
                }
                if(operators.contains(token)) {
                    pif.add(token, pifValue);
                    i--;
                    continue;
                }
                else {
                    i = iBefore;
                }
            }
            else if(isPartOfReservedWords(String.valueOf(line.charAt(i)))) {
                token = "";
                while (i < line.length() && isPartOfReservedWords(token + line.charAt(i))) {
                    token += line.charAt(i);
                    i++;
                }
                if(reservedWords.contains(token)) {
                    pif.add(token, pifValue);
                    i--;
                    continue;
                }
                else {
                    i = iBefore;
                }
            }
            if(line.charAt(i) == '\'' || line.charAt(i) == '\"'){
                token = String.valueOf(line.charAt(i));
                i++;
                while (i < line.length() && line.charAt(i) != '\'' && line.charAt(i) != '\"'){
                    token += line.charAt(i);
                    i++;
                }
                if(i < line.length())
                    token += line.charAt(i);
                if(isConstant(token)){
                    pifValue = constantsST.add(token);
                    pif.add(token, pifValue);
                }
                else {
                    errors += "Lexical error on line " + lineNumber + " on " + token;
                    System.out.println("Lexical error on line " + lineNumber + " on " + token);
                    return;
                }
            } else if (Character.isDigit(line.charAt(i))) {
                token = "";
                while (i < line.length() && Character.isDigit(line.charAt(i))){
                    token += line.charAt(i);
                    i++;
                }
                if(!separators.contains(String.valueOf(line.charAt(i))) && !isPartOfOperator(String.valueOf(line.charAt(i))))
                {
                    i = iBefore;
                }
                else {
                    i--;
                    if (isConstant(token)) {
                        pifValue = constantsST.add(token);
                        pif.add(token, pifValue);
                    } else {
                        errors += "Lexical error on line " + lineNumber + " on " + token;
                        System.out.println("Lexical error on line " + lineNumber + " on " + token);
                        return;
                    }
                    continue;
                }
            }
            else {
                token = "";
                while (i < line.length() && Character.isLetterOrDigit(line.charAt(i))) {
                    token += line.charAt(i);
                    i++;
                }
                i--;
                if (isIdentifier(token)) {
                    pifValue = identifiersST.add(token);
                    pif.add(token, pifValue);
                } else {
                    errors += "Lexical error on line " + lineNumber + " on " + token;
                    System.out.println("Lexical error on line " + lineNumber + " on " + token);
                    return;
                }
            }
        }
    }

    private void scanLine2(String line, int lineNumber){
        var tokenizer = new StringTokenizer(line, separatorsString, true);
        while(tokenizer.hasMoreTokens()) {
            Pair<Integer,Integer> pifValue = new Pair<>(-1,-1);
            var token = tokenizer.nextToken();
            if (!token.equals(" ")) {
                token = token.strip();
                if (separators.contains(token)) {
                    pif.add(token, pifValue);
                } else if (operators.contains(token)) {
                    pif.add(token, pifValue);
                } else if(reservedWords.contains(token)){
                    pif.add(token, pifValue);
                }
                else {
                    if (isConstant(token)) {
                        pifValue = constantsST.add(token);
                        pif.add("constant", pifValue);
                    } else if (isIdentifier(token)) {
                        pifValue = identifiersST.add(token);
                        pif.add("identifier", pifValue);
                    } else {
                        errors += "Syntax error: unidentified token [" + token + "] on line " + lineNumber + "\n";
                        //System.out.println("Syntax error: unidentified token [" + token + "] on line " + lineNumber);
                    }
                }
            }
        }

    }

    private boolean isPartOfOperator(String c){
        for (String operator : operators) {
            if (operator.startsWith(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPartOfReservedWords(String c){
        for (String operator : reservedWords) {
            if (operator.startsWith(c)) {
                return true;
            }
        }
        return false;
    }

    private void readTokens(String fileName) {
        File program = new File(fileName);
        Scanner reader;
        try {
            reader = new Scanner(program);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while (reader.hasNextLine()){
            var line = reader.nextLine();
            if(line.equals("operators:"))
                populateOperators(reader);
            else if(line.equals("separators:"))
                populateSeparators(reader);
            else if(line.equals("reserved words:"))
                populateReservedWords(reader);
        }
    }

    private void populateOperators(Scanner reader){
        int i=17;
        while (reader.hasNextLine() && i>0){
            var line = reader.nextLine();
            operators.add(line);
            i--;
        }
    }

    private void populateSeparators(Scanner reader){
        int i=6;
        while (reader.hasNextLine() && i>0){
            var line = reader.nextLine();
            separators.add(line);
            i--;
        }
    }

    private void populateReservedWords(Scanner reader){
        int i=10;
        while (reader.hasNextLine() && i>0){
            var line = reader.nextLine();
            reservedWords.add(line);
            i--;
        }
    }

    private boolean isIdentifier(String token){
        FiniteAutomata finiteAutomata = new FiniteAutomata("D:\\Stuff\\faculta\\anu3-sem1\\FLCD\\lab\\project\\FLCD-lab\\FAData\\FAIdentifier.in");
        return finiteAutomata.isAccepted(token);
        //return token.matches("^[a-zA-Z]([a-zA-Z]|[0-9])*$");
    }

    private boolean isConstant(String token){
        FiniteAutomata finiteAutomata = new FiniteAutomata("D:\\Stuff\\faculta\\anu3-sem1\\FLCD\\lab\\project\\FLCD-lab\\FAData\\FAConstant.in");
        return finiteAutomata.isAccepted(token);
//        return token.matches("\"[a-zA-Z0-9]*\"|'[a-zA-Z0-9]'|[0-9]|[1-9][0-9]*|\\+[0-9]|[1-9][0-9]]|-[0-9]|[1-9][0-9]|\\+[1-9]|-[1-9]]");
    }

    @Override
    public String toString() {
        String s = errors + "\nIdentifiers Symbol Table:\n";
        var iST = identifiersST.getHashTable().getElems();
        for(int i=0; i<iST.size(); i++){
            if(iST.get(i).size() > 0){
                for(int j=0; j<iST.get(i).size(); j++){
                    s += "(" + i + ", " + j + ") -> " + iST.get(i).get(j) + "\n";
                }
            }
        }

        s += "Constants Symbol Table:\n";

        var cST = constantsST.getHashTable().getElems();
        for(int i=0; i<cST.size(); i++){
            if(cST.get(i).size() > 0){
                for(int j=0; j<cST.get(i).size(); j++){
                    s += "(" + i + ", " + j + ") -> " + cST.get(i).get(j) + "\n";
                }
            }
        }
        s += "PIF:\n";
        s += pif;
        return s;
    }
}
