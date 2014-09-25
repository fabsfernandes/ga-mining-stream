package br.com.ufu.lsi.model;

import java.io.Serializable;
import java.util.List;

public class RuleSet implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private String encodedClass;
    
    private List<Chromossome> population;
    
    public RuleSet( String encodedClass, List< Chromossome > population ) {
        this.encodedClass = encodedClass;
        this.population = population;
    }

    public String getEncodedClass() {
        return encodedClass;
    }

    public void setEncodedClass( String encodedClass ) {
        this.encodedClass = encodedClass;
    }

    public List< Chromossome > getPopulation() {
        return population;
    }

    public void setPopulation( List< Chromossome > population ) {
        this.population = population;
    }

}
