/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.gui.view;

import java.awt.Color;
import java.awt.Event;
import java.awt.Point;
import java.util.LinkedList;
import processing.core.PApplet;
import static processing.core.PConstants.P2D;

/**
 *
 * @author wcu
 */
public class ViewRender  extends PApplet{

    VisualGuiControl control;
    VisualGuiModel model;
    private SensorNode selectedNode;
    
    public ViewRender(VisualGuiControl cont) {
        control = cont;
        
    }
    

    
    public void setModel(VisualGuiModel v)
    {
        model = v;
    }

    @Override
    public void setup() {
        super.setup();
        size(600, 600, P2D);
    }

    @Override
    public boolean mouseUp(Event event, int x, int y) {
        if(selectedNode!=null)
            selectedNode.position.setLocation(x, y);
        return true;
    }

    @Override
    public void mouseReleased() {
        if(selectedNode!=null)
            selectedNode.position.setLocation(mouseX, mouseY);
        selectedNode =null;
        
    }

    
    
    @Override
    public void mousePressed() {
        super.mousePressed(); 
        System.out.println("click "+mouseX+":"+mouseY);
        SensorNode node = getClickedNode(mouseX, mouseY);
        
        if(node!=null)
        {
            selectedNode = node;
        }
       
    }

    
    
    @Override
    public void draw() {
        super.draw(); //To change body of generated methods, choose Tools | Templates.
        
        fill(255, 255, 255);
        rect(0, 0, 600, 600);
       fill(2, 25, 55);
        //System.out.println("draw");
       
        if(model==null)
            return;
        for(Object nod:model.getNodes().toArray())
        {
            fill(2, 25, 55);
            SensorNode node = (SensorNode) nod;
            ellipse((int)node.position.x,(int)node.position.y, 10, 10);
            
            fill(24, 215, 55);
            text(""+node.id, (int)node.position.x,(int)node.position.y);
        }
        
        for(Object nod:model.getNodes().toArray())
        {
           
            SensorNode node1 = (SensorNode) nod;
            for(Object nod2:model.getNodes().toArray())
            {       
               
                SensorNode node2 = (SensorNode) nod2;
                if(node2 == node1)
                    continue;
                if(node1.getRssi(node2.id)==0)
                    continue;
                System.out.println(node1.getMetadata(node2.id));
                if(node1.getMetadata(node2.id)!=null && node1.getMetadata(node2.id).get("StdDev")!=null)
                {
                    strokeWeight(3);
                    stroke((float) ((Double)(node1.getMetadata(node2.id).get("StdDev"))*10),12,12);
                }
                else
                {
                    stroke(231,15,85);
                }
                line((int)node1.position.x, (int)node1.position.y, (int)node2.position.x, (int)node2.position.y);
                Point mid = getMid(node1.position, node2.position);
                text("r:"+(((double)Math.round(node1.getRssi(node2.id)*100))/100), mid.x, mid.y);
                stroke(231,15,85);
            }
        }
    }
    
    
    //support
    
    private SensorNode getClickedNode(int x, int y)
    {
        SensorNode node = null;
        double maxDist = 10;
        for(Object nod:model.getNodes().toArray())
        {
            SensorNode n = (SensorNode) nod;
            if(n.position.distance(x, y)<=maxDist)
            {
                node = n;
                maxDist =n.position.distance(x, y);
            }
        }
        return node;
    }
    
    private Point getMid(Point a, Point b) {
        Point result = new Point(a);
        result.translate((b.x - a.x) / 2, (b.y - a.y) / 2);
        return result;
    }
}
