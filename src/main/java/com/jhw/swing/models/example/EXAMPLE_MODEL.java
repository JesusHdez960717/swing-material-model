/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jhw.swing.models.example;

import com.jhw.modules.default_config.DefaultConfigSwingModule;
import com.jhw.swing.material.standards.MaterialColors;
import com.jhw.swing.ui.MaterialLookAndFeel;
import java.awt.BorderLayout;
import javax.swing.UIManager;

/**
 *
 * @author Jesus Hernandez Barrios (jhernandezb96@gmail.com)
 */
public class EXAMPLE_MODEL extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public EXAMPLE_MODEL() {
        initComponents();

        jPanel1.setBackground(MaterialColors.BLUE_50);
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(new CargoDetailView(), BorderLayout.WEST);
        jPanel1.add(new CargoPanel());
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        this.setContentPane(jPanel1);

        pack();

        this.setExtendedState(MAXIMIZED_BOTH);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (Exception e) {
        }

        DefaultConfigSwingModule.init().register(null);
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EXAMPLE_MODEL().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}