package mygame;

import java.util.HashMap;
import java.util.Map;
import objects.GameObject;

public class World {

    private static World instance = null;

    public static World getInstance() {
            if (instance == null) {
                    instance = new World();
                    instance.init();
            }
            return instance;
    }

    private World() {}

    private Map<Integer, GameObject> entityMap = new HashMap<Integer, GameObject>();

    private void init() {
    }

    public void register(Integer id, GameObject entity) {
        entityMap.put(id, entity);
    }

    public GameObject getEntityById(int identifier) {
        return entityMap.get(identifier);
    }

    public float[] getSpawnLocation() {
        return new float[3];
    }

    public Map<Integer, GameObject> getEntityMap() {
        return entityMap;
    }

}
