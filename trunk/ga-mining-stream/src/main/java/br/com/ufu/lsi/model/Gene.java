package br.com.ufu.lsi.model;

import java.io.Serializable;

public class Gene implements Serializable {
    
    //private Boolean active;
    
    private String value;
    
    private Boolean isClass;

    public Gene( String value, Boolean isClass ) {
        super();
        //this.active = active;
        this.value = value;
        this.isClass = isClass;
    }


    public String getValue() {
        return value;
    }

    public void setValue( String value ) {
        this.value = value;
    }

    public Boolean getIsClass() {
        return isClass;
    }

    public void setIsClass( Boolean isClass ) {
        this.isClass = isClass;
    }

    @Override
    public String toString() {
        return "Gene [value=" + value + ", isClass=" + isClass + "]";
    }
    
    public String toStringEncoded() {
        return value;
    }

}
