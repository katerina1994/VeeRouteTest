package com.example.TestVeeRoute;

class SymbolWithPosition {

    private final Bracket mBracket;

    final int sourceIndex;
    final char ch;

    private int mDepth;

    SymbolWithPosition(char ch, int index) {
        this.sourceIndex = index;
        this.ch = ch;
        this.mBracket = Bracket.create(ch);
    }

    boolean isBracket() {
        return this.mBracket != null;
    }

    boolean isLeft() {
        return this.isBracket() && mBracket.isLeft();
    }

    boolean isPair(SymbolWithPosition other) {
        return this.isBracket() && this.mBracket.isPair(other.mBracket);
    }

    int getDepth() {
        return mDepth;
    }

    void setDepth(int depth) {
        this.mDepth = depth;
    }

}
