
package br.com.ufu.lsi.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ufu.lsi.generator.RuleSetEvaluator;
import br.com.ufu.lsi.generator.StreamDistributor;
import br.com.ufu.lsi.generator.StreamGenerator;
import br.com.ufu.lsi.genetic.GeneticEngine;
import br.com.ufu.lsi.genetic.PopulationCreator;
import br.com.ufu.lsi.model.Chromossome;
import br.com.ufu.lsi.model.FinalRule;
import br.com.ufu.lsi.model.NurseryDataset;
import br.com.ufu.lsi.model.RuleSet;
import br.com.ufu.lsi.model.Window;
import br.com.ufu.lsi.util.RandomGenerator;

public class Main {

    private StreamGenerator streamGenerator = new StreamGenerator();

    private StreamDistributor streamDistributor = new StreamDistributor();
    
    private PopulationCreator populationCreator = new PopulationCreator();
    
    private RuleSetEvaluator ruleSetEvaluator = new RuleSetEvaluator();
    
    private GeneticEngine geneticEngine = new GeneticEngine();

    private HashMap< String, Window > windows = new HashMap< String, Window >();
    
    private HashMap< String, RuleSet > ruleSets = new HashMap< String, RuleSet >();
    
    private List<FinalRule> finalRuleSet = new ArrayList<FinalRule>();

    public void run() {

        int index = 0;
        int conceptDrift = 50000; //100000;
        int errorCalc = 5000;
        
        List<String> lines = streamGenerator.buildDataset( 205000 );//405000 );

        streamDistributor.initializeWindows( windows, NurseryDataset.encodedClasses );
        
        populationCreator.initializeRuleSets( ruleSets, NurseryDataset.encodedClasses );
        
        List< String > records = streamGenerator.readChunkSequence( lines, index, 300 );
        index += 300;
        
        List< Chromossome > chromossomes = streamDistributor.analyzeWindows( 3, records, windows );

        streamDistributor.distribute( chromossomes, windows );

        populationCreator.createPopulation( ruleSets, 50 );
        
        geneticEngine.setInitialPopulationFitness( ruleSets, windows, 0.7, 0.3 );
        
        long init = System.currentTimeMillis();

        for( int i = 0, conceptDriftCounter = 0, errorCalcCounter = 0; ; i++, index += 300, conceptDriftCounter+=300, errorCalcCounter+=300 ) {
            
            if( conceptDriftCounter > conceptDrift ) {
                
                conceptDriftCounter = 0;
                
                int att1 = RandomGenerator.randInt( 0, NurseryDataset.numberNonClassAttributes-1 );
                int att2 = att1;
                while( att2 == att1 )
                    att2 = RandomGenerator.randInt( 0, NurseryDataset.numberNonClassAttributes-1 );
                int att3 = att1;
                while( att3 == att1 || att3 == att2 )
                    att3 = RandomGenerator.randInt( 0, NurseryDataset.numberNonClassAttributes-1 );
                int att4 = att1;
                while( att4 == att1 || att4 == att2 || att4 == att3 )
                    att4 = RandomGenerator.randInt( 0, NurseryDataset.numberNonClassAttributes-1 );
                int att5 = att1;
                while( att5 == att1 || att5 == att2 || att5 == att3 || att5 == att4 )
                    att5 = RandomGenerator.randInt( 0, NurseryDataset.numberNonClassAttributes-1 );
                
                /*streamGenerator.conceptDriftOnAttribute( lines, 0 );
                streamGenerator.conceptDriftOnAttribute( lines, 1 );
                streamGenerator.conceptDriftOnAttribute( lines, 2 );
                streamGenerator.conceptDriftOnAttribute( lines, 3 );
                streamGenerator.conceptDriftOnAttribute( lines, 4 );
                streamGenerator.conceptDriftOnAttribute( lines, 5 );
                streamGenerator.conceptDriftOnAttribute( lines, 6 );
                streamGenerator.conceptDriftOnAttribute( lines, 7 );*/
                
                streamGenerator.conceptDriftOnAttribute( lines, att1 );
                streamGenerator.conceptDriftOnAttribute( lines, att2 );
                streamGenerator.conceptDriftOnAttribute( lines, att3 );
            }
           
            
            records = streamGenerator.readChunkSequence( lines, index, 300 );
            if( records == null )
                break;
            
            chromossomes = streamDistributor.analyzeWindows( 3, records, windows );

            streamDistributor.distribute( chromossomes, windows );
            
            if( errorCalcCounter > errorCalc ) {
                errorCalcCounter = 0;
                double error = ruleSetEvaluator.calculateError( finalRuleSet, windows );
                System.out.printf( "%.4f\n", error );
                
                //System.out.println( System.currentTimeMillis() - init );
            }
            
            geneticEngine.findRule( ruleSets, windows, 0.7, 0.3 );
            
            ruleSetEvaluator.handleFinalRuleSet( ruleSets, finalRuleSet, windows );
                        
        }
        System.out.println( "======= Final Rule Set =======");
        int cont = 0;
        for( FinalRule finalRule : finalRuleSet ) {
            if( finalRule.getConfidence() > 0.7 && finalRule.getSupport() > 0.05 ) {
                cont++;
                System.out.println( finalRule );
            }
            
        }
        //System.out.println( cont );
        System.out.println();
        
        System.out.println( "======= Total time ======= " );
        System.out.println( (System.currentTimeMillis() - init ) + " ms" );
    }

    public static void main( String... args ) {

        Main main = new Main();
        main.run();
    }

}
