package wsn.pp.filter;


import java.util.Collections;
import java.util.LinkedList;
import wsn.pp.data.ScienceTool;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class LinkMeanFilter extends Filter {

    private LinkedList<Double> data = new LinkedList<Double>();
    private int window;
    private LinkInfoReciver lsr;

    public LinkMeanFilter(int window, LinkInfoReciver lsr) {
        super(lsr);
        this.window = window;
        this.lsr = lsr;
    }
    
    public void recvLinkInfo(LinkInfo ls) {
          if(ScienceTool._SCIENCE)
        {
            window = (int)ScienceTool.getParameter("window");
        }
          
        data.addLast(ls.power);
        if(data.size() >= window){
            double mean = 0,error = 0;
            for(Double value: data){
                mean += value;
            }
            
            mean = mean / data.size();
            //error = (Collections.max(data)-Collections.min(data))/2;
            ls = new LinkInfo(ls);
            ls.power = mean;
            
            data.removeFirst();
            
            super.recvLinkInfo(ls);
        }
    }
    
    
}
