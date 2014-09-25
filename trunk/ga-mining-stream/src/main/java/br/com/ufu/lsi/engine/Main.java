
package br.com.ufu.lsi.engine;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;

import br.com.ufu.lsi.generator.StreamDistributor;
import br.com.ufu.lsi.generator.StreamGenerator;
import br.com.ufu.lsi.genetic.GeneticEngine;
import br.com.ufu.lsi.genetic.PopulationCreator;
import br.com.ufu.lsi.model.Chromossome;
import br.com.ufu.lsi.model.NurseryDataset;
import br.com.ufu.lsi.model.RuleSet;
import br.com.ufu.lsi.model.Window;
import br.com.ufu.lsi.util.Print;

public class Main {

    private StreamGenerator streamGenerator = new StreamGenerator();

    private StreamDistributor streamDistributor = new StreamDistributor();
    
    private PopulationCreator populationCreator = new PopulationCreator();
    
    private GeneticEngine geneticEngine = new GeneticEngine();

    private HashMap< String, Window > windows = new HashMap< String, Window >();
    
    private HashMap< String, RuleSet > ruleSets = new HashMap< String, RuleSet >();

    public void run() {

        BufferedReader bf = streamGenerator.buildDataset( 100000 );

        streamDistributor.initializeWindows( windows, NurseryDataset.encodedClasses );
        
        populationCreator.initializeRuleSets( ruleSets, NurseryDataset.encodedClasses );
        
        

        for ( int i = 0; i < 1; i++ ) {
            
            List< String > records = streamGenerator.readChunkSequence( 300, NurseryDataset.datasetSize, bf );
            // List< String > records = streamGenerator.readChunkRandomly( 300, NurseryDataset.datasetSize, bf );
            //Print.printRecords( records );

            List< Chromossome > chromossomes = streamDistributor.analyzeWindows( 3, records, windows );

            streamDistributor.distribute( chromossomes, windows );

            populationCreator.createPopulation( ruleSets, 50 );
            
            geneticEngine.setInitialPopulationFitness( ruleSets, windows, 0.7, 0.3 );
            
            Print.printRuleSets( ruleSets );
            //Print.printWindows( windows );
            
            geneticEngine.findRule( ruleSets, windows, 0.7, 0.3 );
        }
    }

    public static void main( String... args ) {

        Main main = new Main();
        main.run();
    }

}
