import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.FileInputStream;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.util.*;
import java.io.*;
import org.apache.log4j.*;
public class FileReaderProgram
{
    private String keyword;
    private File directory;
    private ArrayList<File> finalList;
    private ArrayList<File> errorList;
    private int count;
    private String text;
    private boolean PDF = false;
    private boolean Word = false;
    private boolean Text = false;

    public FileReaderProgram(String keyword, String dir) throws Exception
    {
        //BasicConfigurator.configure();
        this.keyword = keyword;
        directory = new File(dir);

        if (directory.exists() == false){
            System.out.println("Directory doesn't exist");
            return;
        }

        finalList = new ArrayList<File>();
        errorList = new ArrayList<File>();
        count = 0;
        File[] directoryListing = directory.listFiles();
        String[] files = directory.list();
        String[] fileType = new String[directoryListing.length];


        for(int i = 0; i < directoryListing.length; i++)
        {
            fileType[i] = ""+files[i];
        }

        if (directoryListing != null)
        {
            for (File file : directoryListing)
            {
                if(fileType[count].contains("pdf"))
                {
                    readPDF(file);
                }
                if(fileType[count].contains("docx"))
                {
                    readWord(file);
                }
                if(fileType[count].contains("txt"))
                {
                    readText(file);
                }

                count++;
            }
        }

    }

    public void readPDF(File x) throws Exception
    {
        try {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            PDDocument search = PDDocument.load(x);
            text = pdfStripper.getText(search);
            if (text.contains(keyword)){
                finalList.add(x);
            }
            else if(text.length() <= 0){
                errorList.add(x);
            }
            search.close();
        }catch(Exception e){
            System.out.println(e);
            errorList.add(x);
        }
    }


    public void readWord(File x) throws Exception
    {
        try {
            XWPFDocument wordDoc = new XWPFDocument(new FileInputStream(x));

            XWPFWordExtractor we = new XWPFWordExtractor(wordDoc);
            text = we.getText();
            if (text.contains(keyword)) {
                finalList.add(x);
            }
            else if(text.length() <= 0){
                errorList.add(x);
            }

        }catch(Exception e){
            System.out.println(e);
            errorList.add(x);


        }
    }

    public void readText(File x) throws Exception
    {
        try {
            BufferedReader txt = new BufferedReader(new FileReader(x));

            text = txt.readLine();
            if (text.contains(keyword)) {
                finalList.add(x);
            }
            else if(text.length() <= 0){
                errorList.add(x);
            }


        }catch(Exception e){
            System.out.println(e);
            errorList.add(x);

        }

    }




    public String GetFinalList()
    {
        System.out.println("The following files contained the word " + keyword + " " + finalList);
        System.out.println("The following files caused an error " + errorList);
        return "";
    }
}
