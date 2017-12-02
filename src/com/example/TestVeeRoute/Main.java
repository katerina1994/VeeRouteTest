package com.example.TestVeeRoute;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        List<SymbolWithPosition> symbols = new ArrayList<>();
        String input = "1+{2*{3+4}-6}-1-[1+[-1+2*(aaa)/2]/(10-[10-{a^2}])]";

        boolean isValid = valid(input, symbols);
        System.out.println(isValid ? "Valid" : "Not valid");
        if (!isValid){
            return;
        }

        System.out.println("Do you need formatted output (Could be slow)? (y/n)");
        char ch = (char) System.in.read();
        if (ch != 'y' && ch != 'Y') {
            return;
        }

        List<String> formattedResult = convertToStrings(symbols);
        for (String str : formattedResult) {
            System.out.println(str);
        }
    }

    private static List<String> convertToStrings(List<SymbolWithPosition> symbols) {
        Vector<char[]> vector = new Vector<>();
        for (SymbolWithPosition br : symbols) {
            if (vector.size() < br.getDepth() + 1) {
                vector.setSize(br.getDepth() + 1);
            }
            char[] str = vector.get(br.getDepth());
            if (str == null) {
                str = new char[symbols.size()];
                Arrays.fill(str, ' ');
                vector.set(br.getDepth(), str);
            }
            str[br.sourceIndex] = br.ch;
        }

        List<String> result = new ArrayList<>(vector.size());
        for (char[] str : vector) {
            result.add(new String(str));
        }
        return result;
    }

    private static boolean valid(String st, List<SymbolWithPosition> symbols) {
        Stack<SymbolWithPosition> stack = new Stack<>();
        for (int i = 0; i < st.length(); i++) {
            SymbolWithPosition br = new SymbolWithPosition(st.charAt(i), i);

            if (br.isBracket()) {

                if (br.isLeft()) {
                    stack.push(br);
                } else {
                    if (stack.empty()) {
                        System.out.println("Unexpected closing bracket " + br.ch + " at " + br.sourceIndex);
                        return false;
                    }
                    SymbolWithPosition leftBr = stack.pop();
                    if (!br.isPair(leftBr)) {
                        System.out.println("Unpaired closing bracket " + br.ch + " at " + br.sourceIndex
                                + " for opening bracket "+ leftBr.ch + " at " + leftBr.sourceIndex);
                        return false;
                    }
                    leftBr.setDepth(stack.size());
                    symbols.add(leftBr);
                    br.setDepth(stack.size());
                    symbols.add(br);
                    System.out.println("Pair: " + leftBr.ch + " " + st.charAt(i) + " at: " + leftBr.sourceIndex + ", " + i);
                }

            } else {
//                System.out.println("Unexpected non-bracket symbol " + br.ch + " at " + br.sourceIndex);
//                return false;
                br.setDepth(stack.size());
                symbols.add(br);
            }

        }

        if (stack.empty()) {
            return true;
        }
        SymbolWithPosition leftBr = stack.pop();
        System.out.println("Opening bracket " + leftBr.ch + " at " + leftBr.sourceIndex + " doesn't have its closing pair");
        return false;
    }

}
