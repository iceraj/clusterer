package com.bugaco.ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.*;
import javax.swing.JButton;
import com.bugaco.ui.models.users.DocumentModelUser;
import com.bugaco.ui.models.DocumentModel;
import java.awt.event.ActionEvent;
import javax.jnlp.FileOpenService;
import java.io.*;
import javax.swing.text.*;

/**
 * <p>Title: Mioritic</p>
 *
 * <p>Description: Clusterer 3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: bugaco</p>
 *
 * @author Ivica Ceraj
 * @version 1.0
 */
public class LoadBean extends JPanel implements DocumentModelUser {

    class LoadFile implements java.awt.event.ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            java.io.InputStream is = null ;
            long length = 0 ;
            try
            {
                javax.swing.JFileChooser fileChooser = new javax.swing.
                        JFileChooser();
                int retVal = fileChooser.showOpenDialog(jLoadPanel);
                if( retVal == javax.swing.JFileChooser.APPROVE_OPTION )
                {
                        is = new java.io.FileInputStream(fileChooser.
                                getSelectedFile());
                    length = fileChooser.getSelectedFile().length() ;
                }
                System.err.println(retVal + " " + fileChooser + " " + length );
            } catch (FileNotFoundException ex5) {
            ex5.printStackTrace() ;
        } catch( java.security.AccessControlException ex )
            {
                FileOpenService fos = null;
                try {
                    fos = (FileOpenService) javax.jnlp.ServiceManager.lookup(
                            "javax.jnlp.FileOpenService");
                    javax.jnlp.FileContents content = fos.openFileDialog(null, null);
                    if( content != null )
                    {
                        is = content.getInputStream() ;
                        length = content.getLength() ;
                    }
                } catch (Exception ex2) {
                    ex2.printStackTrace();
                    javax.swing.JOptionPane.showMessageDialog( (java.awt.Component) (e.getSource()) , new javax.swing.JLabel( "Can not load file due to: " + ex2.getMessage() ) );
                } catch (  java.lang.NoClassDefFoundError ex3 )
                {
                    ex3.printStackTrace();
                    javax.swing.JOptionPane.showMessageDialog( (java.awt.Component) (e.getSource()) , new javax.swing.JLabel( "Can not load file due to: " + ex3.getMessage() ) );

                }
            }
            if( is != null )
            {
                try {
                    model.remove(0, model.getLength());
                    java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader( is ) );
                    String line = null;
                    StringBuffer sb = new StringBuffer() ;
                    while( true )
                    {
                        line = reader.readLine();
                        if( line == null )
                        {
                            break ;
                        }
                        sb.append( line ) ;
                        sb.append( "\r\n" ) ;
                    }
                    model.insertString( 0 , sb.toString() , null );
                    reader.close();
                    is.close();
                } catch (BadLocationException ex3) {
                    ex3.printStackTrace();
                } catch (IOException ex4) {
                    ex4.printStackTrace();
                }
            }
        }

    }

    public LoadBean() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        setDocumentModel( null );
        this.setLayout( boxLayout );
        jLoad.setText("Import file");
        jLoad.addActionListener( new LoadFile() );
        jContent.setText("jTextArea1");
        jScrollPane.getViewport().add(jContent);

        jLoadPanel.setLayout( new java.awt.BorderLayout() ) ;
        jLoadPanel.add(jLoad , java.awt.BorderLayout.WEST );
        this.add(jScrollPane, java.awt.BorderLayout.CENTER);
        this.add(jLoadPanel);
    }
    DocumentModel model = null ;
    JScrollPane jScrollPane = new JScrollPane();
    javax.swing.BoxLayout boxLayout = new javax.swing.BoxLayout( this , javax.swing.BoxLayout.PAGE_AXIS ) ;
    JTextArea jContent = new JTextArea( 10 , 60 );
    GridLayout gridLayout1 = new GridLayout();
    JPanel jLoadPanel = new JPanel();
    JButton jLoad = new JButton();

    public void setDocumentModel(DocumentModel model) {
        if( model != null )
        {
            jContent.setDocument(model);
        }
        else
        {
            jContent.setDocument( new javax.swing.text.PlainDocument() ) ;
        }
        this.model = model ;
    }

    public DocumentModel getDocumentModel() {
        return model ;
    }
}
