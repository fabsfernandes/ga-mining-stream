
package br.com.ufu.lsi.model;

public class Nursery extends Chromossome {

    public Nursery() {

        this.genes = new Gene[ NurseryDataset.attributes.size() ];
    }

    public String encode( String value ) {

        String[] values = value.split( "," );
        for ( int i = 0; i < values.length; i++ ) {

            String code = NurseryDataset.attributes.get( i ).get( values[ i ] );
            Boolean isClass = ( i + 1 == values.length ) ? true : false;
            Boolean isActive = true;
            
            if( isActive && !isClass ){
                code = ("1" + code);
            }
            
            Gene gene = new Gene( isActive, code, isClass );
            genes[ i ] = gene;
        }
        return genes[ genes.length - 1 ].getValue();
    }

    public void decode() {

    }
    
    
    public String getEncodedClass() {
        return genes[ genes.length - 1 ].getValue();
    }


    @Override
    public String toString() {
        StringBuilder chromossome = new StringBuilder( "" );
        for ( Gene gene : genes ) {
            chromossome.append( gene + "\n" );
        }
        return chromossome.toString();
    }
    
    public String toStringEncoded() {
        
        StringBuilder chromossome = new StringBuilder( "" );
        
        for ( Gene gene : genes ) {
            chromossome.append( gene.toStringEncoded() + " | " );
        }
        return chromossome.toString();
    }

}
