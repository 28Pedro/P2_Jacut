package br.ufal.ic.p2.jackut.wrappers;

public class DoubleClassReturn<A,B>{

    private A objA;
    private B objB;

    public DoubleClassReturn(A objA, B objB) {
        this.objA = objA;
        this.objB = objB;
    }

    public A getFirst(){
        return objA;
    }

    public B getSecond(){
        return objB;
    }

}
