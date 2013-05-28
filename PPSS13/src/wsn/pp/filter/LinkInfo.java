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
        this.metaData = new HashMap<>();
    }
    
    public AbstractMap.SimpleEntry<Integer, Integer> asEntry(){
        return new AbstractMap.SimpleEntry<>(sourceNode, destinationNode);
    }
    
    public LinkInfo(LinkInfo ls){
        this(ls.sourceNode, ls.destinationNode, ls.power, ls.timestamp);
    }
}
