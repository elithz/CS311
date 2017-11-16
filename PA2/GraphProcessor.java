// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)


/**	
*	@Filename		GraphProcessor.java
*	@Description	GraphProcessor of PA2
*	@Version		1.0
*	@Created		11.10.2017 15h00min23s
*	@Author			elith(Ningyuan Zhang), nick(Siyuan Zen)
*	@Company		NERVE Software
*/


import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.TreeMap;


public class GraphProcessor
{
	// other member fields and methods

	 
    //hashMaped graph
    private HashMap<Integer, Vertex> graph;
    public int numVertices = 0;
	
  //self-made path structure STTR, removed unusable methods
    class STTR<Key extends Comparable<Key>, Value> implements Iterable<Key> {
        private TreeMap<Key, Value> st;
        public STTR() {
            st = new TreeMap<Key, Value>();
        }

        public Value get(Key key) {
            if (key == null) throw new IllegalArgumentException();
            return st.get(key);
        }

        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException();
            if (val == null) st.remove(key);
            else             st.put(key, val);
        }

        public void delete(Key key) {
            if (key == null) throw new IllegalArgumentException();
            st.remove(key);
        }

        public void remove(Key key) {
            if (key == null) throw new IllegalArgumentException();
            st.remove(key);
        }

        public boolean contains(Key key) {
            if (key == null) throw new IllegalArgumentException();
            return st.containsKey(key);
        }


        public Iterable<Key> keys() {
            return st.keySet();
        }

        public Iterator<Key> iterator() {
            return st.keySet().iterator();
        }

    }
    
    
    class Vertex {
        private String vertex;
        private ArrayList<String> edges;
        private boolean visited = false;
        
        public Vertex(String vertex){
            this.vertex = vertex;
            edges = new ArrayList<String>();
        }

        public String getVertex(){
            return this.vertex;
        }

        public void setEdge(String vertex2){
            edges.add(vertex2);
        }

        public ArrayList<String> getEdges(){
            return edges;
        }

        public void setVisited(boolean visited){
            this.visited = visited;
        }
        
        public boolean isVisited(){
            return visited;
        }
        
        public boolean hasPathTo(String v){
    		STTR<String, String>  previous = new STTR<String, String>();
    	    STTR<String, Integer> distance = new STTR<String, Integer>();
    		Queue<String> q = new LinkedList<String>();
            q.add(this.getVertex());
            distance.put(this.getVertex(), 0);
            //perform bfs on graph
            while (!q.isEmpty()) {
                String a = q.remove();
                for (String w : graph.get(a.hashCode()).edges) {
                    if (!distance.contains(w)) {
                        q.remove(w);
                        distance.put(w, 1 + distance.get(a));
                        previous.put(w, a);
                    }
                }
            }
            return distance.contains(v);
    	}
        
        public int distanceTo(String v){
    		STTR<String, String>  previous = new STTR<String, String>();
    	    STTR<String, Integer> distance = new STTR<String, Integer>();
    		Queue<String> q = new LinkedList<String>();
            q.add(this.getVertex());
            distance.put(this.getVertex(), 0);
            
           //perform bfs on graph
            while (!q.isEmpty()) {
                String a = q.remove();
                for (String w : graph.get(a.hashCode()).edges) {
                    if (!distance.contains(w)) {
                        q.remove(w);
                        distance.put(w, 1 + distance.get(a));
                        previous.put(w, a);
                    }
                }
            }
            return distance.get(v);
    	}

    }
    
    
    
    
	//NOTE: graphData should be an absolute file path
	public GraphProcessor(String graphData)
	{
		// implementation
		 try{
	            //First build the graph
	            FileReader path = new FileReader(new File(graphData));
	            Scanner s = new Scanner(path);
	            numVertices = s.nextInt();
	            graph = new HashMap<>();
	            while(s.hasNext()){
	                String a = s.next();
	                String b = s.next();
	                //if new vertex comes in, put it in a
	                if(!graph.containsKey(a.hashCode())){
	                    Vertex g = new Vertex(a);
	                    graph.put(a.hashCode(), g);
	                }
	                //connect to b vertex
	                graph.get(a.hashCode()).setEdge(b);
	                //check b vertex to make sure in graph
	                if(!graph.containsKey(b.hashCode())){
	                    Vertex g = new Vertex(b);
	                    graph.put(b.hashCode(), g);
	                }
	            }

	            s.close();
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	}

	
	
	public int outDegree(String v)
	{
		// implementation
		 if(!graph.containsKey(v.hashCode()) || v.equals(null))
	            return 0;
	     return graph.get(v.hashCode()).getEdges().size();
	}

	public ArrayList<String> bfsPath(String u, String v)
	{
		// implementation
		STTR<String, String>  previous = new STTR<String, String>();
	    STTR<String, Integer> distance = new STTR<String, Integer>();
		Queue<String> q = new LinkedList<String>();
        q.add(u);
        distance.put(u, 0);
        
        // repeated remove next vertex v from q and insert
        // all its neighbors, provided they haven't yet been visited
        while (!q.isEmpty()) {
            String a = q.remove();
            for (String w : graph.get(a.hashCode()).edges) {
                if (!distance.contains(w)) {
                    q.remove(w);
                    distance.put(w, 1 + distance.get(a));
                    previous.put(w, a);
                }
            }
        }
        
        ArrayList<String> path = new ArrayList<String>();
        while (v != null && distance.contains(v)) {
            path.add(v);
            v = previous.get(v);
        }
        return path;
	}

	public int diameter()
	{
		// implementation
		 int best = -1;
	     for (Vertex s : graph.values()) {
	         for (Vertex v : graph.values()) {
	             if (s.hasPathTo(v.getVertex()) && s.distanceTo(v.getVertex()) > best) {
	                 best = s.distanceTo(v.getVertex());
	             }
	         }
	     }
	     return best;
	}
	
	public int centrality(String v)
	{
		// implementation
		int counter = 0;
		for (Vertex s : graph.values()) 
	         for (Vertex x : graph.values()) 
	        	 if(this.bfsPath(s.getVertex(), x.getVertex()).contains(v))
	        			 counter++;
	    return counter;
	}
	
	private void compute() throws FileNotFoundException{
		int maxOutD = 0;
		String maxOutDPage = "";
		for (Vertex s : this.graph.values())
			if(this.outDegree(s.getVertex()) >= maxOutD)
				maxOutD = this.outDegree(s.getVertex());	
		for (Vertex s : this.graph.values())
			if(this.outDegree(s.getVertex()) == maxOutD)
				maxOutDPage = maxOutDPage + s.getVertex();
		
		
		int diam = this.diameter();
		
		
		int maxCentr = 0;
		String maxCentrPage = "";
		for (Vertex s : this.graph.values())
			if(this.centrality(s.getVertex()) >= maxCentr)
				maxCentr = this.centrality(s.getVertex());	
		for (Vertex s : this.graph.values())
			if(this.centrality(s.getVertex()) == maxCentr)
				maxCentrPage = maxCentrPage + s.getVertex();
		
		int numVir = this.numVertices;
		
		String temp = "";
		int x = 0;
		FileReader path = new FileReader(new File("wikiCS.txt"));
        Scanner s = new Scanner(path);
		while(s.hasNext()){
			temp = s.next();
			x++;
		}
		int NumEage = x - 1;
		
		System.out.println(maxOutDPage);
		System.out.println(maxCentrPage);
		System.out.println(numVir);
		System.out.println(NumEage);
		System.out.println(diam);
	}

}