package br.com.ufu.lsi.model;

public class WindowStatistic {
    
    private int size;
    
    private double proportion;

    public WindowStatistic( int size, double proportion ) {
        super();
        this.size = size;
        this.proportion = proportion;
    }

    public int getSize() {
        return size;
    }

    public void setSize( int size ) {
        this.size = size;
    }

    public double getProportion() {
        return proportion;
    }

    public void setProportion( double proportion ) {
        this.proportion = proportion;
    }
    
    
  

}
