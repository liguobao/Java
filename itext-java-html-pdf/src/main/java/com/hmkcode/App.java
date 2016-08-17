package com.hmkcode;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class App 
{
    public static void main( String[] args ) throws DocumentException, IOException,Exception
    {
      // step 1
    	Document document = new Document();
        // step 2
    	PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("pdf.pdf"));
        // step 3
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream("index.html"));	
        //step 5

         document.close();

        System.out.println( "PDF Created!" );

        URL url = new URL("http://www.micmiu.com/os/linux/shell-dev-null");
        String urlsource = getURLSource(url);
        htmlCodeComeString(urlsource,"pdftest.pdf");


    }
    public static void  htmlCodeComeString(String htmlCode, String pdfPath) {
        Document doc = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(pdfPath));
            doc.open();
            // 解决中文问题
           // BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
         //   Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
          //  Paragraph t = new Paragraph(htmlCode, FontChinese);
            Paragraph t = new Paragraph(htmlCode);
            doc.add(t);
            doc.close();
            System.out.println("文档创建成功");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    public static String getURLSource(URL url) throws Exception    {
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5 * 1000);
        InputStream inStream =  conn.getInputStream();  //通过输入流获取html二进制数据
        byte[] data = readInputStream(inStream);        //把二进制数据转化为byte字节数据
        String htmlSource = new String(data);
        return htmlSource;
    }

    /** *//**
 * 把二进制流转化为byte字节数组
 * @param instream
 * @return byte[]
 * @throws Exception
 */
    public static byte[] readInputStream(InputStream instream) throws Exception {
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    byte[]  buffer = new byte[1204];
    int len = 0;
    while ((len = instream.read(buffer)) != -1){
        outStream.write(buffer,0,len);
    }
    instream.close();
    return outStream.toByteArray();
}

}
