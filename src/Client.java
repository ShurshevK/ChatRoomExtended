

import org.w3c.dom.events.MouseEvent;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

import java.util.Calendar;
import java.text.SimpleDateFormat;import javax.swing.*;

public class Client implements ActionListener{

    JPanel p1;
    JTextField t1;
    JButton b1;
    static JPanel a1;
    static JFrame f1 = new JFrame();

    static Box vertical = Box.createVerticalBox();


    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;

    Boolean typing;

    Client(){
        f1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 395, 70);
        f1.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("3.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel I1 = new JLabel(i3);
        I1.setBounds(5, 17, 30, 30);
        p1.add(I1);

        I1.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }
        });



        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("frog.jpg"));
        Image i5 = i4.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel I2 = new JLabel(i6);
        I2.setBounds(30, 5, 60, 60);
        p1.add(I2);


        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("video.png"));
        Image i8 = i7.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel I5 = new JLabel(i9);
        I5.setBounds(230, 5, 60, 60);
        p1.add(I5);

        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("phone.png"));
        Image i12 = i11.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon i13 = new ImageIcon(i12);
        JLabel I6 = new JLabel(i13);
        I6.setBounds(290, 20, 35, 35);
        p1.add(I6);

        ImageIcon i14 = new ImageIcon(ClassLoader.getSystemResource("3icon.png"));
        Image i15 = i14.getImage().getScaledInstance(6, 15, Image.SCALE_DEFAULT);
        ImageIcon i16 = new ImageIcon(i15);
        JLabel I7 = new JLabel(i16);
        I7.setBounds(340, 20, 13, 25);
        p1.add(I7);


        JLabel I3 = new JLabel("Kirill Frog");
        I3.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        I3.setForeground(Color.WHITE);
        I3.setBounds(100, 10, 500, 18);
        p1.add(I3);

        JLabel I4 = new JLabel("Active Now");
        I4.setFont(new Font("SAN_SERIF", Font.PLAIN, 11));
        I4.setForeground(Color.WHITE);
        I4.setBounds(100, 25, 500, 20);
        p1.add(I4);

        Timer t = new Timer(1, new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                if(!typing){
                    I4.setText("Active Now");
                }
            }
        });

        t.setInitialDelay(2000);


        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f1.add(a1);


        t1 = new JTextField();
        t1.setBounds(5, 333,  250, 30);
        t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f1.add(t1);

        t1.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent ke){
                I4.setText("typing...");

                t.stop();

                typing = true;
            }

            public void keyReleased(KeyEvent ke){
                typing = false;

                if(!t.isRunning()){
                    t.start();
                }
            }
        });

        b1 = new JButton ("Send");
        b1.setBounds(260, 333, 100, 30);
        b1.setBackground(new Color(7, 94, 84));
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        b1.addActionListener(this);
        f1.add(b1);

        f1.getContentPane().setBackground(Color.WHITE);
        f1.setLayout(null);
        f1.setSize(380, 405);
        f1.setLocation(400, 200);
        f1.setUndecorated(false);
        f1.setVisible(true);



    }

    public void actionPerformed(ActionEvent ae){

        try{
            String out = t1.getText();

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            //a1.add(p2);
            dout.writeUTF(out);
            t1.setText("");
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static JPanel formatLabel(String out){
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));

        JLabel l1 = new JLabel("<html><p style = \"width : 150px\">"+out+"</p></html>");
        l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        l1.setBackground(new Color(37, 208, 105));
        l1.setOpaque(true);
        l1.setBorder(new EmptyBorder(15,15,15,50));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel l2 = new JLabel();
        l2.setText(sdf.format(cal.getTime()));

        p3.add(l1);
        p3.add(l2);
        return p3;
    }

    public static void main(String[] args){
        new Client().f1.setVisible(true);

        try{

            s = new Socket("127.0.0.1", 6001);
            din  = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            String msginput = "";

            while(true){
                a1.setLayout(new BorderLayout());
                msginput = din.readUTF();
                JPanel p2 = formatLabel(msginput);
                JPanel left = new JPanel(new BorderLayout());
                left.add(p2, BorderLayout.LINE_START);

                vertical.add(left);
                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);
                f1.validate();
            }

        }catch(Exception e){}
    }
}