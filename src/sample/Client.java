package sample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

//right now we are making in  Jframe
public class Client extends JFrame {

    JPanel panel;
    JTextField textField;
    JButton button;
    JTextArea textArea;

    static Socket s;
    static DataInputStream is;
    static DataOutputStream os;


    public Client(){
        //we have set the size of the frame in the below 2 lines
        setSize(350, 500);
        setLocation(900, 100);

        //we are now going to add a green bar on the top of our frame. This we will implement by adding a panel
        panel = new JPanel();
        panel.setLayout(null);
        //we are now moving the panel in our desired position and giving it the height and width we want
        panel.setBounds(0,0,350, 35);
        panel.setBackground(new Color(7, 94, 84));
        add(panel);


        //now we are going to load one of the icons on the JFrame.
        //we will use the classLoader
        ImageIcon imageIcon= new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        //for the scaling of the image: we have the below line
        Image image= imageIcon.getImage().getScaledInstance(20,20, Image.SCALE_SMOOTH);
        ImageIcon imageIcon2= new ImageIcon(image);

        ImageIcon imageIcon3= new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image image2= imageIcon3.getImage().getScaledInstance(10,18, Image.SCALE_SMOOTH);
        ImageIcon imageIcon4= new ImageIcon(image2);

        ImageIcon imageIcon5= new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image image3= imageIcon5.getImage().getScaledInstance(40,40, Image.SCALE_SMOOTH);
        ImageIcon imageIcon6= new ImageIcon(image3);


        //before we use any image, we need to add it to a label
        JLabel label= new JLabel(imageIcon2);

        //to this label we are adding a mouse listener. to close the window when "back" is clicked
        //comments for below lines written in Onenote

        //back button
        setLayout(null);
        label.setBounds(5, 5, 30, 30);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });


        //settings button
        JLabel label2= new JLabel(imageIcon4);
        label2.setBounds(310, 10, 10, 18);

        //picture
        JLabel label3= new JLabel(imageIcon6);
        label3.setBounds(35, 5, 30, 30);


        //Friend1
        JLabel label4= new JLabel("Friend1");
        label4.setFont(new Font("SAN_SERIF", Font.BOLD, 15));
        label4.setForeground(Color.WHITE);
        label4.setBounds(75, 1, 60, 30);

        //Active now
        JLabel label5= new JLabel("Active now");
        label5.setFont(new Font("SAN_SERIF", Font.ITALIC, 7));
        label5.setForeground(Color.WHITE);
        label5.setBounds(75, 13, 60, 30);


        panel.add(label);
        panel.add(label2);
        panel.add(label3);
        panel.add(label4);
        panel.add(label5);

        //the below method removes the close, maximise, minimise buttons that come default when running.
        setUndecorated(true);
        this.textField= new JTextField();
        textField.setBounds(5, 475, 250, 20 );
        this.textField.setFont(new Font("SAN_SERIF", Font.PLAIN, 15));
        add(textField);

        this.button= new JButton("SEND");
        button.setForeground(Color.WHITE);
        button.setBounds(250, 475,100 , 20);
        button.setBackground((new Color(7, 94, 84)));
        add(button);


        textArea= new JTextArea();
        textArea.setBounds(5, 40 ,340, 420);
        //below line prevents us from changing the chats that we sent. by default the textarea is editable.
        //after being set to false it can only be changed by the setters and getter of the textArea.
        textArea.setEditable(false);
        textArea.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        //ctrl, click the 2 methods below and read their descriptions.
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        add(textArea);


        //first try my way then try his way
        this.button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String msg= textField.getText();
                textArea.append("\n\t\t\t\t\t\t"+ msg);
                textField.setText("");

                try {
                    os.writeUTF(msg);
                } catch (IOException ioException) {

                }
            }
        });


    }


    public static void main(String[] args)  {
        Client client= new Client();
        //the setVisible method is how we basically make the window visible
        client.setVisible(true);

        try{
             s= new Socket(InetAddress.getLocalHost(), 7777 );
             is= new DataInputStream(s.getInputStream());
             os= new DataOutputStream(s.getOutputStream());

             while(true){
                 String msg= is.readUTF();
                 client.textArea.append("\n"+msg);
             }



        }
        catch(Exception e){

        }

    }
}
