/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package txteditor;

import java.text.AttributedString;
import javax.swing.JTextArea;

/**
 *
 * @author cfenglv
 */
public class matchFun {

    StringBuffer repStr;
    AttributedString attribstring;

    /*方法strFind用于实现字符串查找，返回匹配的次数*/
    public int strFind(JTextArea jt, String s1, String s2, int pos, int cnt) {
        /*变量i和j分别表示主串和模式串中当前字符串的位置，k表示匹配次数*/
        int i, j, k = 0;
        //pos代表主串中开始比较的位置
        i = pos;
        j = 0;
        while (i < s1.length() && j < s2.length()) {
            if (s1.charAt(i) == s2.charAt(j)) {
                ++i;
                ++j;
                if (j == s2.length()) {
                    //j=s2.length()表示字符串匹配成功，匹配次数加1
                    k = k + 1;
                    //将指示主串和模式串中当前字符的变量i和j进行回退
                    if (cnt > 0) {
                        jt.setSelectionStart(i - j);
                        jt.setSelectionEnd(i);
                    }
                    i = i - j + 1;
                    j = 0;
                    --cnt;
                }
            } else {
                i = i - j + 1;
                j = 0;
            }
        }
        return k;
    }
    /*方法strReplace用于实现字符串替换操作，返回替换的次数*/

    public void strReplace(JTextArea jt, String s) {
        jt.replaceSelection(s);
        return;
    }

    public int strReplaceAll(JTextArea jt, String s1, String s2, String s3, int pos) {
        /*变量i和j分别表示主串和模式串中当前字符串的位置，k表示匹配次数*/
        int i, j, k = 0;
        i = pos;
        j = 0;
        //将s1转化成StringBuffer型进行操作
        repStr = new StringBuffer(s1);

        while (i < repStr.length() && j < s2.length()) {
            if (repStr.charAt(i) == s2.charAt(j)) {
                ++i;
                ++j;
                if (j == s2.length()) {
                    /*j=s2.length()表示字符串匹配成功，匹配次数加1，此外对主串进行字符串替换*/
                    k = k + 1;
                    repStr.replace(i - j, i, s3);
                    //将j进行重新赋值开始新的比较
                    j = 0;
                }
            } else {
                i = i - j + 1;
                j = 0;
            }
        }
        return k;
    }
}
