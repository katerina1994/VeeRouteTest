package com.example.TestVeeRoute;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        List<Bracket> brackets = new ArrayList<>();
        String input = "1+{2*{3+4}-6}-1-[1+[-1+2*(aaa)/2]/(10-[10-{a^2}])]";


        boolean isValid = valid(input, brackets);
        System.out.println(isValid ? "Valid" : "Not valid");
        if (!isValid){
            return;
        }

        System.out.println("Do you need formatted output (Could be slow)? (y/n)");
        char ch = (char) System.in.read();
        if (ch != 'y' && ch != 'Y') {
            return;
        }

        List<String> formattedResult = convertToStrings(brackets);
        for (String str : formattedResult) {
            System.out.println(str);
        }
    }

    private static List<String> convertToStrings(List<Bracket> brackets) {
        Vector<char[]> vector = new Vector<>();
        for (Bracket br : brackets) {
            if (vector.size() < br.getDepth() + 1) {
                vector.setSize(br.getDepth() + 1);
            }
            char[] str = vector.get(br.getDepth());
            if (str == null) {
                str = new char[brackets.size()];
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

    private static boolean valid(String st, List<Bracket> brackets) {
        Stack<Bracket> stack = new Stack<>();
        for (int i = 0; i < st.length(); i++) {
            Bracket br = new Bracket(st.charAt(i), i);

            if (br.isBracket()) {

                if (br.isLeft()) {
                    stack.push(br);
                } else {
                    if (stack.empty()) {
                        System.out.println("Unexpected closing bracket " + br.ch + " at " + br.sourceIndex);
                        return false;
                    }
                    Bracket leftBr = stack.pop();
                    if (!br.isPair(leftBr)) {
                        System.out.println("Unpaired closing bracket " + br.ch + " at " + br.sourceIndex
                                + " for opening bracket "+ leftBr.ch + " at " + leftBr.sourceIndex);
                        return false;
                    }
                    leftBr.setDepth(stack.size());
                    brackets.add(leftBr);
                    br.setDepth(stack.size());
                    brackets.add(br);
                    System.out.println("Pair: " + leftBr.ch + " " + st.charAt(i) + " at: " + leftBr.sourceIndex + ", " + i);
                }

            } else {
//                System.out.println("Unexpected non-bracket symbol " + br.ch + " at " + br.sourceIndex);
//                return false
                br.setDepth(stack.size());
                brackets.add(br);
            }

        }

        if (stack.empty()) {
            return true;
        }
        Bracket leftBr = stack.pop();
        System.out.println("Opening bracket " + leftBr.ch + " at " + leftBr.sourceIndex + " doesn't have its closing pair");
        return false;
    }

}
