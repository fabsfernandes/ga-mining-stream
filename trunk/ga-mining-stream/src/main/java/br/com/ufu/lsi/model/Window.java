
package br.com.ufu.lsi.model;

import java.util.List;

public class Window {

    private List< Chromossome > chromossomes;
    
    private String encodedClass;
    
    // TODO: analyze windows sizes. Temporary dot not worry with this.
    private int size;
    
    public Window( String encodedClass, List<Chromossome> chromossomes ){
        this.encodedClass = encodedClass;
        this.chromossomes = chromossomes;
    }

    public List< Chromossome > getChromossomes() {
        return chromossomes;
    }

    public void setChromossomes( List< Chromossome > chromossomes ) {
        this.chromossomes = chromossomes;
    }

    public String getEncodedClass() {
        return encodedClass;
    }

    public void setEncodedClass( String encodedClass ) {
        this.encodedClass = encodedClass;
    }

    public int getSize() {
        return size;
    }

    public void setSize( int size ) {
        this.size = size;
    }
    
    public void clear(){
        this.chromossomes.clear();
    }

}
