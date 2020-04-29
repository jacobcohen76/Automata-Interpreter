package automatons;

public class Tape
{
	private Character B;
	
	private class Node
	{
		public Character data;
		public Node prev;
		public Node next;
		
		public Node(Character data, Node prev, Node next)
		{
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
		
		public Node()
		{
			this(B, null, null);
		}
		
		public String toString()
		{
			if(this == head)
				return "head";
			else if(this == tail)
				return "tail";
			else
				return String.valueOf(data);
		}
	}
	
	private void insert(Node L, Node R)
	{
		L.next = R;
		R.prev = L;
	}
	
	private Node head, tail;
	private Node current;
	private int size;
	
	public Tape(Character B)
	{
		this.B = B;
		
		head = new Node();
		tail = new Node();
		current = new Node();
		insert(head, current);
		insert(current, tail);
		size = 1;
	}
	
	public void load(String input)
	{
		insert(head, current);
		insert(current, tail);
		
		if(input.length() <= 0)
		{
			write(B);
			current = head.next;
		}
		else
		{
			for(int i = 0; i < input.length(); i++)
			{
				write(input.charAt(i));
				moveRight();
			}
			
			current = current.prev;
			current.next = tail;
			tail.prev = current;
			
			current = head.next;
		}
	}
	
	public void moveLeft()
	{
		if(current.prev == head)
		{
			Node node = new Node();
			insert(head, node);
			insert(node, current);
			size += 1;
		}
		current = current.prev;
	}
	
	public void moveRight()
	{
		if(current.next == tail)
		{
			Node node = new Node();
			insert(current, node);
			insert(node, tail);
			size += 1;
		}
		current = current.next;
	}
	
	public Character read()
	{
		return current.data;
	}
	
	public void write(Character data)
	{
		current.data = data;
	}
	
	public int size()
	{
		return size;
	}
	
	public String getLHS()
	{
		String LHS = "";
		Node itr = head.next;
		while(itr != current)
		{
			LHS += itr.data;
			itr = itr.next;
		}
		return LHS;
	}
	
	public String getRHS()
	{
		String RHS = "";
		Node itr = current.next;
		while(itr != tail)
		{
			RHS += itr.data;
			itr = itr.next;
		}
		return RHS;
	}
	
	public String getLHS(int length)
	{
		String LHS = getLHS();
		if(length > 0)
		{
			if(LHS.length() > length)
			{
				LHS = LHS.substring(LHS.length() - length, LHS.length());
			}
			else if(LHS.length() < length)
			{
				for(int i = 0; i < (length - LHS.length()); i++)
					LHS = B + LHS;
			}
		}
		return LHS;
	}
	
	public String getRHS(int length)
	{
		String RHS = getRHS();
		if(length > 0)
		{
			if(RHS.length() > length)
			{
				RHS = RHS.substring(0, length);
			}
			else if(RHS.length() < length)
			{
				for(int i = 0; i < (length - RHS.length()); i++)
					RHS = RHS + B;
			}
		}
		return RHS;
	}
	
	public String toString()
	{
		String str1 = "";
		String str2 = "";
		
		Node itr = head.next; 
		while(itr != tail)
		{
			str1 += itr.data == null ? "_" : itr.data;
			str2 += itr == current ? "^" : " ";
			itr = itr.next;
		}
		
		return str1 + "\n" + str2;
	}
}
