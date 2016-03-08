import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
/* This class ensures all the jdbc - mysql connectivity activities
 * All the reusable instance variables have been marked as static
 */
public class LoadJDBCDriver  // to load the driver and establish the connection
{
	static final String JDBC_DRIVER="com.mysql.jdbc.Driver"; // it's mandatory
	static final String DB_URL="jdbc:mysql://localhost:3306/gagan"; // gagan is the name of database in mysql
	static final String USERNAME="root"; // username
	static final String PASSWORD="root"; // password
	public static Connection connection=null;
	public static Scanner sc=new Scanner(System.in); // for console input but i wonder if it is getting used or not 
	static PreparedStatement  stmt=null; // also known as parameterised statement
	public static void main(String [] args) throws ClassNotFoundException,SQLException
	{
		Class.forName(JDBC_DRIVER); // registering the driver
		connection=DriverManager.getConnection(DB_URL,USERNAME,PASSWORD); // establishing the connection
		Main_page mp=new Main_page(); 
		mp.create_front_page();// creating the very first page 
	}
	static int getNewId() // always gets a new id(starts from 0), returns -1 in case of exception
	{
		try
		{
		ResultSet rs=(connection.prepareStatement("Select id from propertydeal;")).executeQuery();
		int maxid=0,x;
		while(rs.next())
		{
			x=rs.getInt(1);
			if(maxid<x)
				maxid=x;
		}
		return maxid+1;
		}
		catch(SQLException se)
		{
			JOptionPane.showMessageDialog(null, "SQL Exception in getting a new id","Unsuccessfull",JOptionPane.INFORMATION_MESSAGE);
		}
		return -1;
	}	
}
/* This class handles all the functions behind the first main menu
 * It particulary has button associated with their respective fundtions
 */
class Main_page extends JFrame implements ActionListener
{
	JButton insert_button=new JButton(" Insert a new record");
	JButton search_button= new JButton(" Search for a record");
	JButton view_button=new JButton(" View all records");
	JButton delete_button=new JButton(" Delete a record");
	JButton modify_button=new JButton(" Modify a record");
	JButton view_image_button=new JButton("View an Image");
	JPanel front_panel=new JPanel();
	JFrame front_frame=new JFrame(" Frong page");
	LoadJDBCDriver l=new LoadJDBCDriver();
	void create_front_page()
	{
		front_panel.add(insert_button);
		front_panel.add(search_button);
		front_panel.add(delete_button);
		front_panel.add(view_button);
		front_panel.add(view_image_button);
		front_panel.add(modify_button);
		front_frame.add(front_panel);
		front_frame.setSize(1366,720);
		front_frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		front_frame.setVisible(true);
		insert_button.addActionListener(this);
		search_button.addActionListener(this);
		delete_button.addActionListener(this);
		view_button.addActionListener(this);
		view_image_button.addActionListener(this);
		modify_button.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) 
	{
		Insert_page ip=new Insert_page();
		Search_page sp=new Search_page();
		View_all_page vap =new View_all_page();
		View_an_image vai=new View_an_image();
		Delete_page dp=new Delete_page();
		Update_page up=new Update_page();
		if(e.getSource()==insert_button)
		{
			front_frame.setVisible(false);
				ip.create_insert_page();
		}
		else if(e.getSource()==search_button)
		{
				front_frame.setVisible(false);
				sp.create_search_page();
		}
		else if(e.getSource()==delete_button)
		{
			front_frame.setVisible(false);
			dp.create_delete_page();
		}
		else if(e.getSource()==view_button)
		{
			front_frame.setVisible(false);
			vap.create_view_all_page();
		}
		else if(e.getSource()==view_image_button)
		{
			front_frame.setVisible(false);
			vai.create_front_page();
		}
		else
		{
			front_frame.setVisible(false);
			up.create_update_page();
		}
	}
}
/*
 * Insert page class handles activities when u hit' insert a record' button
 */
class Insert_page extends JFrame implements ActionListener
{
	JFrame successful=new JFrame(" Successfully inserted!!");
	JFrame unsuccessful=new JFrame(" Insertion Unsuccessfull!!");
	JFrame insert_frame=new JFrame(" Insert a record");
	JPanel insert_panel=new JPanel();
	JButton back=new JButton("Back");
	JButton submit=new JButton("Submit");
	JButton add_image=new JButton("Add an Image");
	JLabel name=new JLabel("Name:");
	JLabel address=new JLabel("Address:");
	JLabel date=new JLabel("Date:");
	JLabel rent=new JLabel("Rent:");
	JLabel area=new JLabel("Area:");
	JLabel phone_no=new JLabel("Mobile Number:");
	JLabel image_path=new JLabel("Image Path:");
	JTextField name_t=new JTextField(15);
	JTextField address_t=new JTextField(25);
	JTextField date_t=new JTextField(12);
	JTextField rent_t=new JTextField(10);
	JTextField area_t=new JTextField(10);
	JTextField image_t=new JTextField(40);
	JTextField phone_no_t=new JTextField(10);
	void create_insert_page()
	{
		insert_panel.add(back);
		back.addActionListener(this);
		insert_panel.add(name);
		insert_panel.add(name_t);
		name_t.setMaximumSize(name_t.getPreferredSize());
		name_t.addActionListener(this);
		insert_panel.add(address);
		insert_panel.add(address_t);
		address_t.setMaximumSize(address_t.getPreferredSize());
		address_t.addActionListener(this);
		insert_panel.add(date);
		insert_panel.add(date_t);
		date_t.setMaximumSize(date_t.getPreferredSize());
		date_t.addActionListener(this);
		insert_panel.add(rent);
		insert_panel.add(rent_t);
		rent_t.setMaximumSize(rent_t.getPreferredSize());
		rent_t.addActionListener(this);
		insert_panel.add(area);
		insert_panel.add(area_t);
		area_t.setMaximumSize(area_t.getPreferredSize());
		area_t.addActionListener(this);
		insert_panel.add(phone_no);
		insert_panel.add(phone_no_t);
		phone_no_t.setMaximumSize(phone_no_t.getPreferredSize());
		phone_no_t.addActionListener(this);
		insert_panel.add(image_path);
		insert_panel.add(image_t);
		image_t.setMaximumSize(image_t.getPreferredSize());
		image_t.addActionListener(this);
		insert_panel.add(add_image);
		add_image.addActionListener(this);
		insert_panel.add(Box.createRigidArea(new Dimension(10,10)));
		insert_panel.add(submit);
		submit.addActionListener(this);
		insert_frame.getContentPane().add(insert_panel ,BorderLayout.CENTER);
		insert_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		insert_frame.setSize(1366, 720);
		insert_frame.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		String name_s,address_s,rent_s,area_s,phone_no_s,date_s,path_s;
		int rent_i;
		long phone_no_l;
		if(e.getSource()==add_image) // invokes a JFileChooser for adding path of an image
		{
			JFileChooser choose_image = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
		    choose_image.setFileFilter(filter);
		    choose_image.showOpenDialog(null);
		    path_s=choose_image.getSelectedFile().getAbsolutePath();
		    image_t.setText(path_s);
		}
		if(e.getSource()==back) // back to Main menu
		{
			Main_page m=new Main_page();
			insert_frame.setVisible(false);
			m.create_front_page();
		}
			path_s=image_t.getText();
			name_s=name_t.getText();
			address_s=address_t.getText();
			rent_s=rent_t.getText();
			phone_no_s=phone_no_t.getText();
			date_s=date_t.getText();
			area_s=area_t.getText();
		if(e.getSource()==submit)
		{
			if(!name_s.isEmpty()&&!address_s.isEmpty()&&!rent_s.isEmpty()&&!area_s.isEmpty()&&!phone_no_s.isEmpty()&&!date_s.isEmpty()&&!path_s.isEmpty())
			{
				String query="insert into propertydeal values(?,?,?,?,?,?,?,?);";
				try{
					rent_i=Integer.parseInt(rent_s);
					phone_no_l=Long.parseLong(phone_no_s);
					LoadJDBCDriver.stmt=LoadJDBCDriver.connection.prepareStatement(query);
					LoadJDBCDriver.stmt.setString(1,name_s);		
					LoadJDBCDriver.stmt.setString(2,address_s);	
					LoadJDBCDriver.stmt.setInt(3,LoadJDBCDriver.getNewId());
					LoadJDBCDriver.stmt.setString(4,date_s);		
					LoadJDBCDriver.stmt.setInt(5,rent_i);	
					LoadJDBCDriver.stmt.setString(6,area_s);
					LoadJDBCDriver.stmt.setLong(7,phone_no_l);
					LoadJDBCDriver.stmt.setString(8,path_s);
					System.out.println(LoadJDBCDriver.stmt.executeUpdate());
					JOptionPane.showMessageDialog(null, "Inserted Successfully!!","Successfull",JOptionPane.INFORMATION_MESSAGE);
					name_t.setText(null);
					address_t.setText(null);
					date_t.setText(null);
					rent_t.setText(null);
					area_t.setText(null);
					phone_no_t.setText(null);
					image_t.setText(null);
				}
				catch(SQLException e2)
				{
					JOptionPane.showMessageDialog(null, "Query not executed","Unsuccessfull",JOptionPane.INFORMATION_MESSAGE);
				}
				catch(NumberFormatException e3)
				{
					unsuccessful.setSize(100,100);
					unsuccessful.getContentPane().add(new JLabel("UNSUCCESSFULL!!! exception"));
					unsuccessful.setVisible(true);
					JOptionPane.showMessageDialog(null, "Either Rent or Phone Number is invalid","Unsuccessfull",JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Enter All The Fields","Unsuccessfull",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}
/*
 * This class ensures the activites that goes behind when u hit ' search for a record button
 */
class Search_page extends JFrame implements ActionListener
{
	int col_no=0;
	int no_of_records_found=0;
	String result="";
	JFrame search_frame_primary=new JFrame("Search for a record");
	JFrame search_frame_secondary=new JFrame("Search for a record");
	JPanel search_panel=new JPanel();
	JPanel after_action = new JPanel();
	JFrame final_search_frame=new JFrame("Showing Results!!");
	JPanel final_search_panel=new JPanel();
	JButton initial_letter=new JButton("Search by Initial Characters of Name");
	JButton name=new JButton("Search by Name");
	JButton id=new JButton("Search by Id");
	JButton address=new JButton("Search by Address");
	JButton date=new JButton("Search by Date");
	JButton rent=new JButton("Search by Rent");
	JButton area=new JButton("Search by Area");
	JButton phone_no=new JButton("Search by Mobile Number");
	JTextField get_info=new JTextField(15);
	JLabel show_label=new JLabel();
	JButton back=new JButton(" Back");
	JButton back_1=new JButton("Back");
	JButton submit=new JButton(" Submit");
	JTextArea search_area=new JTextArea(5,70);
	JScrollPane search_scroll=new JScrollPane(search_area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	void create_search_page() // initially controll goes to this method when button(search for an record is hit)
	{
		BoxLayout b=new BoxLayout(search_panel,BoxLayout.Y_AXIS);
		search_panel.setLayout(b);
		search_panel.add(Box.createRigidArea(new Dimension(10,10)));	
		search_panel.add(back);
		back.addActionListener(this);
		search_panel.add(Box.createRigidArea(new Dimension(10,10)));
		search_panel.add(initial_letter);
		initial_letter.addActionListener(this);
		search_panel.add(Box.createRigidArea(new Dimension(10,10)));
		search_panel.add(name);
		name.addActionListener(this);
		search_panel.add(Box.createRigidArea(new Dimension(10,10)));
		search_panel.add(id);
		id.addActionListener(this);
		search_panel.add(Box.createRigidArea(new Dimension(10,10)));
		search_panel.add(address);
		address.addActionListener(this);
		search_panel.add(Box.createRigidArea(new Dimension(10,10)));
		search_panel.add(date);
		date.addActionListener(this);
		search_panel.add(Box.createRigidArea(new Dimension(10,10)));
		search_panel.add(rent);
		rent.addActionListener(this);
		search_panel.add(Box.createRigidArea(new Dimension(10,10)));
		search_panel.add(area);
		area.addActionListener(this);
		search_panel.add(Box.createRigidArea(new Dimension(10,10)));
		search_panel.add(phone_no);
		phone_no.addActionListener(this);
		search_frame_primary.getContentPane().add(search_panel, BorderLayout.CENTER);
		search_frame_primary.setSize(1366, 720);
		search_frame_primary.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		search_frame_primary.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		Main_page m=new Main_page();
		search_panel.setVisible(false);
		if(e.getSource()==back) // back to main menu
		{
			search_frame_primary.setVisible(false);
			m.create_front_page();
			return; // so that ending statement of this method doesn't get executed
		}
		if(e.getSource()==initial_letter)
		{
			search_frame_primary.setVisible(false);
			show_label.setText(" Enter Initial Alphabets of Name: ");
			after_action.add(show_label);
			after_action.add(get_info);
			col_no=-1; //  to ensure that this actually doesn't belong to any column number , so choosing an arbitary value
			after_action.add(submit);
			submit.addActionListener(this);
		}
		if(e.getSource()==back_1) // back to search page
		{
			//search_area.setText("");
			final_search_frame.setVisible(false);
			new Search_page().create_search_page();
			return; // so that ending statements of this method don't get executed
		}
		if(e.getSource()==name)
		{
			search_frame_primary.setVisible(false);
			show_label.setText(" Enter Name: ");
			after_action.add(show_label);
			after_action.add(get_info);
			col_no=1;
			after_action.add(submit);
			submit.addActionListener(this);
		}
		if(e.getSource()==address)
		{
			search_frame_primary.setVisible(false);
			show_label.setText(" Enter Address: ");
			after_action.add(show_label);
			after_action.add(get_info);
			col_no=2;
			after_action.add(submit);
			submit.addActionListener(this);
		}
		if(e.getSource()==id)
		{
			search_frame_primary.setVisible(false);
			show_label.setText(" Enter ID: ");
			after_action.add(show_label);
			after_action.add(get_info);
			col_no=3;
			after_action.add(submit);
			submit.addActionListener(this);
		}
		if(e.getSource()==date)
		{
			search_frame_primary.setVisible(false);
			show_label.setText(" Enter Date: ");
			after_action.add(show_label);
			after_action.add(get_info);
			col_no=4;
			after_action.add(submit);
			submit.addActionListener(this);
		}
		if(e.getSource()==rent)
		{
			search_frame_primary.setVisible(false);
			show_label.setText(" Enter Rent: ");
			after_action.add(show_label);
			after_action.add(get_info);
			col_no=5;
			after_action.add(submit);
			submit.addActionListener(this);
		}
		if(e.getSource()==area)
		{
			search_frame_primary.setVisible(false);
			show_label.setText(" Enter Area: ");
			after_action.add(show_label);
			after_action.add(get_info);
			col_no=6;
			after_action.add(submit);
			submit.addActionListener(this);
		}
		if(e.getSource()==phone_no)
		{
			search_frame_primary.setVisible(false);
			show_label.setText(" Enter Mobile Number: ");
			after_action.add(show_label);
			after_action.add(get_info);
			col_no=7;
			after_action.add(submit);
			submit.addActionListener(this);
		}
		if(e.getSource()==submit)
		{
			search_frame_secondary.setVisible(false);
			search_frame_primary.setVisible(false);
			print_result(col_no,get_info.getText());
			return; // so that last statements don't get executed
		}
		search_frame_secondary.getContentPane().add(after_action);
		search_frame_secondary.setSize(1366, 720);
		search_frame_secondary.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		search_frame_secondary.setVisible(true);
	}
	void print_result(int col_number,String info) // How results are to be printed on search_area(JtextArea) field
	{
		search_area.setFont(new Font("serif",Font.BOLD+Font.ITALIC,18));
		String name,address,date,area;
		int id,rent;
		long phone_no;
		if(col_number==-1)
		{
			try
			{
			LoadJDBCDriver.stmt=LoadJDBCDriver.connection.prepareStatement("select * from propertydeal where person_name like" + "'" + info+"%';");
		//	LoadJDBCDriver.stmt.setString(1, info); // here info is the first name characters to be searched
			ResultSet rs=LoadJDBCDriver.stmt.executeQuery();
			while(rs.next())
			{
					no_of_records_found++;
					name=rs.getString(1);
					address=rs.getString(2);
					date=rs.getString(4);
					id=rs.getInt(3);
					rent=rs.getInt(5);
					area=rs.getString(6);
					phone_no=rs.getLong(7);
					result +=String.format("%-3s | %-20s | %-50s | %-15s | %-6s | %-6s | %-11s \n",""+id,name,address,date,""+rent,""+area,""+phone_no);
			}
			}
			catch(SQLException a)
			{
				System.out.println("Exception at executing query at search method in Search_page class");
			}
			if(no_of_records_found==0)
			{
				result=" No Match Found";
			}
			else
			{
				result=" Records Found: "+no_of_records_found + "\n" +String.format("%-3s | %-20s | %-50s | %-15s | %-6s | %-6s | %-11s \n","ID","Name","Address","Date","Rent","Area","Phone Number") +result; 
			}
		}
		else
		{
		try{
		LoadJDBCDriver.stmt=LoadJDBCDriver.connection.prepareStatement("select * from propertydeal");
		ResultSet rs=LoadJDBCDriver.stmt.executeQuery();	
		while(rs.next())
		{
			if(rs.getString(col_number).equalsIgnoreCase(info))// match occured
			{
				no_of_records_found++;
				name=rs.getString(1);
				address=rs.getString(2);
				date=rs.getString(4);
				id=rs.getInt(3);
				rent=rs.getInt(5);
				area=rs.getString(6);
				phone_no=rs.getLong(7);
				result +=String.format("%-3s | %-20s | %-50s | %-15s | %-6s | %-6s | %-11s \n",""+id,name,address,date,""+rent,""+area,""+phone_no);
			}
		}
		}
		catch(SQLException a)
		{
			System.out.println("Exception at executing query at search method in Search_page class");
		}
		if(no_of_records_found==0)
		{
			result=" No Match Found";
		}
		else
		{
			result=" Records Found: "+no_of_records_found + "\n" +String.format("%-3s | %-20s | %-50s | %-15s | %-6s | %-6s | %-11s \n","ID","Name","Address","Date","Rent","Area","Phone Number") +result; 
		}
		}
		search_area.setText(result);
		final_search_panel.add(back_1);
		back_1.addActionListener(this);
		final_search_panel.add(search_scroll);
		final_search_frame.getContentPane().add(final_search_panel, BorderLayout.CENTER);
		final_search_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		final_search_frame.setSize(1366, 720);
		final_search_frame.setVisible(true);
	} // print_result method closing brace
} //  Search_page class closing brace
/*
 * Special class for view_an_image button as I was not sure I can do this but since as per my father's demand I have to add an image so did I
 */
class View_an_image implements ActionListener
{
	String id;
	int id_i;
	JButton back=new JButton("back");
	JButton go=new JButton("go");
	JLabel enter_id_l=new JLabel("Enter Id:");
	JTextField enter_id_t=new JTextField(5);
	JFrame View_an_image_frame=new JFrame("View_an_image");
	JPanel View_an_image_panel=new JPanel();
	void create_front_page()
	{
		View_an_image_panel.add(back);
		View_an_image_panel.add(enter_id_l);
		View_an_image_panel.add(enter_id_t);
		View_an_image_panel.add(go);
		back.addActionListener(this);
		go.addActionListener(this);
		View_an_image_frame.getContentPane().add(View_an_image_panel, BorderLayout.CENTER);
		View_an_image_frame.setSize(1366, 720);
		View_an_image_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		View_an_image_frame.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		Main_page m =new Main_page();
		if(e.getSource()==back) // back to main menu
		{
			View_an_image_frame.setVisible(false);
			m.create_front_page();
		}
		if(e.getSource()==go)
		{
			id=enter_id_t.getText();
			if(id.equals(""))
			{
				JOptionPane.showMessageDialog(null, "Please Enter The Id","Unsuccessfull",JOptionPane.INFORMATION_MESSAGE);
			}
			try
			{
				id_i=Integer.parseInt(id);
			}
			catch(NumberFormatException i)
			{
				JOptionPane.showMessageDialog(null, "Please Enter a positive Integer","Unsuccessfull",JOptionPane.INFORMATION_MESSAGE);
			}
			display_image(id_i);
		}
	}
	void display_image(int id) // a new mrhtod just to displat image coressponding to id 
	{
		String query = "select image_path from propertydeal where id="+id+";";
		String path;
		ImageIcon image;
		Image scaled_image;
		JLabel label;
		try{
			LoadJDBCDriver.stmt=LoadJDBCDriver.connection.prepareStatement(query);
			ResultSet rs=LoadJDBCDriver.stmt.executeQuery();
			while(rs.next())
			{
				path=rs.getString(1);
				JPanel panel=new JPanel();
				panel.setPreferredSize(new Dimension(480, 640));
				JFrame p=new JFrame(" Main page");
				try
				{
				image= new ImageIcon(new File(path).toURI().toURL());
				scaled_image=image.getImage().getScaledInstance(480,640,Image.SCALE_SMOOTH);
				label=new JLabel(new ImageIcon(scaled_image));
				panel.add(label);
				}
				catch(MalformedURLException mue)
				{
					JOptionPane.showMessageDialog(null,"Error Occured in selecting path for image","Unsuccessfull",JOptionPane.INFORMATION_MESSAGE);
				}
				p.add(panel);
				p.pack();
				p.setVisible(true);
			}
		}
		catch(SQLException se)
		{
			JOptionPane.showMessageDialog(null, "Query is not Executed","Unsuccessfull",JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
/*
 * This pretty much includes displaying all the stuff in database on a JTextArea(further which is conatined by n JScrollPAne)
 */
class View_all_page extends JFrame implements ActionListener
{
	JFrame view_all_page_frame=new JFrame (" View All Records");
	JPanel view_all_page_panel=new JPanel();
	JButton back=new JButton("Back");
	JTextArea view_all_page_area=new JTextArea(10,60);
	JScrollPane view_scroll=new JScrollPane(view_all_page_area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	Main_page m;
	String name,address,date,append,area;
	int id,rent;
	long phone_no;
	String query=" Select * from propertydeal;";
	void create_view_all_page()
	{
		view_all_page_panel.add(back);
		view_all_page_panel.add(view_scroll);
		view_all_page_frame.getContentPane().add(view_all_page_panel, BorderLayout.CENTER);
		view_all_page_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		view_all_page_frame.setSize(1366, 720);
		view_all_page_frame.setVisible(true);
		back.addActionListener(this);
		try
		{
		LoadJDBCDriver.stmt=LoadJDBCDriver.connection.prepareStatement(query);
		ResultSet rs=LoadJDBCDriver.stmt.executeQuery();
		view_all_page_area.setText(String.format("%-3s | %-20s | %-50s | %-15s | %-6s | %-6s | %-11s \n","ID","Name","Address","Date","Rent","Area","Phone Number"));
		while(rs.next())
		{
			name=rs.getString(1);
			address=rs.getString(2);
			date=rs.getString(4);
			id=rs.getInt(3);
			rent=rs.getInt(5);
			area=rs.getString(6);
			phone_no=rs.getLong(7);
			//System.out.printf("%d",id);
			append=String.format("%-4d | %-20s | %-50s | %-15s | %-6d | %-6s | %-11d \n",id,name,address,date,rent,area,phone_no);
			view_all_page_area.setFont(new Font("serif", Font.BOLD+Font.ITALIC, 18));
			view_all_page_area.append(append);
		}
		}
		catch(SQLException e1)
		{
			System.out.println(" Exception at executing query in view_all_page class");
		}
	}
	public void actionPerformed(ActionEvent e)
	{
		view_all_page_frame.setVisible(false);
		m=new Main_page();
		m.create_front_page();
	}
}
/*
 * ohh! This class deletes a record but deletion is to be done by id only
 * And once deleted u cannot recover your data
 * In order to avoid conflicts between different records(having same field values) concept of id is introduced
 */
class Delete_page extends JFrame implements ActionListener
{
	Main_page m;
	JFrame delete_page_frame=new JFrame("Delete a record!!");
	JPanel delete_page_panel=new JPanel();
	JButton back=new JButton("Back");
	JButton submit=new JButton("Submit");
	JLabel delete_page_label=new JLabel("Enter ID:");
	JTextField delete_page_field=new JTextField(5);
	void create_delete_page()
	{
		delete_page_panel.add(back);
		back.addActionListener(this);
		delete_page_panel.add(delete_page_label);
		delete_page_panel.add(delete_page_field);
		delete_page_field.addActionListener(this);
		delete_page_panel.add(submit);
		submit.addActionListener(this);
		delete_page_frame.getContentPane().add(delete_page_panel, BorderLayout.CENTER);
		delete_page_frame.setSize(1366, 720);
		delete_page_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		delete_page_frame.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
	//	delete_page_frame.setVisible(false);
		if(e.getSource()==back)
		{
			delete_page_frame.setVisible(false);
			m=new Main_page();
			m.create_front_page();
		}
		if(e.getSource()==submit)
		{
			String get_id=delete_page_field.getText();
			boolean flag=false;
			String sql="delete from propertydeal where id=?;";
			try{
			LoadJDBCDriver.stmt=LoadJDBCDriver.connection.prepareStatement(sql);
			LoadJDBCDriver.stmt.setInt(1,Integer.parseInt(get_id));
			ResultSet rs=(LoadJDBCDriver.connection.prepareStatement("Select id from propertydeal;")).executeQuery();
			while(rs.next())
			{
				if(rs.getString(1).equals(get_id))
				{
					LoadJDBCDriver.stmt.executeUpdate();
					JOptionPane.showMessageDialog(delete_page_frame,"Deletion Sucessfull!!","Successfull",JOptionPane.INFORMATION_MESSAGE);
					flag=true;
				}
			}
			}
			catch(SQLException e1)
			{
				System.out.println("Exception in update query in delete_page class");
			}
			catch(NumberFormatException e2)
			{
				System.out.println(" Exception in parsing id into int ");
			}
			if(get_id.equals("")) // empty textfield
			{
				JOptionPane.showMessageDialog(delete_page_frame,"Please Enter text","Unsuccessfull",JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				if(!flag) // record not found
				{
					JOptionPane.showMessageDialog(delete_page_frame,"Record Not Found","Unsuccessfull",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}
}
/*
 * Updation is all achieved by id only
 * 
 */
class Update_page extends JFrame implements ActionListener
{
	JFrame update_page_frame=new JFrame(" Update a record");
	JPanel update_page_panel=new JPanel();
	JButton back=new JButton("Back");
	JButton submit=new JButton("Submit");
	JButton go=new JButton("go");
	JButton add_image=new JButton("Add an Image");
	JLabel enter_id=new JLabel("Enter Id:");
	JTextField get_id=new JTextField(5);
	JLabel unsucccessful=new JLabel();
	JLabel successful=new JLabel("Successfully Updated!!");
	String name,address,date,rent,area,phone_nu,image_path;
	JFrame after_update_frame=new JFrame();
	JPanel after_update_panel=new JPanel();
	boolean flag=false;
	JButton back_1=new JButton("Back");
	JLabel name_l=new JLabel("Name:");
	JLabel address_l=new JLabel("Address:");
	JLabel date_l=new JLabel("Date:");
	JLabel rent_l=new JLabel("Rent:");
	JLabel area_l=new JLabel("Area:");
	JLabel phone_no_l=new JLabel("Mobile Number:");
	JLabel image_path_l=new JLabel("Image Path:");
	JTextField name_t=new JTextField(15);
	JTextField address_t=new JTextField(25);
	JTextField date_t=new JTextField(12);
	JTextField rent_t=new JTextField(10);
	JTextField area_t=new JTextField(10);
	JTextField phone_no_t=new JTextField(10);
	JTextField image_path_t=new JTextField(50);
	String get_id_s;
	void create_update_page()
	{
		update_page_panel.add(back);
		update_page_panel.add(enter_id);
		update_page_panel.add(get_id);
		update_page_panel.add(submit);
		update_page_frame.getContentPane().add(update_page_panel, BorderLayout.CENTER);
		update_page_frame.setSize(1366, 720);
		update_page_frame.setVisible(true);
		update_page_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		back.addActionListener(this);
		submit.addActionListener(this);
		get_id.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==back)
		{
			Main_page m=new Main_page();
			update_page_frame.setVisible(false);
			m.create_front_page();
		}
		if(e.getSource()==back_1)
		{
			after_update_frame.setVisible(false);
			new Update_page().create_update_page();;
		}
		if(e.getSource()==submit)
		{
			//update_page_frame.setVisible(false);
			get_id_s=get_id.getText();
			if(get_id_s.equals(""))
			{
				JOptionPane.showMessageDialog(null,"Please Enter the Id","Unsuccessfull",JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				try{
				update_record(Integer.parseInt(get_id_s));
				update_page_frame.setVisible(false);
				}
				catch(NumberFormatException e1)
				{
					JOptionPane.showMessageDialog(null,"Please enter a positive intger in text","Unsuccessfull",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		if(e.getSource()==add_image) // invokes a JFileChooser to fetch the path of image
		{
			JFileChooser choose_image = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images", "jpg", "gif");
		    choose_image.setFileFilter(filter);
		    choose_image.showOpenDialog(null);
		    image_path=choose_image.getSelectedFile().getAbsolutePath();
		    image_path_t.setText(image_path);
		}
		if(e.getSource()==go)
		{
			name=name_t.getText();
			address=address_t.getText();
			date=date_t.getText();
			rent=rent_t.getText();
			area=area_t.getText();
			phone_nu=phone_no_t.getText();
			long phone_no_long=Long.parseLong(phone_nu);
			int rent_i=Integer.parseInt(rent);
			String query="update propertydeal set person_name=?,address=?,deal_date=?,Id=?,rent=?,Area=?,phone_number=?,image_path=? where id =?;";
			try
			{
			LoadJDBCDriver.stmt=LoadJDBCDriver.connection.prepareStatement(query);
			LoadJDBCDriver.stmt.setString(1,name);		
			LoadJDBCDriver.stmt.setString(2,address);	
			LoadJDBCDriver.stmt.setString(3,date);	
			LoadJDBCDriver.stmt.setInt(4,Integer.parseInt(get_id_s));	
			LoadJDBCDriver.stmt.setInt(5,rent_i);	
			LoadJDBCDriver.stmt.setString(6,area);
			LoadJDBCDriver.stmt.setLong(7,phone_no_long);
			LoadJDBCDriver.stmt.setString(8,image_path);
			LoadJDBCDriver.stmt.setInt(9,Integer.parseInt(get_id_s));
			LoadJDBCDriver.stmt.executeUpdate();
			}
			catch(SQLException e1)
			{
				System.out.println(" Exception at updating query in actionPerformed method in update_page class");
			}
			JOptionPane.showMessageDialog(null, "Updated","Successfull!",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	void update_record(int id) // dedicated method for updating the record
	{
		try
		{
		ResultSet rs=(LoadJDBCDriver.connection.prepareStatement("Select * from propertydeal;")).executeQuery();
		while(rs.next())
		{
			if(rs.getInt(3)==id)
			{
				name=rs.getString(1);
				address=rs.getString(2);
				date=rs.getString(4);
				rent=rs.getString(5);
				area=rs.getString(6);
				phone_nu=rs.getString(7);
				image_path=rs.getString(8);
				flag=true;
			}
		}
		}
		catch(SQLException e)
		{
			System.out.print(" Exception in searching the id in update_record method in Update_page class");
		}
		if(flag)
		{
			after_update_frame.setTitle(" Update the record!");
			name_t.setText(name);
			address_t.setText(address);
			date_t.setText(date);
			rent_t.setText(rent);
			area_t.setText(area);
			image_path_t.setText(image_path);
			phone_no_t.setText(phone_nu);
			after_update_panel.add(back_1);
			after_update_panel.add(name_l);
			after_update_panel.add(name_t);
			after_update_panel.add(address_l);
			after_update_panel.add(address_t);
			after_update_panel.add(date_l);
			after_update_panel.add(date_t);
			after_update_panel.add(rent_l);
			after_update_panel.add(rent_t);
			after_update_panel.add(area_l);
			after_update_panel.add(area_t);
			after_update_panel.add(phone_no_l);
			after_update_panel.add(phone_no_t);
			after_update_panel.add(image_path_l);
			after_update_panel.add(image_path_t);
			after_update_panel.add(add_image);
			after_update_panel.add(go);
			add_image.addActionListener(this);
			back_1.addActionListener(this);
			go.addActionListener(this);
			name_t.addActionListener(this);
			address_t.addActionListener(this);
			date_t.addActionListener(this);
			rent_t.addActionListener(this);
			area_t.addActionListener(this);
			phone_no_t.addActionListener(this);
			after_update_frame.getContentPane().add(after_update_panel, BorderLayout.CENTER);
			after_update_frame.setSize(1366, 720);
			after_update_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			after_update_frame.setVisible(true);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Record Not found","Unsuccessfull",JOptionPane.INFORMATION_MESSAGE);
		}
	}
}