
package txteditor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author cfenglv
 */
public class TextFindReplace implements ActionListener
{
    private JFrame frObj;
    private JTextArea taObj;
    private JTextField tfObj1,tfObj2;
    private JButton find_button;
    private JButton replace_button;
    private JButton replace_all_button;
    private JButton exit_button;
    private JPanel pObj2,pObj3,pObj4,pObj5;
    private JLabel find_label, replace_label;
    boolean boolObj = false;
    JDialog diaObj;
    Label textLab;
    JButton ok_button = new JButton("OK");
    
    int cnt; //count the find times
    String check;
    int matchNum;
    public void create(JTextArea jt)
    {
        
        cnt = 1;
        check = "";
        //变量matchNum代表字符串匹配的次数，初始值为0
        matchNum = 0;
        //下面是创建主界面
        frObj = new JFrame("文本的查找与替换");
        frObj.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //taObj代表界面上的文本区域
        taObj = jt;
        //tfObj1代表用于输入待查找或替换前的字符串的文本框
        tfObj1 = new JTextField();
        //tfObj2代表用于输入替换后的字符串的文本框
        tfObj2 = new JTextField();
        //初始化两个按钮对象，分别用来实现”查找“和”替换“操作
        find_button = new JButton("Find Next");
        replace_button = new JButton("Replace");
        replace_all_button = new JButton("Replace all");
        exit_button = new JButton("Exit");
        //下面的三个Label对象用于显示有关的提示信息
        find_label = new JLabel("待查找或替换前的字符串：");
        replace_label = new JLabel("替换后的字符串：");
        //接下来的5个Panel对象用于控制主界面上各组件的位置和大小
        pObj2 = new JPanel();
        pObj3 = new JPanel();
        pObj4 = new JPanel();
        pObj5 = new JPanel();
        //pObj2用于控制第一个文本框和相关提示信息的相对位置
        pObj2.setLayout(new BorderLayout());
        pObj2.add("North", find_label);
        pObj2.add("Center",tfObj1);
        //pObj3用于控制第二个文本框和相关提示信息的相对位置
        pObj3.setLayout(new BorderLayout());
        pObj3.add("North", replace_label);
        pObj3.add("Center",tfObj2);
        //pObj4用于控制Find按钮和Replace按钮的相对位置
        pObj4.setLayout(new FlowLayout(FlowLayout.CENTER,10,20));
        pObj4.add(find_button);
        pObj4.add(replace_button);
        pObj4.add(replace_all_button);
        pObj4.add(exit_button);
        //pObj5用于控制pObj2、pObj3和pObj4的相对位置
        pObj5.setLayout(new GridLayout(3,1));
        pObj5.add(pObj2);
        pObj5.add(pObj3);
        pObj5.add(pObj4);
        //最后通过frObj控制整体的效果
        frObj.setLayout(new GridLayout(1,1));
        frObj.add(pObj5);
        //为Find按钮、Replace按钮和主窗口添加事件监听器
        find_button.addActionListener(this);
        replace_button.addActionListener(this);
        replace_all_button.addActionListener(this);
        exit_button.addActionListener(this);
 
        //下面两个语句设置主界面的大小并让主界面可见
        frObj.setBounds(350, 180, 400,320);
        frObj.setSize(400,320);
        frObj.setVisible(true);
         
        /*下面的语句用于创建一个对话框，当用户单击Find按钮或者Replace按钮后，根据结果显示
        一个有关结果信息的对话框，对话框上有一个Label组件和一个OK按钮，Label组件用于显示
        查找或替换字符串的次数*/
        diaObj = new JDialog(frObj);
        diaObj.setLayout(new FlowLayout(FlowLayout.CENTER,40,20));          
        textLab = new Label("");
        diaObj.add(textLab);
        diaObj.add(ok_button);
        ok_button.addActionListener(this);
        diaObj.setBounds(750, 200, 200, 120);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == exit_button)
        {
            check = "";
            frObj.dispose();
            cnt = 0;
        }
        else if(e.getSource() == ok_button)
        {
            diaObj.setVisible(false);
        }
        else 
        {
            /*String型变量strObj1和strObj2获得文本区域和第一个文本框中的文字内容*/
            String strObj1 = taObj.getText();
            String strObj2 = tfObj1.getText();
            //实例化一个matchFun类的对象
            matchFun classObj = new matchFun();
            //下面的if语句处理单击Find按钮事件    
            if (e.getSource() == find_button) 
            {
                if(strObj2.equals(check))
                {
                    if(cnt < matchNum)
                        ++cnt;
                    else 
                        cnt = 1;
                }
                else
                {
                    cnt = 1;
                    check = strObj2;
                }
                //通过调用matchFun类的方法strFind计算出字符串匹配的次数
                //matchNum = classObj.strFind(strObj1, strObj2, cursorPos);
                matchNum = classObj.strFind(taObj, strObj1, strObj2, 0, cnt);
                
                //下面的一行语句重新设置对话框上Label对象的文本内容
                textLab.setText("Find "+ cnt +" out of " + matchNum);
                diaObj.setVisible(true);
            }
            //接下来的if语句处理单击Replace all 按钮事件
            if (e.getSource() == replace_all_button) 
            {
                //变量strObj3获得第二个文本框中的字符串
                String strObj3 = tfObj2.getText();
                //通过调用matchFun类中的strReplace按钮计算字符串匹配次数
                //matchNum = classObj.strReplace(strObj1, strObj2, strObj3, cursorPos);
                matchNum = classObj.strReplaceAll(taObj, strObj1, strObj2, strObj3, 0);
                //重新设置对话框上Label对象的文本内容
                textLab.setText("Replaced all " + matchNum + " text");
                //下面的语句用于刷新字符串替换后文本区域的文字显示
                StringBuffer taText = classObj.repStr;
                taObj.setText(taText.toString());
                diaObj.setVisible(true);
            }
            if(e.getSource() == replace_button)
            {
                String strObj3 = tfObj2.getText();
                classObj.strReplace(taObj, strObj3);
                textLab.setText("Replaced the selected text");
                diaObj.setVisible(true);
            }
        }
    }
}