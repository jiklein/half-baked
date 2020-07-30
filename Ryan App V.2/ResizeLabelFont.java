import javax.swing.*;
public class ResizeLabelFont {
    
    
    public static void main(String[] args) {
        JFrame f = new JFrame();
        JPanel p = new JPanel();
        f.setBounds(0,0,500,500);
        p.setBounds(0,0,500,500);
        p.setLayout(null);
        
        JLabel l = new JLabel("Hi");
        String s = "<html>";
        s+="JimSlimhuhiuhhi "+"<div align=right> 10 <br>1<div/>";
        
        
        
        s+="<html/>";
        l.setText(s);
        l.setBounds(0,0,300,60);
        
        p.add(l);
        f.add(p);
        p.setVisible(true);
        f.setVisible(true);
        
    }
    
}