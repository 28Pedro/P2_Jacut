package br.ufal.ic.p2.jackut.exceptions;

public class FileError extends Exception {
    public FileError(String path){super("Error in path: " + path );}
}
