package br.com.ufu.lsi.util.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.ufu.lsi.generator.StreamGenerator;

public class TestConceptDrift {
    
    @Test
    public void conceptDriftTest(){
        StreamGenerator generator = new StreamGenerator();
        
        List< String > dataset = new ArrayList<String>();
        dataset.add( "usual,proper,complete,1,convenient,convenient,slightly_prob,not_recom,not_recom" ); 
        dataset.add( "pretentious,critical,complete,2,critical,inconv,slightly_prob,recommended,spec_prior" );
        dataset.add( "great_pret,improper,complete,1,critical,inconv,problematic,priority,spec_prior" );
        dataset.add( "usual,proper,complete,1,convenient,inconv,nonprob,recommended,very_recom" );
        
        generator.conceptDriftOnAttribute( dataset, 7 );
        
        for( String str : dataset )
        System.out.println( str );
    }

}
