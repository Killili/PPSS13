/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.filter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jan
 */
public class Filter implements LinkInfoReciver {

    private List<LinkInfoReciver> childs = new ArrayList<LinkInfoReciver>();
    public Filter(LinkInfoReciver nextFilter) {
        registerFilter(nextFilter);
    }
    
    public LinkInfoReciver registerFilter(LinkInfoReciver lir){
        if(lir != null){
            childs.add(lir);
        }
        return this;
    }
    
    public LinkInfoReciver removeFilter(LinkInfoReciver lir){
        if(lir != null){
            childs.remove(lir);
        }
        return this;
    }
    
    @Override
    public void recvLinkInfo(LinkInfo ls) {
        for(LinkInfoReciver lir: childs){
            lir.recvLinkInfo(ls);
        }
    }
    
}
