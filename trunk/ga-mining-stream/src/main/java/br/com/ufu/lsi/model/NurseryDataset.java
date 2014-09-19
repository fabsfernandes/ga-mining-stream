package br.com.ufu.lsi.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Nursery Dataset Map
 * 
 * Attributes: 
 *      (1) parents: usual, pretentious, great_pret
 *      (2) has_nurs: proper, less_proper, improper, critical, very_crit
 *      (3) form: complete, completed, incomplete, foster 
 *      (4) children: 1, 2, 3, more 
 *      (5) housing: convenient, less_conv, critical 
 *      (6) finance: convenient, inconv
 *      (7) social: nonprob, slightly_prob, problematic
 *      (8) health: recommended, priority, not_recom  
 *      (9) class: not_recom, recommended, very_recom, priority, spec_prior 
 * 
 * @author fabiola
 *
 */

public class NurseryDataset {
    
    public static final HashMap<Integer,HashMap<String,String>> attributes;
    
    public static final List<String> decodedClasses;
    public static final List<String> encodedClasses;
    
    static {
        attributes = new HashMap<Integer,HashMap<String,String>>();
        decodedClasses = Arrays.asList( "not_recom", "recommended", "very_recom", "priority", "spec_prior" );
        encodedClasses = Arrays.asList( "10000", "01000", "00100", "00010", "00001" );
        
        HashMap<String,String> parents = new HashMap<String,String>();
        parents.put( "usual", "100" );
        parents.put( "pretentious", "010" );
        parents.put( "great_pret", "001" );
        attributes.put( 0, parents );
        
        HashMap<String,String> has_nurs = new HashMap<String,String>();
        has_nurs.put( "proper", "10000" );
        has_nurs.put( "less_proper", "01000" );
        has_nurs.put( "improper", "00100" );
        has_nurs.put( "critical", "00010" );
        has_nurs.put( "very_crit", "00001" );
        attributes.put( 1, has_nurs );
        
        HashMap<String,String> form = new HashMap<String,String>();
        form.put( "complete", "1000" );
        form.put( "completed", "0100" );
        form.put( "incomplete", "0010" );
        form.put( "foster", "0001" );
        attributes.put( 2, form );
        
        HashMap<String,String> children = new HashMap<String,String>();
        children.put( "1", "1000" );
        children.put( "2", "0100" );
        children.put( "3", "0010" );
        children.put( "more", "0001" );
        attributes.put( 3, children );
        
        HashMap<String,String> housing = new HashMap<String,String>();
        housing.put( "convenient", "100" );
        housing.put( "less_conv", "010" );
        housing.put( "critical", "001" );
        attributes.put( 4, housing );
        
        HashMap<String,String> finance = new HashMap<String,String>();
        finance.put( "convenient", "10" );
        finance.put( "inconv", "01" );
        attributes.put( 5, finance );
        
        HashMap<String,String> social = new HashMap<String,String>();
        social.put( "nonprob", "100" );
        social.put( "slightly_prob", "010" );
        social.put( "problematic", "001" );
        attributes.put( 6, social );
        
        HashMap<String,String> health = new HashMap<String,String>();
        health.put( "recommended", "100" );
        health.put( "priority", "010" );
        health.put( "not_recom", "001" );
        attributes.put( 7, health );
        
        HashMap<String,String> clazz = new HashMap<String,String>();
        clazz.put( "not_recom", "10000" );
        clazz.put( "recommend", "01000" );
        clazz.put( "very_recom", "00100" );
        clazz.put( "priority", "00010" );
        clazz.put( "spec_prior", "00001" );
        attributes.put( 8, clazz );
        
        
    }

}
