import com.reflecty.ObjectBuilderMachine;
import com.reflecty.builders.ObjectBuilderMachineBuilder;
import com.reflecty.configurations.NamespaceTypeMatcherImpl;
import com.reflecty.mainClasses.*;

public class MainClass {

    public static void main(String[] args) {
        ObjectBuilderMachine objectBuilderMachine = configureObjectMachineBuilder();

        FancyControllerExample(objectBuilderMachine);
        LazyNodeExample(objectBuilderMachine);
    }

    private static ObjectBuilderMachine configureObjectMachineBuilder() {
        ObjectBuilderMachineBuilder builder = new ObjectBuilderMachineBuilder();

        return builder
                .registerImplmentation(new NamespaceTypeMatcherImpl<>("oracle", DatabaseConnector.class), OracleDatabaseConnector.class)
                .registerImplmentation(new NamespaceTypeMatcherImpl<>("mysql", DatabaseConnector.class), MySqlDatabaseConnector.class)
                .build();
    }

    private static void FancyControllerExample(ObjectBuilderMachine objectBuilderMachine) {
        MyFancyController instance = objectBuilderMachine.getInstance(MyFancyController.class);
        System.out.println(instance.queryTheDatabase());
    }

    private static void LazyNodeExample(ObjectBuilderMachine objectBuilderMachine) {
        LazyNodeChain lazyNodeChain = objectBuilderMachine.getInstance(LazyNodeChain.class);

        LazyNode root = lazyNodeChain.getLazyNode();
        LazyNode firstChildNode = root.getNextNode();
        LazyNode secondChildNode = firstChildNode.getNextNode();

        System.out.println(firstChildNode==secondChildNode);
    }
}
