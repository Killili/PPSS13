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
import sun.java2d.pipe.SpanShapeRenderer;
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
        btnTest = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstEstimate = new javax.swing.JList();
        btnRaw = new javax.swing.JButton();
        btnKNN = new javax.swing.JButton();
        btnATMF = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblConfStatus = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblStateWeighted = new javax.swing.JLabel();
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

        btnLearn.setText("Learn from Live Data");
        btnLearn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLearnActionPerformed(evt);
            }
        });

        btnForget.setText("Forget Dataset");
        btnForget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForgetActionPerformed(evt);
            }
        });

        txtPattern.setText("Flur");

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

        btnTest.setText("Test against File and assign weights");
        btnTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTestActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnForget, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLearn, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRecord, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPlayback, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPattern, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFile))
                            .addComponent(btnPlay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTest, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLearn)
                    .addComponent(jLabel4)
                    .addComponent(txtPattern, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnForget)
                    .addComponent(btnPlayback))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTest)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRecord)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Learning", jPanel1);

        lstEstimate.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRaw)
                    .addComponent(btnKNN)
                    .addComponent(btnATMF))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Per Link State", jPanel2);

        lblStatus.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        lblStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStatus.setText("Unkown");

        jLabel6.setText("State by unwiegthed Mayority");

        jLabel7.setText("State by confidence wiegthed Mayority ( c )");

        lblConfStatus.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        lblConfStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConfStatus.setText("Unkown");

        jLabel8.setText("State by confidence wiegthed by Error rate for that specific State ( c * w )");

        lblStateWeighted.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        lblStateWeighted.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStateWeighted.setText("Unkown");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
                    .addComponent(lblConfStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblStateWeighted, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblConfStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStateWeighted)
                .addContainerGap(123, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("System State", jPanel3);

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
                .addContainerGap(553, Short.MAX_VALUE))
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
                .addContainerGap(303, Short.MAX_VALUE))
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
        }
    }//GEN-LAST:event_lstEstimateMouseClicked

    private void btnRawActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRawActionPerformed
        if ((LinkKNN) lstEstimate.getSelectedValue() != null) {
            Entry<Integer, Integer> tmp = links.get((LinkKNN) lstEstimate.getSelectedValue());
            linkFilter.registerLinkFilter(tmp.getKey(), tmp.getValue(), new LinkPlot("Raw",linkFilter));
        }
    }//GEN-LAST:event_btnRawActionPerformed

    private void btnATMFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnATMFActionPerformed
        if ((LinkKNN) lstEstimate.getSelectedValue() != null) {
            LinkATMFFilter atmf = atmfs.get((LinkKNN) lstEstimate.getSelectedValue());
            atmf.registerFilter(new LinkPlot("ATMF",atmf));
        }
    }//GEN-LAST:event_btnATMFActionPerformed

    private void btnKNNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKNNActionPerformed
        if ((LinkKNN) lstEstimate.getSelectedValue() != null) {
            LinkKNN knn = (LinkKNN) lstEstimate.getSelectedValue();
            knn.startPloting();
        }
    }//GEN-LAST:event_btnKNNActionPerformed

    private void jSpinner2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner2StateChanged
        LinkPlot.miny = (Integer) jSpinner2.getValue();
    }//GEN-LAST:event_jSpinner2StateChanged

    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged
        LinkPlot.maxy = (Integer) jSpinner1.getValue();
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
        File file = new File("data/" + txtFile.getText());
        if (file.exists()) {
            for (LinkKNN knn : knns) {
                knn.learnType((String) lstTypes.getSelectedValue());
            }
            Datasource.getInstance().playRecording(file);
            for (LinkKNN knn : knns) {
                knn.stopLearning();
            }
        }
        if (lstTypes.getSelectedIndex() < lstTypes.getModel().getSize()) {
            lstTypes.setSelectedIndex(lstTypes.getSelectedIndex() + 1);
        }
    }//GEN-LAST:event_btnPlaybackActionPerformed

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        File file = new File("data/" + txtFile.getText());
        Datasource.getInstance().playRecording(file);
    }//GEN-LAST:event_btnPlayActionPerformed

    private void btnTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTestActionPerformed
        File file = new File("data/" + txtFile.getText());
        if (file.exists()) {
            for (LinkKNN knn : knns) {
                knn.testType((String) lstTypes.getSelectedValue());
            }
            Datasource.getInstance().playRecording(file);
            for (LinkKNN knn : knns) {
                knn.stopTesting();
            }
        }
        if (lstTypes.getSelectedIndex() < lstTypes.getModel().getSize()) {
            lstTypes.setSelectedIndex(lstTypes.getSelectedIndex() + 1);
        }
    }//GEN-LAST:event_btnTestActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnATMF;
    private javax.swing.JButton btnForget;
    private javax.swing.JButton btnKNN;
    private javax.swing.JButton btnLearn;
    private javax.swing.JButton btnPlay;
    private javax.swing.JButton btnPlayback;
    private javax.swing.JButton btnRaw;
    private javax.swing.JButton btnRecord;
    private javax.swing.JButton btnTest;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblConfStatus;
    private javax.swing.JLabel lblStateWeighted;
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
        if (data.contains(ls)) {
            data.remove(ls);
        }
        data.add(ls);

        if ( data.size() >= knns.size() ) {

            HashMap<String, Float> counter = new HashMap<String, Float>();
            for (LinkInfo li : data) {
                String key = (String) li.getMetaData().get("KNNState");
                if(key == null) return;
                if (counter.containsKey(key)) {
                    float tmp = counter.get(key);
                    tmp += 1;
                    counter.put(key, tmp);
                } else {
                    counter.put(key, 1f);
                }
            }
            Entry<String, Float> bestHit = getBestHit(counter);

            lblStatus.setText(bestHit.getKey() + " " + (float)(bestHit.getValue()));
            
            counter.clear();
            for (LinkInfo li : data) {
                String key = (String) li.getMetaData().get("KNNState");
                if(key == null) return;
                if (counter.containsKey(key)) {
                    Float tmp = counter.get(key);
                    tmp += (Float)li.getMetaData().get("KNNConfidence");
                    counter.put(key, tmp);
                } else {
                    counter.put(key,(Float)li.getMetaData().get("KNNConfidence"));
                }
            }
            bestHit = getBestHit(counter);
            lblConfStatus.setText(bestHit.getKey() + " " + (float)(bestHit.getValue()));
            
            for (LinkInfo li : data) {
                String key = (String) li.getMetaData().get("KNNState");
                if(key == null) return;
                if((Float)li.getMetaData().get("KNNStateWeigth") == null ) return;
                if (counter.containsKey(key)) {
                    Float tmp = counter.get(key);
                    tmp += (Float)li.getMetaData().get("KNNConfidence") * (Float)li.getMetaData().get("KNNStateWeigth");
                    counter.put(key, tmp);
                } else {
                    counter.put(key,(Float)li.getMetaData().get("KNNConfidence") * (Float)li.getMetaData().get("KNNStateWeigth"));
                }
            }
            bestHit = getBestHit(counter);
            lblStateWeighted.setText(bestHit.getKey() + " " + (float)(bestHit.getValue()));
        }
    }

    private Entry<String, Float> getBestHit(HashMap<String, Float> counter) {
        List<Entry<String, Float>> hits = new ArrayList<Entry<String, Float>>(counter.entrySet());
        Collections.sort(hits, new Comparator<Map.Entry<String, Float>>() {
            @Override
            public int compare(Entry<String, Float> o1, Entry<String, Float> o2) {
                if (o1.getValue() > o2.getValue()) {
                    return -1;
                } else if (o1.getValue() < o2.getValue()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return hits.get(0);
    }

    private boolean learn(boolean record) throws FileNotFoundException {
        File file = new File("data/" + txtFile.getText());
        if (lstTypes.getSelectedIndex() < 0) {
            return true;
        }
        if (btnLearn.getText().equals("Learn")) {
            for (LinkKNN knn : knns) {
                knn.learnType((String) lstTypes.getSelectedValue());
            }
            if (record) {
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
