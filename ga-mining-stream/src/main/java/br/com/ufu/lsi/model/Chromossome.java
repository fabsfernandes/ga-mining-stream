package br.com.ufu.lsi.model;

public abstract class Chromossome {
    
    protected double fitness;
        
    protected Gene [] genes;
    
    public abstract String encode( String value );
    
    public abstract void decode();

    public abstract String toStringEncoded();
}
