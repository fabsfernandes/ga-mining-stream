
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
            
            if( !isClass ){
                code = ("1" + code);
            }
            
            Gene gene = new Gene( code, isClass );
            genes[ i ] = gene;
        }
        return genes[ genes.length - 1 ].getValue();
    }

    public void decode() {

    }
    
    
    public String getEncodedClass() {
        return genes[ genes.length - 1 ].getValue();
    }
    
    public boolean antecedentEquals( Chromossome chrom ) {
        
        Gene [] chromossomeGenes = chrom.getGenes();
        for( int i = 0; i<chromossomeGenes.length-1; i++ ) {
            String value = this.genes[i].getValue();
            if( value.charAt( 0 ) == '1' ) {
                if( !chromossomeGenes[i].getValue().equals( value ) )
                    return false;
            }
        }
        return true;
    }

    public boolean antecedentConsequentEquals( Chromossome chrom ) {
        
        Gene [] chromossomeGenes = chrom.getGenes();
        for( int i = 0; i<chromossomeGenes.length-1; i++ ) {
            String value = this.genes[i].getValue();
            if( value.charAt( 0 ) == '1' ) {
                if( !chromossomeGenes[i].getValue().equals( value ) )
                    return false;
            }
        }
        
        String chromossomeClassValue = this.genes[ this.genes.length-1 ].getValue();
        String chromClassValue = chromossomeGenes[ chromossomeGenes.length-1 ].getValue();
        
        return chromossomeClassValue.equals( chromClassValue ) ? true : false;
    }

    @Override
    public boolean equals( Object object ) {
        Nursery chromossome = (Nursery) object;
        Gene [] chromossomeGenes = chromossome.getGenes();
        for( int i = 0; i<chromossomeGenes.length; i++ ) {
            if( !chromossomeGenes[i].getValue().equals( this.genes[i].getValue() ) )
                return false;
        }
        return true;
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
