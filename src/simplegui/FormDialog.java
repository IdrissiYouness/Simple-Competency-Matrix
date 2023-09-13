/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplegui;
import javax.swing.*;  
import java.awt.event.*;  
/**
 *
 * @author DELL
 */
public class FormDialog {
    
    static JDialog d;  
    public FormDialog(){
        JFrame f= new JFrame();
        d = new JDialog( f, "Dialog Example", true);  
        JButton b = new JButton ("OK");  
        b.addActionListener ((ActionEvent e) -> {
            FormDialog.d.setVisible(false);  
        });  
        d.add( new JLabel ("Click button to continue."));  
        d.add(b);   
        d.setSize(300,300);    
        d.setVisible(true);
    } 
    
}
