package br.com.ufu.lsi.model;

public class Gene {
    
    private Boolean active;
    
    private String value;
    
    private Boolean isClass;

    public Gene( Boolean active, String value, Boolean isClass ) {
        super();
        this.active = active;
        this.value = value;
        this.isClass = isClass;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive( Boolean active ) {
        this.active = active;
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
        return "Gene [active=" + active + ", value=" + value + ", isClass=" + isClass + "]";
    }
    
    public String toStringEncoded() {
        return value;
    }

}
