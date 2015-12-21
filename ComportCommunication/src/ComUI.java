import gnu.io.SerialPort;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;

import javax.crypto.SealedObject;
import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

public class ComUI extends JFrame {
	private JPanel contentPane;
	private JTextField txBaudRate;
	private JTextField txDataBits;
	private JTextField txStopBits;
	private JTextField txtRwrn;

	private JComboBox<String> comboBox;
	public JTextArea textArea;
	public JScrollPane scrollPane;
	private JComboBox<String> textComPort;
	private SerialCommObject comportObject;
	private SerialCommInterfaceImp serialCom;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComUI frame = new ComUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ComUI() {
		setResizable(false);
		setTitle("RS232  測試");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 381, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblCom = new JLabel("Com Port");
		lblCom.setBounds(10, 23, 62, 15);
		contentPane.add(lblCom);
		textComPort = new JComboBox();
		textComPort.setModel(new DefaultComboBoxModel(
				(String[]) new SerialCommInterfaceImp().searchForPorts()
						.toArray(new String[0])));
		textComPort.setBounds(75, 20, 96, 21);
		contentPane.add(textComPort);

		JButton btRefreshCom = new JButton("Refresh ComportList");
		btRefreshCom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textComPort.setModel(new DefaultComboBoxModel(
						(String[]) new SerialCommInterfaceImp()
								.searchForPorts().toArray(new String[0])));
			}
		});
		btRefreshCom.setBounds(180, 20, 165, 21);
		contentPane.add(btRefreshCom);

		JLabel lblBaudRate = new JLabel("Baud Rate");
		lblBaudRate.setBounds(10, 51, 72, 15);
		contentPane.add(lblBaudRate);

		txBaudRate = new JTextField();
		txBaudRate.setText("9600");
		txBaudRate.setBounds(75, 45, 96, 21);
		contentPane.add(txBaudRate);
		txBaudRate.setColumns(10);

		txDataBits = new JTextField();
		txDataBits.setText("8");
		txDataBits.setBounds(75, 70, 96, 21);
		contentPane.add(txDataBits);
		txDataBits.setColumns(10);

		JLabel lblDataBytes = new JLabel("Data Bits");
		lblDataBytes.setBounds(10, 76, 72, 15);
		contentPane.add(lblDataBytes);

		JLabel lblStopBits = new JLabel("Stop Bits");
		lblStopBits.setBounds(10, 105, 62, 15);
		contentPane.add(lblStopBits);

		txStopBits = new JTextField();
		txStopBits.setText("1");
		txStopBits.setBounds(75, 99, 96, 21);
		contentPane.add(txStopBits);
		txStopBits.setColumns(10);

		JLabel lblParity = new JLabel("Parity");
		lblParity.setBounds(10, 132, 46, 15);
		contentPane.add(lblParity);
		comboBox = new JComboBox();

		comboBox.setModel(new DefaultComboBoxModel(new String[] { "NONE",
				"ODD", "EVEN", "MARK", "SPACE" }));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(75, 129, 96, 21);
		contentPane.add(comboBox);

		textArea = new JTextArea();
		textArea.setBounds(10, 275, 345, 200);
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(10, 275, 345, 200);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JToggleButton tglbtnNewToggleButton = new JToggleButton("Open");
		tglbtnNewToggleButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
				AbstractButton abstractButton = (AbstractButton) e.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				System.out.println("Action - selected=" + selected + "\n");
				
				if (selected) {
					System.out.println("open~~");
					if (!checkDataEmpty()) {
						System.out.println("set data empty.");
						return;
					}
					comportObject = new SerialCommObject(""
							+ textComPort.getSelectedItem(), Integer
							.parseInt(txBaudRate.getText()), Integer
							.parseInt(txDataBits.getText()), Integer
							.parseInt(txStopBits.getText()), comboBox
							.getSelectedIndex());

					serialCom = new SerialCommInterfaceImp();

					try {
						System.out.println(serialCom.connect(comportObject));
					} catch (NullPointerException xx) {
						System.out.println("port not exist!!!");
						// stopAccessData();
						System.out.println(xx);
						abstractButton.setSelected(false);
						return;
					}

				} else {
					System.out.println("stop~~");
					stopAccessData();
					abstractButton.setText("OPEN");
					abstractButton.setSelected(false);

				}
				abstractButton.setText("STOP");
				abstractButton.setSelected(true);
			}
		});
		tglbtnNewToggleButton.setBounds(244, 58, 80, 42);
		contentPane.add(tglbtnNewToggleButton);

	}

	private boolean checkDataEmpty() {
		if (textComPort.getSelectedIndex() < 0) {
			return false;
		}
		if (txBaudRate.getText().trim().length() <= 0) {
			return false;
		}
		if (txDataBits.getText().trim().length() <= 0) {
			return false;
		}
		if (txStopBits.getText().trim().length() <= 0) {
			return false;
		}
		if (comboBox.getSelectedIndex() < 0) {
			return false;
		}
		return true;
	}

	public void stopAccessData() {
		serialCom.disconnect(comportObject);
		serialCom = null;
		System.gc();
	}
}
