package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private UndirectedGraph<Country, DefaultEdge> graph;
	List<Country> countries;
	Map<Integer, Country> countryMap;
	List<CountryAndNum> stanziali;
	
	public Model(){
		super();
	}
	
	public List<CountryAndNum> creaGrafo(int anno){
		
		// creo un nuovo grafo ogni volta --- l'interfaccia può essere usata più volte senza essere riavviata
		
		this.graph = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		
		// aggiungi i vertici
		
		BordersDAO dao = new BordersDAO();
		
		countries = dao.loadAllCountries();
		
		this.countryMap = new HashMap<Integer, Country>();
		
		for(Country c : this.countries){
			this.countryMap.put(c.getcCode(), c);
		}
		
		Graphs.addAllVertices(graph, countries);
		
		// aggiungi gli archi --- uso IDMap per costruire gli oggetti Country
		
		List<IntegerPair> confini = dao.getCountryPairs(anno);
		
		for(IntegerPair p : confini){
			
			graph.addEdge(this.countryMap.get(p.getNumero1()), this.countryMap.get(p.getNumero2()));
			
		}
		
		List<CountryAndNum> lista = new ArrayList<CountryAndNum>();
		
		// aggiungo alla lista solo se ha confinanti
		
		for(Country c : this.countries){
			
			int confinanti = Graphs.neighborListOf(graph, c).size();
			
			if(confinanti!=0){
				lista.add(new CountryAndNum(c, confinanti));
			}
			
		}
		
		Collections.sort(lista);
		
		return lista;
		
	}
	
	public static void main(String args[]){
		
		Model m = new Model();
		
		List<CountryAndNum> lista1 = m.creaGrafo(2000);
		
		System.out.println(m.graph);
		for(CountryAndNum cn : lista1){
			System.out.format("%s: %d\n", cn.getCountry().toString(), cn.getNum());
		}
		
		List<CountryAndNum> lista2 = m.creaGrafo(1900);
		
		System.out.println(m.graph);
		for(CountryAndNum cn : lista2){
			System.out.format("%s: %d\n", cn.getCountry().toString(), cn.getNum());
		}
		
	}

	public int simula(Country partenza) {
		
		Simulator simulator = new Simulator(this.graph);
		
		simulator.inserisci(partenza);
		
		simulator.run();
		
		this.stanziali = simulator.getPresenti();
		
		return simulator.getPassi();
		
	}
	
	public List<CountryAndNum> getStanziali(){
		return this.stanziali;
	}

}
