package com.bugaco.ui;

import javax.jnlp.*;

/**
 * <p>Title: Mioritic</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: bugaco</p>
 *
 * @author Ivica Ceraj
 * @version 1.0
 */
public class ExportDialog {
    public ExportDialog() {
    }

    public static void exportDialog( javax.swing.JComponent parent,
                                     String title,
                                     Object message,
                                     String textMessage)
    {
        String[] str = new String[ 3 ] ;
        str[ 0 ] = "Copy to clipboard" ;
        str[ 1 ] = "Save file" ;
        str[ 2 ] = "Close" ;
        int ret = javax.swing.JOptionPane.showOptionDialog( parent , message , title ,
                javax.swing.JOptionPane.DEFAULT_OPTION,
                javax.swing.JOptionPane.PLAIN_MESSAGE,
                null,
                str,
                "Close"
                ) ;
        if( ret == 0 )
        {
            ClipboardService clipboard = null;
            try {
                clipboard = (javax.jnlp.ClipboardService) javax.jnlp.
                            ServiceManager.lookup("javax.jnlp.ClipboardService");
                if( clipboard != null )
                {
                    clipboard.setContents( new java.awt.datatransfer.StringSelection( textMessage ) );
                }
            } catch (UnavailableServiceException ex) {
                javax.swing.JOptionPane.showMessageDialog( parent , "Clipboard not available on this version on Web Start" , "ERROR" , javax.swing.JOptionPane.ERROR_MESSAGE );
            }
        }
        if( ret == 1 )
        {
            FileSaveService save = null;
            try {
                save = (javax.jnlp.FileSaveService) javax.jnlp.ServiceManager.
                       lookup("javax.jnlp.FileSaveService");
                if( save != null )
                {
                    System.out.println( save.toString() ) ;
                    javax.jnlp.FileContents file = save.saveFileDialog( null, null, new java.io.ByteArrayInputStream( textMessage.getBytes()) , null ) ;
                    file.getOutputStream( true ).write( textMessage.getBytes() );
                    file = null ;
                }

            } catch (UnavailableServiceException ex1) {
                javax.swing.JOptionPane.showMessageDialog( parent , "Save to local system not available on this version on Web Start" , "ERROR" , javax.swing.JOptionPane.ERROR_MESSAGE );
            } catch (java.io.IOException ex2)
            {
                javax.swing.JOptionPane.showMessageDialog( parent , "Save failed with :" + ex2.getMessage() , "ERROR" , javax.swing.JOptionPane.ERROR_MESSAGE );
            }
        }
    }



}
