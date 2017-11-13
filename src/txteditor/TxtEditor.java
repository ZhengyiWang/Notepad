package txteditor;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

public class TxtEditor extends JFrame implements ActionListener {

    String file_name;
    String file_dir;
    String tempString;
    //the filename and the path since last time saved
    String fileName = "";
    JPanel x = new JPanel();
    JTextArea wen = new JTextArea(20, 50);

    JMenuItem font = new JMenuItem("Font(F)");
    JMenuItem w = new JMenuItem("Word wrap(W)");

    //定义菜单项
    JMenuItem xin = new JMenuItem("New(N)");
    JMenuItem open = new JMenuItem("Open(O)");
    JMenuItem save = new JMenuItem("Save(S)");
    JMenuItem lsave = new JMenuItem("Save as(A)");
    JMenuItem tui = new JMenuItem("Exit(X)");

    JMenuItem cut = new JMenuItem("Cut(T)");
    JMenuItem copy = new JMenuItem("Copy(C)");
    JMenuItem cast = new JMenuItem("Paste(P)");
    JMenuItem delete = new JMenuItem("Delete(L)");
    JMenuItem undo = new JMenuItem("Undo(Z)");
    JMenuItem redo = new JMenuItem("Redo(Y)");
    JMenuItem translator = new JMenuItem("Translator(T)");
    JMenuItem find = new JMenuItem("Find and replace(R)");

    JMenuItem s = new JMenuItem("Status bar(S)");

    // JMenuItem fhi=new JMenuItem("FindHelpItem");
    JMenuItem abi = new JMenuItem("AboutBoxItem");

    JPopupMenu pop = new JPopupMenu();
    JMenuItem pop_cut = new JMenuItem("Cut");
    JMenuItem pop_copy = new JMenuItem("Copy");
    JMenuItem pop_cast = new JMenuItem("Paste");
    JMenuItem pop_delete = new JMenuItem("Delete");
    JMenuItem pop_undo = new JMenuItem("Undo");
    JMenuItem pop_redo = new JMenuItem("Redo");
    
    private UndoManager undomang;
    private Document doc = null;
     
    TxtEditor() {
        super("TextEditer");
        ImageIcon icon=new ImageIcon("src\\image\\1.png"); 
        setIconImage(icon.getImage()); 
        //小小对话框
        setBounds(250, 100, 700, 450);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(TxtEditor.this, "Really Want To Exit", "Seriously?", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    if (e.getWindow() == TxtEditor.this) {
                        System.exit(0);
                    } else {
                        return;
                    }
                }
            }
        });
        undomang = new UndoManager()
        {
            private static final long serialVersionUID = -5960092671497318496L;
            public void undoableEditHappened(UndoableEditEvent e) {
                this.addEdit(e.getEdit());
            }
        };
        doc = wen.getDocument();
        doc.addUndoableEditListener(undomang);
        //set the hotkey
        xin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        cast.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        //identify the panel

        add(new JScrollPane(wen));  //the scroll pane
        wen.setFont(new Font("楷体", Font.PLAIN, 20));

        //create the menu
        JMenuBar cai = new JMenuBar();
        this.setJMenuBar(cai);
        cai.setOpaque(true);
        JMenu jian = new JMenu("File(F)");
        jian.add(xin);
        jian.add(open);
        jian.add(save);
        jian.add(lsave);
        jian.addSeparator();//分界线
        jian.add(tui);
        cai.add(jian);

        JMenu bian = new JMenu("Edit(E)");
        bian.add(cut);
        bian.add(copy);
        bian.add(cast);
        bian.add(delete);
        bian.add(undo);
        bian.add(redo);
        bian.add(translator);
        bian.add(find);
        cai.add(bian);

        pop.add(pop_cut);
        pop.add(pop_copy);
        pop.add(pop_cast);
        pop.add(pop_delete);
        pop.add(pop_undo);
        pop.add(pop_redo);
        wen.add(pop);
        JMenu geshi = new JMenu("Format(O)");

        geshi.add(w);
        geshi.add(font);

        cai.add(geshi);

        JMenu view = new JMenu("View(V)");
        view.add(s);
        cai.add(view);

        JMenu help = new JMenu("Help(H)");
        // help.add(fhi);
        help.add(abi);
        cai.add(help);

        // JMenu
        //add all the listener
        //    ------------------------------
        w.addActionListener(this);
        font.addActionListener(this);
        xin.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        lsave.addActionListener(this);
        tui.addActionListener(this);
        cut.addActionListener(this);
        copy.addActionListener(this);
        cast.addActionListener(this);
        delete.addActionListener(this);
        find.addActionListener(this);
        translator.addActionListener(this);
        undo.addActionListener(this);
        redo.addActionListener(this);
        // fhi.addActionListener(this);
        abi.addActionListener(this);
        pop_cut.addActionListener(this);
        pop_copy.addActionListener(this);
        pop_cast.addActionListener(this);
        pop_delete.addActionListener(this);
        pop_undo.addActionListener(this);
        pop_redo.addActionListener(this);
        
        wen.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    pop.show(wen, e.getX(), e.getY());
                }
            }
        }
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String actionCommand = e.getActionCommand();
        if (e.getSource() instanceof JMenu);
        {
            if (e.getSource() == xin) {
                newfile();
            } else if (e.getSource() == open) {
                openfile();
            } else if (e.getSource() == save) {
                savefile();
            } else if (e.getSource() == lsave) {
                lsavefile();
            } else if (e.getSource() == cut || e.getSource() == pop_cut) {
                cutfile();
            } else if (e.getSource() == copy || e.getSource() == pop_copy) {
                copyfile();
            } else if (e.getSource() == cast || e.getSource() == pop_cast) {
                castfile();
            } else if (e.getSource() == delete || e.getSource() == pop_delete) {
                deletefile();
            } else if (e.getSource() == find) {
                TextFindReplace TFRObj = new TextFindReplace();
                TFRObj.create(wen);
            } else if (e.getSource() == abi) {
                HelpFrame helpFrame = new HelpFrame();
            } else if (e.getSource() == font) {
                FontChooser fc = new FontChooser(wen);

            } else if (e.getSource() == w) {
                wen.setLineWrap(true);//激活自动换行功能
                wen.setWrapStyleWord(true);//激活断行不断字功能
            } else if (e.getSource() == tui) {
                int option = JOptionPane.showConfirmDialog(TxtEditor.this, "Really Want To Exit", "Seriously?", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    return ;
                }
            } else if (e.getSource() == translator) {
                translate();
            } else if (e.getSource() == undo || e.getSource() == pop_undo) {
                if(undomang.canUndo())
                    undomang.undo();
            } else if (e.getSource() == redo || e.getSource() == pop_redo) {
                if(undomang.canRedo())
                    undomang.redo();
            }
            
        }
    }
        //identify the method

    public void newfile() {
        savefile();
        wen.setText(null);
        fileName = "";
    }

    //open
    public void openfile() {
        String fileName = null;
        FileDialog df = new FileDialog(this, "open", FileDialog.LOAD);
        df.setVisible(true);
        //create a new file
        File f = new File(df.getDirectory() + df.getFile());
        //get the filename
        fileName = df.getDirectory() + df.getFile();
        char ch[] = new char[(int) f.length()];
        try {
            //read the data
            BufferedReader bw = new BufferedReader(new FileReader(f));
            bw.read(ch);
            bw.close();
        } catch (FileNotFoundException fe) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException ie) {
            System.out.println("IO error");
            System.exit(0);
        }

        String s = new String(ch);
        wen.setText(s);
    }

    //save
    public void savefile() {
        if (fileName.equals("")) {
            FileDialog df = new FileDialog(this, "save", FileDialog.SAVE);
            df.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent ee) {
                    System.exit(0);
                }
            });
            df.setVisible(true);
            String s = wen.getText();

            try {
                File f = new File(df.getDirectory() + df.getFile());
                fileName = df.getDirectory() + df.getFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write(s, 0, s.length());
                bw.close();
            } catch (FileNotFoundException fe_) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException ie_) {
                System.out.println(" IO error");
                System.exit(0);
            }
        } //if the file have already saved
        else {
            String s = wen.getText();

            try {
                File f = new File(fileName);

                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write(s, 0, s.length());
                bw.close();

            } catch (FileNotFoundException fe_) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException ie_) {
                System.out.println(" IO error");
                System.exit(0);
            }

        }

    }

    //save as
    public void lsavefile() {
        FileDialog df = new FileDialog(this, "save as", FileDialog.SAVE);
        df.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent ee) {
                System.exit(0);
            }
        });
        df.setVisible(true);
        String s = wen.getText();
        try {
            File f = new File(df.getDirectory() + df.getFile());
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(s, 0, s.length());
            bw.close();
        } catch (FileNotFoundException fe_) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException ie_) {
            System.out.println(" IO error");
            System.exit(0);
        }
    }

    //cut
    public void cutfile() 
    {
        tempString = wen.getSelectedText();
        if(tempString != null)
        {
            StringBuffer tmp = new StringBuffer(wen.getText());
            int start = wen.getSelectionStart();
            int len = wen.getSelectedText().length();
            tmp.delete(start, start + len);
            wen.setText(tmp.toString());
        }
    }

    //copy
    public void copyfile() {
        tempString = wen.getSelectedText();
    }

    //paste
    public void castfile() {
        if(tempString != null)
        {
            StringBuffer tmp = new StringBuffer(wen.getText());
            //get the position that need to paste
            int start = wen.getSelectionStart();

                tmp.insert(start, tempString);
            //set the text by the new text 
            wen.setText(tmp.toString());
        }
    }

    //delete
    public void deletefile() {
        StringBuffer tmp = new StringBuffer(wen.getText());
        int start = wen.getSelectionStart();
        int len = wen.getSelectedText().length();
        tmp.delete(start, start + len);
        wen.setText(tmp.toString());
    }

    private void translate() {
        // TODO add your handling code here:
        new Translator().setVisible(true);

    }

    public static void main(String[] args) {
        TxtEditor w = new TxtEditor();
        w.pack();
        w.setVisible(true);

    }
}
