/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplegui;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.*;

/**
 *
 * @author DELL
 */

public class TableActionCellRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp =  super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); 
        
        
        PanelAction action = new PanelAction();
        action.setBackground(comp.getBackground());
        return action;
    }
    
}
