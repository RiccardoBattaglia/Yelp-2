package it.polito.tdp.yelp.model;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private YelpDao dao;
	private Graph<Review, DefaultWeightedEdge> grafo;
	private Map<String, Business> businessMap;
	private List<Business> businessList;
	private Map<String, Review> reviewMap;
	private List<Review> reviewList;
	
	public Model() {
		this.dao = new YelpDao();
		
		this.businessMap=new HashMap<>();
		this.dao = new YelpDao();
		this.businessList = this.dao.getAllBusiness();
		for(Business i : businessList) {
			this.businessMap.put(i.getBusinessId(), i);
		}
		
		this.reviewMap=new HashMap<>();
		this.dao = new YelpDao();
		this.reviewList = this.dao.getAllReviews();
		for(Review i : reviewList) {
			this.reviewMap.put(i.getReviewId(), i);
		}
	}
	
public List<String> getCity(){
		
		List<String> result = new LinkedList<>();
		
		for(Business i : businessList) {
			if(!result.contains(i.getCity())) {
			result.add(i.getCity());
		}
		}
		
		return result;
	}

public List<String> getLocali(String city){
	
	List<String> result = new LinkedList<>();
	
	for(Business i : businessList) {
		if(i.getCity().equals(city)) {
		result.add(i.getBusinessName());
	}
	}
	
	return result;
}

public void creaGrafo(String locale) {
	// TODO Auto-generated method stub

this.grafo = new SimpleDirectedWeightedGraph<Review, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	
// Aggiunta VERTICI 
//List<Integer> vertici = this.dao.getVertici(nome);
//Graphs.addAllVertices(this.grafo, vertici);

String businessId="";

for(Business i : this.businessList) {
	if(i.getBusinessName().equals(locale)) {
		businessId=i.getBusinessId();
	}
}

List<Review> vertici=new LinkedList<>();

for(Review i : reviewList) {
	if(i.getBusinessId().equals(businessId)) {
		vertici.add(i);
	}
}

Graphs.addAllVertices(this.grafo, vertici);

// Aggiunta ARCHI

for (Review v1 : vertici) {
	for (Review v2 : vertici) {
		if(v2.getDate().isAfter(v1.getDate())) {
			this.grafo.addEdge(v1,v2);
		}
	}}

for (Review v1 : vertici) {
	List<DefaultWeightedEdge> archi=new LinkedList<>();
	archi.addAll(this.grafo.outgoingEdgesOf(v1));
	for (DefaultWeightedEdge a : archi) {
		this.grafo.setEdgeWeight(a, ChronoUnit.DAYS.between(v1.getDate(), this.grafo.getEdgeTarget(a).getDate()));
	}
	}
}

public int nVertici() {
return this.grafo.vertexSet().size();
}

public int nArchi() {
return this.grafo.edgeSet().size();
}

//public Set<Track> getVertici(){
//
//Set<Track> vertici=this.grafo.vertexSet();
//
//return vertici;
//}

public List<String> trovaMaxArchiUscenti() {
	
	int n=0;
	
	for (Review i : this.grafo.vertexSet()) {
		if(this.grafo.outgoingEdgesOf(i).size()>n) {
			n=this.grafo.outgoingEdgesOf(i).size();
		}
	}
	
	List<String> listaMax=new LinkedList<>();
	for (Review i : this.grafo.vertexSet()) {
		if(this.grafo.outgoingEdgesOf(i).size()==n) {
			listaMax.add(i.getReviewId());
		}
		
	}
	
	return listaMax;
}

public Integer dimmiNArchi( List<String> lista) {
	
	int n=0;
	
	for (String i : lista) {
		for (Review j : this.grafo.vertexSet()) {
		if(i==j.getReviewId()) {
			n=this.grafo.outgoingEdgesOf(j).size();
		}}
	}
	
	return n;
}
	
	
}
