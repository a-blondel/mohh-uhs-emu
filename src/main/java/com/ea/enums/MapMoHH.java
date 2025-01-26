package com.ea.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum MapMoHH {

//    Modes:
//    1 - Domination = Capture and hold
//    2 - Infiltration = Capture The Flag
//    3 - Demolition
//    4 - Hold the Line = King Of the Hill
//    5 - Battlelines
//    8 - Deathmatch

    ITALY_CITY_DOM("MAP1", "115", "73", "1", "Italy City: Domination"),
    ITALY_MT_VILLAGE_INF("MAP2", "124", "7c", "2", "Mnt. Village Day: Infiltration"),
    ITALY_BEACH_DEM("MAP3", "131", "83", "3", "The Beachhead: Demolition"),
    ITALY_AIRFIELD_DEM("MAP4", "132", "84", "3", "The Airfield: Demolition"),
    ITALY_MT_VILLAGE_NIGHT_BL("MAP5", "153", "99", "5", "Mnt. Village Night: Battlelines"),
    ITALY_BEACH_DM("MAP6", "181", "b5", "8", "The Beachhead: Deathmatch"),
    ITALY_AIRFIELD_DM("MAP7", "182", "b6", "8", "The Airfield: Deathmatch"),
    ITALY_MT_VILLAGE_NIGHT_DM("MAP8", "183", "b7", "8", "Mnt. Village Night: Deathmatch"),
    ITALY_MT_VILLAGE_DM("MAP9", "184", "b8", "8", "Mnt. Village Day: Deathmatch"),
    ITALY_CITY_DM("MAP10", "185", "b9", "8", "Italy City: Deathmatch"),
    HOLLAND_CITY_DOM("MAP11", "211", "d3", "1", "Netherlands City: Domination"),
    HOLLAND_BRIDGE_NIGHT_DOM("MAP12", "216", "d8", "1", "Holland Bridge Dusk: Domination"),
    HOLLAND_STREET_INF("MAP13", "222", "de", "2", "Netherlands Street: Infiltration"),
    HOLLAND_BRIDGE_HTL("MAP14", "245", "f5", "4", "Holland Bridge Rain: Hold the Line"),
    HOLLAND_CITY_DM("MAP15", "281", "119", "8", "Netherlands City: Deathmatch"),
    HOLLAND_STREET_DM("MAP16", "282", "11a", "8", "Netherlands Street: Deathmatch"),
    HOLLAND_CHURCH_DM("MAP17", "283", "11b", "8", "Netherlands Church: Deathmatch"),
    HOLLAND_BRIDGE_DM("MAP18", "285", "11d", "8", "Holland Bridge Rain: Deathmatch"),
    HOLLAND_BRIDGE_NIGHT_DM("MAP19", "286", "11e", "8", "Holland Bridge Dusk: Deathmatch"),
    BELGIUM_FOREST_INF("MAP20", "324", "144", "2", "Belgium Forest: Infiltration"),
    BELGIUM_CASTLE_DEM("MAP21", "336", "150", "3", "Belgium Castle: Demolition"),
    BELGIUM_RIVER_NIGHT_HTL("MAP22", "341", "155", "4", "Belgium River Night: Hold the Line"),
    BELGIUM_RANCH_BL("MAP23", "352", "160", "5", "Belgium Ranch: Battlelines"),
    BELGIUM_RIVER_NIGHT_DM("MAP24", "381", "17d", "8", "Belgium River Night: Deathmatch"),
    BELGIUM_RANCH_DM("MAP25", "382", "17e", "8", "Belgium Ranch: Deathmatch"),
    BELGIUM_RIVER_DM("MAP26", "383", "17f", "8", "Belgium River Day: Deathmatch"),
    BELGIUM_FOREST_DM("MAP27", "384", "180", "8", "Belgium Forest: Deathmatch"),
    BELGIUM_CASTLE_DM("MAP28", "386", "182", "8", "Belgium Castle: Deathmatch");

    public final String code;
    public final String decimalId;
    public final String hexId;
    public final String modeId;
    public final String name;

    MapMoHH(String code, String decimalId, String hexId, String modeId, String name) {
        this.code = code;
        this.decimalId = decimalId;
        this.hexId = hexId;
        this.modeId = modeId;
        this.name = name;
    }

    public static String getMapNameByHexId(String hexId) {
        for (MapMoHH map : MapMoHH.values()) {
            if (map.hexId.equals(hexId)) {
                return map.name;
            }
        }
        return null;
    }

    private static final Map<String, List<String>> mapStatId = new HashMap<>();

    static {
        mapStatId.put("1", List.of("153", "183")); // IT Mnt. Village Night (Village B)
        mapStatId.put("2", List.of("131", "181")); // IT Beach
        mapStatId.put("3", List.of("132", "182")); // IT Airfield
        mapStatId.put("4", List.of("124", "184")); // IT Mnt. Village Day (Village A)
        mapStatId.put("9", List.of("115", "185")); // IT City
        mapStatId.put("10", List.of("211", "281")); // NL City
        mapStatId.put("11", List.of("216", "286")); // NL Holland Bridge Dusk (Bridge B)
        mapStatId.put("12", List.of("222", "282")); // NL Street
        mapStatId.put("13", List.of("245", "285")); // NL Bridge (Bridge A)
        mapStatId.put("16", List.of("283")); // NL Church
        mapStatId.put("19", List.of("324", "384")); // BE Forest
        mapStatId.put("20", List.of("336", "386")); // BE Castle
        mapStatId.put("21", List.of("383")); // BE River (River A)
        mapStatId.put("22", List.of("352", "382")); // BE Ranch
        mapStatId.put("25", List.of("341", "381")); // BE Belgium River Night (River B)
    }

    public static String getMapStatId(String key) {
        for (Map.Entry<String, List<String>> entry : mapStatId.entrySet()) {
            if (entry.getValue().contains(key)) {
                return entry.getKey();
            }
        }
        return "1";
    }
}
