/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.gui.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import wsn.pp.data.Datasource;
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
    Datasource loggin;
    private String store;
    
    public VisualGuiControl(LinkInfoReciver lir,String file, Datasource loggin)
    {
        super(lir);
        this.loggin = loggin;
        if(file!=null)
            loadFile(file);
        
        
    }


    @Override
    public void recvLinkInfo(LinkInfo ls) {
        
        //System.out.println("rec Link");
        VisualGuiModel.getInstance().updateRssi(ls);
        super.recvLinkInfo(ls);
    }

    void addNode() {
        VisualGuiModel.getInstance().nodes.add(new SensorNode((int)(Math.random()*500),50,VisualGuiModel.getInstance().nodes.size()));
    }

    @Override
    public void run() {
        
        view = new VisualGui(this);
   
        
        view.setVisible(true);
        final VisualGuiControl control = this;
        
        Thread t = new Thread(new Runnable() {
         
            @Override
            public void run() {
                panel = new ViewRender(control);
                panel.init();
        
                
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
        VisualGuiModel.setInstance((VisualGuiModel) in.readObject());
            System.out.println("Loaded "+VisualGuiModel.getInstance().nodes.size()+" points");
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
        out.writeObject(VisualGuiModel.getInstance());
        }
        catch(Exception e)
        {
            System.err.println(e);
        }
    }

    void playFile(String text) {
        store = text;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                loggin.playRecording(new File(store));
            }
        });
        t.start();
        
        
    }

    void setVisibleParts(boolean heat, boolean lines, boolean values) {
        panel.setVisibleParts(heat,lines,values);
    }

    void setSpread(String text) {
        VisualGuiModel.getInstance().spreadRate = Double.parseDouble(text);
    }

    void setCooling(String text) {
        VisualGuiModel.getInstance().coolingRate = Double.parseDouble(text);
    }

    void setHeat(String text) {
        VisualGuiModel.getInstance().heatMultiplay =  Double.parseDouble(text);
    }

    void setRange(String text) {
        VisualGuiModel.getInstance().range =  Double.parseDouble(text);
    }
    
    
    
    
}
