

import java.io.IOException;



public class Compile {
	
	
	public static void main(String[] args) {
		System.out.println(start());
	}


	/**
	 * Compile the application and get the return string that contains if it was successful.
	 * @return printed text while compilation.
	 */
	public static String start() {
		try {
			Runtime rt = Runtime.getRuntime();
			Process pCompile = rt.exec("sh compile.sh",null);
			pCompile.waitFor();
			return Exec.stringFromProc(pCompile);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return "Error during compiling!";
		}
	}
	

}
