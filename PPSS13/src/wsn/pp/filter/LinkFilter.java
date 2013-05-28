package wsn.pp.filter;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class LinkFilter implements LinkInfoReciver {

    private Map<Map.Entry<Integer, Integer>, List<LinkInfoReciver>> filterList = new HashMap<>();

    public void registerLinkFilter(int sourceNode, int destNode, LinkInfoReciver filter) {
        AbstractMap.SimpleEntry<Integer, Integer> pair = new AbstractMap.SimpleEntry<>(sourceNode, destNode);
        if (filterList.containsKey(pair)) {
            filterList.get(pair).add(filter);
        } else {
            ArrayList<LinkInfoReciver> list = new ArrayList<LinkInfoReciver>();
            list.add(filter);
            filterList.put(pair, list);
        }
    }

    public void recvLinkInfo(LinkInfo ls) {
        if (filterList.containsKey(ls.asEntry())) {
            for (LinkInfoReciver lsr : filterList.get(ls.asEntry())) {
                lsr.recvLinkInfo(ls);
            }
        }
    }
}
