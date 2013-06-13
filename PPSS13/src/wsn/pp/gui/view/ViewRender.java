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
    private SensorNode selectedNode;
    private double[][] heatMap;
    private boolean heat;
    private boolean lines;
    private boolean values;
    
    public ViewRender(VisualGuiControl cont) {
        control = cont;
        heatMap = new double[600][600];
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

     void setVisibleParts(boolean heat, boolean lines, boolean values) 
     {
         this.heat = heat;
         this.lines = lines;
         this.values = values;
    }
  
    
    @Override
    public void draw() {
        //super.draw(); //To change body of generated methods, choose Tools | Templates.
        
        fill(255, 255, 255);
        rect(0, 0, 600, 600);
       fill(2, 25, 55);
        //System.out.println("draw");
        calcHeatmap();
        
        VisualGuiModel model = VisualGuiModel.getInstance();
        if(model==null)
            return;
        
        //System.out.println("try to draw "+model.nodes.size()+" points");
        if(heat)
        for(int x=0;x<heatMap.length;x++)
            for(int y=0;y<heatMap[0].length;y++)
            {
                if(heatMap[x][y]<2)
                    continue;
                //System.out.println("h"+heatMap[x][y]);
                stroke((int)(heatMap[x][y]*25),23,255-(int)(heatMap[x][y]*25));
                rect(x, y, 1, 1);
            }
        
        for(Object nod:model.getNodes().toArray())
        {
            fill(2, 25, 55);
            SensorNode node = (SensorNode) nod;
            ellipse((int)node.position.x,(int)node.position.y, 10, 10);
            
            fill(24, 215, 55);
            text(""+node.id, (int)node.position.x+7,(int)node.position.y+7);
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
                //System.out.println(node1.getMetadata(node2.id));
                
                if(node1.getMetadata(node2.id)!=null && node1.getMetadata(node2.id).get("StdDev")!=null)
                {
                    strokeWeight(3);
                    stroke((float) ((Double)(node1.getMetadata(node2.id).get("StdDev"))*10),12,12);
                }
                else
                {
                    stroke(231,15,85);
                }
                if(lines)
                    line((int)node1.position.x, (int)node1.position.y, (int)node2.position.x, (int)node2.position.y);
                Point mid = getMid(node1.position, node2.position);
                if(values)
                    text("r:"+(((double)Math.round(node1.getRssi(node2.id)*100))/100), mid.x, mid.y);
                stroke(231,15,85);
            }
        }
        
    }
    
    private void calcHeatmap()
    {
        VisualGuiModel model = VisualGuiModel.getInstance();
        double coolingRate = model.coolingRate;
        double heatMultiplay =model.heatMultiplay;
        double spreadRate = model.spreadRate;
        double range = model.range;
      
        
        
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
                
                if(node1.getMetadata(node2.id)!=null && node1.getMetadata(node2.id).get("StdDev")!=null)
                {
                    strokeWeight(3);
                    double d =((Double)(node1.getMetadata(node2.id).get("StdDev"))*10);
                    heatPath(heatMap, node1.position, node2.position,(int)(d*heatMultiplay));
                    //heatMiddel(heatMap, node1.position, node2.position,(int)(d*heatMultiplay));
                }
            }
        }
        
        coolMap(heatMap, coolingRate);
        heatMap =spreadheat(heatMap, spreadRate, (int)range);
        
    }
    
    private double[][] spreadheat(double[][] map,double rate,int range)
    {
        double[][] out = new double[map.length][map[0].length];
        for(int x=0;x<map.length;x++)
            for(int y=0;y<map[0].length;y++)
                spreadHeatPoint(out, map, rate, range, x, y);
        return out;
    }
    
    private void spreadHeatPoint(double[][] map,double[][] sourceMap,double rate,int range,int x,int y)
    {
        if(sourceMap[x][y]== 0)
            return;
        for(int xOff = -range;xOff<=range;xOff++)
             for(int yOff = -range;yOff<=range;yOff++)
             {
                 if(!checkPosition(map, x+xOff, y+yOff)||(xOff==0&&yOff==0))
                     continue;
                 map[x+xOff][y+yOff]+= sourceMap[x][y]*rate;
             }
    }
    
    private boolean checkPosition(double[][] map,int x, int y)
    {
        return x>=0 && y>=0&& map.length>x && map[0].length>y;
    }
    
    private void heatMiddel(double[][] map,Point a, Point b,int heat)
    {
       double pathLenght = a.distance(b),rest = pathLenght;
       Point mid = getMid(a, b);
       map[mid.x][mid.y] = Math.max(heat,map[mid.x][mid.y]);
       
    }
    
    private void heatPath(double[][] map,Point a, Point b,int heat)
    {
        double pathLenght = a.distance(b),rest = pathLenght;
        
        double xPath,yPath;
        xPath = b.x-a.x;
        yPath = b.y-a.y;
        
        while(rest >0)
        {
            //System.out.println("Heat "+(a.x+xPath*(rest/pathLenght))+":"+((a.y+yPath*(rest/pathLenght)))+" -> "+heat);
            //map[(int)(a.x+xPath*(rest/pathLenght))][(int)(a.y+yPath*(rest/pathLenght))] = Math.max(heat,map[(int)(a.x+xPath*(rest/pathLenght))][(int)(a.y+yPath*(rest/pathLenght))]);
            map[(int)(a.x+xPath*(rest/pathLenght))][(int)(a.y+yPath*(rest/pathLenght))] += heat;
            rest--;
        }
    }
    
    private void coolMap(double[][] map,double rate)
    {
        for(double[] x:map)
        {
            for(int i =0;i<x.length;i++)
            {
               // System.out.println("cool "+x[i] + " -> "+((int)(rate*x[i])));
                x[i] = (rate*x[i]);
            }
        }
    }
    
    
    //support
    
    private SensorNode getClickedNode(int x, int y)
    {
        SensorNode node = null;
        double maxDist = 10;
        
        VisualGuiModel model = VisualGuiModel.getInstance();
        
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
