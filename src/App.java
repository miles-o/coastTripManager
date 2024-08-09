// program to report info on people for the coast trip, whether they are coming, have paid, etc.
// created by Miles O'Hara-Dewhurst
// created on 5/8/2024
// last edited on 5/8/2024


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class App {
    public static void main(String[] args) {
        JFrame f = newJFrame();
    }

    private static JFrame newJFrame() {
        Font font = new Font("SansSerif", Font.PLAIN, 25);
        JFrame f = new JFrame();//creating instance of JFrame
        f.setMinimumSize(new Dimension(1000, 1000));

        JTextField tf = new JTextField();
        tf.setBounds(f.getWidth()/2 - 150,50, 300,50);
        tf.setFont(font);
        tf.setText("File chooser");
        tf.setEditable(false);

        JButton closeButton =new JButton("Close");
        closeButton.setBounds(f.getWidth()/2 - 50, (int) (f.getHeight() * 0.8),100, 40);

        JButton b1 = getjButton(f);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        f.add(b1);f.add(closeButton);f.add(tf);

        f.setSize(400,500);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
        return f;
    }


    private static JButton getjButton(JFrame f) {
        JButton b1 = new JButton("Choose file");
        b1.setBounds(f.getWidth()/2 - 50, 140, 100, 40);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jf = new JFileChooser();
                jf.setCurrentDirectory(new File(System.getProperty("user.home") + "/downloads"));
                int result = jf.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jf.getSelectedFile();
                    sortPeople(selectedFile, f);
                }
            }
        });
        return b1;
    }

    private static void sortPeople(File inFile, JFrame f) {
        Curb curb = new Curb();
        House house = new House();
        try {
            BufferedReader templateReader = new BufferedReader(new FileReader("peopleTemplate.csv"));
            BufferedReader peopleReader = new BufferedReader(new FileReader(inFile.getAbsolutePath()));
            String line;
            ArrayList<String> people = curb.getPeople();
            int i = 0;
            while ((line = templateReader.readLine()) != null) {
                String[] values = line.split(",");
                String name = values[0];
                String coming = values[1];
                Person person = new Person(name, coming);
                curb.addPerson(person);
            }
            int numOfPeople = curb.getAmtOfPeople();
            while ((line = peopleReader.readLine()) != null) {
                while (i < numOfPeople) {
                    String[] values = line.split(",");
                    String name = values[0].toLowerCase();
                    if (Objects.equals(name, "name")) break;
                    else {
                        String curbName = people.get(i);
                        String number = values[1];
                        String email = values[2];
                        String coming = values[3].toLowerCase(Locale.ROOT);
                        String drinks;
                        try {
                            drinks = values[4];
                        } catch (Exception ex) {
                            drinks = "";
                        }
                        if (Objects.equals(name, curbName)) {
                            curb.removePerson(name);
                            Person person = new Person(name, number, email, coming, drinks);
                            house.addPerson(person, drinks);
                            numOfPeople--;
                        }
                    }
                    i++;
                }
                i = 0;
            }
            peopleReader.close();
            templateReader.close();
            JButton saveButton = new JButton("Save file");
            saveButton.setBounds(f.getWidth()/2 - 50, 180, 100, 40);
            f.add(saveButton);
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    writeFile(house.calculatePrice(), house.getDrinks(), curb.getAmtOfPeople(), house.getAmtOfPeople(), house.getPrice());
                }
            });
        } catch (Exception ex) {
            System.out.println("Error " + ex);
        }
    }
    public static void writeFile(double inPricePerPerson, int inDrinks, int inCurbPeople, int inHousePeople, double inPrice) {
        try {
            JFileChooser f = new JFileChooser();
            int option = f.showSaveDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                BufferedWriter dataWriter = new BufferedWriter(new FileWriter(f.getSelectedFile().getAbsolutePath()));

                dataWriter.write("house price," + inPricePerPerson + "\n");
                dataWriter.write("house and alcohol predicted price," + ((((inDrinks / inHousePeople) * 2.41) * 5) + inPricePerPerson) + "\n");
                dataWriter.write("confirmed," + inHousePeople + "\n");
                dataWriter.write("unconfirmed," + inCurbPeople + "\n");
                dataWriter.write("min pp," + (inPrice / (inHousePeople + inCurbPeople)) + "\n");
                dataWriter.write("total drinks," + (inDrinks * 5) + "\n");
                dataWriter.write("avg drinks pp," + (inDrinks / inHousePeople) + "\n");
                dataWriter.flush();
                dataWriter.close();

            }
        } catch (Exception ex) {
            System.out.println("Error " + ex);
        }
    }


}
