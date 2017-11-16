// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)

import java.util.ArrayList;

import java.io.File;
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

	 
    //The hashMap of graph
    private HashMap<Integer, Vertex> graph;
    //The hashMap of the reverse graph
    private HashMap<Integer,Vertex> graphR;
    //The output from SCC Algo
    
    private HashMap<Integer, String> S;
    //private ArrayList<String> allSCCs;
    private int components = 0;
    private int largest = 0;

    
    //my finishTime 'counter'. FinishDFS said to set FinishTime[
    private Stack<Vertex> finishTime;

    private int numVertices = 0;
	
    
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

        public boolean isVisited(){
            return visited;
        }

        public void setVisited(boolean visited){
            this.visited = visited;
        }
        
        public boolean hasPathTo(String v){
    		ST<String, String>  prev = new ST<String, String>();
    	    ST<String, Integer> dist = new ST<String, Integer>();
    		Queue<String> queue = new LinkedList<String>();
            queue.add(this.getVertex());
            dist.put(this.getVertex(), 0);
            
            // repeated remove next vertex v from queue and insert
            // all its neighbors, provided they haven't yet been visited
            while (!queue.isEmpty()) {
                String a = queue.remove();
                for (String w : graph.get(a.hashCode()).edges) {
                    if (!dist.contains(w)) {
                        queue.remove(w);
                        dist.put(w, 1 + dist.get(a));
                        prev.put(w, a);
                    }
                }
            }
            return dist.contains(v);
    	}
        
        public int distanceTo(String v){
    		ST<String, String>  prev = new ST<String, String>();
    	    ST<String, Integer> dist = new ST<String, Integer>();
    		Queue<String> queue = new LinkedList<String>();
            queue.add(this.getVertex());
            dist.put(this.getVertex(), 0);
            
            // repeated remove next vertex v from queue and insert
            // all its neighbors, provided they haven't yet been visited
            while (!queue.isEmpty()) {
                String a = queue.remove();
                for (String w : graph.get(a.hashCode()).edges) {
                    if (!dist.contains(w)) {
                        queue.remove(w);
                        dist.put(w, 1 + dist.get(a));
                        prev.put(w, a);
                    }
                }
            }
            return dist.get(v);
    	}

    }
    
    class ST<Key extends Comparable<Key>, Value> implements Iterable<Key> {
        private TreeMap<Key, Value> st;
        public ST() {
            st = new TreeMap<Key, Value>();
        }

        public Value get(Key key) {
            if (key == null) throw new IllegalArgumentException("called get() with null key");
            return st.get(key);
        }

        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("called put() with null key");
            if (val == null) st.remove(key);
            else             st.put(key, val);
        }

        public void delete(Key key) {
            if (key == null) throw new IllegalArgumentException("called delete() with null key");
            st.remove(key);
        }

        public void remove(Key key) {
            if (key == null) throw new IllegalArgumentException("called remove() with null key");
            st.remove(key);
        }

        public boolean contains(Key key) {
            if (key == null) throw new IllegalArgumentException("called contains() with null key");
            return st.containsKey(key);
        }

        public int size() {
            return st.size();
        }

        public boolean isEmpty() {
            return size() == 0;
        }

        public Iterable<Key> keys() {
            return st.keySet();
        }

        public Iterator<Key> iterator() {
            return st.keySet().iterator();
        }

        public Key min() {
            if (isEmpty()) throw new NoSuchElementException("called min() with empty symbol table");
            return st.firstKey();
        }

        public Key max() {
            if (isEmpty()) throw new NoSuchElementException("called max() with empty symbol table");
            return st.lastKey();
        }

        public Key ceiling(Key key) {
            if (key == null) throw new IllegalArgumentException("called ceiling() with null key");
            Key k = st.ceilingKey(key);
            if (k == null) throw new NoSuchElementException("all keys are less than " + key);
            return k;
        }

        public Key floor(Key key) {
            if (key == null) throw new IllegalArgumentException("called floor() with null key");
            Key k = st.floorKey(key);
            if (k == null) throw new NoSuchElementException("all keys are greater than " + key);
            return k;
        }
    }
    
	//NOTE: graphData should be an absolute file path
	public GraphProcessor(String graphData)
	{
		// implementation
		 try{
	            //First build the graph
	            FileReader data = new FileReader(new File(graphData));
	            Scanner s = new Scanner(data);
	            numVertices = s.nextInt();
	            graph = new HashMap<>();
	            while(s.hasNext()){
	                String first = s.next();
	                String second = s.next();
	                //if a new vertex is coming in at the first spot, put it in first
	                if(!graph.containsKey(first.hashCode())){
	                    Vertex g = new Vertex(first);
	                    graph.put(first.hashCode(), g);
	                }
	                //add the edge connection to the second vertex
	                graph.get(first.hashCode()).setEdge(second);
	                //check the second vertex and make sure its in the graph
	                if(!graph.containsKey(second.hashCode())){
	                    Vertex g = new Vertex(second);
	                    graph.put(second.hashCode(), g);
	                }
	            }

	            s.close();

	            //Now find the SCC's
	            //using the private methods below derived from the lecture notes.
	            //SCC();
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
		ST<String, String>  prev = new ST<String, String>();
	    ST<String, Integer> dist = new ST<String, Integer>();
		Queue<String> queue = new LinkedList<String>();
        queue.add(u);
        dist.put(u, 0);
        
        // repeated remove next vertex v from queue and insert
        // all its neighbors, provided they haven't yet been visited
        while (!queue.isEmpty()) {
            String a = queue.remove();
            for (String w : graph.get(a.hashCode()).edges) {
                if (!dist.contains(w)) {
                    queue.remove(w);
                    dist.put(w, 1 + dist.get(a));
                    prev.put(w, a);
                }
            }
        }
        
        ArrayList<String> path = new ArrayList<String>();
        while (v != null && dist.contains(v)) {
            path.add(v);
            v = prev.get(v);
        }
        return path;
	}

	public int diameter()
	{
		// implementation
		 int best = -1;
	     for (Vertex s : graph.values()) {
	         //PathFinder finder = new PathFinder(G, s);
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
		return 0;
		// implementation
	}

}