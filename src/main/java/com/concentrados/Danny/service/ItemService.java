package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Item;
import java.util.List;

public interface ItemService {
    public List<Item> gets();
    public void save(Item item);
    public void delete(Item item);
    public Item get(Item item);
    public void update(Item item);
    public double getTotal();
}