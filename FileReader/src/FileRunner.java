public class FileRunner
{
    public static void main(String[] args) throws Exception
    {
        try {
            FileReaderProgram read = new FileReaderProgram("SAT", "/Users/Om/Desktop/FileReader/Files");
            System.out.println(read.GetFinalList());
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
