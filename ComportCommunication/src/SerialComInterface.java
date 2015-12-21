import gnu.io.SerialPort;

import java.util.List;



public interface SerialComInterface {
	
	List<String> searchForPorts();
	boolean connect(SerialCommObject com);
	boolean initIOStream(SerialPort serialPort);
	//void initListener(SerialPort serialPort);
	void disconnect(SerialCommObject com);
	
}
