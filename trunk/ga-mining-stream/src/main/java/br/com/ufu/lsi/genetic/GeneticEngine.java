
package br.com.ufu.lsi.genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;

import br.com.ufu.lsi.model.Chromossome;
import br.com.ufu.lsi.model.Gene;
import br.com.ufu.lsi.model.NurseryDataset;
import br.com.ufu.lsi.model.RuleSet;
import br.com.ufu.lsi.model.Window;
import br.com.ufu.lsi.util.RandomGenerator;

public class GeneticEngine {

    public void findRule( HashMap< String, RuleSet > ruleSets, HashMap< String, Window > windows,
            double w1, double w2 ) {

        for ( Map.Entry< String, RuleSet > ruleSetMap : ruleSets.entrySet() ) {

            RuleSet ruleSet = ruleSetMap.getValue();
            calculateRuleSetFitness( ruleSet, windows, w1, w2 );
            RuleSet newSubPopulation = createNewSubPopulation( ruleSet, 0.8, 0.6, 0.3, 0.3, windows, w1, w2 );
            eliteSelection( ruleSet, newSubPopulation, 0.1 );
        }
    }
    
    public void eliteSelection( RuleSet ruleSet, RuleSet newSubPopulation, double eliteSelectionRate ) {
        
        List<Chromossome> population = ruleSet.getPopulation();
        List<Chromossome> children = newSubPopulation.getPopulation();
        
        Double quantityParentsSelectionFloat = Math.ceil( eliteSelectionRate * population.size() );
        int quantityParentsSelection = quantityParentsSelectionFloat.intValue();
        
        int populationSize = population.size();
        int j = 0;
        population.subList( quantityParentsSelection, populationSize ).clear();
        //for( int i = quantityParentsSelection; population.size() < populationSize && j < children.size(); i++ ) {
         //   population.add( children.get( j++ ) );
        //}
        for( j=0; j < children.size(); j++ ) {
            population.add( children.get( j ) );
        }
        
        Collections.sort(  population );
    }

    public RuleSet createNewSubPopulation( RuleSet population, Double crossoverRate,
            Double mutationRate, Double insertionRate, Double removalRate, HashMap< String, Window > windows,
            double w1, double w2 ) {

        List< Chromossome > parentsSelected = selectParents( population, crossoverRate );

        List< Chromossome > children = crossover( parentsSelected );

        mutation( children, mutationRate );
        
        insertionAndRemoval( children, insertionRate, removalRate );
        
        calculatePopulationFitness( children, windows, w1, w2 );
        
        RuleSet newSubPopulation = new RuleSet( population.getEncodedClass(), children );
        
        return newSubPopulation;
    }
    
    public void insertionAndRemoval( List<Chromossome> children, Double insertionRate, Double removalRate ) {
        
        for( Chromossome chromossome : children ) {
            Gene [] genes = chromossome.getGenes();
            List<Gene> genesDeactivated = new ArrayList<Gene>();
            List<Gene> genesActivated = new ArrayList<Gene>();
            
            int deactiveted = 0;
            int activeted = 0;
            for( int i = 0; i<genes.length-1; i++ ) {
                String value = genes[i].getValue();
                if( value.charAt( 0 ) == '1' ) {
                    activeted++;
                    genesActivated.add( genes[i] );
                } else {
                    deactiveted++; 
                    genesDeactivated.add( genes[i] );
                }
            }
            
            
            double rateToActivate = (insertionRate * deactiveted) / (genes.length-1);
            Double quantityToActivateFloat = Math.ceil( rateToActivate * deactiveted );
            int quantityToActivate = quantityToActivateFloat.intValue();
            
            List<Integer> genesToActivate = RandomGenerator.randListValues( 0, genesDeactivated.size()-1, quantityToActivate );
            for( Integer i : genesToActivate ){
                String newValue = genesDeactivated.get( i ).getValue().replaceFirst( "0", "1" );
                genesDeactivated.get( i ).setValue( newValue );
                
            }
            
            double rateToDeactivate = (removalRate * activeted) / (genes.length-1);
            Double quantityToDeactivateFloat = Math.ceil( rateToDeactivate * activeted );
            int quantityToDeactivate = quantityToDeactivateFloat.intValue();
            
            List<Integer> genesToDeactivate = RandomGenerator.randListValues( 0, genesActivated.size()-1, quantityToDeactivate );
            for( Integer i : genesToDeactivate ){
                String newValue = genesActivated.get( i ).getValue().replaceFirst( "1", "0" );
                genesActivated.get( i ).setValue( newValue );
            }
        }
    }

    public void mutation( List< Chromossome > children, Double mutationRate ) {

        Double quantity = mutationRate * children.size();
        int mutationNumber = quantity.intValue();

        List< Integer > mutationChromossomes = RandomGenerator.randListValues( 0, children.size() - 1, mutationNumber );

        for ( Integer i : mutationChromossomes ) {
            mutate( children.get( i ) );
        }
    }

    public void mutate( Chromossome chromossome ) {
        
        Gene [] genes = chromossome.getGenes();
        
        for ( int j = 0; j < genes.length - 1; j++ ) {

            String value = genes[ j ].getValue();
            while ( value == genes[ j ].getValue() ) {
                value = RandomGenerator.randValue( NurseryDataset.attributes.get( j ) );
            }
            genes[ j ].setValue( value );
        }

    }

    public List< Chromossome > crossover( List< Chromossome > parentsSelected ) {
        List< Chromossome > children = new ArrayList< Chromossome >();

        /*for ( int i = 0; i < parentsSelected.size() - 1; i += 2 ) {
            Chromossome c1 = parentsSelected.get( i );
            Chromossome c2 = parentsSelected.get( i + 1 );
            Chromossome[] twoChildren = twoPointCrossover( c1, c2 );

           // if ( ! children.contains( twoChildren[ 0 ] ) && ! children.contains( twoChildren[ 1 ] ) ) {
                children.add( twoChildren[ 0 ] );
                children.add( twoChildren[ 1 ] );
           // } else {
           //     i -= 2;
          //  }
        }*/

        while( children.size() < 45 ) {
        for ( int i = 0; i < parentsSelected.size() - 1; i += 2 ) {
            Chromossome c1 = parentsSelected.get( i );
            Chromossome c2 = parentsSelected.get( i + 1 );
            Chromossome[] twoChildren = twoPointCrossover( c1, c2 );

                children.add( twoChildren[ 0 ] );
                if( children.size() < 45 )
                    children.add( twoChildren[ 1 ] );
        }
        }
        return children;
    }

    public Chromossome[] twoPointCrossover( Chromossome c1, Chromossome c2 ) {

        Chromossome child1 = SerializationUtils.clone( c1 );
        Chromossome child2 = SerializationUtils.clone( c2 );

        int firstPoint = RandomGenerator.randInt( 0, child1.getGenes().length - 2 );
        int secondPoint = firstPoint;
        while ( secondPoint == firstPoint ) {
            secondPoint = RandomGenerator.randInt( 0, child1.getGenes().length - 2 );
        }

        for ( int i = firstPoint + 1; i <= secondPoint; i++ ) {
            Gene[] child1genes = child1.getGenes();
            Gene[] child2genes = child2.getGenes();
            String tempValue = child1genes[ i ].getValue();
            child1genes[ i ].setValue( child2genes[ i ].getValue() );
            child2genes[ i ].setValue( tempValue );
        }

        Chromossome[] twoChildren = {
                child1, child2
        };

        return twoChildren;
    }

    public List< Chromossome > selectParents( RuleSet population, Double crossoverRate ) {

        RuleSet populationClone = SerializationUtils.clone( population );

        Double quantity = Math.ceil(crossoverRate * population.getPopulation().size());
        int parentsNumber = quantity.intValue();
        parentsNumber = parentsNumber % 2 == 0 ? parentsNumber : parentsNumber + 1;

        List< Chromossome > parents = new ArrayList< Chromossome >();
        while ( parents.size() < parentsNumber ) {

            Chromossome[] selectedParents = rouletteWheelSelection( populationClone );
            parents.add( selectedParents[ 0 ] );
            parents.add( selectedParents[ 1 ] );

            populationClone.getPopulation().remove( selectedParents[ 0 ] );
            populationClone.getPopulation().remove( selectedParents[ 1 ] );
        }
        
        populationClone = null;

        return parents;
    }

    public Chromossome[] rouletteWheelSelection( RuleSet population ) {
        List< Chromossome > roulette = new ArrayList< Chromossome >();
        double totalSum = 0.0;
        for ( Chromossome chromossome : population.getPopulation() ) {
            totalSum += chromossome.getFitness();
        }

        for ( Chromossome chromossome : population.getPopulation() ) {
            double fitness = chromossome.getFitness();
            double proportion = fitness / totalSum;
            double quantity = Math.ceil( proportion * 100 );
            for ( int j = 0; j < quantity; j++ ) {
                roulette.add( chromossome );
            }
        }

        int parentId1 = RandomGenerator.randInt( 0, roulette.size() - 1 );
        int parentId2 = parentId1;
        while ( parentId2 == parentId1 )
            parentId2 = RandomGenerator.randInt( 0, roulette.size() - 1 );

        Chromossome[] parents = {
                roulette.get( parentId1 ), roulette.get( parentId2 )
        };

        return parents;
    }

    
    public void calculatePopulationFitness( List<Chromossome> children, HashMap<String, Window> windows, double w1, double w2 ) {
        
        for ( Chromossome chromossome : children ) {
            double fitness = fitness( chromossome, windows, w1, w2 );
            chromossome.setFitness( fitness );
        }
        Collections.sort( children );
    }
    
    public void calculateRuleSetFitness( RuleSet population, HashMap< String, Window > windows,
            double w1, double w2 ) {

        List< Chromossome > chromossomes = population.getPopulation();

        calculatePopulationFitness( chromossomes, windows, w1, w2 );
    }

    public void setInitialPopulationFitness( HashMap< String, RuleSet > ruleSets,
            HashMap< String, Window > windows, double w1, double w2 ) {

        for ( Map.Entry< String, RuleSet > ruleSetMap : ruleSets.entrySet() ) {

            RuleSet ruleSet = ruleSetMap.getValue();
            List< Chromossome > chromossomes = ruleSet.getPopulation();

            calculatePopulationFitness( chromossomes, windows, w1, w2 );
        }
    }

    public double fitness( Chromossome chromossome, HashMap< String, Window > windows, double w1,
            double w2 ) {

        double predictiveAccuracy = predictiveAccuracy( chromossome, windows );

        double comprehensibility = comprehensibility( chromossome );

        return ( w1 * predictiveAccuracy ) + ( w2 * comprehensibility );
    }

    public double predictiveAccuracy( Chromossome chromossome, HashMap< String, Window > windows ) {

        double numberA = 0.0;
        double numberAC = 0.0;

        for ( Map.Entry< String, Window > windowMap : windows.entrySet() ) {
            Window window = windowMap.getValue();

            List< Chromossome > training = window.getChromossomes();
            for ( Chromossome chrom : training ) {
                if ( chromossome.antecedentEquals( chrom ) ) {
                    numberA++ ;
                }
                if ( chromossome.antecedentConsequentEquals( chrom ) ) {
                    numberAC++ ;
                }
            }
        }

        double predictiveAccuracy = numberA == 0.0 || numberAC == 0.0 ? 0.0 : ( numberAC - 0.5 ) / numberA;

        return predictiveAccuracy;
    }

    public double comprehensibility( Chromossome chromossome ) {

        double L = chromossome.getGenes().length - 1;
        double x = 0.0;
        for ( Gene gene : chromossome.getGenes() ) {
            String geneValue = gene.getValue();
            if ( geneValue.charAt( 0 ) == '1' ) {
                x++ ;
            }
        }

        return ( L - x ) / ( L - 1 );
    }

}
