package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Item;
import java.util.List;

public interface ItemService {

    List<Item> getItems();

    Item getItem(Item item);

    void save(Item item);

    void delete(Item item);

    void update(Item item);

    void clear();

    double getTotal();

    void facturar();
}