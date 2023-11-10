package ma.enset.comptecqrseventsoursing.cammon_api.eventes;

import lombok.Getter;

public abstract class BaseEvent<T>  {
    @Getter private T id;

    public BaseEvent(T id) {
        this.id = id;
    }
}
