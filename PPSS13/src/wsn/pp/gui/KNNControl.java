/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wsn.pp.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import wsn.pp.data.Datasource;
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
        txtPattern = new javax.swing.JTextField();
        btnRecord = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnPlayback = new javax.swing.JButton();
        txtFile = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnPlay = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstEstimate = new javax.swing.JList();
        btnRaw = new javax.swing.JButton();
        btnKNN = new javax.swing.JButton();
        btnATMF = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jSpinner1 = new javax.swing.JSpinner();
        jSpinner2 = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lstTypes.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Empty", "Standing-1", "Standing-2", "Standing-3", "Moving" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstTypes.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstTypesValueChanged(evt);
            }
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

        txtPattern.setText("Test");

        btnRecord.setText("Learn and Save to File");
        btnRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecordActionPerformed(evt);
            }
        });

        jLabel4.setText("Pattern Name");

        btnPlayback.setText("Learn from File");
        btnPlayback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlaybackActionPerformed(evt);
            }
        });

        jLabel5.setText("Filename");

        btnPlay.setText("Just playback");
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnForget, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(btnLearn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPlayback, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRecord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPattern, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtFile))
                    .addComponent(btnPlay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(btnPlayback)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRecord)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPlay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnForget)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPattern, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
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
                .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(lblStatus)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Overview", jPanel3);

        jSpinner1.setValue(LinkPlot.maxy);
        jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });

        jSpinner2.setValue(LinkPlot.miny);
        jSpinner2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner2StateChanged(evt);
            }
        });

        jLabel1.setText("Line Plot");

        jLabel2.setText("y min");

        jLabel3.setText("y max");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addComponent(jSpinner1))
                .addContainerGap(304, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(281, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Options", jPanel4);

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
        try {
            learn(false);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KNNControl.class.getName()).log(Level.SEVERE, null, ex);
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

    private void jSpinner2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner2StateChanged
        LinkPlot.miny = (Integer)jSpinner2.getValue();
    }//GEN-LAST:event_jSpinner2StateChanged

    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged
        LinkPlot.maxy = (Integer)jSpinner1.getValue();
    }//GEN-LAST:event_jSpinner1StateChanged

    private void lstTypesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstTypesValueChanged
        txtFile.setText(txtPattern.getText() + "-" + lstTypes.getSelectedValue() + ".dat");
    }//GEN-LAST:event_lstTypesValueChanged

    private void btnRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecordActionPerformed
        try {
            learn(true);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KNNControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRecordActionPerformed

    private void btnPlaybackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlaybackActionPerformed
        File file = new File( "data/" + txtFile.getText() );
        if( file.exists() ){
            for (LinkKNN knn : knns) {
                knn.learnType((String) lstTypes.getSelectedValue());
            }
            Datasource.getInstance().playRecording(file);
            for (LinkKNN knn : knns) {
                knn.stopLearning();
            }
        }
    }//GEN-LAST:event_btnPlaybackActionPerformed

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        File file = new File( "data/" + txtFile.getText() );
        Datasource.getInstance().playRecording(file);
    }//GEN-LAST:event_btnPlayActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnATMF;
    private javax.swing.JButton btnForget;
    private javax.swing.JButton btnKNN;
    private javax.swing.JButton btnLearn;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnPlayback;
    private javax.swing.JButton btnRaw;
    private javax.swing.JButton btnRecord;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JList lstEstimate;
    private javax.swing.JList lstTypes;
    private javax.swing.JTextField txtFile;
    private javax.swing.JTextField txtPattern;
    // End of variables declaration//GEN-END:variables

    public void updateKNN(LinkKNN aThis) {
        DefaultListModel dlm = (DefaultListModel) lstEstimate.getModel();
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

    private boolean learn(boolean record) throws FileNotFoundException {
        File file = new File( "data/" + txtFile.getText() );
        if (lstTypes.getSelectedIndex() < 0) {
            return true;
        }
        if (btnLearn.getText().equals("Learn")) {
            for (LinkKNN knn : knns) {
                knn.learnType((String) lstTypes.getSelectedValue());
            }
            if(record){
                Datasource.getInstance().startRecording(file);
            }
            btnLearn.setText("Stop");
            btnPlayback.setEnabled(false);
            btnRecord.setEnabled(false);
        } else {
            for (LinkKNN knn : knns) {
                knn.stopLearning();
            }
            Datasource.getInstance().stopRecording();
            btnLearn.setText("Learn");
            btnPlayback.setEnabled(true);
            btnRecord.setEnabled(true);
        }
        return false;
    }
}
