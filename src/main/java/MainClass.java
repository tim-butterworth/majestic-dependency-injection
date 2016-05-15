import com.reflecty.ObjectBuilderMachine;
import com.reflecty.builders.ObjectBuilderMachineBuilder;
import com.reflecty.configurations.NamespaceTypeMatcherImpl;
import com.reflecty.mainClasses.DatabaseConnector;
import com.reflecty.mainClasses.MyFancyController;
import com.reflecty.mainClasses.MySqlDatabaseConnector;
import com.reflecty.mainClasses.OracleDatabaseConnector;

public class MainClass {

    public static void main(String[] args) {
        ObjectBuilderMachineBuilder builder = new ObjectBuilderMachineBuilder();

        ObjectBuilderMachine objectBuilderMachine = builder
                .registerImplmentation(new NamespaceTypeMatcherImpl<>("oracle", DatabaseConnector.class), OracleDatabaseConnector.class)
                .registerImplmentation(new NamespaceTypeMatcherImpl<>("oracle", DatabaseConnector.class), MySqlDatabaseConnector.class)
                .build();

        MyFancyController instance = objectBuilderMachine.getInstance(MyFancyController.class);

        System.out.println(instance.queryTheDatabase());
    }
}
