/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplegui;

import java.awt.*;
import java.awt.Component;
import javax.swing.*;

/**
 *
 * @author DELL
 */
public class TableActionCellEditor extends DefaultCellEditor{
    
    
    private TableActionEvent event;
    public TableActionCellEditor(TableActionEvent event){
        super(new JCheckBox());
        this.event = event;
    }
    

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
       
        PanelAction action =  new PanelAction();
        action.initEvent(event, row);
        action.setBackground(new Color(248,64,24));
        return action;
    }
    
    
}
