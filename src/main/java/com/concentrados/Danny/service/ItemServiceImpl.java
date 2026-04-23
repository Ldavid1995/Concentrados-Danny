package com.concentrados.Danny.service;

import com.concentrados.Danny.domain.Item;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private HttpSession session;

    @Override
    public List<Item> gets() {
        List<Item> listaItems = (List<Item>) session.getAttribute("listaItems");
        if (listaItems == null) {
            listaItems = new ArrayList<>();
            session.setAttribute("listaItems", listaItems);
        }
        return listaItems;
    }

    @Override
    public void save(Item item) {
        boolean existe = false;
        List<Item> lista = gets(); // Usamos gets() para asegurar consistencia

        for (Item i : lista) {
            if (i.getIdProducto().equals(item.getIdProducto())) {
                i.setCantidad(i.getCantidad() + item.getCantidad());
                existe = true;
                break;
            }
        }

        if (!existe) {
            lista.add(item);
        }

        session.setAttribute("listaItems", lista);
    }

    @Override
    public void delete(Item item) {
        List<Item> listaItems = gets();
        listaItems.removeIf(i -> i.getIdProducto().equals(item.getIdProducto()));
        session.setAttribute("listaItems", listaItems);
    }

    @Override
    public Item get(Item item) {
        List<Item> listaItems = gets();
        for (Item i : listaItems) {
            if (i.getIdProducto().equals(item.getIdProducto())) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void update(Item item) {
        List<Item> listaItems = gets();
        for (Item i : listaItems) {
            if (i.getIdProducto().equals(item.getIdProducto())) {
                i.setCantidad(item.getCantidad());
                break;
            }
        }
        session.setAttribute("listaItems", listaItems);
    }

    /**
     * Implementación para la HU-24: Calcula el total de la proforma
     */
    @Override
    public double getTotal() {
        double total = 0;
        List<Item> listaItems = gets();
        for (Item i : listaItems) {
            total += (i.getPrecio() * i.getCantidad());
        }
        return total;
    }
}