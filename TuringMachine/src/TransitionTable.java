import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import automatons.DFA;
import automatons.PDA;
import automatons.State;
import automatons.TM;
import automatons.TMTransition;

public class TransitionTable extends JPanel
{
	private static final long serialVersionUID = 7287497888143957132L;
	
	private JLabel[][] table;
	private HashMap<State, Integer> stateIndexMap;
	private HashMap<Character, Integer> alphabetIndexMap;
	protected SpringLayout layout;
	
	private String toString(TMTransition tmt)
	{
		if(tmt == null)
			return "-";
		else
			return tmt.toString();
	}
	
	public void select(State state, Character c)
	{
		select(state);
		select(c);
		JLabel label = table[stateIndexMap.get(state)][alphabetIndexMap.get(c)];
		label.setBackground(Color.CYAN);
	}
	
	public void select(State state)
	{
		markRow(stateIndexMap.get(state), Color.YELLOW);
	}
	
	public void select(Character c)
	{
		markCol(alphabetIndexMap.get(c), Color.YELLOW);
	}
	
	private int selectedRow = -1;
	private int selectedCol = -1;
	private Color clear = new Color(0, 0, 0, 0);
	
	public void unmark()
	{
		unmarkRow();
		unmarkCol();
	}
	
	private void markRow(int i, Color c)
	{
		if(selectedRow < 0 && i >= 0)
		{
			for(int j = 0; j < table[i].length; j++)
			{
				table[i][j].setOpaque(true);
				table[i][j].setBackground(c);
			}
			repaint();
			selectedRow = i;
		}
	}
	
	private void unmarkRow()
	{
		if(selectedRow >= 0)
		{
			for(int j = 0; j < table[selectedRow].length; j++)
			{
				table[selectedRow][j].setOpaque(false);
				table[selectedRow][j].setBackground(clear);
			}
			selectedRow = -1;
		}
	}
	
	private void unmarkCol()
	{
		if(selectedCol >= 0)
		{
			for(int i = 0; i < table.length; i++)
			{
				table[i][selectedCol].setOpaque(false);
				table[i][selectedCol].setBackground(clear);
			}
			selectedCol = -1;
		}
	}
	
	private void markCol(int j, Color c)
	{
		if(selectedCol < 0 && j >= 0)
		{
			for(int i = 0; i < table.length; i++)
			{
				table[i][j].setOpaque(true);
				table[i][j].setBackground(c);
			}
			repaint();
			selectedCol = j;
		}
	}
	
	private int horizontalGap = 5;
	private int verticalGap = 5;
	
	public TransitionTable(PDA pda)
	{
		
	}
	
	public TransitionTable(DFA dfa)
	{
		this(dfa.Q, dfa.Σ, dfa.transitionMap);
	}
	
	private TransitionTable(Set<State> Q, Set<Character> Σ, HashMap<State, HashMap<Character, State>> transitionMap)
	{
		ArrayList<State> states = new ArrayList<State>();
		ArrayList<Character> alphabet = new ArrayList<Character>();
		
		states.addAll(Q);
		alphabet.addAll(Σ);
		Collections.sort(states);
		Collections.sort(alphabet);
		
		Font font = new Font("Consolas", Font.PLAIN, 12);
		table = new JLabel[states.size() + 1][alphabet.size() + 1];
		table[0][0] = new JLabel("STATE\\SYMBOL");
		table[0][0].setFont(font);
		
		stateIndexMap = new HashMap<State, Integer>();
		alphabetIndexMap = new HashMap<Character, Integer>();
		for(int i = 0; i < alphabet.size(); i++)
		{
			table[0][i + 1] = new JLabel(String.valueOf(alphabet.get(i)));
			table[0][i + 1].setFont(font);
			alphabetIndexMap.put(alphabet.get(i), i + 1);
		}
		
		for(int i = 0; i < states.size(); i++)
		{
			table[i + 1][0] = new JLabel(states.get(i).toString());
			table[i + 1][0].setFont(font);
			stateIndexMap.put(states.get(i), i + 1);
			for(int j = 0; j < alphabet.size(); j++)
			{
				String transitionString = "";
				transitionString += "(" + alphabet.get(i) + ", " + transitionMap.get(states.get(i)).get(alphabet.get(j)) + ")";
				table[i + 1][j + 1] = new JLabel(transitionString);
				table[i + 1][j + 1].setFont(font);
			}
		}
		
		layout = new SpringLayout();
		setLayout(layout);
		formatTable();
		addAll();
	}
	
	public TransitionTable(TM tm)
	{
		this(tm.Q, tm.Σ, tm.Γ, tm.transitionMap);
	}
	
	public TransitionTable(Set<State> Q, Set<Character> inputAlphabet, Set<Character> tapeAlphabet, HashMap<State, HashMap<Character, TMTransition>> transitionMap)
	{
		ArrayList<State> states = new ArrayList<State>();
		ArrayList<Character> alphabet = new ArrayList<Character>();
		
		states.addAll(Q);
		Set<Character> unionInputAndTape = new HashSet<Character>();
		unionInputAndTape.addAll(inputAlphabet);
		unionInputAndTape.addAll(tapeAlphabet);
		alphabet.addAll(unionInputAndTape);
		
		Collections.sort(states);
		Collections.sort(alphabet);
		
		Font font = new Font("Consolas", Font.PLAIN, 12);
		
		table = new JLabel[states.size() + 1][alphabet.size() + 1];
		table[0][0] = new JLabel("STATE\\SYMBOL");
		table[0][0].setFont(font);
		
		stateIndexMap = new HashMap<State, Integer>();
		alphabetIndexMap = new HashMap<Character, Integer>();
		for(int i = 0; i < alphabet.size(); i++)
		{
			table[0][i + 1] = new JLabel(String.valueOf(alphabet.get(i)));
			table[0][i + 1].setFont(font);
			alphabetIndexMap.put(alphabet.get(i), i + 1);
		}
		
		for(int i = 0; i < states.size(); i++)
		{
			table[i + 1][0] = new JLabel(states.get(i).toString());
			table[i + 1][0].setFont(font);
			stateIndexMap.put(states.get(i), i + 1);
			for(int j = 0; j < alphabet.size(); j++)
			{
				table[i + 1][j + 1] = new JLabel(toString(transitionMap.get(states.get(i)).get(alphabet.get(j))));
				table[i + 1][j + 1].setFont(font);
			}
		}
		
//		for(JLabel[] labels : table)
//			for(JLabel label : labels)
//			{
//				label.setOpaque(true);
//				label.setBackground(Color.RED);
//			}
		
		layout = new SpringLayout();
		setLayout(layout);
		formatTable();
		addAll();
	}
	
	private void addAll()
	{
		for(int i = 0; i < table.length; i++)
			for(int j = 0; j < table[i].length; j++)
				add(table[i][j]);
	}
	
	public void formatTable()
	{
		layout.putConstraint(SpringLayout.SOUTH, table[table.length - 1][0], -verticalGap, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, table[table.length - 1][0], +horizontalGap, SpringLayout.WEST, this);
		
		JLabel prev = null;
		for(int j = 0; j < table[0].length; j++)
			formatColumn(j);
		
		int width = widest();
		for(int j = 0; j < table[0].length; j++)
		{
			JLabel current = widestInColumn(j);
			if(j != 0)
				link(prev, current, width);
			else
				layout.putConstraint(SpringLayout.WEST, current, +horizontalGap, SpringLayout.WEST, this);
			prev = current;
		}
		for(int j = 1; j < table[0].length; j++)
		{
			layout.putConstraint(SpringLayout.SOUTH, table[table.length - 1][j], -verticalGap, SpringLayout.SOUTH, this);
		}
		layout.putConstraint(SpringLayout.SOUTH, table[table.length - 1][0], -verticalGap, SpringLayout.SOUTH, this);
	}
	
	private void link(JLabel widestCol1, JLabel widestCol2, int widest)
	{
		int width1 = widestCol1.getFontMetrics(widestCol1.getFont()).stringWidth(widestCol1.getText());
		int width2 = widestCol2.getFontMetrics(widestCol2.getFont()).stringWidth(widestCol2.getText());
		layout.putConstraint(SpringLayout.WEST, widestCol2, ((widest - width2) / 2 + (widest - width1) / 2) + horizontalGap, SpringLayout.EAST, widestCol1);
	}
	
	private JLabel formatColumn(int j)
	{
		JLabel widest = widestInColumn(j);
		int width = widest.getFontMetrics(widest.getFont()).stringWidth(widest.getText());
		for(int i = 0; i < table.length; i++)
		{
			if(table[i][j] != widest)
				layout.putConstraint(SpringLayout.WEST, table[i][j], -(table[i][j].getFontMetrics(table[i][j].getFont()).stringWidth(table[i][j].getText()) - width) / 2, SpringLayout.WEST, widest);
			if(i < table.length - 1)
			{
				layout.putConstraint(SpringLayout.SOUTH, table[i][j], -verticalGap, SpringLayout.NORTH, table[i + 1][j]);
			}
		}
		return widest;
	}
	
	private int widest()
	{
		int width = 0;
		for(int i = 0; i < table.length; i++)
		for(int j = 0; j < table[i].length; j++)
		{
			JLabel label = table[i][j];
			int labelWidth = label.getFontMetrics(label.getFont()).stringWidth(label.getText());
			if(labelWidth > width)
			{
				width = labelWidth;
			}
		}
		return width;
	}
	
	private JLabel widestInColumn(int i)
	{
		JLabel widest = null;
		int width = 0;
		for(int j = 0; j < table[i].length; j++)
		{
			JLabel label = table[j][i];
			int labelWidth = label.getFontMetrics(label.getFont()).stringWidth(label.getText());
			if(labelWidth > width)
			{
				width = labelWidth;
				widest = label;
			}
		}
		return widest;
	}
}
