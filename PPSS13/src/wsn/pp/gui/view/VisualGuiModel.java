/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.gui.view;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Set;
import wsn.pp.filter.LinkInfo;

/**
 *
 * @author wcu
 */
public class VisualGuiModel implements Serializable{

    public static VisualGuiModel m=null;
    LinkedList<SensorNode> nodes;
    private int updateTime = 100;
    
    //For heat maps
    public double coolingRate = 0.2;
    public double heatMultiplay =0.5;
    public double spreadRate = 0.1;
    public double range = 2;
    
    
    static void setInstance(VisualGuiModel visualGuiModel) {
        m = visualGuiModel;
    }
    
    public static VisualGuiModel getInstance()
    {
        if (m==null)
            m = new VisualGuiModel();
        return m;
    }
    
    
    
    
    
    private VisualGuiModel() {
        nodes  = new LinkedList<SensorNode>();
        m = this;
    }

    //Getter and Setter
    
    public LinkedList<SensorNode> getNodes() {
        return nodes;
    }
    

    
    public void updateRssi(int id, int destId ,double val) {
        for(SensorNode n:nodes)
        {
            if(n.id == id)
            {
                n.setRSSI(destId,val);
                return;
            }
        }
        
    }

    void updateRssi(LinkInfo ls) {
        for(SensorNode n:nodes)
        {
            if(n.id == ls.getSourceNode())
            {
                n.setRSSI(ls.getDestinationNode(),ls.getPower());
                n.setMetadata(ls.getDestinationNode(),ls.getMetaData());
                return;
            }
        }
    }
    
    
    
    
}
