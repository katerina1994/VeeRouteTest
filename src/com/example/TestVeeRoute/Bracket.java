package com.example.TestVeeRoute;

class Bracket {
    private final static String LEFT_BRACKETS = "({[";

    private final boolean mIsLeft;
    private final bracketType mType;

    final int sourceIndex;
    final char ch;

    private int mDepth;

    Bracket(char ch, int index) {
        this.sourceIndex = index;
        this.ch = ch;
        this.mIsLeft = LEFT_BRACKETS.indexOf(ch) >= 0;

        switch (ch) {
            case '(':
            case ')':
                this.mType = bracketType.round;
                break;
            case '[':
            case ']':
                this.mType = bracketType.square;
                break;
            case '{':
            case '}':
                this.mType = bracketType.curly;
                break;
            default:
                this.mType = bracketType.nonBracket;
                break;
        }
    }

    boolean isBracket() {
        return this.mType != bracketType.nonBracket;
    }

    boolean isLeft() {
        return mIsLeft;
    }

    boolean isPair(Bracket other) {
        return this.mType == other.mType && this.mIsLeft != other.mIsLeft;
    }

    int getDepth() {
        return mDepth;
    }

    void setDepth(int depth) {
        this.mDepth = depth;
    }


    private enum bracketType {
        round,
        square,
        curly,
        nonBracket
    }
}
