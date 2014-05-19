import java.io.*;
import com.jingwei.mobile.ocr.OCR;
public class demo
{
	public static void main(String [] args)
	{
		try
		{
			RandomAccessFile fis = new RandomAccessFile("./014.jpg", "r");
			System.out.println("len = " + fis.length());
			byte [] cbuf = new byte [(int)fis.length()];
			int clen = fis.read(cbuf);
			System.out.println("rlen = " + clen);
			OCR ocr = new OCR();
			if(ocr == null)
				return;

			if(!ocr.ocr_init("ocr_data/"))
			{
				System.out.println("init fail");
				return;
			}

			int status = ocr.ocr_readJpgBuffer(clen/2, cbuf);
			
			System.out.println("Status = "+ status);
			
/*			if(!ocr.ocr_read("/home/yunkai/Desktop/OCR_Server/java/000.webp"))
			{
				System.out.println("read file fail");
				return;
			}
*/
/*			if(!ocr.ocr_readWebpBuffer(clen, cbuf))
			{
				System.out.println("read buff fail");
				return;
			}
*/
			if(status > 0)
			{
				System.out.println("num = "+ocr.num);
				for(int i=0; i<ocr.num; i++)
				{
					System.out.println("attr["+i+"] = "+ocr.attr[i]+"\t"+ocr.strs[i]);
				}
			}
			
			ocr.ocr_free();
			fis.close();
		}
		catch(Exception e)
		{e.printStackTrace();}
	}
}
