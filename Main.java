import java.util.ArrayList;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        // Crear algunos símbolos
        ArrayList<Integer> args1 = new ArrayList<>();
        args1.add(1);
        args1.add(2);
        Symbol symbol1 = new SymbolImpl(100, 1, "variable", args1);
        
        ArrayList<Integer> args2 = new ArrayList<>();
        args2.add(3);
        Symbol symbol2 = new SymbolImpl(200, 2, "function", args2);

        // Crear una tabla de símbolos
        SymbolTable globalTable = new SymbolTable(null);
        globalTable.addSymbol("x", symbol1);
        globalTable.addSymbol("myFunc", symbol2);
        
        // Probar la búsqueda en la tabla de símbolos
        Optional<Symbol> foundSymbol = globalTable.lookup("x");
        foundSymbol.ifPresent(s -> System.out.println("Encontrado: " + s.getCat() + " con dir: " + s.getDir()));
        
        Optional<Symbol> notFoundSymbol = globalTable.lookup("y");
        System.out.println("Símbolo 'y' encontrado: " + notFoundSymbol.isPresent());

        // Crear una pila de tablas de símbolos
        SymbolTableStack symbolTableStack = new SymbolTableStackImpl();
        symbolTableStack.push(globalTable);
        
        // Crear una nueva tabla de símbolos
        SymbolTable localTable = new SymbolTable(globalTable);
        localTable.addSymbol("y", new SymbolImpl(300, 1, "variable", new ArrayList<>()));
        symbolTableStack.push(localTable);

        // Probar la búsqueda de un símbolo en la pila
        Optional<SymbolTable> lookupResult = symbolTableStack.lookup("y");
        lookupResult.ifPresent(table -> {
            Optional<Symbol> symbol = table.lookup("y");
            symbol.ifPresent(s -> System.out.println("Símbolo 'y' encontrado en tabla local con dir: " + s.getDir()));
        });

        // Probar la búsqueda de un símbolo en la tabla base
        Optional<SymbolTable> baseTable = symbolTableStack.base();
        baseTable.ifPresent(table -> {
            Optional<Symbol> symbol = table.lookup("x");
            symbol.ifPresent(s -> System.out.println("Símbolo 'x' encontrado en tabla base con dir: " + s.getDir()));
        });

        // Probar la creación de tipos
        TypeTable typeTable = new TypeTableImpl();
        int intTypeId = typeTable.addType("int", 4, -1); // tipo básico
        int structTypeId = typeTable.addType("MyStruct", localTable); // tipo estructurado

        System.out.println("Tipo 'int' creado con ID: " + intTypeId);
        System.out.println("Tipo 'MyStruct' creado con ID: " + structTypeId);
        
        // Probar la recuperación de tipos
        Optional<Type> retrievedType = typeTable.getType(intTypeId);
        retrievedType.ifPresent(type -> System.out.println("Tipo recuperado: " + type.getName() + " con tamaño: " + type.getTam()));
    }
}
