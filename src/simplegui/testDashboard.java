/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package simplegui;
import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.text.MessageFormat;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


/**
 *
 * @author DELL
 */

public class testDashboard extends JFrame {

    /**
     * Creates new form testDashboard
     * @param namewelcome
     */
    public  int QualifId;
    private Connection con;
    public testDashboard(String namewelcome) {
        initComponents();
        Component[] components = new Component[]{new paneltest(1), new paneltest(2), new paneltest(3),new paneltest(4),new paneltest(5),new paneltest(6)};

        panelSlider.setSliderComponent(components);

        progressIndicator.initSlider(panelSlider);
        
        DatabaseConnection dbc = DatabaseConnection.getDatabaseConnection();
        con=dbc.getConnection();
        admin_label.setText(namewelcome);
        admin_label.setForeground(new Color(248,64,24));
        
        show_qualifications();
        show_operateurs();
        show_suivi();
        fill_matrice();
        
        
        
        
       
        
        
        
        
        TableActionEvent qualifevent = new TableActionEvent(){
            @Override
            public void onEdit(int row) {
                ModifQualification.setVisible(true);      
            }
               
            @Override
            public void onDelete(int row) {
                
                QualifId = Integer.parseInt(qualification_jtable.getValueAt(qualification_jtable.getSelectedRow(),0).toString());
                if(qualification_jtable.isEditing()){
                    qualification_jtable.getCellEditor().stopCellEditing();
                }
                
                DefaultTableModel model = (DefaultTableModel) qualification_jtable.getModel();      
                model.removeRow(row);
                
                 try{
                   Statement stmt = con.createStatement();
                   //stmt.execute("delete from qualifications where IDqualification="+QualifId);
                   stmt.execute("UPDATE qualifications SET archived = 1 WHERE IDqualification="+QualifId);
               }catch(Exception ex){
                   System.out.println(ex);
               }
            }
            
            
            @Override
            public void onView(int row) {
                //nothing
            }
            
        };
        
        TableActionEvent operevent = new TableActionEvent(){
            @Override
            public void onEdit(int row) {
                
                int current_matricule = Integer.parseInt(operateurs_jtable.getValueAt(operateurs_jtable.getSelectedRow(),1).toString());
                matricule_txt1.setText(current_matricule+"");
                
                String current_nom = operateurs_jtable.getValueAt(operateurs_jtable.getSelectedRow(),2).toString();
                nom_txt1.setText(current_nom);
                
                String current_prenom = operateurs_jtable.getValueAt(operateurs_jtable.getSelectedRow(),3).toString();
                prenom_txt1.setText(current_prenom);
                
                String current_date = operateurs_jtable.getValueAt(operateurs_jtable.getSelectedRow(),4).toString();
                datedembauche_txt1.setText(current_date);
                
                 String current_equipe = operateurs_jtable.getValueAt(operateurs_jtable.getSelectedRow(),5).toString();
                equipe_txt1.setText(current_equipe);
                
                
                
                modifOperateurDialog.setVisible(true);
                
            }

            @Override
            public void onDelete(int row) {
               int OperId = Integer.parseInt(operateurs_jtable.getValueAt(operateurs_jtable.getSelectedRow(),0).toString());
                if(operateurs_jtable.isEditing()){
                    operateurs_jtable.getCellEditor().stopCellEditing();
                }
                
                DefaultTableModel model = (DefaultTableModel) operateurs_jtable.getModel();      
                model.removeRow(row);
                
                 try{
                   Statement stmt = con.createStatement();
                   //stmt.execute("delete from qualifications where IDqualification="+QualifId);
                   stmt.execute("DELETE from operateurs where IDoperateur="+OperId);
               }catch(Exception ex){
                   System.out.println(ex);
               }
            }

            @Override
            public void onView(int row) {
                //nothing
            }
            
        };
        
        TableActionEvent suivievent = new TableActionEvent(){
            @Override
            public void onEdit(int row) {
                
               modifsuivi.setVisible(true);  
                if (suivi_jtable.isEditing()) {
                    suivi_jtable.getCellEditor().stopCellEditing();
                }
            
                
                
                /*
                String current_nom = operateurs_jtable.getValueAt(operateurs_jtable.getSelectedRow(),2).toString();
                nom_txt1.setText(current_nom);
                
                String current_prenom = operateurs_jtable.getValueAt(operateurs_jtable.getSelectedRow(),3).toString();
                prenom_txt1.setText(current_prenom);
                
                String current_date = operateurs_jtable.getValueAt(operateurs_jtable.getSelectedRow(),4).toString();
                datedembauche_txt1.setText(current_date);
                
                 String current_equipe = operateurs_jtable.getValueAt(operateurs_jtable.getSelectedRow(),5).toString();
                equipe_txt1.setText(current_equipe);
                */
            }

            @Override
            public void onDelete(int row) {
               System.out.println("deletesuivi");
            }
            
            
            @Override
            public void onView(int row) {
                /*
                int phases_lengh = 7;
                
                for(int i=1;i<phases_lengh-1;i++){
                    phases_tab.setEnabledAt(i, false);
                }
                */
                //phases_jdialog.setVisible(true);
                phases_jdialog1.setVisible(true);
                
            }
            
        };
        
        qualification_jtable.getColumnModel().getColumn(2).setCellRenderer(new TableActionCellRender());
        qualification_jtable.getColumnModel().getColumn(2).setCellEditor(new TableActionCellEditor(qualifevent));
        
        operateurs_jtable.getColumnModel().getColumn(6).setCellRenderer(new TableActionCellRender());
        operateurs_jtable.getColumnModel().getColumn(6).setCellEditor(new TableActionCellEditor(operevent));
        
        
        
        suivi_jtable.getColumnModel().getColumn(5).setCellRenderer(new TableActionCellRenderSuivi());
        suivi_jtable.getColumnModel().getColumn(5).setCellEditor(new TableActionCellEditorSuivi(suivievent));
                
        formateurs_combo.setSelectedItem(null);
        
        
        /*
        
         JTableHeader tableHeader = matrice_jtable.getTableHeader();
         tableHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
             @Override
             public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                 // Call the default renderer for regular cells to maintain the regular row appearance
                 return table.getDefaultRenderer(Object.class).getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
             }
             
              @Override
               public Dimension getPreferredSize() {
                   Dimension dimension = super.getPreferredSize();
                   dimension.height = 35; 
                   return dimension;
               }
         });
         
         
         
        tableHeader.setUI(new javax.swing.plaf.basic.BasicTableHeaderUI());
        tableHeader.setPreferredSize(new Dimension(tableHeader.getPreferredSize().width, 35));
        */
        
        JTableHeader tableHeader = matrice_jtable.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(tableHeader.getPreferredSize().width, 35));
        
        Color headerColor = new Color(254, 230, 225);
        tableHeader.setBackground(headerColor);
        
        
       

        


    }
    
    public ArrayList<Qualifications> Qua_list() {
        
           ArrayList<Qualifications> Qua_list = new ArrayList<>();
       try{
            String query_1 = "Select * from qualifications where archived = 0";				
            Statement st= con.createStatement();		
            ResultSet rs=st.executeQuery(query_1);
            
            Qualifications qualification;

            
            while(rs.next()){
                qualification = new Qualifications(rs.getInt("IDqualification"),rs.getString("Nom"));
                Qua_list.add( qualification);
            }

	}catch(Exception w1){
	    System.out.println(w1);	
	}
   
      return Qua_list;
    }
    
    public void show_qualifications(){
        
        ArrayList<Qualifications> list = Qua_list();
        DefaultTableModel model = (DefaultTableModel)qualification_jtable.getModel();
        Object[] row = new Object[6];
        for(int i=0;i<list.size();i++){
            row[0] = list.get(i).getId_qualification();
            row[1] = list.get(i).getNom();
            model.addRow(row);
        }
    }
    
    
    public ArrayList<Operateurs> Oper_list() {
        
           ArrayList<Operateurs> Qua_list = new ArrayList<>();
       try{
            String query_1 = "Select * from operateurs";				
            Statement st= con.createStatement();		
            ResultSet rs=st.executeQuery(query_1);
            
            Operateurs operateur;

            
            while(rs.next()){
                operateur = new Operateurs(rs.getInt("IDoperateur"),rs.getInt("Matricules"),rs.getString("Nom"),rs.getString("Prenom"),rs.getString("Datedembauche"),rs.getString("Equipe"));
                Qua_list.add( operateur);
            }

	}catch(Exception w1){
	    System.out.println(w1);	
	}
   
      return Qua_list;
    }
    
    
    public void show_operateurs(){
        
        ArrayList<Operateurs> list = Oper_list();
        DefaultTableModel model = (DefaultTableModel)operateurs_jtable.getModel();
        Object[] row = new Object[6];
        for(int i=0;i<list.size();i++){
            row[0] = list.get(i).getId_operateur();
            row[1] = list.get(i).getMatricules();
            row[2] = list.get(i).getNom();
            row[3] = list.get(i).getPrenom();
            row[4] = list.get(i).getDatedembauche();
            row[5] = list.get(i).getEquipe();
            model.addRow(row);
        }
    }
    
    public void show_suivi(){
        
        try{
            String join_query = "SELECT s.IDsuivi, o.Matricules AS operateur_matricule,o.Nom,o.Prenom, q.Nom AS qualification_nom, s.statue "
             + "FROM suivi s "
             + "JOIN operateurs o ON s.IDoperateur = o.IDoperateur "
             + "JOIN qualifications q ON s.IDqualification = q.IDqualification";
            
            Statement st= con.createStatement();
            ResultSet rs = st.executeQuery(join_query);
            
            DefaultTableModel suivi_model = (DefaultTableModel)suivi_jtable.getModel();
            
            while (rs.next()) {
                Object[] row = {rs.getInt("IDsuivi"), rs.getString("operateur_matricule"),rs.getString("Nom")+" "+rs.getString("Prenom"),rs.getString("qualification_nom"), rs.getString("statue")};
                suivi_model.addRow(row);
            }
                
        }catch(Exception ex){
            System.out.println(ex);
        }
        
    }
    
    
    public void fill_matrice(){
        
         try{
             
            String query_1 = "SELECT * from qualifications";
            String query_2 = "SELECT * from operateurs";
            
            Statement st= con.createStatement();
            
            ResultSet rs1 = st.executeQuery(query_1);
            
            DefaultTableModel matrice_model = (DefaultTableModel)matrice_jtable.getModel();
            matrice_model.setRowCount(0);
            matrice_model.setColumnCount(0);
            matrice_model.addColumn("");
            matrice_model.addColumn("");
            while (rs1.next()) {
                String columnName = rs1.getString("Nom");
                matrice_model.addColumn(columnName);
            }
            
            ResultSet rs2 = st.executeQuery(query_2);
            
            while (rs2.next()) { 
                
                int matricule = rs2.getInt("Matricules");
                String nom = rs2.getString("Nom");
                String prenom = rs2.getString("Prenom");
                Object[] row = new Object[matrice_model.getColumnCount()];
                row[0] = matricule;
                row[1] = nom +" "+prenom;
                
                //Object[] row = {rs2.getInt("Matricules")};
                matrice_model.addRow(row);

            }
            
            /*
             
            int rowCount = matrice_jtable.getRowCount();
            ArrayList<Object> values;
            values = new ArrayList<>();

            for (int i = 0; i < rowCount; i++) {
                Object value = matrice_jtable.getValueAt(i, 0); // Get value at column index 0 (first column)
                values.add(value);
            }

            // Access the values retrieved from the first column
            for (Object value : values) {
                System.out.println(value);
            }
            */
            
       
            
            

            /*
            for (int rowIndex = 0; rowIndex < matrice_jtable.getRowCount(); rowIndex++) {
                int matricule = (int) matrice_jtable.getValueAt(rowIndex, matriculeColumnIndex);


                for (int colIndex = qualificationStartIndex; colIndex < matrice_jtable.getColumnCount(); colIndex++) {
                    String qualification = matrice_jtable.getColumnName(colIndex);
                    
                    
                     
                     String join_query = "SELECT s.IDsuivi, o.Matricules AS operateur_matricule, o.Nom, o.Prenom, q.Nom AS qualification_nom, s.statue "
                    + "FROM suivi s "
                    + "JOIN operateurs o ON s.IDoperateur = o.IDoperateur "
                    + "JOIN qualifications q ON s.IDqualification = q.IDqualification "
                    + "WHERE q.Nom = '"+qualification+"' AND o.Matricules = '"+matricule+"'";


                    //String query = "SELECT Statue FROM suivi WHERE idqualification = '" + qualification + "' AND idoperateur = '" + matricule + "'";

                    try (Statement statement = con.createStatement();
                         ResultSet resultSet = statement.executeQuery(join_query)) {
                        if (resultSet.next()) {
                            String statue = resultSet.getString("statue");
                            if (statue.equals("F")) {
                                matrice_jtable.setValueAt("F", rowIndex, colIndex);
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            */
            
            int matriculeColumnIndex = 0; 
            int qualificationStartIndex = 1;          
 
            Map<String, String> qualificationStatusMap = new HashMap<>();

            String query = "SELECT o.Matricules, q.Nom, s.Statue " +
                    "FROM suivi s " +
                    "JOIN operateurs o ON s.IDoperateur = o.IDoperateur " +
                    "JOIN qualifications q ON s.IDqualification = q.IDqualification";
            try (Statement statement = con.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    String matricule = resultSet.getString("Matricules");
                    String qualification = resultSet.getString("Nom");
                    String status = resultSet.getString("Statue");
                    qualificationStatusMap.put(matricule + "_" + qualification, status);
                }
            }

            for (int rowIndex = 0; rowIndex < matrice_jtable.getRowCount(); rowIndex++) {
                String matricule = matrice_jtable.getValueAt(rowIndex, matriculeColumnIndex).toString();

                for (int colIndex = qualificationStartIndex; colIndex < matrice_jtable.getColumnCount(); colIndex++) {
                    String qualification = matrice_jtable.getColumnName(colIndex);


                    String status = qualificationStatusMap.get(matricule + "_" + qualification);

                    if (status != null && status.equals("F")) {
                        matrice_jtable.setValueAt("F", rowIndex, colIndex);
                    }
                }
            }  
                
        }catch(Exception ex){
            System.out.println(ex);
        }
        
        TableColumnModel columnModel = matrice_jtable.getColumnModel();
        int columnIndex = 0; 
        int nextIndex = (columnIndex + 1) % columnModel.getColumnCount();
        TableColumn column = columnModel.getColumn(columnIndex);
        int columnWidth = 8;
        column.setPreferredWidth(columnWidth);
        
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                
                if (column == columnIndex) {
                    Font boldFont = rendererComponent.getFont().deriveFont(Font.BOLD);
                    rendererComponent.setFont(boldFont);
                }
                if (column == nextIndex) {
                    Font boldFont = rendererComponent.getFont().deriveFont(Font.BOLD);
                    rendererComponent.setFont(boldFont);
                }

                return rendererComponent;
            }
        };
        
        columnModel.getColumn(columnIndex).setCellRenderer(cellRenderer);
        columnModel.getColumn(nextIndex).setCellRenderer(cellRenderer);
        

       
    }
    
    public void refreshTable() {
        try {
            PreparedStatement stmt;
            stmt = con.prepareStatement("SELECT * FROM qualifications where archived = 0");
            ResultSet rs = stmt.executeQuery();

            // Create a new table model with the latest data
            DefaultTableModel model =  (DefaultTableModel)qualification_jtable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[] { rs.getInt("IDqualification"), rs.getString("Nom") });
            }

            // Set the new table model to the JTable
            qualification_jtable.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void refreshOperateursTable() {
        try {
            PreparedStatement stmt;
            stmt = con.prepareStatement("SELECT * FROM operateurs");
            ResultSet rs = stmt.executeQuery();

            // Create a new table model with the latest data
            DefaultTableModel model =  (DefaultTableModel)operateurs_jtable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[] { rs.getInt("IDoperateur"),rs.getInt("Matricules"),rs.getString("Nom"),rs.getString("Prenom"),rs.getString("Datedembauche"),rs.getString("Equipe") });
            }

            // Set the new table model to the JTable
            operateurs_jtable.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void refreshSuiviTable() {
        try {
            PreparedStatement stmt;
            
            String join_query = "SELECT s.IDsuivi, o.Matricules AS operateur_matricule,o.Nom,o.Prenom, q.Nom AS qualification_nom, s.statue "
             + "FROM suivi s "
             + "JOIN operateurs o ON s.IDoperateur = o.IDoperateur "
             + "JOIN qualifications q ON s.IDqualification = q.IDqualification";
            stmt = con.prepareStatement(join_query);
            ResultSet rs = stmt.executeQuery();

            // Create a new table model with the latest data
            DefaultTableModel model =  (DefaultTableModel)suivi_jtable.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[] { rs.getInt("IDsuivi"), rs.getString("operateur_matricule"),rs.getString("Nom")+" "+rs.getString("Prenom"),rs.getString("qualification_nom"), rs.getString("statue") });
            }

            // Set the new table model to the JTable
            suivi_jtable.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
         
    public void fill_qualif_combo(JComboBox combo){
        
        combo.removeAllItems();
         try{
             String fillquery = "SELECT * FROM qualifications where archived = 0";
             PreparedStatement statement = con.prepareStatement(fillquery);
             ResultSet rs = statement.executeQuery();
             
             
             while(rs.next()){
                 combo.addItem(rs.getString("Nom"));
             }
             
         }catch (SQLException ex) {
            ex.printStackTrace();
        }
         
        combo.setSelectedItem(null);
     }
     
    public void fill_oper_combo(JComboBox combo){
        
         combo.removeAllItems();
         try{
             String fillquery = "SELECT * FROM operateurs";
             PreparedStatement statement = con.prepareStatement(fillquery);
             ResultSet rs = statement.executeQuery();
             
             
             while(rs.next()){
                 combo.addItem(rs.getString("Matricules"));
             }
             
         }catch (SQLException ex) {
            ex.printStackTrace();
        }
         
        combo.setSelectedItem(null);
     }
      
      
    public  void resetAutoIncrement(String tableName) {
        try  {
            DatabaseConnection dbc = DatabaseConnection.getDatabaseConnection();
            con=dbc.getConnection();
            
            Statement statement = con.createStatement();

            String countQuery = "SELECT COUNT(*) AS count FROM " + tableName;
            ResultSet countResult = statement.executeQuery(countQuery);
            countResult.next();
            int recordCount = countResult.getInt("count");
            
            if (recordCount == 0) {
                String resetQuery = "ALTER TABLE " + tableName + " AUTO_INCREMENT = 1";
                statement.execute(resetQuery);
            }
            
            //retreive & display data

        } catch (SQLException e) {
            e.printStackTrace();
        }
       }
    
    public String getNomFromDatabase(int matricule) {
            String nom = null;
            String query = "SELECT Nom FROM operateurs WHERE Matricules = "+matricule;

            try (PreparedStatement statement = con.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    nom = resultSet.getString("Nom");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return nom;
        }
    public String getPrenomFromDatabase(int matricule) {
            String prenom = null;
            String query = "SELECT Prenom FROM operateurs WHERE Matricules = "+matricule;

            try (PreparedStatement statement = con.prepareStatement(query)) {
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    prenom = resultSet.getString("Prenom");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return prenom;
        }

    public int getOpIdFromDatabase(int selectedMatricule) {
            int selectedId = -1; 
            try {

                String query = "SELECT IDoperateur FROM operateurs WHERE matricules ="+selectedMatricule;
                PreparedStatement statement = con.prepareStatement(query);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    selectedId = rs.getInt("IDoperateur");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return selectedId;
        }


    public int getQualifIdFromDatabase(String selectedQualif) {
            int selectedId = -1; 
            try {

                String query = "SELECT IDqualification FROM qualifications WHERE Nom = ? ";
                PreparedStatement statement = con.prepareStatement(query);
                statement.setString(1, selectedQualif);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    selectedId = rs.getInt("IDqualification");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return selectedId;
        }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        SuiviDialog = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        qualifications_combo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        formateurs_combo = new javax.swing.JComboBox<>();
        operateurs_combo = new javax.swing.JComboBox<>();
        prenom_txtcheck = new javax.swing.JTextField();
        nom_txtcheck = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        ajoutQualification = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        qualification_textfield = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        ModifQualification = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        qualification_updated = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        ajoutOperateurDialog = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        prenom_txt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        matricule_txt = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        nom_txt = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        datedembauche_txt = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        equipe_txt = new javax.swing.JTextField();
        jDialog22 = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        phases_jdialog = new javax.swing.JDialog();
        jPanel11 = new javax.swing.JPanel();
        phases_tab = new javax.swing.JTabbedPane();
        panel_formation = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jButton18 = new javax.swing.JButton();
        panel_formationpara = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        modifOperateurDialog = new javax.swing.JDialog();
        jPanel12 = new javax.swing.JPanel();
        prenom_txt1 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        matricule_txt1 = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        nom_txt1 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        datedembauche_txt1 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        equipe_txt1 = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        modifsuivi = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jButton16 = new javax.swing.JButton();
        qualifications_combo2 = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jButton17 = new javax.swing.JButton();
        formateurs_combo2 = new javax.swing.JComboBox<>();
        operateurs_combo2 = new javax.swing.JComboBox<>();
        prenom_txtcheck2 = new javax.swing.JTextField();
        nom_txtcheck2 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        phases_jdialog1 = new javax.swing.JDialog();
        jPanel17 = new javax.swing.JPanel();
        progressIndicator = new raven.progressindicator.ProgressIndicator();
        panelSlider = new raven.progressindicator.PanelSlider();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btn_side1 = new javax.swing.JPanel();
        ind_1 = new javax.swing.JPanel();
        option_1 = new javax.swing.JLabel();
        btn_side2 = new javax.swing.JPanel();
        ind_2 = new javax.swing.JPanel();
        option_2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btn_side3 = new javax.swing.JPanel();
        ind_3 = new javax.swing.JPanel();
        option_3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        admin_label = new javax.swing.JLabel();
        logout_btn = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        optionsParentPanel = new javax.swing.JPanel();
        paneloption1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        search_ic2 = new javax.swing.JLabel();
        search_textField_qualif = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        qualification_jtable = new javax.swing.JTable();
        paneloption2 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        suivi_jtable = new javax.swing.JTable();
        search_textField_suivi = new javax.swing.JTextField();
        search_ic1 = new javax.swing.JLabel();
        paneloption3 = new javax.swing.JPanel();
        addopBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        operateurs_jtable = new javax.swing.JTable();
        search_textField_op = new javax.swing.JTextField();
        search_ic = new javax.swing.JLabel();
        matrice = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        matrice_jtable = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();

        jScrollPane1.setViewportView(jEditorPane1);

        SuiviDialog.setModal(true);
        SuiviDialog.setResizable(false);
        SuiviDialog.setSize(new java.awt.Dimension(400, 600));
        SuiviDialog.setLocationRelativeTo(null);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMinimumSize(new java.awt.Dimension(380, 600));
        jPanel3.setPreferredSize(new java.awt.Dimension(400, 600));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel4.setText("Prenom");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 380, -1, 30));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel5.setText("Formateur");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, -1, 30));

        jButton2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 490, -1, 40));

        qualifications_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qualifications_comboActionPerformed(evt);
            }
        });
        jPanel3.add(qualifications_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 270, 36));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel6.setText("Ajouter Suivi");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, -1, -1));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel7.setText("Titre de formation ");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, -1, 30));

        jButton3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton3.setText("Save");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 490, -1, 40));

        formateurs_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Formateur1", "Formateur2", "Formateur3" }));
        formateurs_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formateurs_comboActionPerformed(evt);
            }
        });
        jPanel3.add(formateurs_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 270, 36));

        operateurs_combo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                operateurs_comboItemStateChanged(evt);
            }
        });
        operateurs_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                operateurs_comboActionPerformed(evt);
            }
        });
        jPanel3.add(operateurs_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, 270, 36));

        prenom_txtcheck.setEnabled(false);
        prenom_txtcheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prenom_txtcheckActionPerformed(evt);
            }
        });
        jPanel3.add(prenom_txtcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, 270, 36));

        nom_txtcheck.setEnabled(false);
        jPanel3.add(nom_txtcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, 270, 36));

        jLabel22.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel22.setText("Operateur");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, 30));

        jLabel23.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel23.setText("Nom");
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, -1, 30));

        javax.swing.GroupLayout SuiviDialogLayout = new javax.swing.GroupLayout(SuiviDialog.getContentPane());
        SuiviDialog.getContentPane().setLayout(SuiviDialogLayout);
        SuiviDialogLayout.setHorizontalGroup(
            SuiviDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
        );
        SuiviDialogLayout.setVerticalGroup(
            SuiviDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        ajoutQualification.setModal(true);
        ajoutQualification.setResizable(false);
        ajoutQualification.setSize(new java.awt.Dimension(400, 300));
        ajoutQualification.setLocationRelativeTo(null);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(400, 300));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton4.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(248, 64, 24));
        jButton4.setText("Cancel");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 200, -1, 40));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel10.setText("Ajouter une Qualification");
        jPanel6.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel11.setText("Nom de Qualification");
        jPanel6.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, 30));
        jPanel6.add(qualification_textfield, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 270, 36));

        jButton5.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(248, 64, 24));
        jButton5.setText("Save");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, -1, 40));

        javax.swing.GroupLayout ajoutQualificationLayout = new javax.swing.GroupLayout(ajoutQualification.getContentPane());
        ajoutQualification.getContentPane().setLayout(ajoutQualificationLayout);
        ajoutQualificationLayout.setHorizontalGroup(
            ajoutQualificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        ajoutQualificationLayout.setVerticalGroup(
            ajoutQualificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        ModifQualification.setModal(true);
        ModifQualification.setResizable(false);
        ModifQualification.setSize(new java.awt.Dimension(400, 300));
        ModifQualification.setLocationRelativeTo(null);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(400, 300));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(248, 64, 24));
        jButton6.setText("Cancel");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, -1, 40));

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel12.setText("Modifier une Qualification");
        jPanel8.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, -1, -1));

        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel13.setText("Nom de Nouveau Qualification");
        jPanel8.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, -1, 30));

        qualification_updated.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qualification_updatedActionPerformed(evt);
            }
        });
        jPanel8.add(qualification_updated, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 270, 36));

        jButton7.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(248, 64, 24));
        jButton7.setText("Save");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 210, -1, 40));

        javax.swing.GroupLayout ModifQualificationLayout = new javax.swing.GroupLayout(ModifQualification.getContentPane());
        ModifQualification.getContentPane().setLayout(ModifQualificationLayout);
        ModifQualificationLayout.setHorizontalGroup(
            ModifQualificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        ModifQualificationLayout.setVerticalGroup(
            ModifQualificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        ajoutOperateurDialog.setModal(true);
        ajoutOperateurDialog.setResizable(false);
        ajoutOperateurDialog.setSize(new java.awt.Dimension(400, 550));
        ajoutOperateurDialog.setLocationRelativeTo(null);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setPreferredSize(new java.awt.Dimension(400, 550));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel9.add(prenom_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, 270, 36));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel8.setText("Prenom");
        jPanel9.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, -1, 30));

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel9.setText("Nom");
        jPanel9.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, -1, 30));

        jButton10.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(248, 64, 24));
        jButton10.setText("Cancel");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 480, 90, 30));

        jLabel14.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel14.setText("Ajouter un Operateur");
        jPanel9.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel15.setText("Matricule");
        jPanel9.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, -1, 30));
        jPanel9.add(matricule_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 270, 36));

        jButton11.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(248, 64, 24));
        jButton11.setText("Save");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 480, 80, 30));
        jPanel9.add(nom_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 270, 36));

        jLabel16.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel16.setText("Equipe");
        jPanel9.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 370, -1, 30));
        jPanel9.add(datedembauche_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, 270, 36));

        jLabel17.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel17.setText("Date d'embauche");
        jPanel9.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, -1, 30));
        jPanel9.add(equipe_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 400, 270, 36));

        javax.swing.GroupLayout ajoutOperateurDialogLayout = new javax.swing.GroupLayout(ajoutOperateurDialog.getContentPane());
        ajoutOperateurDialog.getContentPane().setLayout(ajoutOperateurDialogLayout);
        ajoutOperateurDialogLayout.setHorizontalGroup(
            ajoutOperateurDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        ajoutOperateurDialogLayout.setVerticalGroup(
            ajoutOperateurDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialog22.setModal(true);
        jDialog22.setResizable(false);
        jDialog22.setSize(new java.awt.Dimension(400, 500));
        SuiviDialog.setLocationRelativeTo(null);

        jPanel10.setBackground(new java.awt.Color(217, 217, 217));
        jPanel10.setPreferredSize(new java.awt.Dimension(400, 500));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel10.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, 270, 36));

        jLabel18.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel18.setText("Durée *");
        jPanel10.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, -1, 30));

        jLabel19.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel19.setText("Formateur");
        jPanel10.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, -1, 30));

        jButton12.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton12.setText("Cancel");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 410, -1, 40));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Formateur 1", "Formateur  2", "Formateur  3", "Formateur  4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel10.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 270, 36));

        jLabel20.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel20.setText("Ajouter une formation");
        jPanel10.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, -1, -1));

        jLabel21.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel21.setText("Titre de formation *");
        jPanel10.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, -1, 30));
        jPanel10.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 270, 36));

        jButton13.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton13.setText("Save");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 410, -1, 40));

        javax.swing.GroupLayout jDialog22Layout = new javax.swing.GroupLayout(jDialog22.getContentPane());
        jDialog22.getContentPane().setLayout(jDialog22Layout);
        jDialog22Layout.setHorizontalGroup(
            jDialog22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jDialog22Layout.setVerticalGroup(
            jDialog22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTable1);

        phases_jdialog.setModal(true);
        phases_jdialog.setResizable(false);
        phases_jdialog.setSize(new java.awt.Dimension(800, 600));
        phases_jdialog.setLocationRelativeTo(null);

        jPanel11.setBackground(new java.awt.Color(217, 217, 217));
        jPanel11.setPreferredSize(new java.awt.Dimension(800, 600));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel36.setText("formation");

        jButton18.setText("Suivant");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_formationLayout = new javax.swing.GroupLayout(panel_formation);
        panel_formation.setLayout(panel_formationLayout);
        panel_formationLayout.setHorizontalGroup(
            panel_formationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_formationLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton18)
                .addGap(39, 39, 39))
            .addGroup(panel_formationLayout.createSequentialGroup()
                .addGap(348, 348, 348)
                .addComponent(jLabel36)
                .addContainerGap(336, Short.MAX_VALUE))
        );
        panel_formationLayout.setVerticalGroup(
            panel_formationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_formationLayout.createSequentialGroup()
                .addContainerGap(228, Short.MAX_VALUE)
                .addComponent(jLabel36)
                .addGap(222, 222, 222)
                .addComponent(jButton18)
                .addGap(21, 21, 21))
        );

        phases_tab.addTab("Formation", panel_formation);

        jLabel37.setText("Formation parallele");

        javax.swing.GroupLayout panel_formationparaLayout = new javax.swing.GroupLayout(panel_formationpara);
        panel_formationpara.setLayout(panel_formationparaLayout);
        panel_formationparaLayout.setHorizontalGroup(
            panel_formationparaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_formationparaLayout.createSequentialGroup()
                .addGap(314, 314, 314)
                .addComponent(jLabel37)
                .addContainerGap(306, Short.MAX_VALUE))
        );
        panel_formationparaLayout.setVerticalGroup(
            panel_formationparaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_formationparaLayout.createSequentialGroup()
                .addGap(223, 223, 223)
                .addComponent(jLabel37)
                .addContainerGap(270, Short.MAX_VALUE))
        );

        phases_tab.addTab("Formation parallele", panel_formationpara);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 501, Short.MAX_VALUE)
        );

        phases_tab.addTab("Validation", jPanel5);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 501, Short.MAX_VALUE)
        );

        phases_tab.addTab("Certification", jPanel14);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 501, Short.MAX_VALUE)
        );

        phases_tab.addTab("Observation", jPanel15);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 501, Short.MAX_VALUE)
        );

        phases_tab.addTab("Recertification", jPanel16);

        jPanel11.add(phases_tab, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 750, 540));

        javax.swing.GroupLayout phases_jdialogLayout = new javax.swing.GroupLayout(phases_jdialog.getContentPane());
        phases_jdialog.getContentPane().setLayout(phases_jdialogLayout);
        phases_jdialogLayout.setHorizontalGroup(
            phases_jdialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        phases_jdialogLayout.setVerticalGroup(
            phases_jdialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        modifOperateurDialog.setModal(true);
        modifOperateurDialog.setResizable(false);
        modifOperateurDialog.setSize(new java.awt.Dimension(400, 550));
        modifOperateurDialog.setLocationRelativeTo(null);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setPreferredSize(new java.awt.Dimension(400, 550));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel12.add(prenom_txt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 270, 36));

        jLabel24.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel24.setText("Prenom");
        jPanel12.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, -1, 30));

        jLabel25.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel25.setText("Nom");
        jPanel12.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, -1, 30));

        jButton14.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton14.setForeground(new java.awt.Color(248, 64, 24));
        jButton14.setText("Cancel");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel12.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 490, 90, 30));

        jLabel26.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel26.setText("Modifier un Operateur");
        jPanel12.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, -1, -1));

        jLabel27.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel27.setText("Matricule");
        jPanel12.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, -1, 30));
        jPanel12.add(matricule_txt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 270, 36));

        jButton15.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton15.setForeground(new java.awt.Color(248, 64, 24));
        jButton15.setText("Save");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel12.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 490, 80, 30));
        jPanel12.add(nom_txt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 270, 36));

        jLabel28.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel28.setText("Equipe");
        jPanel12.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, -1, 30));
        jPanel12.add(datedembauche_txt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, 270, 36));

        jLabel29.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel29.setText("Date d'embauche");
        jPanel12.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, -1, 30));
        jPanel12.add(equipe_txt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, 270, 36));

        javax.swing.GroupLayout modifOperateurDialogLayout = new javax.swing.GroupLayout(modifOperateurDialog.getContentPane());
        modifOperateurDialog.getContentPane().setLayout(modifOperateurDialogLayout);
        modifOperateurDialogLayout.setHorizontalGroup(
            modifOperateurDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        modifOperateurDialogLayout.setVerticalGroup(
            modifOperateurDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane7.setViewportView(jList1);

        modifsuivi.setModal(true);
        modifsuivi.setResizable(false);
        modifsuivi.setSize(new java.awt.Dimension(400, 600));
        modifsuivi.setLocationRelativeTo(null);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setMinimumSize(new java.awt.Dimension(380, 600));
        jPanel13.setPreferredSize(new java.awt.Dimension(400, 600));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel30.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel30.setText("Prenom");
        jPanel13.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 380, -1, 30));

        jLabel31.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel31.setText("Formateur");
        jPanel13.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, -1, 30));

        jButton16.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton16.setText("Cancel");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 490, -1, 40));

        qualifications_combo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qualifications_combo2ActionPerformed(evt);
            }
        });
        jPanel13.add(qualifications_combo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 270, 36));

        jLabel32.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel32.setText("Modifier Suivi");
        jPanel13.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, -1, -1));

        jLabel33.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel33.setText("Titre de formation ");
        jPanel13.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, -1, 30));

        jButton17.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jButton17.setText("Save");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 490, -1, 40));

        formateurs_combo2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Formateur1", "Formateur2", "Formateur3" }));
        formateurs_combo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formateurs_combo2ActionPerformed(evt);
            }
        });
        jPanel13.add(formateurs_combo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 270, 36));

        operateurs_combo2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                operateurs_combo2ItemStateChanged(evt);
            }
        });
        operateurs_combo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                operateurs_combo2ActionPerformed(evt);
            }
        });
        jPanel13.add(operateurs_combo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, 270, 36));

        prenom_txtcheck2.setEnabled(false);
        prenom_txtcheck2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prenom_txtcheck2ActionPerformed(evt);
            }
        });
        jPanel13.add(prenom_txtcheck2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, 270, 36));

        nom_txtcheck2.setEnabled(false);
        jPanel13.add(nom_txtcheck2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, 270, 36));

        jLabel34.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel34.setText("Operateur");
        jPanel13.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, 30));

        jLabel35.setFont(new java.awt.Font("Century Gothic", 1, 15)); // NOI18N
        jLabel35.setText("Nom");
        jPanel13.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, -1, 30));

        javax.swing.GroupLayout modifsuiviLayout = new javax.swing.GroupLayout(modifsuivi.getContentPane());
        modifsuivi.getContentPane().setLayout(modifsuiviLayout);
        modifsuiviLayout.setHorizontalGroup(
            modifsuiviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
        );
        modifsuiviLayout.setVerticalGroup(
            modifsuiviLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        phases_jdialog1.setModal(true);
        phases_jdialog1.setResizable(false);
        phases_jdialog1.setSize(new java.awt.Dimension(900, 650));
        phases_jdialog1.setLocationRelativeTo(null);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setPreferredSize(new java.awt.Dimension(800, 650));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        progressIndicator.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));
        progressIndicator.setFont(new java.awt.Font("Century Gothic", 0, 15)); // NOI18N
        progressIndicator.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Formation", "Formation parallele", "validation", "certification", "observation", "recertification" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        progressIndicator.setOpaque(false);
        progressIndicator.setProgress(0.0F);
        progressIndicator.setProgressColor(new java.awt.Color(250, 135, 110));
        progressIndicator.setProgressColorGradient(new java.awt.Color(248, 64, 24));
        progressIndicator.setProgressFill(true);
        jPanel17.add(progressIndicator, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 870, -1));
        jPanel17.add(panelSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 750, 450));

        jButton19.setText("Back");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        jPanel17.add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 550, -1, -1));

        jButton20.setText("Suivant");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });
        jPanel17.add(jButton20, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 550, -1, -1));

        javax.swing.GroupLayout phases_jdialog1Layout = new javax.swing.GroupLayout(phases_jdialog1.getContentPane());
        phases_jdialog1.getContentPane().setLayout(phases_jdialog1Layout);
        phases_jdialog1Layout.setHorizontalGroup(
            phases_jdialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
        );
        phases_jdialog1Layout.setVerticalGroup(
            phases_jdialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, phases_jdialog1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        btn_side1.setBackground(new java.awt.Color(0, 0, 0));
        btn_side1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btn_side1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_side1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_side1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_side1MousePressed(evt);
            }
        });
        btn_side1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout ind_1Layout = new javax.swing.GroupLayout(ind_1);
        ind_1.setLayout(ind_1Layout);
        ind_1Layout.setHorizontalGroup(
            ind_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 9, Short.MAX_VALUE)
        );
        ind_1Layout.setVerticalGroup(
            ind_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 63, Short.MAX_VALUE)
        );

        btn_side1.add(ind_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        option_1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        option_1.setForeground(new java.awt.Color(255, 255, 255));
        option_1.setText("Qualifications");
        btn_side1.add(option_1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        btn_side2.setBackground(new java.awt.Color(0, 0, 0));
        btn_side2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        btn_side2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_side2MousePressed(evt);
            }
        });
        btn_side2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout ind_2Layout = new javax.swing.GroupLayout(ind_2);
        ind_2.setLayout(ind_2Layout);
        ind_2Layout.setHorizontalGroup(
            ind_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 9, Short.MAX_VALUE)
        );
        ind_2Layout.setVerticalGroup(
            ind_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 63, Short.MAX_VALUE)
        );

        btn_side2.add(ind_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        option_2.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        option_2.setForeground(new java.awt.Color(255, 255, 255));
        option_2.setText("Gestion Suivi");
        btn_side2.add(option_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel7.setBackground(new java.awt.Color(0, 0, 0));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel7MousePressed(evt);
            }
        });
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("La matrice Global");
        jPanel7.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 160, -1));

        btn_side3.setBackground(new java.awt.Color(0, 0, 0));
        btn_side3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btn_side3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_side3MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_side3MousePressed(evt);
            }
        });
        btn_side3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ind_3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout ind_3Layout = new javax.swing.GroupLayout(ind_3);
        ind_3.setLayout(ind_3Layout);
        ind_3Layout.setHorizontalGroup(
            ind_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 9, Short.MAX_VALUE)
        );
        ind_3Layout.setVerticalGroup(
            ind_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 63, Short.MAX_VALUE)
        );

        btn_side3.add(ind_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        option_3.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        option_3.setForeground(new java.awt.Color(255, 255, 255));
        option_3.setText("Operateurs");
        btn_side3.add(option_3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
            .addComponent(btn_side2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_side3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btn_side1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(btn_side1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btn_side3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btn_side2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 186, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 210, 640));

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));

        admin_label.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        admin_label.setText(" ");

        logout_btn.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        logout_btn.setForeground(new java.awt.Color(255, 255, 255));
        logout_btn.setText("LOGOUT");
        logout_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logout_btnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logout_btnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logout_btnMouseExited(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/simplegui/aptiv-logo-withoutdots.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Zone de Preparation");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 358, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(162, 162, 162)
                .addComponent(admin_label)
                .addGap(75, 75, 75)
                .addComponent(logout_btn)
                .addGap(33, 33, 33))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(logout_btn)
                        .addComponent(admin_label)))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 100));

        optionsParentPanel.setBackground(new java.awt.Color(255, 255, 255));
        optionsParentPanel.setLayout(new java.awt.CardLayout());

        paneloption1.setBackground(new java.awt.Color(255, 255, 255));
        paneloption1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton1.setText("Ajouter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        paneloption1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, -1, 30));

        search_ic2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/simplegui/search.png"))); // NOI18N
        paneloption1.add(search_ic2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 180, -1, 30));

        search_textField_qualif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_textField_qualifActionPerformed(evt);
            }
        });
        search_textField_qualif.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_textField_qualifKeyReleased(evt);
            }
        });
        paneloption1.add(search_textField_qualif, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 180, 170, 30));

        qualification_jtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDqualification", "Nom", "Actions"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        qualification_jtable.setRowHeight(40);
        qualification_jtable.getTableHeader().setReorderingAllowed(false);
        qualification_jtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                qualification_jtableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(qualification_jtable);
        if (qualification_jtable.getColumnModel().getColumnCount() > 0) {
            qualification_jtable.getColumnModel().getColumn(0).setResizable(false);
            qualification_jtable.getColumnModel().getColumn(1).setResizable(false);
            qualification_jtable.getColumnModel().getColumn(2).setResizable(false);
        }

        paneloption1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 768, 320));

        optionsParentPanel.add(paneloption1, "card2");

        paneloption2.setBackground(new java.awt.Color(255, 255, 255));
        paneloption2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton8.setText("Ajouter");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        paneloption2.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        suivi_jtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDsuivi", "Matricule d'operateur", "Nom Complet", "Qualification", "Statue", "Actions"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        suivi_jtable.getTableHeader().setReorderingAllowed(false);
        suivi_jtable.setRowHeight(40);
        jScrollPane5.setViewportView(suivi_jtable);
        if (suivi_jtable.getColumnModel().getColumnCount() > 0) {
            suivi_jtable.getColumnModel().getColumn(0).setPreferredWidth(8);
            suivi_jtable.getColumnModel().getColumn(4).setPreferredWidth(10);
        }

        paneloption2.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 850, 400));

        search_textField_suivi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_textField_suiviActionPerformed(evt);
            }
        });
        search_textField_suivi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_textField_suiviKeyReleased(evt);
            }
        });
        paneloption2.add(search_textField_suivi, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 140, 170, 30));

        search_ic1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/simplegui/search.png"))); // NOI18N
        paneloption2.add(search_ic1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 140, -1, 30));

        optionsParentPanel.add(paneloption2, "card2");

        paneloption3.setBackground(new java.awt.Color(255, 255, 255));
        paneloption3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addopBtn.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        addopBtn.setText("Ajouter");
        addopBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addopBtnActionPerformed(evt);
            }
        });
        paneloption3.add(addopBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, 30));

        operateurs_jtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IDoperateur", "Matricules", "Nom", "Prenom", "Datedembauche", "Equipe", "Actions"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        operateurs_jtable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        operateurs_jtable.setRowHeight(40);
        operateurs_jtable.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(operateurs_jtable);
        if (operateurs_jtable.getColumnModel().getColumnCount() > 0) {
            operateurs_jtable.getColumnModel().getColumn(6).setResizable(false);
        }

        paneloption3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 870, 330));

        search_textField_op.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_textField_opActionPerformed(evt);
            }
        });
        search_textField_op.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search_textField_opKeyReleased(evt);
            }
        });
        paneloption3.add(search_textField_op, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 110, 170, 30));

        search_ic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/simplegui/search.png"))); // NOI18N
        paneloption3.add(search_ic, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 110, -1, 30));

        optionsParentPanel.add(paneloption3, "card2");

        matrice.setBackground(new java.awt.Color(255, 255, 255));
        matrice.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        matrice_jtable.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        matrice_jtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        matrice_jtable.setEnabled(false);
        matrice_jtable.getTableHeader().setReorderingAllowed(false);
        matrice_jtable.setInheritsPopupMenu(true);
        matrice_jtable.setRowHeight(40);
        matrice_jtable.setRowSelectionAllowed(false);
        matrice_jtable.setShowHorizontalLines(true);
        matrice_jtable.setShowVerticalLines(true);
        jScrollPane6.setViewportView(matrice_jtable);

        matrice.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 830, 430));

        jButton9.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        jButton9.setText("Telecharger la matrice");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        matrice.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 580, 220, -1));

        optionsParentPanel.add(matrice, "card2");

        jPanel1.add(optionsParentPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 910, 640));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_side1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_side1MousePressed
    
        setColor(btn_side1);
        resetColor(btn_side2);
        resetColor(btn_side3);
        resetColor(jPanel7);
        option_1.setForeground(new Color(248,64,24));
        option_2.setForeground(new Color(255,255,255));
        option_3.setForeground(new Color(255,255,255));
        ind_1.setBackground(new Color(248,64,24));
        ind_2.setBackground(new Color(255,255,255));
        ind_3.setBackground(new Color(255,255,255));
        
        
        optionsParentPanel.removeAll();
        optionsParentPanel.add(paneloption1);
        optionsParentPanel.repaint();
        optionsParentPanel.revalidate();
        
        
    }//GEN-LAST:event_btn_side1MousePressed

    private void btn_side2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_side2MousePressed
        setColor(btn_side2);
        resetColor(btn_side1);
        resetColor(btn_side3);
        resetColor(jPanel7);
        option_2.setForeground(new Color(248,64,24));
        option_1.setForeground(new Color(255,255,255));
        option_3.setForeground(new Color(255,255,255));
        
        ind_2.setBackground(new Color(248,64,24));
        ind_3.setBackground(new Color(255,255,255));
        ind_1.setBackground(new Color(255,255,255));
        optionsParentPanel.removeAll();
        optionsParentPanel.add(paneloption2);
        optionsParentPanel.repaint();
        optionsParentPanel.revalidate();
        fill_qualif_combo(qualifications_combo);
        fill_qualif_combo(qualifications_combo2);
        fill_oper_combo(operateurs_combo);
        fill_oper_combo(operateurs_combo2);
    }//GEN-LAST:event_btn_side2MousePressed

    private void logout_btnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logout_btnMouseEntered
        logout_btn.setForeground(new Color(248,64,24));
        //logout_btn.setFont(logout_btn.getFont().deriveFont(Font.BOLD));
    }//GEN-LAST:event_logout_btnMouseEntered

    
    private void logout_btnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logout_btnMouseExited
        logout_btn.setForeground(new Color(255,255,255));
        //logout_btn.setFont(logout_btn.getFont().deriveFont(Font.PLAIN));
    }//GEN-LAST:event_logout_btnMouseExited

    private void logout_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logout_btnMouseClicked
         this.dispose();
         Login loginGUI = new Login();
         loginGUI.setVisible(true);
         Image iconImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("favicon-aptiv.png"));
         loginGUI.setIconImage(iconImage);
    }//GEN-LAST:event_logout_btnMouseClicked

    private void btn_side1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_side1MouseEntered

    }//GEN-LAST:event_btn_side1MouseEntered

    private void btn_side1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_side1MouseExited

    }//GEN-LAST:event_btn_side1MouseExited

    private void qualifications_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qualifications_comboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qualifications_comboActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //jLabel8.setText(jTextField2.getText());
        //jLabel9.setText(jTextField1.getText());
        //jLabel10.setText(jComboBox1.getSelectedItem().toString());
        
        Object selectedMatriculeItem = operateurs_combo.getSelectedItem();
        String matricule = selectedMatriculeItem.toString();
        int selectedMatricule = Integer.parseInt(matricule);
        int selectedOpId = getOpIdFromDatabase(selectedMatricule);
        
        
        String updatedQualifString= qualifications_combo.getSelectedItem().toString();
        int updatedQualifId = getQualifIdFromDatabase(updatedQualifString);
        
        
        
        //int IDoperateur = operateurs_combo.getSelectedIndex()+1;
        //int IDqualif=qualifications_combo.getSelectedIndex()+1;  
        
        int IDformateur = formateurs_combo.getSelectedIndex()+1;
        
        String defStatus = "F";
        
        try{
            
          if(formateurs_combo.getSelectedItem()== null||qualifications_combo.getSelectedItem()==null||operateurs_combo.getSelectedItem()==null){
               JOptionPane.showMessageDialog(this, "Empty Data!");
          }else{
                String query_1 = "Insert into suivi(IDqualification,IDoperateur,ID,Statue) values ("+updatedQualifId+",'"+selectedOpId+"','"+IDformateur+"','"+defStatus+"')";         
                Statement st= con.createStatement();	
                st.execute(query_1);
                refreshSuiviTable();
                SuiviDialog.dispose();
          }

	}catch(Exception w1){
	    System.out.println(w1);	
	}
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        SuiviDialog.setVisible(false);
        SuiviDialog.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        ajoutQualification.setVisible(false);
        ajoutQualification.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String newQualif = qualification_textfield.getText();
        int zone = 1;
        int IDadmin = 1;
        try{
            
            if("".equals(newQualif)){
                JOptionPane.showMessageDialog(this, "Empty Data!");
            }else{
                String query_1 = "Insert into qualifications(Nom,IDzone,ID) values ('"+newQualif+"','"+zone+"','"+IDadmin+"')";				
                Statement st= con.createStatement();		
                st.execute(query_1);
                refreshTable();
                qualification_textfield.setText("");
                ajoutQualification.dispose();
            }

	}catch(Exception w1){
	    System.out.println(w1);	
	}
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        ModifQualification.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        String new_qualif  = qualification_updated.getText();
                
                QualifId = Integer.parseInt(qualification_jtable.getValueAt(qualification_jtable.getSelectedRow(),0).toString());
                if(qualification_jtable.isEditing()){
                    qualification_jtable.getCellEditor().stopCellEditing();
                }
                            try {
 
                    //int id = (int) qualification_jtable.getValueAt(row, 0);


                    PreparedStatement stmt = con.prepareStatement("UPDATE qualifications SET Nom = ? WHERE IDqualification = ?");
                    stmt.setString(1, new_qualif);
                    stmt.setInt(2, QualifId);
                    stmt.executeUpdate();


                    DefaultTableModel model = (DefaultTableModel) qualification_jtable.getModel();
                    model.setValueAt(new_qualif, qualification_jtable.getSelectedRow(), 1);


                    model.fireTableDataChanged();

                } catch (SQLException e) {
                    e.printStackTrace();
                } 
        
        ModifQualification.dispose();
        refreshSuiviTable();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void qualification_updatedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qualification_updatedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qualification_updatedActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        SuiviDialog.setVisible(true);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void btn_side3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_side3MousePressed
        setColor(btn_side3);
        resetColor(btn_side1);
        resetColor(btn_side2);
        resetColor(jPanel7);
        option_3.setForeground(new Color(248,64,24));
        option_1.setForeground(new Color(255,255,255));
        option_2.setForeground(new Color(255,255,255));
        
        ind_3.setBackground(new Color(248,64,24));
        ind_2.setBackground(new Color(255,255,255));
        ind_1.setBackground(new Color(255,255,255));
        optionsParentPanel.removeAll();
        optionsParentPanel.add(paneloption3);
        optionsParentPanel.repaint();
        optionsParentPanel.revalidate();
    }//GEN-LAST:event_btn_side3MousePressed

    private void addopBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addopBtnActionPerformed
        ajoutOperateurDialog.setVisible(true);
    }//GEN-LAST:event_addopBtnActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        
        String newmatricule = matricule_txt.getText();
        String newnom = nom_txt.getText();
        String newprenom = prenom_txt.getText();
        String newdate = datedembauche_txt.getText();
        String newequipe = equipe_txt.getText();
        
        try{
            
            if("".equals(newmatricule) ||"".equals(newnom) ||"".equals(newprenom) ||"".equals(newdate) ||"".equals(newequipe)){
                JOptionPane.showMessageDialog(this, "Empty Data!");
            }else{
                String query_1 = "Insert into operateurs(Matricules,Nom,Prenom,Datedembauche,Equipe) values ('"+newmatricule+"','"+newnom+"','"+newprenom+"','"+newdate+"','"+newequipe+"')";				
                Statement st= con.createStatement();		
                st.execute(query_1);
                refreshOperateursTable();
                matricule_txt.setText("");
                nom_txt.setText("");
                prenom_txt.setText("");
                datedembauche_txt.setText("");
                equipe_txt.setText("");
                ajoutOperateurDialog.dispose();
            }

	}catch(Exception w1){
	    System.out.println(w1);	
	}        
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton13ActionPerformed

    private void formateurs_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formateurs_comboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_formateurs_comboActionPerformed

    private void operateurs_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_operateurs_comboActionPerformed
      
        
    }//GEN-LAST:event_operateurs_comboActionPerformed

    private void search_textField_opActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_textField_opActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_textField_opActionPerformed

    private void search_textField_opKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_textField_opKeyReleased
    
     DefaultTableModel obj = (DefaultTableModel) operateurs_jtable.getModel();
     TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
     operateurs_jtable.setRowSorter(obj1);
     
     String query = search_textField_op.getText();

     RowFilter<DefaultTableModel, Object> filter = new RowFilter<DefaultTableModel, Object>() {
      @Override
      public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
        for (int columnIndex = 0; columnIndex < entry.getModel().getColumnCount(); columnIndex++) {
            String text = entry.getStringValue(columnIndex);
            if (text.toLowerCase().contains(query.toLowerCase())) {
                return true; 
            }
        }
        return false; 
      }
    };

    obj1.setRowFilter(filter);
     
     //obj1.setRowFilter(RowFilter.regexFilter(jTextField2.getText()));
        
        
        
    }//GEN-LAST:event_search_textField_opKeyReleased

    private void jPanel7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MousePressed
        resetColor(btn_side1);
        resetColor(btn_side2);
        resetColor(btn_side3);
        
        ind_1.setBackground(Color.WHITE);
        ind_2.setBackground(Color.WHITE);
        ind_3.setBackground(Color.WHITE);
        
        option_1.setForeground(Color.WHITE);
        option_2.setForeground(Color.WHITE);
        option_3.setForeground(Color.WHITE);
        

        jPanel7.setBackground(new Color(248,64,24));

        
        optionsParentPanel.removeAll();
        optionsParentPanel.add(matrice);
        optionsParentPanel.repaint();
        optionsParentPanel.revalidate();
        fill_matrice();
    }//GEN-LAST:event_jPanel7MousePressed

    private void search_textField_suiviActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_textField_suiviActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_textField_suiviActionPerformed

    private void search_textField_suiviKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_textField_suiviKeyReleased
     
     DefaultTableModel obj = (DefaultTableModel) suivi_jtable.getModel();
     TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
     suivi_jtable.setRowSorter(obj1);
     
     String query = search_textField_suivi.getText();

     RowFilter<DefaultTableModel, Object> filter = new RowFilter<DefaultTableModel, Object>() {
      @Override
      public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Object> entry) {
        for (int columnIndex = 0; columnIndex < entry.getModel().getColumnCount(); columnIndex++) {
            String text = entry.getStringValue(columnIndex);
            if (text.toLowerCase().contains(query.toLowerCase())) {
                return true; 
            }
        }
        return false; 
      }
    };

    obj1.setRowFilter(filter);
     
    }//GEN-LAST:event_search_textField_suiviKeyReleased

    private void operateurs_comboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_operateurs_comboItemStateChanged
             
        
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            Object selectedItem = operateurs_combo.getSelectedItem();
            String matricule = selectedItem.toString();
            int matriculeValue = Integer.parseInt(matricule);
            if (matricule!=null) {
                
                String nom = getNomFromDatabase(matriculeValue);
                String prenom = getPrenomFromDatabase(matriculeValue);


                nom_txtcheck.setText(nom);
                prenom_txtcheck.setText(prenom); 
            }else{
                nom_txtcheck.setText("");
                prenom_txtcheck.setText("");
            }
        }
        
        
               
    }//GEN-LAST:event_operateurs_comboItemStateChanged

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        modifOperateurDialog.dispose();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        
        
        int OperId = Integer.parseInt(operateurs_jtable.getValueAt(operateurs_jtable.getSelectedRow(),0).toString());
        int newmatricule = Integer.parseInt(matricule_txt1.getText());
        String newnom = nom_txt1.getText();
        String newprenom = prenom_txt1.getText();
        String newdate = datedembauche_txt1.getText();
        String newequipe = equipe_txt1.getText();
        
        try{
            
            if("".equals(newmatricule) ||"".equals(newnom) ||"".equals(newprenom) ||"".equals(newdate) ||"".equals(newequipe)){
                JOptionPane.showMessageDialog(this, "Empty Data!");
            }else{
                
                String query = "UPDATE Operateurs SET Matricules = ?, Nom = ?, Prenom = ?, Datedembauche = ?,Equipe = ? WHERE IDoperateur = ?";
                PreparedStatement statement = con.prepareStatement(query);
                
                statement.setInt(1, newmatricule);
                statement.setString(2, newnom);
                statement.setString(3, newprenom);
                statement.setString(4, newdate);
                statement.setString(5, newequipe);
                statement.setInt(6, OperId);
                
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Row updated successfully!");
                } else {
                    System.out.println("No rows updated.");
                }
                
                //String query_1 = "";				
                //Statement st= con.createStatement();		
                //st.execute(query_1);
                refreshOperateursTable();
                matricule_txt.setText("");
                nom_txt.setText("");
                prenom_txt.setText("");
                datedembauche_txt.setText("");
                equipe_txt.setText("");
                ajoutOperateurDialog.dispose();
            }

	}catch(Exception w1){
	    System.out.println(w1);	
	}
        
        modifOperateurDialog.dispose();
        refreshSuiviTable();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void search_textField_qualifKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search_textField_qualifKeyReleased
        DefaultTableModel obj = (DefaultTableModel) qualification_jtable.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
        qualification_jtable.setRowSorter(obj1);

        String query = search_textField_qualif.getText();

        RowFilter<DefaultTableModel, Object> filter = new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Object> entry) {
                for (int columnIndex = 0; columnIndex < entry.getModel().getColumnCount(); columnIndex++) {
                    String text = entry.getStringValue(columnIndex);
                    if (text.toLowerCase().contains(query.toLowerCase())) {
                        return true;
                    }
                }
                return false;
            }
        };

        obj1.setRowFilter(filter);
    }//GEN-LAST:event_search_textField_qualifKeyReleased

    private void search_textField_qualifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_textField_qualifActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_search_textField_qualifActionPerformed

    private void qualification_jtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_qualification_jtableMouseClicked
        /*
        QualifId =Integer.parseInt(qualification_jtable.getValueAt(qualification_jtable.getSelectedRow(),0).toString());
        System.out.println(QualifId);
        */
    }//GEN-LAST:event_qualification_jtableMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ajoutQualification.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_side3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_side3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_side3MouseExited

    private void prenom_txtcheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prenom_txtcheckActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prenom_txtcheckActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        /*
        String path = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = j.showSaveDialog(this);
        if(x==JFileChooser.APPROVE_OPTION){
            path = j.getSelectedFile().getPath();
        }
        */
        MessageFormat header = new  MessageFormat("Matrice Global");
        MessageFormat footer = new  MessageFormat(" ");
        try{
            matrice_jtable.print(JTable.PrintMode.FIT_WIDTH,header,footer);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Cannot be printed !" + e.getMessage());
        }
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        modifsuivi.dispose();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void qualifications_combo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qualifications_combo2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qualifications_combo2ActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        
        
        int newSelectedOperateur = Integer.parseInt(operateurs_combo2.getSelectedItem().toString());
        int newOperId = getOpIdFromDatabase(newSelectedOperateur);
        
        
        String updatedQualifString= qualifications_combo2.getSelectedItem().toString();
        int updatedQualifId = getQualifIdFromDatabase(updatedQualifString);
        
        int newSelectedQualifId=qualifications_combo2.getSelectedIndex()+1;
        int newSelectedFormateurId=formateurs_combo2.getSelectedIndex()+1; 
        
        
        int suiviId = Integer.parseInt(suivi_jtable.getValueAt(suivi_jtable.getSelectedRow(),0).toString());
        String defStatue = "F";
        
        try{
            
            if(formateurs_combo2.getSelectedItem()== null||qualifications_combo2.getSelectedItem()==null||operateurs_combo2.getSelectedItem()==null){
                JOptionPane.showMessageDialog(this, "Empty Data!");
            }else{
                
                String query = "UPDATE suivi SET IDqualification = ?, IDoperateur = ?, ID = ?,Statue = ? WHERE IDsuivi = ?";
                PreparedStatement statement = con.prepareStatement(query);
                
                statement.setInt(1, updatedQualifId);
                statement.setInt(2, newOperId);
                statement.setInt(3, newSelectedFormateurId);
                statement.setString(4, defStatue);
                statement.setInt(5, suiviId);
                
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Row updated successfully!");
                } else {
                    System.out.println("No rows updated.");
                }
                
                refreshSuiviTable();
                matricule_txt.setText("");
                nom_txt.setText("");
                prenom_txt.setText("");
                datedembauche_txt.setText("");
                equipe_txt.setText("");
            }

	}catch(Exception w1){
	    System.out.println(w1);	
	}
        
        modifsuivi.dispose();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void formateurs_combo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formateurs_combo2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_formateurs_combo2ActionPerformed

    private void operateurs_combo2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_operateurs_combo2ItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_operateurs_combo2ItemStateChanged

    private void operateurs_combo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_operateurs_combo2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_operateurs_combo2ActionPerformed

    private void prenom_txtcheck2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prenom_txtcheck2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prenom_txtcheck2ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        phases_tab.setSelectedIndex(1);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        progressIndicator.previous();
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
        progressIndicator.next();
    }//GEN-LAST:event_jButton20ActionPerformed

    
    /**
     * @param args the command line arguments
     */
    
    void setColor(JPanel panel){
        panel.setBackground(new Color(255,255,255));
    }
    
     void resetColor(JPanel panel){
        panel.setBackground(new Color(0,0,0));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog ModifQualification;
    private javax.swing.JDialog SuiviDialog;
    private javax.swing.JButton addopBtn;
    javax.swing.JLabel admin_label;
    private javax.swing.JDialog ajoutOperateurDialog;
    private javax.swing.JDialog ajoutQualification;
    private javax.swing.JPanel btn_side1;
    private javax.swing.JPanel btn_side2;
    private javax.swing.JPanel btn_side3;
    private javax.swing.JTextField datedembauche_txt;
    private javax.swing.JTextField datedembauche_txt1;
    private javax.swing.JTextField equipe_txt;
    private javax.swing.JTextField equipe_txt1;
    private javax.swing.JComboBox<String> formateurs_combo;
    private javax.swing.JComboBox<String> formateurs_combo2;
    private javax.swing.JPanel ind_1;
    private javax.swing.JPanel ind_2;
    private javax.swing.JPanel ind_3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JDialog jDialog22;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel logout_btn;
    private javax.swing.JPanel matrice;
    private javax.swing.JTable matrice_jtable;
    private javax.swing.JTextField matricule_txt;
    private javax.swing.JTextField matricule_txt1;
    private javax.swing.JDialog modifOperateurDialog;
    private javax.swing.JDialog modifsuivi;
    private javax.swing.JTextField nom_txt;
    private javax.swing.JTextField nom_txt1;
    private javax.swing.JTextField nom_txtcheck;
    private javax.swing.JTextField nom_txtcheck2;
    private javax.swing.JComboBox<String> operateurs_combo;
    private javax.swing.JComboBox<String> operateurs_combo2;
    private javax.swing.JTable operateurs_jtable;
    private javax.swing.JLabel option_1;
    private javax.swing.JLabel option_2;
    private javax.swing.JLabel option_3;
    private javax.swing.JPanel optionsParentPanel;
    private raven.progressindicator.PanelSlider panelSlider;
    private javax.swing.JPanel panel_formation;
    private javax.swing.JPanel panel_formationpara;
    private javax.swing.JPanel paneloption1;
    private javax.swing.JPanel paneloption2;
    private javax.swing.JPanel paneloption3;
    private javax.swing.JDialog phases_jdialog;
    private javax.swing.JDialog phases_jdialog1;
    private javax.swing.JTabbedPane phases_tab;
    private javax.swing.JTextField prenom_txt;
    private javax.swing.JTextField prenom_txt1;
    private javax.swing.JTextField prenom_txtcheck;
    private javax.swing.JTextField prenom_txtcheck2;
    private raven.progressindicator.ProgressIndicator progressIndicator;
    static javax.swing.JTable qualification_jtable;
    private javax.swing.JTextField qualification_textfield;
    private javax.swing.JTextField qualification_updated;
    private javax.swing.JComboBox<String> qualifications_combo;
    private javax.swing.JComboBox<String> qualifications_combo2;
    private javax.swing.JLabel search_ic;
    private javax.swing.JLabel search_ic1;
    private javax.swing.JLabel search_ic2;
    private javax.swing.JTextField search_textField_op;
    private javax.swing.JTextField search_textField_qualif;
    private javax.swing.JTextField search_textField_suivi;
    private javax.swing.JTable suivi_jtable;
    // End of variables declaration//GEN-END:variables

}
