/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package txteditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.*;

public class Translator extends javax.swing.JFrame {

    String text;
    String result;
    JLabel jLabel1;
    JScrollPane jScrollPane1;
    JScrollPane jScrollPane2;
    JTextArea jTextArea1;
    JLabel jLabel2;
    JTextArea jTextArea2;
    JButton jButton1;

    public Translator() 
    {
        initComponents();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(350, 180, 400, 300);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    public void initComponents() {

        jLabel1 = new JLabel();
        jScrollPane1 = new JScrollPane();
        jTextArea1 = new JTextArea();
        jLabel2 = new JLabel();
        jScrollPane2 = new JScrollPane();
        jTextArea2 = new JTextArea();
        jButton1 = new JButton();
        jTextArea1.setLineWrap(true);
        jTextArea2.setLineWrap(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        /*设置图标*/
       ImageIcon icon=new ImageIcon("src\\image\\2.png"); 
        setIconImage(icon.getImage()); 

        
        jLabel1.setText("请输入您要翻译的内容：");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel2.setText("翻译结果：");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton1.setText("翻译");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addContainerGap(236, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel2)
                                                .addComponent(jButton1))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jScrollPane2))
                                        .addGap(72, 72, 72))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>    

    class ReadByPost extends Thread {//在翻译按钮中设置监听器，按下翻译后执行该方法
        @Override
        public void run() {
            try {
                /*和有道建立连接 */
                URL url = new URL("http://fanyi.youdao.com/openapi.do");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.addRequestProperty("encoding", "UTF-8");
                /*
                 public void addrequestproperty(string key,string value)   
                 添加由键值对指定的一般请求属性。此方法不会改写与相同键关联的现有值。 
                 参数： key - 用于识别请求的关键字（例如，"accept"）   value - 与该键关联的值。 
                 */

                connection.setDoInput(true);// 使用 URL 连接进行输出
                connection.setDoOutput(true);// 使用 URL 连接进行输入

                connection.setRequestMethod("POST");// 设置URL请求方法 ，为post 
                /*
                 post方式：就传输方式讲参数会被打包在数据报中传输，
                 从CONTENT_LENGTH这个环境变量中读取，便于传送较大一些的数据，
                 同时因为不暴露数据在浏览器的地址栏中，安全性相对较高，但这样的处理效率会受到影响。
                 */

                OutputStream os = connection.getOutputStream();////获取URLConnection对象对应的输出流
                OutputStreamWriter osw = new OutputStreamWriter(os);//将字节流转换为字符流。
                BufferedWriter bw = new BufferedWriter(osw);//转换为有缓存的字符流

                text = jTextArea1.getText().toString();//得到文本框中要翻译的内容
                String a = "keyfrom=fadabvaa&key=522071532&type=data&doctype=json&version=1.1&q=" + text;
                 //调用有道翻译数据接口，将要翻译的内容递交
                bw.write(a);//发送请求
                bw.flush();//清空缓存

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                BufferedReader br = new BufferedReader(isr);//定义BufferedReader输入流来读取URL的响应

                String line;
                /*截取翻译内容*/
                while ((line = br.readLine()) != null) {
                    String[] arr = line.split("]");
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].contains("translation")) {
                            result = arr[i].substring(17, arr[i].length() - 1);
                            jTextArea2.setText(result);//将翻译结果在文本框中显示
                        }
                    }
                }

                /*关闭输入、输出流*/
                bw.close();
                osw.close();
                os.close();

                br.close();
                isr.close();
                is.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        new ReadByPost().start();

    }

    
}