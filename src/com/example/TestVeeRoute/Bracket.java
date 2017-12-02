package com.example.TestVeeRoute;

import java.util.HashMap;
import java.util.Map;

class Bracket {

    private static String[] bracketsDefinition = {
            "()",
            "{}",
            "[]"
    };

    private final static Map<Character, Bracket> DefinedBracketsMap = initDefinedBrackets();

    private static Map<Character, Bracket> initDefinedBrackets() {
        Map<Character, Bracket> result = new HashMap<>();
        for (int i = 0; i < bracketsDefinition.length; i++) {
            String pair = bracketsDefinition[i];
            result.put(pair.charAt(0), new Bracket(true, i));
            result.put(pair.charAt(1), new Bracket(false, i));
        }
        return result;
    }

    static Bracket create(char symbol) {
        return DefinedBracketsMap.get(symbol);
    }

    private final boolean mIsLeft;
    private final int mType;

    private Bracket(boolean isLeft, int type) {
        this.mIsLeft = isLeft;
        this.mType = type;
    }

    boolean isLeft() {
        return this.mIsLeft;
    }

    boolean isPair(Bracket other) {
        return other != null && this.mType == other.mType && this.mIsLeft != other.mIsLeft;
    }
}
