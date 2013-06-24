package wsn.pp.filter;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class LinkFilter implements LinkInfoReciver {

    private Map<Map.Entry<Integer, Integer>, List<LinkInfoReciver>> filterList = new HashMap<Map.Entry<Integer, Integer>, List<LinkInfoReciver>>();

    public void registerLinkFilter(int sourceNode, int destNode, LinkInfoReciver filter) {
        AbstractMap.SimpleEntry<Integer, Integer> pair = new AbstractMap.SimpleEntry<Integer, Integer>(sourceNode, destNode);
        synchronized (filterList) {
            if (filterList.containsKey(pair)) {
                filterList.get(pair).add(filter);
            } else {
                ArrayList<LinkInfoReciver> list = new ArrayList<LinkInfoReciver>();
                list.add(filter);
                filterList.put(pair, list);
            }
        }
    }
    
    public void removeFilter(LinkInfoReciver lir){
        synchronized (filterList){
            Entry<Entry<Integer, Integer>, List<LinkInfoReciver>> tmp = null;
            for (Map.Entry<Map.Entry<Integer, Integer>, List<LinkInfoReciver>> entry : filterList.entrySet()) {
                if(entry.getValue().contains( lir )){
                    tmp = entry;
                }
            }
            if(tmp != null){
                filterList.get(tmp.getKey()).remove(lir);
            }
        }
    }

    public void recvLinkInfo(LinkInfo ls) {
        synchronized (filterList) {
            if (filterList.containsKey(ls.asEntry())) {
                for (LinkInfoReciver lsr : filterList.get(ls.asEntry())) {
                    lsr.recvLinkInfo(ls);
                }
            }
        }
    }
}
