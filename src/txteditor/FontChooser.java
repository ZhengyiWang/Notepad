package txteditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;

public class FontChooser extends JDialog {

    public static final int CANCEL_OPTION = 0;
    public static final int APPROVE_OPTION = 1;
    private JLabel fontTypeFace = null;//字体标签
    private JLabel fontStyle = null;//字形标签
    private JLabel fontSize = null;//字体大小标签
    private JLabel demonstrationLabel = null;//文本示例标签
    private JTextField fontTypeFaceText = null;//字体显示区
    private JTextField fontStyleText = null;//字形显示区
    private JTextField fontSizeText = null;//字体大小显示区
    private JList fontTypeFaceList = null;//字体列表
    private JScrollPane fontTypeFaceListScrollPane = null;//字体列表滚动条
    private JList fontStyleList = null;//字形列表
    private JList fontSizeList = null;//字体大小列表
    private JScrollPane fontSizeListScrollPane = null;//字体大小列表滚动条
    private JButton sure = null;//确定按钮
    private JButton cancle = null;//取消按钮
    private Map sizeMap = null;
    private String returnFontTypeFace = "宋体";//存储字体默认宋体
    private int returnFontStyle = Font.PLAIN;//存储字形默认常规
    private int returnFontSize = 9;//存储字体大小默认9号
    private final JTextArea wen;
    

    FontChooser(JTextArea jt) {
        //super();
        this.setResizable(false);
        this.setTitle("字体选择器");
        this.setBounds(200, 200, 400, 400);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //this.setVisible(true);
        wen = jt;
        init();
    }

    private void init() {
        fontTypeFace = new JLabel("字体：");
        fontStyle = new JLabel("字形：");
        fontSize = new JLabel("大小：");
        demonstrationLabel = new JLabel("示例文本Welcome");
        fontTypeFaceText = new JTextField("宋体");
        fontTypeFaceText.setEditable(false);
        fontStyleText = new JTextField("常规");
        fontStyleText.setEditable(false);
        fontSizeText = new JTextField("12");
        fontSizeText.setEditable(false);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontTypeFaceStr = ge.getAvailableFontFamilyNames();//电脑系统上的字体
        fontTypeFaceList = new JList(fontTypeFaceStr);//初始化字体列表
        fontTypeFaceListScrollPane = new JScrollPane(fontTypeFaceList);//滚动条
        fontStyleList = new JList(new String[]{"常规", "斜体", "粗体", "粗斜体"});

        String[] fontSizeStr = new String[]{"8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72",
            "初号", "小初", "一号", "小一", "二号", "小二", "三号", "小三", "四号", "小四", "五号", "小五", "六号", "小六", "七号", "八号"};
        int sizeVal[] = {8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72, 42, 36, 26, 24, 22, 18, 16, 15, 14, 12, 11, 9, 8, 7, 6, 5};
        sizeMap = new HashMap();
        for (int i = 0; i < fontSizeStr.length; ++i) {
            sizeMap.put(fontSizeStr[i], sizeVal[i]);
        }
        fontSizeList = new JList(fontSizeStr);
        fontSizeListScrollPane = new JScrollPane(fontSizeList);
        sure = new JButton("确定");
        cancle = new JButton("取消");

        Container container = this.getContentPane();
        container.setLayout(null);
        container.add(fontTypeFace);
        fontTypeFace.setBounds(20, 30, 40, 20);
        container.add(fontTypeFaceText);
        fontTypeFaceText.setBounds(20, 60, 100, 20);
        container.add(fontTypeFaceListScrollPane);
        fontTypeFaceList.setSelectedValue("宋体", true);
        fontTypeFaceListScrollPane.setBounds(20, 80, 100, 150);
        container.add(fontStyle);
        fontStyle.setBounds(140, 30, 40, 20);
        container.add(fontStyleText);
        fontStyleText.setBounds(140, 60, 100, 20);
        container.add(fontStyleList);
        fontStyleList.setBounds(140, 80, 100, 150);
        fontStyleList.setSelectedValue("常规", true);
        container.add(fontSize);
        fontSize.setBounds(260, 30, 40, 20);
        container.add(fontSizeText);
        fontSizeText.setBounds(260, 60, 100, 20);
        container.add(fontSizeListScrollPane);
        fontSizeList.setSelectedValue(9, false);
        fontSizeListScrollPane.setBounds(260, 80, 100, 150);
        demonstrationLabel.setBorder(BorderFactory.createTitledBorder("示例"));
        container.add(demonstrationLabel);
        demonstrationLabel.setBounds(20, 250, 220, 100);
        container.add(sure);
        sure.setBounds(270, 250, 60, 30);
        sure.setSelected(true);
        container.add(cancle);
        cancle.setBounds(270, 285, 60, 30);
        fontTypeFaceList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                returnFontTypeFace = (String) fontTypeFaceList.getSelectedValue();
                fontTypeFaceText.setText(returnFontTypeFace);
                demonstrationLabel.setFont(new Font(returnFontTypeFace, returnFontStyle, returnFontSize)
                );
            }
        });
        fontStyleList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String value = (String) fontStyleList.getSelectedValue();
                if (value.equals("常规")) {
                    returnFontStyle = Font.PLAIN;
                }
                if (value.equals("斜体")) {
                    returnFontStyle = Font.ITALIC;
                }
                if (value.equals("粗体")) {
                    returnFontStyle = Font.BOLD;
                } else if (value.equals("粗斜体")) {
                    returnFontStyle = Font.BOLD | Font.ITALIC;
                }
                fontStyleText.setText(value);
                demonstrationLabel.setFont(new Font(returnFontTypeFace, returnFontStyle, returnFontSize));
            }
        });
        fontSizeList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                returnFontSize = (Integer) sizeMap.get(fontSizeList.getSelectedValue());
                fontSizeText.setText(String.valueOf(returnFontSize));
                demonstrationLabel.setFont(new Font(returnFontTypeFace, returnFontStyle, returnFontSize));
            }
        });
        sure.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                wen.setFont((new Font(returnFontTypeFace, returnFontStyle, returnFontSize)));
                doDispose();
            }
        });
        cancle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doDispose();
            }
        });
        this.setVisible(true);
    }

    private void showErrorDialog(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, "错误", JOptionPane.ERROR_MESSAGE);
    }

    public String getFontTypeFace() {
        return returnFontTypeFace;
    }

    public int getFontStyle() {
        return returnFontStyle;
    }

    public int getFontSize() {
        return returnFontSize;
    }

    public void doDispose() {
        this.dispose();
    }

    Font getSelectFont() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    int showFontDialog(String theVersion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
