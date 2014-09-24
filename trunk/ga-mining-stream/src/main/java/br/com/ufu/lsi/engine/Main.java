
package br.com.ufu.lsi.engine;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;

import br.com.ufu.lsi.generator.StreamDistributor;
import br.com.ufu.lsi.generator.StreamGenerator;
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

    private HashMap< String, Window > windows = new HashMap< String, Window >();
    
    private HashMap< String, RuleSet > ruleSets = new HashMap< String, RuleSet >();

    public void run() {

        BufferedReader bf = streamGenerator.buildDataset( 100000 );

        streamDistributor.initializeWindows( windows, NurseryDataset.encodedClasses );
        
        populationCreator.initializeRuleSets( ruleSets, NurseryDataset.encodedClasses );
        
        populationCreator.createPopulation( ruleSets, 50 );
        
        Print.printRuleSets( ruleSets );

        /*for ( int i = 0; i < 15; i++ ) {
            
            List< String > records = streamGenerator.readChunkSequence( 300, NurseryDataset.datasetSize, bf );
            // List< String > records = streamGenerator.readChunkRandomly( 300, NurseryDataset.datasetSize, bf );
            //Print.printRecords( records );

            List< Chromossome > chromossomes = streamDistributor.analyzeWindows( 3, records, windows );

            streamDistributor.distribute( chromossomes, windows );

            Print.printWindows( windows );
        }*/
    }

    public static void main( String... args ) {

        Main main = new Main();
        main.run();
    }

}
