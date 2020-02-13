import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Class to convert between arabic and roman both ways in real time using GUI
 * @author Johnathan Poeschel
 * @version 1.0, 11 Oct 2019
 */
public class ArabicToRoman {
    /**
     * string to hold arabic input
     */
    private String arabicInput = "";
    /**
     * string to hold roman input
     */
    private String romanInput = "";
    /**
     * String array holds the roman numeral characters
     */
    private final String[] romanNumerals = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
    /**
     * int array to hold corresponding values to the roman numerals
     */
    private final int[] arabicValues = {1000,900,500,400,100,90,50,40,10,9,5,4,1};

    /**
     * constructor creates GUI and watches for inputs
     */
    ArabicToRoman() {
        JFrame frame = new JFrame("Arabic-Roman Translation");
        frame.setSize(500,150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        JTextField arabic = new JTextField();
        JLabel arabicLabel = new JLabel("Arabic");
        JTextField roman = new JTextField();
        JLabel romanLabel = new JLabel("Roman");
        arabic.setPreferredSize(new Dimension(100,50));
        roman.setPreferredSize(new Dimension(100,50));
        arabic.addKeyListener(new KeyAdapter(){
            /**
             *key released function watches for event inside arabic
             * @param e button released
             */
            @Override
            public void keyReleased(KeyEvent e){
                if(e.getKeyCode() != KeyEvent.VK_SHIFT && e.getKeyCode() != KeyEvent.VK_LEFT && e.getKeyCode() != KeyEvent.VK_RIGHT){
                    arabicInput =  arabicInput + e.getKeyChar();
                }
                if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                    arabicInput = arabic.getText();
                }
                roman.setText(convertToRoman(arabicInput)); //convert to roman and display in roman text box
            }
        });
        roman.addKeyListener(new KeyAdapter() {
            /**
             * key released function watches for event inside roman
             * @param e button released
             */
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() != KeyEvent.VK_SHIFT && e.getKeyCode() != KeyEvent.VK_LEFT && e.getKeyCode() != KeyEvent.VK_RIGHT){
                    romanInput = romanInput+e.getKeyChar();
                }
                if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                    romanInput = roman.getText();
                }
                if(roman.getText().equals("")){
                    arabic.setText("");
                }
                romanInput = romanInput.toUpperCase(); //turns lowercase inputs into uppercase before converting to arabic
                arabic.setText(convertToArabic(romanInput)); //convert to arabic and display in arabic text box
            }
        });
        panel.add(arabic);
        panel.add(arabicLabel);
        panel.add(roman);
        panel.add(romanLabel);
        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * converts arabic to roman
     * @param stringArabicInput input from arabic
     * @return string of roman numerals
     */
    private String convertToRoman(String stringArabicInput){
        if(stringArabicInput.matches("[0-9]+")){
            int numberArabicInput = Integer.parseInt(stringArabicInput);
            if(numberArabicInput <=0 || numberArabicInput >4000){return "Invalid Input";}
            StringBuilder letterform = new StringBuilder();
            int i = 0;
            while(numberArabicInput>0){ //while arabic number is greater than zero
                if(numberArabicInput - arabicValues[i] >=0){ // if you can subtract roman numeral amount starting with the largest amount
                    numberArabicInput = numberArabicInput-arabicValues[i]; //subtract that number
                    letterform.append(romanNumerals[i]); //append corresponding roman numeral to stringbuilder
                }
                else{ //if you can subtract anymore of the current type without gong negative, move to next smallest roman numeral, append as many times as you can subtract without going negative
                    i++;
                }
            }
            return letterform.toString();
        }
        if(stringArabicInput.compareTo("")==0){
            return "";
        }
        else {
            return "Invalid Input";
        }
    }

    /**
     * converts roman numerals to arabic numbers
     * @param romanInput input from roman
     * @return string of arabic numbers
     */
    private String convertToArabic(String romanInput){
        for(int i =1; i<4000; i++){
            if(convertToRoman(Integer.toString(i)).compareTo(romanInput)==0){
                return Integer.toString(i);
            }
        }
        if(romanInput.compareTo("")==0){
            return "";
        }
        return "Invalid Input";
    }
}
