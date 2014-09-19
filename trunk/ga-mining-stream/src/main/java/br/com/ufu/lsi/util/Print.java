
package br.com.ufu.lsi.util;

import java.util.HashMap;
import java.util.Map;

import br.com.ufu.lsi.model.Chromossome;
import br.com.ufu.lsi.model.Window;

public class Print {

    public static void printWindows( HashMap<String, Window> windows ){
        
        for (Map.Entry<String, Window> entry : windows.entrySet()) {
            
            String encodedClass = entry.getKey();
            Window window = (Window) entry.getValue();
            
            System.out.println( "==> Window " + encodedClass + " (" + window.getChromossomes().size() + " elements)" );
            for( Chromossome chromossome : window.getChromossomes() ){
                System.out.println( chromossome.toStringEncoded() );
            }
            System.out.println();
            
        }
    }
}
