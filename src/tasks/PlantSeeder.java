package tasks;

public class PlantSeeder implements Runnable{
    @Override
    public void run() {

    }

//    private void seedPlants(Island island) {
//        Map<Integer, Map<Integer, ArrayList<Entity>>> islandMap = island.getIslandMap();
//        EntityFactory plantFactory = new PlantFactory();
//
//        islandMap.values()
//                .stream()
//                .flatMap(map -> map.values().stream())
//                .forEach(list -> {
//                    int limit = new Random().nextInt(0, Entities.PLANT.getCountOnCell());
//                    for (int i = 0; i < limit; i++) {
//                        list.add(plantFactory.createEntity(Entities.PLANT));
//                    }
//                });
//    }
}
