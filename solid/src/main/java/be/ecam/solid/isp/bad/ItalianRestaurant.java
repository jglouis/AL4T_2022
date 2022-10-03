package be.ecam.solid.isp.bad;

import java.util.List;

//Don't
public interface ItalianRestaurant {
    void orderPasta();

    void orderPizza();

    List<String> getMenu();
}
