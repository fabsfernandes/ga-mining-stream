
package br.com.ufu.lsi.generator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;

import br.com.ufu.lsi.model.Chromossome;
import br.com.ufu.lsi.model.FinalRule;
import br.com.ufu.lsi.model.RuleSet;
import br.com.ufu.lsi.model.Window;

public class RuleSetEvaluator {

    public void insertIntoFinalRuleSet( HashMap< String, RuleSet > ruleSets,
            List< FinalRule > finalRuleSet, HashMap< String, Window > windows ) {

        Chromossome bestRule = findBestRule( ruleSets );

        Double support = calculateSupport( bestRule, windows );

        Double confidence = calculateConfidence( bestRule, windows );

        FinalRule finalRule = new FinalRule( bestRule, support, confidence );
        checkContradiction( finalRule, finalRuleSet );

        finalRuleSet.add( finalRule );
        
        System.out.println( finalRule );
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

}
