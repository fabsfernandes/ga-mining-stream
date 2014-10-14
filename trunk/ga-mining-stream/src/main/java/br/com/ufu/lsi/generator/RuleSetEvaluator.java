
package br.com.ufu.lsi.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;

import br.com.ufu.lsi.genetic.GeneticEngine;
import br.com.ufu.lsi.model.Chromossome;
import br.com.ufu.lsi.model.FinalRule;
import br.com.ufu.lsi.model.RuleSet;
import br.com.ufu.lsi.model.Window;

public class RuleSetEvaluator {
    
    public double calculateError( List< FinalRule > finalRuleSet, HashMap< String, Window > windows ) {
        
        double correct = 0.0;
        double total = 0.0;
        for ( Map.Entry< String, Window > windowMap : windows.entrySet() ) {
            String windowClass = windowMap.getKey();
            Window window = windowMap.getValue();
            
            total += window.getChromossomes().size();
            
            for( Chromossome chrom : window.getChromossomes() ){
                boolean triggered = false;
                for( FinalRule finalRule : finalRuleSet ) {    
                    if( finalRule.getConfidence() > 0.7 ) {
                        if( finalRule.getRule().antecedentEquals( chrom ) ){
                            triggered = true;
                            if( finalRule.getRule().getEncodedClass().equals( windowClass ) ) {
                                correct++;
                                break;
                            }
                        }
                    }
                }
                if( !triggered ) {
                    if( chrom.getEncodedClass().equals( "00100" ) )
                        correct++;
                }
            }
        }
        
        double accuracy = correct/total;
        
        return (1.0 - accuracy);
        
    }
    
    public double calculateError2( List< FinalRule > finalRuleSet, HashMap< String, Window > windows ) {
        
      
        double numberClass = 0.0;
        double totalSum = 0.0;
        
        for ( Map.Entry< String, Window > windowMap : windows.entrySet() ) {
            String windowClass = windowMap.getKey();
            Window window = windowMap.getValue();
            numberClass++;
            double correct = 0.0;
            double totalClass = window.getChromossomes().size();
            
            for( Chromossome chrom : window.getChromossomes() ){
                for( FinalRule finalRule : finalRuleSet ) {                
                    if( finalRule.getConfidence() > 0.0 ) {
                        if( finalRule.getRule().getEncodedClass().equals( windowClass ) ) {
                        
                            if( finalRule.getRule().antecedentEquals( chrom ) ){
                                correct++;
                                break;
                            }
                        }
                    }
                }
            }
            if( totalClass != 0.0 )
                totalSum += (correct/totalClass);
        }
        
        double averageAccuracy = totalSum / numberClass;
        
        return (1.0 - averageAccuracy);
        
    }
        

    public void handleFinalRuleSet( HashMap< String, RuleSet > ruleSets,
            List< FinalRule > finalRuleSet, HashMap< String, Window > windows ) {

        List<Chromossome> bestRules = findBestRules( ruleSets );
        
        for( Chromossome bestRule : bestRules) {
            Double support = calculateSupport( bestRule, windows );
    
            Double confidence = calculateConfidence( bestRule, windows );
            
            FinalRule finalRule = new FinalRule( bestRule, support, confidence );
            checkContradiction( finalRule, finalRuleSet );
    
            finalRuleSet.add( finalRule );
        }
        updateSupportConfidenceAndFitness( finalRuleSet, windows );
    }
    
    public void updateSupportConfidenceAndFitness( List<FinalRule> finalRuleSet, HashMap< String, Window > windows ) {
        for( FinalRule rule : finalRuleSet ) {
            rule.setConfidence( calculateConfidence( rule.getRule(), windows ) );
            rule.setSupport( calculateSupport( rule.getRule(), windows ) );
            
            // temp
            rule.getRule().setFitness( new GeneticEngine().fitness( rule.getRule(), windows, 0.7, 0.3 ) );
        }
    }

    public void checkContradiction( FinalRule finalRule, List< FinalRule > finalRuleSet ) {

        for (Iterator<FinalRule> it = finalRuleSet.iterator(); it.hasNext(); ) {
            FinalRule rule = it.next();
            Chromossome chrom = rule.getRule();
            Chromossome finalChrom = finalRule.getRule();
            if ( chrom.sameAntecedent( finalChrom ) ) {
                it.remove();
            }
        }
    }

    public Double calculateConfidence( Chromossome bestRule, HashMap< String, Window > windows ) {

        double totalAntecedents = 0.0;
        double totalAntecedentsConsequents = 0.0;
        for ( Map.Entry< String, Window > windowMap : windows.entrySet() ) {

            Window window = windowMap.getValue();

            List< Chromossome > population = window.getChromossomes();
            for ( Chromossome chromossome : population ) {
                if ( bestRule.antecedentEquals( chromossome ) ) {
                    totalAntecedents++ ;
                    if ( chromossome.getEncodedClass().equals( bestRule.getEncodedClass() ) ) {
                        totalAntecedentsConsequents++ ;
                    }
                }
            }
        }

        return totalAntecedents > 0 ? totalAntecedentsConsequents / totalAntecedents : 0.0;
    }

    public Double calculateSupport( Chromossome bestRule, HashMap< String, Window > windows ) {

        double totalRegisters = 0.0;
        double totalItemsets = 0.0;
        for ( Map.Entry< String, Window > windowMap : windows.entrySet() ) {
            String windowClass = windowMap.getKey();
            Window window = windowMap.getValue();

            List< Chromossome > population = window.getChromossomes();
            totalRegisters += population.size();
            if ( windowClass.equals( bestRule.getEncodedClass() ) ) {
                for ( Chromossome chromossome : population ) {
                    if ( bestRule.antecedentEquals( chromossome ) ) {
                        totalItemsets++ ;
                    }
                }
            }
        }
        return totalItemsets / totalRegisters;
    }

    public Chromossome findBestRule( HashMap< String, RuleSet > ruleSets ) {

        Chromossome bestRule = null;
        for ( Map.Entry< String, RuleSet > ruleSetMap : ruleSets.entrySet() ) {

            RuleSet ruleSet = ruleSetMap.getValue();
            Chromossome candidate = ruleSet.getPopulation().get( 0 );
            if ( bestRule == null ) {
                bestRule = candidate;
            } else {
                if ( bestRule.compareTo( candidate ) == 1 ) {
                    bestRule = candidate;
                }
            }
        }

        return SerializationUtils.clone( bestRule );
    }
    
    public List<Chromossome> findBestRules( HashMap< String, RuleSet > ruleSets ) {

        List<Chromossome> bestRules = new ArrayList<Chromossome>();
        
        for ( Map.Entry< String, RuleSet > ruleSetMap : ruleSets.entrySet() ) {

            RuleSet ruleSet = ruleSetMap.getValue();
            Chromossome candidate = ruleSet.getPopulation().get( 0 );
            bestRules.add( SerializationUtils.clone( candidate ) );
        }

        return bestRules;
    }

}
