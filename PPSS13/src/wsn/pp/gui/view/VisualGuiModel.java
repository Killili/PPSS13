/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.gui.view;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Set;
import wsn.pp.filter.LinkInfo;

/**
 *
 * @author wcu
 */
public class VisualGuiModel {
    LinkedList<SensorNode> nodes;
    private int updateTime = 100;
    public VisualGuiModel() {
        nodes  = new LinkedList<SensorNode>();
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
