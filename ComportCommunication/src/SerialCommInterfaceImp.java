import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class SerialCommInterfaceImp implements SerialComInterface {

	CommPortIdentifier portIdentifier;
	CommPort commPort;

	@Override
	public List<String> searchForPorts() {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();

		Enumeration portList = CommPortIdentifier.getPortIdentifiers();

		while (portList.hasMoreElements()) {
			CommPortIdentifier portId = (CommPortIdentifier) portList
					.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				list.add(portId.getName());
				System.out.println();
			}
		}
		return list;
	}


	@Override
	public boolean connect(SerialCommObject comObject) {
		// TODO Auto-generated method stub
		try {
			portIdentifier = CommPortIdentifier.getPortIdentifier(comObject
					.getComportName());
		} catch (NoSuchPortException e) {
			System.out.println(comObject.getComportName()
					+ " port is not exist!!");
			disconnect(comObject);
			System.out.println(e);
			e.printStackTrace();
		}

		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			try {
				commPort = portIdentifier.open(this.getClass().getName(), 2000);
			} catch (PortInUseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(" COMPORT:" + commPort + " portIdentifier:"
					+ portIdentifier);
			if (commPort instanceof SerialPort) {

				comObject.setSerialPort((SerialPort) commPort);
				System.out.println("portName=" + comObject.getComportName());
				System.out.println("BaudRate=" + comObject.getBaudRate());
				System.out.println("DataBits=" + comObject.getDataBITS());
				System.out.println("StopBits=" + comObject.getStopBITS());
				System.out.println("Parity=" + comObject.getParity());
				try {
					comObject.getSerialPort().setSerialPortParams(
							comObject.getBaudRate(), comObject.getDataBITS(),
							comObject.getStopBITS(), comObject.getParity());
					return true;
				} catch (UnsupportedCommOperationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	@Override
	public boolean initIOStream(SerialPort serialPort) {
		// TODO Auto-generated method stub
		
		return false;
	}


	@Override
	public void disconnect(SerialCommObject com) {
		// TODO Auto-generated method stub
		com.getSerialPort().removeEventListener();
		com.getSerialPort().close();
		com.setSerialPort(null);
		portIdentifier = null;
		commPort.close();
		commPort = null;
		System.gc();
	}




	/*
	 * @Override public void initListener(SerialPort serialPort) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */
}
