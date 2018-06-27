import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.text.*;

/**
 * Title: MathTeacher
 * Description: MathTeacher is a basic math teacher program used by young children to practice simple arithmetic.
 * @author Siyue Chen
 * @Date 2018/5/26
 * @Version 1.6
 */
public class MathTeacher {
    //Declaration of instance variables.
    private JTextField ansInput = new JTextField(3); //The text field that users type their answer
    private JLabel questionLabel = new JLabel();    //Label that shows the question
    private JLabel informLabel = new JLabel();   //Inform users whether the answer is correct in the label
    private JLabel correctAns = new JLabel();   //Label that shows correct answers to the previous answer
    private JLabel scoreLabel = new JLabel();   //Label that shows the score

    private int resultNum;  //Result of the arithmetic question
    private int score,count;    //One correct answer for one score & count overall questions
    private int prevNum1, prevNum2, preResultNum;   //Numbers and result from the previous question
    private String operator = "";  //the operator '+ - * /'

    /**
     * main() method that launches the application
     * @param args No args input
     */
    public static void main(String[] args){
        new MathTeacher();
    }

    /**
     * This constructor constructs all the panels
     * including question panel and number pad panel
     * and constructs all the buttons and labels in it
     */
    public MathTeacher(){
        /**
         * quesPanel shows questions, correct answers, notifications and score
         * Check button and reset score button are included
         * Using GridLayout
         */
        JPanel quesPanel = new JPanel();
        quesPanel.setLayout(new GridLayout(7,1,5,5));
        questionGenerate(); //Generate a question randomly
        quesPanel.add(questionLabel);
        quesPanel.add(ansInput);

        JButton checkButton = new JButton("Check"); //Press to check your answer
        quesPanel.add(checkButton);
        checkButton.addActionListener(new ButtonListener());
        quesPanel.add(correctAns);
        ansInput.setDocument(new NumberLimitedDmt()); //Limit user's input
        quesPanel.add(informLabel);
        quesPanel.add(scoreLabel);

        JButton resetButton = new JButton("Reset your score"); //Press to reset the record
        quesPanel.add(resetButton);
        resetButton.addActionListener(new ButtonListener());

        printScore(); //print the score on scoreLabel

        //Set font of the labels and buttons
        questionLabel.setFont(new Font("Arial",Font.BOLD,30));
        ansInput.setFont(new Font("Arial",Font.PLAIN,30));
        checkButton.setFont(new Font("Arial",Font.BOLD,35));
        correctAns.setText("Correct answer shows here");
        correctAns.setFont(new Font("Times New Roman",Font.PLAIN,20));
        informLabel.setFont(new Font("Arial",Font.PLAIN,20));
        informLabel.setText("Valid answer: -9 to 100");
        informLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        scoreLabel.setFont(new Font("Arial",Font.PLAIN,20));
        scoreLabel.setBorder(BorderFactory.createLineBorder(Color.black,2));
        resetButton.setFont(new Font("Arial",Font.ITALIC,20));

        /**
         * numPanel shows number pad and clear button
         * Using GridLayout
         */
        JPanel numPanel = new JPanel();
        numPanel.setLayout(new GridLayout(4,3,20,20));
        String[] numName = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "-", "0", "Clr"};
        JButton[] numPad = new JButton[13];
        //create buttons for number input and clear
        int i;
        for (i=0;i<12;i++) {
            numPad[i] = new JButton(numName[i]);
            numPanel.add(numPad[i]);
            numPad[i].setFont(new Font("Arial",Font.BOLD,30));
            numPad[i].addActionListener(new ButtonListener());
        }

        /**
         * allPanel contains quesPanel and numPanel
         * Using FlowLayout
         */
        JPanel allPanel = new JPanel();
        allPanel.setLayout(new FlowLayout(FlowLayout.CENTER,30,30));
        allPanel.add(quesPanel);
        allPanel.add(numPanel);
        quesPanel.setPreferredSize(new Dimension(300,300));
        numPanel.setPreferredSize(new Dimension(500,300));

        /**
         * frame is created for the window frame
         */
        JFrame frame = new JFrame();
        frame.add(allPanel);
        frame.setSize(900,400);
        frame.setTitle("Math Teacher");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * ButtonListener implements ActionListener
     * set different functions to buttons
     * @author Siyue Chen
     * @Date 2018/5/27
     */
    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //Check button checks user's input
            if (((JButton) (e.getSource())).getText() == "Check") {
                if (ansInput.getText().isEmpty()) {
                    ansInput.requestFocus();
                    return;
                }
                if(ansInput.getText().equals("-")){
                    ansInput.requestFocus();
                    return;
                }
                else {
                    checkAns(); //check whether the input equals to result
                }
                printCorrect(); //Print correct answer to previous answer
                questionGenerate(); //Generate a new question
                ansInput.setText("");
                printScore(); //Print the score that user get so far
            }

            //Clr button clears the input text field
            if (((JButton) (e.getSource())).getText() == "Clr"){
                ansInput.setText("");
                ansInput.requestFocus();
            }

            //Reset button reset the score recorded
            if (((JButton) (e.getSource())).getText() == "Reset your score"){
                count = 0;
                score = 0;
                scoreLabel.setText(score + " correct out of " + count);
            }

            //minus button
            if (((JButton) (e.getSource())).getText() == "-"){
                if (ansInput.getText().isEmpty()){
                    ansInput.setText("-");
                }
                else {
                    return;
                }
            }

            //Numeric keypad
            else {
                //If 3 numbers has been input, do nothing
                if (ansInput.getText().length()==3){
                    return;
                }
                //else, insert number user presses
                else {
                    ansInput.setText(ansInput.getText() + ((JButton) (e.getSource())).getText() );
                }
            }
        }
    }

    /**
     * NumberLimitedDmt extends PlainDocument
     * set limit so that only numbers and minus can be input.
     * When the input number is larger than 100 or smaller than -9,
     * automatically jump to max or min valid number
     * @author Siyue Chen
     * @Date 2018/5/27
     */
    public class NumberLimitedDmt extends PlainDocument {
        public NumberLimitedDmt() {
            super();
        }
        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null){return;}
            if ((getLength() + str.length()) <= 3) {
                char[] upper = str.toCharArray();
                int length = 0;
                if (ansInput.getText().isEmpty()){
                    for (int i = 0; i < upper.length; i++) {
                        if (upper[i] >= '0' && upper[i] <= '9' || upper[i] == '-') {
                            upper[length++] = upper[i];
                        }
                    }
                }
                else {
                    for (int i = 0; i < upper.length; i++) {
                        if (upper[i] >= '0' && upper[i] <= '9') {
                            upper[length++] = upper[i];
                        }
                    }
                }
                super.insertString(offset, new String(upper, 0, length), attr);

                if (ansInput.getText().isEmpty()){
                    return;
                }
                else{
                    if (isNumeric(ansInput.getText())==true) {
                        if (isNumeric(ansInput.getText()) && Integer.parseInt(ansInput.getText()) > 100) {
                            ansInput.setText("100");
                        }
                    }
                    else{
                        String temp = ansInput.getText();
                        temp = temp.replace('-','0');
                        if (Integer.parseInt(temp) > 9) {
                            ansInput.setText("-9");
                        }
                    }
                }
            }
        }
    }

    /**
     * questionGenerate() method generate a random arithmetic question
     * The arguments are integers in the range 1 to 10
     * The arithmetic operations allowed are +, -, x, /
     */
    private void questionGenerate(){
        Random random = new Random();
        int num1 = random.nextInt(9) + 1;
        int num2 = random.nextInt(9) + 1;
        resultNum = 0;
        int select = random.nextInt(4);

        switch(select){
            case 0:
                operator = " + ";
                resultNum = num1 + num2;
                break;
            case 1:
                operator = " - ";
                resultNum = num1 - num2;
                break;
            case 2:
                operator = " * ";
                resultNum = num1 * num2;
                break;
            case 3:
                operator = " / ";
                //restrict the answer is always an integer
                while (num1*num2>10){
                    num1 = 1+(int)(Math.random()*num1);
                }
                resultNum = num1;
                num1 = num1 * num2;
                break;
            default:
                break;
        }
        prevNum1 = num1; prevNum2 = num2; preResultNum = resultNum;
        ansInput.requestFocus();
        //Print a new question
        questionLabel.setText("Question: " + Integer.toString(num1)+ operator +Integer.toString(num2) + " = ");
    }

    /**
     * isNumeric(String s) method verifies whether the passing String is numeric
     * @param s String the input string
     * @return Return TRUE if it is numeric
     */
    public final static boolean isNumeric(String s) {
        return s != null && !"".equals(s.trim()) && s.matches("^[0-9]*$");
    }

    /**
     * checkAns() method check whether the input equals to result
     */
    private void checkAns() {
        int ansInput = Integer.parseInt(MathTeacher.this.ansInput.getText());
        if (ansInput == resultNum) {
            informLabel.setText("That's RIGHT!");
            score++; //if answer is right, score plus one
        } else {
            informLabel.setText("That's wrong. Try another one!");
        }
        count++; //count plus one when a question is answered
    }

    /**
     * printScore() method print the record of the score
     */
    private void printScore(){
        scoreLabel.setText(score+" correct out of "+count);
    }

    /**
     * printCorrect() method print the correct answer to previous answer
     */
    private void printCorrect(){
        correctAns.setText("Ans: " + Integer.toString(prevNum1)+ operator +Integer.toString(prevNum2) + " = " + preResultNum
                + "    Your ans: " + ansInput.getText());
    }
}
