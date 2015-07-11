package fapra.magenta.heatmap;

import java.util.Hashtable;
import java.util.List;

import javax.swing.JFrame;

import fapra.magenta.heatmap.data.CombinedRow;
import fapra.magenta.heatmap.data.Filter;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import lib.HeatMap;

import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame {

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(794, 960);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        final JLabel lblNewLabel = new JLabel("");
        getContentPane().add(lblNewLabel, BorderLayout.CENTER);
        lblNewLabel.setSize(CombinedRow.pixelX/2, CombinedRow.pixelY/2);
        
        JPanel Configuration = new JPanel();
        getContentPane().add(Configuration, BorderLayout.NORTH);

        JLabel lblStartgrid = new JLabel("startGrid");
        Configuration.add(lblStartgrid);

        txtGridx = new JTextField();
        txtGridx.setText("1");
        Configuration.add(txtGridx);
        txtGridx.setColumns(10);

        txtGridy = new JTextField();
        txtGridy.setText("1");
        Configuration.add(txtGridy);
        txtGridy.setColumns(10);

        JButton btnRedraw = new JButton("Redraw");
        btnRedraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                float multiplier = ((float) slider.getValue()) / 10.0f;
                List<CombinedRow> filteredRows = Filter.queryRows(
                        Integer.parseInt(txtGridx.getText()),
                        Integer.parseInt(txtGridy.getText()),
                        Integer.parseInt(txtEndgridx.getText()),
                        Integer.parseInt(txtEndgridy.getText()));
                System.out.println("Multiplier=" + multiplier);
                Filter.normalizeRows(filteredRows);
                List<Point> convertedRows = Filter.convertPoints(filteredRows);
                System.out.println("Rows=" + convertedRows.size());
                HeatMap hm = new HeatMap(convertedRows, CombinedRow.pixelX, CombinedRow.pixelY);
                BufferedImage img = hm.createHeatMap(multiplier, HeatMap.GRADIENT_HEAT_COLORS);
                Image dimg = img.getScaledInstance(CombinedRow.pixelX/2, CombinedRow.pixelY/2,
                        Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(dimg);
                lblNewLabel.setSize(CombinedRow.pixelX/2, CombinedRow.pixelY/2);
                lblNewLabel.setIcon(icon);
                MainFrame.this.pack();
                //lblHeatmap.invalidate();
                
            }
        });

        JLabel lblEndgrid = new JLabel("endGrid");
        Configuration.add(lblEndgrid);

        txtEndgridx = new JTextField();
        txtEndgridx.setText("0");
        Configuration.add(txtEndgridx);
        txtEndgridx.setColumns(10);

        txtEndgridy = new JTextField();
        txtEndgridy.setText("5");
        Configuration.add(txtEndgridy);
        txtEndgridy.setColumns(10);
        
        JLabel lblDirection = new JLabel("direction");
        Configuration.add(lblDirection);
        
        txtDir = new JTextField();
        txtDir.setText("3");
        Configuration.add(txtDir);
        txtDir.setColumns(10);
        Configuration.add(btnRedraw);
        
        panel = new JPanel();
        getContentPane().add(panel, BorderLayout.WEST);
        panel.setLayout(new BorderLayout(0, 0));
        
        slider = new JSlider();
        panel.add(slider);
        slider.setMinorTickSpacing(10);
        slider.setMaximum(50);
        slider.setValue(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setOrientation(SwingConstants.VERTICAL);
        
        lblValue = new JLabel("value");
        panel.add(lblValue, BorderLayout.NORTH);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                lblValue.setText("" + (slider.getValue() / 10.0f));
            }
        });
        lblValue.setText("" + (slider.getValue() / 10.0f));
        pack();
    }

    private static final long serialVersionUID = 2269971701250845501L;
    private JTextField txtGridx;
    private JTextField txtGridy;
    private JTextField txtEndgridx;
    private JTextField txtEndgridy;
    private JTextField txtDir;
    private JSlider slider;
    private JPanel panel;
    private JLabel lblValue;

    public JSlider getSlider() {
        return slider;
    }
}
