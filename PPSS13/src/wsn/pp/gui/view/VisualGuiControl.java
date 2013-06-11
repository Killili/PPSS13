/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.gui.view;

import com.sun.java.swing.plaf.nimbus.SliderPainter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.PApplet;
import static processing.core.PConstants.P2D;
import wsn.pp.filter.Filter;
import wsn.pp.filter.LinkInfo;
import wsn.pp.filter.LinkInfoReciver;

/**
 *
 * @author Marvin
 */
public class VisualGuiControl extends Filter implements LinkInfoReciver,Runnable{
    
    ViewRender panel;
    VisualGui view;
    VisualGuiModel m;
    
    public VisualGuiControl(LinkInfoReciver lir,String file)
    {
        super(lir);
        if(file!=null)
            loadFile(file);
        
        
    }

    @Override
    public void recvLinkInfo(LinkInfo ls) {
        
        //System.out.println("rec Link");
        m.updateRssi(ls);
        //super.recvLinkInfo(ls);
    }

    void addNode() {
        m.nodes.add(new SensorNode((int)(Math.random()*500),50,m.nodes.size()));
    }

    @Override
    public void run() {
        if(m== null)
            m = new VisualGuiModel();
        view = new VisualGui(this);
   
        
        view.setVisible(true);
        final VisualGuiControl control = this;
        
        Thread t = new Thread(new Runnable() {
         
            @Override
            public void run() {
                panel = new ViewRender(control);
                panel.init();
        
                panel.setModel(m);
                view.getRenderPanel().add(panel);
                while(true){
                panel.start();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(VisualGuiControl.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                }
                
            }
        });
        t.start();
        //for(int i=0;i<9;i++)
        //   addNode();
    }
    
    public void loadFile(String name)
    {
        try
        {
        FileInputStream fileOut = new FileInputStream(name);
        ObjectInputStream in = new ObjectInputStream(fileOut);
        m = (VisualGuiModel) in.readObject();
        
        }
        catch(Exception e)
        {
            System.err.println(e);
        }
        
    }
    
    public void saveFile(String name)
    {
        try
        {
        FileOutputStream fileOut = new FileOutputStream(name);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(m);
        }
        catch(Exception e)
        {
            System.err.println(e);
        }
    }
    
    
    
    
}
