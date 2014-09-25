package br.com.ufu.lsi.model;

import java.io.Serializable;

public abstract class Chromossome implements Serializable {
    
    private static final long serialVersionUID = 1L;

    protected double fitness;
        
    protected Gene [] genes;
    
    public abstract String encode( String value );
    
    public abstract void decode();

    public abstract String toStringEncoded();
    
    public abstract String getEncodedClass();
    
    public abstract boolean antecedentEquals( Chromossome chrom );
    
    public abstract boolean antecedentConsequentEquals( Chromossome chrom );

    public double getFitness() {
        return fitness;
    }

    public void setFitness( double fitness ) {
        this.fitness = fitness;
    }

    public Gene[] getGenes() {
        return genes;
    }

    public void setGenes( Gene[] genes ) {
        this.genes = genes;
    }
}
