/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.gui;

import processing.core.PApplet;
import wsn.pp.filter.Filter;
import wsn.pp.filter.LinkInfo;
import wsn.pp.filter.LinkInfoReciver;

/**
 *
 * @author Marvin
 */
public class VisualGuiControl extends Filter implements LinkInfoReciver{
    
    processing.core.PApplet panel;
    VisualGui view;
    
    public VisualGuiControl(LinkInfoReciver lir)
    {
        super(lir);
        //panel = new PApplet();
        view = new VisualGui(this);
        //view.getRenderPanel().add(panel);
        view.setVisible(true);
        
    }

    @Override
    public void recvLinkInfo(LinkInfo ls) {
        
        super.recvLinkInfo(ls);
    }
    
    
}
