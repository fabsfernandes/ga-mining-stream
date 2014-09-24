package br.com.ufu.lsi.model;

public abstract class Chromossome {
    
    protected double fitness;
        
    protected Gene [] genes;
    
    public abstract String encode( String value );
    
    public abstract void decode();

    public abstract String toStringEncoded();
    
    public abstract String getEncodedClass();

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
