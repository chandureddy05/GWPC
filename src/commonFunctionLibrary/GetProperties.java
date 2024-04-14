package commonFunctionLibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class GetProperties {
	String str;
	private String filepath;
	String key="";
	String value="";
	
	public GetProperties(String filepath) {
		this.filepath=filepath;
	}
	public void ReadProperty()
	{
		String propval="";
		try
		{
			int check=0;
			while(check==0)
			{
				check=1;
				File cfgfile=new File(filepath);
				if (cfgfile.exists()) {
					FileInputStream fis=new FileInputStream(cfgfile);
					BufferedReader br=new BufferedReader(new InputStreamReader(fis));
					String line=null;
					while((line=br.readLine())!=null)
					{
						if (line.contains("=")) 
						{
							String key_value[]=line.split("=");
							key=key_value[0];
							value=key_value[1];
							GlobalData.ObjectRepo.put(key,value);
						}
					}
					
				}else{
					check=0;
					System.out.println("OR file is not available");
					System.out.println(filepath);
					break;
				}
				}
			}catch(IOException e)
		{
				e.printStackTrace();
		}
	}


}
