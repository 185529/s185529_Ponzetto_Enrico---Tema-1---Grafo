package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class Simulator {
	
	// parametri di simulazione
	
	private int INIZIALI = 1000;
	private double PERC_STANZIALI = 0.5;
	
	// modello del mondo
	
	// quanti stanziali per cisacuna nazione
	private Map<Country, Integer> stanziali;
	UndirectedGraph<Country, DefaultEdge> graph;

	// misure in uscita
	
	private int passi;
	
	// coda deglie venti
	
	private PriorityQueue<Event> coda;
	
	// costruttore
	
	public Simulator(UndirectedGraph<Country, DefaultEdge> graph){
		
		// inizializzazione variabili di lavoro
		
		super();
		this.graph = graph;
		this.stanziali = new HashMap<Country, Integer>();
		
		// inizializzo a zero gli stanziali in ogni stato
		for(Country c : graph.vertexSet()){
			this.stanziali.put(c, 0);
		}
		
		this.coda = new PriorityQueue<Event>();
		
	}
	
	public void inserisci(Country c){
		
		Event e = new Event(INIZIALI, c, 1);
		coda.add(e);
		
	}
	
	public void run(){
		
		this.passi = 0;
		
		while(!coda.isEmpty()){
			
			// prelevo evento da coda
			
			Event e = coda.poll();
			
			this.passi = e.getT();
			
			// gestione stanziali
			
			int stanz = (int)(e.getNum()*this.PERC_STANZIALI);
			
			// gestione nomadi
			
			int confinanti = Graphs.neighborListOf(graph, e.getCountry()).size();
			int nomadi = (e.getNum() - stanz)/confinanti;
			
			// aggiunta resto divisione precedente
			
			stanz = e.getNum() - nomadi * confinanti;
			
			// aggiorno modello del mondo --- contabilizzo stanziali
			
			stanziali.put(e.getCountry(), stanziali.get(e.getCountry())+stanz);
			
			// schedulo eventi futuri --- inserire destinazione nomadi --- solo se restano ancora nomadi
			
			if(nomadi>0){
				for(Country c : Graphs.neighborListOf(graph, e.getCountry())){
					Event event = new Event(nomadi, c, e.getT()+1);
					coda.add(event);
				}
			}
			
		}
		
	}
	
	public int getPassi(){
		return this.passi;
	}
	
	public List<CountryAndNum> getPresenti(){
		
		List<CountryAndNum> list = new ArrayList<CountryAndNum>();
		
		for(Country c : stanziali.keySet()){
			if(stanziali.get(c)>0){
				list.add(new CountryAndNum(c, stanziali.get(c)));
			}
		}
		
		Collections.sort(list);
		
		return list;
		
	}
	
}
