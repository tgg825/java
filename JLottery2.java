package lotteryGame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.http.WebSocket.Listener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * CodingForVeterans
 *
 * @author Tim Greenwood (c4v) 
 * Unit 14: Lottery Game
 * User presented with 30 Checkboxes and is able to select
 * up to six numbers. No more than six selections are allowed.
 * Once the 6 numbers are selected, the user is
 * asked to confirm his selection or make any changes.  Once
 * the numbers are confirmed, both the user's numbers and the 
 * randomly selected numbers are displayed and the winnings are 
 * calculated as follows:  6 matches = $1M; 5 matches = $50k
 * 4 matches = $10K; 3 matches = $100. $0 for 0, 1, or 2 matches
 * 
 */
public class JLottery2 extends JFrame implements ItemListener, ActionListener
{
	JButton button = new JButton("Confirm selection");
	public static JCheckBox[] checkList = new JCheckBox[30];
	JLabel label1 = new JLabel("Choose your numbers >> ");
	JLabel randNums = new JLabel();
	JLabel myNums = new JLabel();
	JLabel winner = new JLabel();
	final static int MAX_GUESS = 6;
	int[] randNum = new int[MAX_GUESS];
	int[] numbers = new int[MAX_GUESS]; 
	int selectCounter = 0; 
	
	public JLottery2() 
	{
		super("Lottery Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		
		for(int i = 0; i < randNum.length; ++i) 
		{
			randNum[i] = (int)(Math.random() * 31);	
		}
		
		add(label1);
		
        for (int i = 0; i < 30; ++i) 
        {
            checkList[i] = new JCheckBox("" + (i + 1), false);
            add(checkList[i]);
            checkList[i].addItemListener(this);
        }
		add(button);
		button.setEnabled(false);
		button.addActionListener(this);
		add(randNums);
		add(myNums);
		add(winner);			
	}
	@Override
    public void actionPerformed(ActionEvent e) 
    {
		String rNum = "", myNum = "";
		for(int i = 0; i < randNum.length; ++i)	//Display random numbers
			rNum += " " + randNum[i];
		randNums.setText("Lottery Numbers" + rNum);
		for(int j = 0; j < numbers.length; ++j) //Display my numbers
			myNum += " " + numbers[j];
		myNums.setText("My Numbers" + myNum);
		int matches = 0;
		
		for(int j = 0; j < numbers.length; ++j) 
		{
			for(int i = 0; i < randNum.length; ++i) 
			{
				if(numbers[j] == randNum[i])
					++matches;
			}
		}
		if(matches == 6) 
		{
			winner.setText("You won $1M");
		}
		else 
		{
			if(matches == 5) 
			{
				winner.setText("You won $50K");
			}
			else 
			{
				if(matches == 4) 
				{
					winner.setText("You won $50K");
				}
				else 
				{
					if(matches == 3) 
					{
						winner.setText("You won $100");
					}
					else
						winner.setText("Sorry! Less than 3 matching numbers >> No winnings");
				}
			}
		}
		button.setEnabled(false);	
    }
	@Override
    public void itemStateChanged(ItemEvent check) 
    {
		Object source = check.getSource();
		int select = check.getStateChange();
		if (select == ItemEvent.SELECTED) 
		{
			selectCounter++;
            // check for max selections:
            if (selectCounter == MAX_GUESS) 
            {
            	int j = 0;
            	for (int i = 0; i < checkList.length; ++i) 
                {
                	if (!checkList[i].isSelected()) 
                    {
                		checkList[i].setEnabled(false);	//Disallow any changes	
                    }
                	else 
                	{
                		numbers[j] = i + 1;		//Record current selection in array
                		++j;
                	}
                }
                button.setEnabled(true); //Confirm numbers   
            }       
        }
		else
		{
			selectCounter--;
            // check for less than max selections:
            if (selectCounter < MAX_GUESS) 
            {
            	for (JCheckBox box: checkList) 
                {
                	box.setEnabled(true);	//Allow new selections or changes
                }
                button.setEnabled(false); //Disable button until six numbers selected
            }     
        }
    }
	public static void main(String[] args) 
	{
		JLottery2 frame = new JLottery2();
        frame.setSize(400, 250);
        frame.setVisible(true);
	}
}
