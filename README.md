# MathTeacher
JAVA Mini Project of BUPT International School

MathTeacher is a basic math teacher program with a graphical user interface (GUI). It is designed for young children to practice simple arithmetic. When the application is executed, it will display an arithmetic question such as “5 + 3”. The child will answer the question (“8” in this case) and be told if the answer is correct or not. 
The arithmetic operations allowed are {+, -, x, /}. The arguments are generated automatically with integers in the range 1 to 10.

##1.	How to start
Step 1: Run “javac MathTeacher.java” in command line.
Step 2: Run “java MathTeacher” in command line.
After you enter the commands, you will see the graphical user interface.

##2.	How to use 
- Question: When you launch the application, it will generate a random arithmetic question. 
- Input text field: You are supposed to enter your answer in the text field. You can use Numpad or your keyboard.

Note: 
1.	The valid answer is an integer from -9 to 100. If you input an integer that is larger than 100 or smaller than -9, it will jump to the max or min valid answer.
2.	Characters other than integers and minus are not supposed to be seen in the input. But please feel free because the program has screened the invalid input, i.e. the text field will be irresponsive when you input invalid characters.

- Check: After entering your answer, press this button and you can see the correct answer and your input answer below. Then a new question will be automatically generated. If your answer is right, the Notification box will show “That’s RIGHT!”. If wrong, it will print “That’s wrong. Try another one!”. You can see your score in the Score box.

- Reset your score: If you want to reset your score, press this button.
 
- Numpad: If you don’t want to use your keyboard, you can use your mouse to click on the number buttons to input your answer. If the answer is negative, you can press the minus button first to input the negative sign. If you want to clear your input, just press the Clr button.
