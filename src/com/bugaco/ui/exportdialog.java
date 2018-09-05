package com.bugaco.ui;

import java.awt.datatransfer.*;
import java.awt.Toolkit;

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
            StringSelection selection = new StringSelection(textMessage);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }
        if( ret == 1 )
        {
            java.io.FileOutputStream os;
            try {
                javax.swing.JFileChooser fileChooser = new javax.swing.
                        JFileChooser();
                int retVal = fileChooser.showSaveDialog(parent);
                if (retVal == javax.swing.JFileChooser.APPROVE_OPTION) {
                    os = new java.io.FileOutputStream(fileChooser.
                            getSelectedFile());
                    os.write(textMessage.getBytes());
                    os.close();
                }
            } catch (java.io.FileNotFoundException exfnf) {
                javax.swing.JOptionPane.showConfirmDialog( parent , "File not found: " + exfnf.getLocalizedMessage()) ;
            } catch (java.io.IOException exio) {
                javax.swing.JOptionPane.showConfirmDialog( parent , "IOException: " + exio.getLocalizedMessage()) ;
            }
        }
    }



}
