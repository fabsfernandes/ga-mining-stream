
package br.com.ufu.lsi.engine;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        BufferedReader bf = streamGenerator.buildDataset( 100000 );

        streamDistributor.initializeWindows( windows, NurseryDataset.encodedClasses );
        
        populationCreator.initializeRuleSets( ruleSets, NurseryDataset.encodedClasses );
        
        List< String > records = streamGenerator.readChunkSequence( 300, NurseryDataset.datasetSize, bf );
        // List< String > records = streamGenerator.readChunkRandomly( 300, NurseryDataset.datasetSize, bf );
        
        List< Chromossome > chromossomes = streamDistributor.analyzeWindows( 3, records, windows );

        streamDistributor.distribute( chromossomes, windows );

        populationCreator.createPopulation( ruleSets, 50 );
        
        geneticEngine.setInitialPopulationFitness( ruleSets, windows, 0.7, 0.3 );

        for( int i = 0; !records.isEmpty(); i++ ) {
            
            System.out.println( i + " " + records.size());
            records = streamGenerator.readChunkSequence( 300, NurseryDataset.datasetSize, bf );
            // List< String > records = streamGenerator.readChunkRandomly( 300, NurseryDataset.datasetSize, bf );

            chromossomes = streamDistributor.analyzeWindows( 3, records, windows );

            streamDistributor.distribute( chromossomes, windows );
            
            geneticEngine.findRule( ruleSets, windows, 0.7, 0.3 );
            
            ruleSetEvaluator.insertIntoFinalRuleSet( ruleSets, finalRuleSet, windows );
            
            System.gc();
            
        }
        System.out.println( "Final Set");
        for( FinalRule finalRule : finalRuleSet )
            System.out.println( finalRule );
    }

    public static void main( String... args ) {

        Main main = new Main();
        main.run();
    }

}
