/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.gui;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import wsn.pp.filter.LinkATMFFilter;
import wsn.pp.filter.LinkFilter;
import wsn.pp.filter.LinkInfo;
import wsn.pp.filter.LinkInfoReciver;
import wsn.pp.filter.LinkKNN;
import wsn.pp.filter.LinkPlot;

/**
 *
 * @author Killi
 */
public class KNNControl extends javax.swing.JFrame implements LinkInfoReciver {

    private final List<LinkKNN> knns;
    private final HashMap<LinkKNN, LinkATMFFilter> atmfs;
    private final HashMap<LinkKNN, Map.Entry<Integer, Integer>> links;
    private final LinkFilter linkFilter;
    private final LinkedList<LinkInfo> data;

    /**
     * Creates new form KNNControl
     */
    public KNNControl(LinkFilter lf) {
        this.knns = new ArrayList<LinkKNN>();
        this.atmfs = new HashMap<LinkKNN, LinkATMFFilter>();
        this.links = new HashMap<LinkKNN, Entry<Integer, Integer>>();
        this.linkFilter = lf;
        this.data = new LinkedList<LinkInfo>();
        initComponents();
    }

    public KNNControl addKNN(LinkKNN knn, LinkATMFFilter atmf, int source, int destination) {
        knns.add(knn);
        ((DefaultListModel) lstEstimate.getModel()).addElement(knn);
        this.atmfs.put(knn, atmf);
        this.links.put(knn, new AbstractMap.SimpleEntry<Integer, Integer>(source, destination));
        knn.setKNNControl(this);
        return this;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstTypes = new javax.swing.JList();
        btnLearn = new javax.swing.JButton();
        btnForget = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstEstimate = new javax.swing.JList();
        btnRaw = new javax.swing.JButton();
        btnKNN = new javax.swing.JButton();
        btnATMF = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lstTypes.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Empty", "Standing", "Moving" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lstTypes);

        btnLearn.setText("Learn");
        btnLearn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLearnActionPerformed(evt);
            }
        });

        btnForget.setText("Forget");
        btnForget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForgetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnForget, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(btnLearn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLearn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnForget)
                .addContainerGap(132, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Learning", jPanel1);

        lstEstimate.setModel(new DefaultListModel());
        lstEstimate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstEstimateMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(lstEstimate);

        btnRaw.setText("Raw");
        btnRaw.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRawActionPerformed(evt);
            }
        });

        btnKNN.setText("KNN");
        btnKNN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKNNActionPerformed(evt);
            }
        });

        btnATMF.setText("ATMF");
        btnATMF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnATMFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnRaw)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnATMF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnKNN)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRaw)
                    .addComponent(btnKNN)
                    .addComponent(btnATMF))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Running", jPanel2);

        lblStatus.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        lblStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatus.setText("Unkown");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(lblStatus)
                .addContainerGap(158, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Overview", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLearnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLearnActionPerformed
        if (lstTypes.getSelectedIndex() < 0) {
            return;
        }
        if (btnLearn.getText().equals("Learn")) {
            for (LinkKNN knn : knns) {
                knn.learnType((String) lstTypes.getSelectedValue());
            }
            btnLearn.setText("Stop");
        } else {
            for (LinkKNN knn : knns) {
                knn.stopLearning();
            }
            btnLearn.setText("Learn");
        }
    }//GEN-LAST:event_btnLearnActionPerformed

    private void btnForgetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForgetActionPerformed
        if (lstTypes.getSelectedIndex() < 0) {
            return;
        }
        for (LinkKNN knn : knns) {
            knn.forget((String) lstTypes.getSelectedValue());
        }
    }//GEN-LAST:event_btnForgetActionPerformed

    private void lstEstimateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstEstimateMouseClicked
        if ((LinkKNN) lstEstimate.getSelectedValue() != null) {
            if (evt.getClickCount() >= 2 && evt.getButton() == 1) {
                LinkKNN knn = (LinkKNN) lstEstimate.getSelectedValue();
                knn.startPloting();
            }
            if (evt.getButton() == 3) {
                atmfs.get((LinkKNN) lstEstimate.getSelectedValue()).registerFilter(new LinkPlot("ATMF"));
            }
        }
    }//GEN-LAST:event_lstEstimateMouseClicked

    private void btnRawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRawActionPerformed
        if ((LinkKNN) lstEstimate.getSelectedValue() != null) {

            Entry<Integer, Integer> tmp = links.get((LinkKNN) lstEstimate.getSelectedValue());
            linkFilter.registerLinkFilter(tmp.getKey(), tmp.getValue(), new LinkPlot("Raw"));
        }
    }//GEN-LAST:event_btnRawActionPerformed

    private void btnATMFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnATMFActionPerformed
        if ((LinkKNN) lstEstimate.getSelectedValue() != null) {
            atmfs.get((LinkKNN) lstEstimate.getSelectedValue()).registerFilter(new LinkPlot("ATMF"));
        }
    }//GEN-LAST:event_btnATMFActionPerformed

    private void btnKNNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKNNActionPerformed
        if ((LinkKNN) lstEstimate.getSelectedValue() != null) {
            LinkKNN knn = (LinkKNN) lstEstimate.getSelectedValue();
            knn.startPloting();
        }
    }//GEN-LAST:event_btnKNNActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnATMF;
    private javax.swing.JButton btnForget;
    private javax.swing.JButton btnKNN;
    private javax.swing.JButton btnLearn;
    private javax.swing.JButton btnRaw;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JList lstEstimate;
    private javax.swing.JList lstTypes;
    // End of variables declaration//GEN-END:variables

    public void updateKNN(LinkKNN aThis) {
        DefaultListModel<LinkKNN> dlm = (DefaultListModel<LinkKNN>) lstEstimate.getModel();
        dlm.set(dlm.indexOf(aThis), aThis);
        
    }

    @Override
    public void recvLinkInfo(LinkInfo ls) {
        data.addFirst(ls);
        
        if(data.size() >= knns.size() ){
            
            HashMap<String, Integer> counter = new HashMap<String, Integer>();
            for (LinkInfo li : data) {
                String key = (String)li.getMetaData().get("KNNState");
                if (counter.containsKey(key)) {
                    int tmp = counter.get(key);
                    tmp += 1;
                    counter.put(key, tmp);
                } else {
                    counter.put(key, 1);
                }
            }
            List<Entry<String, Integer>> hits = new ArrayList<Entry<String, Integer>>(counter.entrySet());
            Collections.sort(hits, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                    if (o1.getValue() > o2.getValue()) {
                        return -1;
                    } else if (o1.getValue() < o2.getValue()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
            lblStatus.setText( hits.get(0).getKey() + " " + ((float) (counter.get(hits.get(0).getKey())) / (float) knns.size()) );
            data.removeLast();
        }
    }
}
