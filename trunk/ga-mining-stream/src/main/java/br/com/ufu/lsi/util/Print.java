
package br.com.ufu.lsi.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ufu.lsi.model.Chromossome;
import br.com.ufu.lsi.model.RuleSet;
import br.com.ufu.lsi.model.Window;
import br.com.ufu.lsi.model.WindowStatistic;

public class Print {

    public static void printWindows( HashMap<String, Window> windows ){
        
        for (Map.Entry<String, Window> entry : windows.entrySet()) {
            
            String encodedClass = entry.getKey();
            Window window = (Window) entry.getValue();
            
            System.out.println( "==> Window " + encodedClass );
            System.out.println( "Elements: " + window.getChromossomes().size() );
            System.out.println( "Size: " + window.getCurrentSize().getSize() );
            System.out.print( "History: " );
            for( WindowStatistic stat : window.getHistoryStatistics() ) {
                System.out.printf( "%d / %.2f%%,  ", stat.getSize(), stat.getProportion()*100.0 );
            }
            
            for( Chromossome chromossome : window.getChromossomes() ){
                //System.out.println( chromossome.toStringEncoded() );
            }
            System.out.println();
            System.out.println();
        }
    }
    
    public static void printRecords( List<String> records ){
        int i = 0;
        for( String s : records ){
            System.out.println( "[" + ++i + "] " + s );
        }
    }
    
    public static void printRuleSets( HashMap<String, RuleSet> ruleSets) {
        
        for (Map.Entry<String, RuleSet> entry : ruleSets.entrySet()) {
            
            String encodedClass = entry.getKey();
            RuleSet ruleSet = (RuleSet) entry.getValue();
            
            System.out.println( "==> RuleSet " + encodedClass );
            
            for( Chromossome chromossome : ruleSet.getPopulation() ){
                System.out.println( chromossome.toStringEncoded() + " " + chromossome.getFitness() );
            }
            System.out.println();
        }
    }
    
    public static void printChromossomes( List<Chromossome> chromossomes ) {
        for( Chromossome chromossome : chromossomes )
            System.out.println( chromossome.toStringEncoded() + " " + chromossome.getFitness() );
    }
    
}
