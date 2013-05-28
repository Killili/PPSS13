package wsn.pp.gui;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import wsn.pp.data.Datasource;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author wcu
 */
public class ConfigView extends JFrame implements ActionListener{
Datasource datasource;
JTextField tfId;
JSlider sInterval;
JSlider sPower;

final double minPower = 1,maxPower = 31,minInterval = 30,maxInterval = 5000;

    public ConfigView(Datasource datasource){
    super();
    
    this.datasource = datasource;
    
    init();
    
    }
    
    private void init()
    {

        this.setSize(500, 100);
        this.setLayout(new GridLayout(3,2));
        this.setTitle("Node Config");
        
        JLabel lNodeId = new JLabel("node ID:");
        tfId = new JTextField();
        tfId.setText("1");
        
        JLabel lInterval = new JLabel("Interval");
        sInterval = new JSlider((int)minInterval, (int)maxInterval);
        sInterval.setName("interval");
        
        
        JLabel lPower = new JLabel("Power");
        sPower = new JSlider((int)minPower,(int)maxPower);
        sPower.setName("power");
        
        JButton btnSend = new JButton("send");
        btnSend.addActionListener(this);
        
        this.add(lNodeId);
        this.add(tfId);
        this.add(lInterval);
        this.add(sInterval);
        this.add(lPower);
        this.add(sPower);
        this.add(btnSend);
    }

    public void actionPerformed(ActionEvent ae) 
    {
        datasource.sendConfig(Integer.parseInt(tfId.getText()), sInterval.getValue(), sPower.getValue());
    }
    
}
