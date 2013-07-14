/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.filter;

import java.util.Collections;
import java.util.LinkedList;
import wsn.pp.data.ScienceTool;

/**
 *
 * @author user
 */
public class LinkATMFFilter extends Filter {
    private LinkedList<Double> data = new LinkedList<Double>();
    private int window;
    private LinkInfoReciver lsr;
    private float alpha;

    public LinkATMFFilter(int window, float alpha, LinkInfoReciver lsr){
        super(lsr);
        this.window = window;
        this.alpha = alpha;
    }
    
    @Override
    public void recvLinkInfo(LinkInfo ls) {
        
        
        if(ScienceTool._SCIENCE)
        {
            window = (int)ScienceTool.getParameter("window");
            alpha = (int)ScienceTool.getParameter("alpha");
        }
        
        data.addLast(ls.power);
        if(data.size() >= window){
            LinkedList<Double> clone = (LinkedList<Double>) data.clone();
            Collections.sort(clone);
            for( int i = (int)((window*alpha) / 2); i > 0 ; i-- ){
                clone.removeFirst();
                clone.removeLast();
            }
            double mean = 0,stddev = 0;
            for(Double value: clone){
                mean += value;
            }
            mean = mean / clone.size();
            for(Double value: clone){
                stddev += (mean - value)*(mean - value);
            }
            stddev = Math.sqrt( stddev );
            data.removeFirst();
            //error = Collections.max(data)-Collections.min(data);
            ls = new LinkInfo(ls);
            ls.metaData.put("StdDev", stddev);
            ls.power = mean;
            super.recvLinkInfo(ls);
        }
    }
}
