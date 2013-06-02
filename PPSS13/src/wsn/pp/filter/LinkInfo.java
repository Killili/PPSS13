package wsn.pp.filter;


import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class LinkInfo {
    int sourceNode;
    int destinationNode;
    double power;
    long timestamp;
    Map<String,Object> metaData;

    public LinkInfo(int sourceNode, int destinationNode, double power, long timestamp) {
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
        this.power = power;
        this.timestamp = timestamp;
        this.metaData = new HashMap<String,Object>();
    }
    
    public AbstractMap.SimpleEntry<Integer, Integer> asEntry(){
        return new AbstractMap.SimpleEntry<Integer, Integer>(sourceNode, destinationNode);
    }
    
    public LinkInfo(LinkInfo ls){
        this(ls.sourceNode, ls.destinationNode, ls.power, ls.timestamp);
    }

    public int getDestinationNode() {
        return destinationNode;
    }

    public Map<String, Object> getMetaData() {
        return metaData;
    }

    public double getPower() {
        return power;
    }

    public int getSourceNode() {
        return sourceNode;
    }

    public long getTimestamp() {
        return timestamp;
    }
    
    
}
