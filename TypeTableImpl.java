import java.util.HashMap;
import java.util.Optional;

public class TypeTableImpl implements TypeTable{
    private HashMap<Integer, Type> types = new HashMap<>();
    private int idActual = 0;

    @Override
    public int getTam(int id) {
        return types.containsKey(id) ? types.get(id).getTam() : -1;
    }

    @Override
    public int getItems(int id) {
        return types.containsKey(id) ? types.get(id).getItems() : -1;
    }

    @Override
    public String getName(int id) {
        return types.containsKey(id) ? types.get(id).getName() : null;
    }

    @Override
    public int getParenId(int id) {
        return types.containsKey(id) ? types.get(id).getParenId() : -1;
    }

    @Override
    public SymbolTable getParentStruct(int id) {
        return types.containsKey(id) ? types.get(id).getParentStruct() : null;
    }

    @Override
    public Optional<Type> getType(int id) {
        return Optional.ofNullable(types.get(id));
    }

    @Override
    public int addType(String name, int items, int parent) {
        Type type = new TypeImpl(name, (short) items, (short) 0, parent, null);
        types.put(idActual, type);
        return idActual++;
    }

    @Override
    public int addType(String name, SymbolTable parent) {
        Type type = new TypeImpl(name, (short) 0, (short) 0, -1, parent);
        types.put(idActual, type);
        return idActual++;
    }

}
