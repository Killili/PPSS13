/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.gui.view;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author wcu
 */
public class SensorNode {
    public Point position;
    public int id;
    public double[] nodeRSSI;
    public Long timeout;
    private LinkedList<Map<String, Object>> metaData;
    
    SensorNode(int x, int y, int id) {
        position = new Point(x,y);
        this.id = id;
        nodeRSSI = new double[100];
        metaData = new LinkedList<Map<String, Object>>();
        for(int i=0;i<100;i++)
            metaData.add(null);
    }
 

    void setRSSI(int destId, double val) {
        System.out.println("Changed rssi"+id +"->"+destId+" to :"+val);
        nodeRSSI[destId-1] = val;
    }

    double getRssi(int id) {
        if(id>99||id<0)
            return -0;
        
        return nodeRSSI[id];
        
    }

    Map<String, Object> getMetadata(int dest)
    {
       
        return this.metaData.get(dest+1);
    }
    
    void setMetadata(int dest,Map<String, Object> metaData) {
        
        this.metaData.set(dest, metaData);
        
    }
}
