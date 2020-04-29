import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SpringLayout;

import automatons.State;
import automatons.TM;
import automatons.Tape;

public class TapePanel extends TransitionTable
{
	private static final long serialVersionUID = -1670666974981242316L;
	
	private int length;
	private JLabel LHS, current, RHS, pointer, state;
	private JLabel acceptStatus;
	private JSlider timeSettings;
	
	public TapePanel(int length, TM tm)
	{
		super(tm);
		
		this.length = length;
		LHS = new JLabel();
		current = new JLabel();
		RHS = new JLabel();
		pointer = new JLabel();
		state = new JLabel("CURRENT STATE: ");
		acceptStatus = new JLabel("Deciding...");
		timeSettings = new JSlider(JSlider.VERTICAL, 0, 1000, 500);
		
		layout.putConstraint(SpringLayout.NORTH, state, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, state, 0, SpringLayout.WEST, this);		
		
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, current, 0, SpringLayout.HORIZONTAL_CENTER, this);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, current, 0, SpringLayout.VERTICAL_CENTER, this);
		
		layout.putConstraint(SpringLayout.NORTH, pointer, 0, SpringLayout.SOUTH, current);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, pointer, 0, SpringLayout.HORIZONTAL_CENTER, current);
		
		layout.putConstraint(SpringLayout.EAST, LHS, 0, SpringLayout.WEST, current);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, LHS, 0, SpringLayout.VERTICAL_CENTER, current);

		layout.putConstraint(SpringLayout.WEST, RHS, 0, SpringLayout.EAST, current);
		layout.putConstraint(SpringLayout.VERTICAL_CENTER, RHS, 0, SpringLayout.VERTICAL_CENTER, current);
		
		layout.putConstraint(SpringLayout.NORTH, timeSettings, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, timeSettings, 0, SpringLayout.EAST, this);
		
		layout.putConstraint(SpringLayout.NORTH, acceptStatus, 0, SpringLayout.SOUTH, state);
		layout.putConstraint(SpringLayout.WEST, acceptStatus, 0, SpringLayout.WEST, this);

		this.add(state);
		this.add(LHS);
		this.add(current);
		this.add(RHS);
		this.add(pointer);
		this.add(timeSettings);
		this.add(acceptStatus);
	}
	
	public void setState(State s)
	{
		unmark();
		if(current.getText().length() > 0)
		{
			select(s, current.getText().charAt(0));
		}
		else
			select(s);
		state.setText("CURRENT STATE: " + s);
	}
	
	public long getDelay()
	{
		return (long)timeSettings.getValue();
	}
	
	public void setTape(Tape tape)
	{
		LHS.setText(tape.getLHS(length));
		current.setText(String.valueOf(tape.read()));
		RHS.setText(tape.getRHS(length));
		pointer.setText("^");
	}
	
	public void accept(boolean accepted)
	{
		if(accepted == true)
		{
			acceptStatus.setForeground(Color.GREEN);
			acceptStatus.setText("ACCEPTED");
		}
		else
		{
			acceptStatus.setForeground(Color.RED);
			acceptStatus.setText("REJECTED");
		}
	}
}
