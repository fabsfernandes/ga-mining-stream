
package br.com.ufu.lsi.genetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ufu.lsi.model.Chromossome;
import br.com.ufu.lsi.model.Gene;
import br.com.ufu.lsi.model.Nursery;
import br.com.ufu.lsi.model.NurseryDataset;
import br.com.ufu.lsi.model.RuleSet;
import br.com.ufu.lsi.util.RandomGenerator;

public class PopulationCreator {

    public void initializeRuleSets( HashMap< String, RuleSet > ruleSets,
            List< String > encodedClasses ) {

        for ( String encodedClass : encodedClasses ) {

            RuleSet ruleSet = ruleSets.get( encodedClass );

            if ( ruleSet == null ) {
                ruleSets.put( encodedClass, new RuleSet( encodedClass, new ArrayList< Chromossome >() ) );
            }
        }
    }

    public void createPopulation( HashMap< String, RuleSet > ruleSets, int populationSize ) {

        for ( Map.Entry< String, RuleSet > ruleSetMap : ruleSets.entrySet() ) {
            String ruleSetClass = ruleSetMap.getKey();
            RuleSet ruleSet = ruleSetMap.getValue();

            for ( int i = 0; i < populationSize; i++ ) {
                Chromossome chromossome = new Nursery();
                Gene[] genes = chromossome.getGenes();
                for ( int j = 0; j < genes.length - 1; j++ ) {

                    String value = RandomGenerator.randValue( NurseryDataset.attributes.get( j ) );
                    Gene gene = new Gene( value, false );
                    genes[ j ] = gene;
                }
                genes[ genes.length - 1 ] = new Gene( ruleSetClass, true );

                if ( ! ruleSet.getPopulation().contains( chromossome ) ) {
                    ruleSet.getPopulation().add( chromossome );
                } else {
                    i-- ;
                }
            }
        }
    }
    
    public void distributePopultationInRuleSets( HashMap< String, RuleSet > ruleSets, HashMap< String, List<Chromossome>> population ) {
        
    }

}
