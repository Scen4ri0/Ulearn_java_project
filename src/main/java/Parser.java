import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

public class Parser {

    public static void run(Connection cnt) throws SQLException, FileNotFoundException {
        var statement = cnt.createStatement();
        var printWriter = new PrintWriter("Data.txt");

        var resultSet = statement.executeQuery("SELECT printf(\"%.2f\", sum(networth)) as net, country  FROM forbes GROUP BY country;");

        while (resultSet.next()) {
            printWriter.println(resultSet.getString("country") + ";=" + resultSet.getString("net").replace('.', ','));
        }

        printWriter.close();

        System.out.println("Cамый молодой миллиардер из Франции, капитал которого превышает 10 млрд:");
        System.out.println(statement.executeQuery("select *, min(age) from forbes where country ='France' and networth > 10;").getString("name"));

        resultSet = statement.executeQuery("select name,source, max(networth)  from forbes where country ='United States' and industry='Energy ';");
        System.out.println("\nИмя и компания бизнесмена из США, имеющего самый большой капитал в сфере Energy:");
        System.out.println(resultSet.getString("name") + " " + resultSet.getString("source"));
        statement.close();
    }


}
