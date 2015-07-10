package fapra.magenta.heatmap;

import java.util.ArrayList;
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

public class MainFrame extends JFrame {

    protected final List<CombinedRow> rows;

    public MainFrame(List<CombinedRow> rows) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(794, 960);
        this.rows = rows;
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
                float multiplier = 0.5f;
                List<Point> points = new ArrayList<Point>();
                List<CombinedRow> filteredRows = Filter.filterRows(
                        MainFrame.this.rows, 
                        Integer.parseInt(txtGridx.getText()),
                        Integer.parseInt(txtGridy.getText()),
                        Integer.parseInt(txtEndgridx.getText()),
                        Integer.parseInt(txtEndgridy.getText()),
                        Integer.parseInt(txtDir.getText()));
                List<Point> convertedRows = Filter.convertPoints(filteredRows);
                System.out.println("Rows=" + convertedRows.size());
                HeatMap hm = new HeatMap(convertedRows, CombinedRow.pixelX, CombinedRow.pixelY);
                BufferedImage img = hm.createHeatMap(multiplier);
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
        pack();
    }

    private static final long serialVersionUID = 2269971701250845501L;
    private JTextField txtGridx;
    private JTextField txtGridy;
    private JTextField txtEndgridx;
    private JTextField txtEndgridy;
    private JTextField txtDir;

}
