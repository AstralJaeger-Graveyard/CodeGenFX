package CodeGenFX.Barcode;

/**
 * A exception which will be thrown when invalid data is passed to the barcode generator
 */
public class BarcodeException extends Exception{

	public BarcodeException(){
		
		super();
	}
	
	public BarcodeException(String msg){
		
		super(msg);
	}

}
