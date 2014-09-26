package br.com.ufu.lsi.model;

import java.io.Serializable;

public abstract class Chromossome implements Serializable, Comparable< Chromossome > {
    
    private static final long serialVersionUID = 1L;

    protected double fitness;
        
    protected Gene [] genes;
    
    public abstract String encode( String value );
    
    public abstract void decode();

    public abstract String toStringEncoded();
    
    public abstract String getEncodedClass();
    
    public abstract boolean antecedentEquals( Chromossome chrom );
    
    public abstract boolean antecedentConsequentEquals( Chromossome chrom );
    
    public abstract boolean sameAntecedent( Chromossome chrom );

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
    
    @Override
    public int compareTo( Chromossome otherChromossome ) {
        if( this.fitness < otherChromossome.fitness )
            return 1;
        if( this.fitness > otherChromossome.fitness ) {
            return -1;
        }
        return 0;
    }
}
