package Graph;

import position.Position;
import Map.Map;

/**E' l'interfaccia di una position che all'elemento della position(base) associerà una mappa**/
public interface DecorablePosition<T> extends Position<T>, Map<Object, Object> {

}
