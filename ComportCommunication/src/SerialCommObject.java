import gnu.io.SerialPort;


public class SerialCommObject{
	private String comportName;
	private int baudRate;
	private int dataBITS;
	private int stopBITS;
	private int parity;
	private String message = "";
	private SerialPort serialPort;
	
	public SerialCommObject(){
		super();
	}
	public SerialCommObject(String comportName,int baudRate, int dataBITS, int stopBITS,
			int parity) {
		super();
		this.comportName = comportName;
		this.baudRate = baudRate;
		this.dataBITS = dataBITS;
		this.stopBITS = stopBITS;
		this.parity = parity;
	}
	public String getComportName() {
		return comportName;
	}
	public void setComportName(String comportName) {
		this.comportName = comportName;
	}
	public int getBaudRate() {
		return baudRate;
	}
	public void setBaudRate(int baudRate) {
		this.baudRate = baudRate;
	}
	public int getDataBITS() {
		return dataBITS;
	}
	public void setDataBITS(int dataBITS) {
		this.dataBITS = dataBITS;
	}
	public int getStopBITS() {
		return stopBITS;
	}
	public void setStopBITS(int stopBITS) {
		this.stopBITS = stopBITS;
	}
	public int getParity() {
		return parity;
	}
	public void setParity(int parity) {
		this.parity = parity;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public SerialPort getSerialPort() {
		return serialPort;
	}
	public void setSerialPort(SerialPort serialPort) {
		this.serialPort = serialPort;
	}
	
}
